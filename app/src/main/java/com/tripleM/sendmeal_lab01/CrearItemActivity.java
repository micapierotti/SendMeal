package com.tripleM.sendmeal_lab01;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tripleM.sendmeal_lab01.model.Plato;
import com.tripleM.sendmeal_lab01.retrofit.PlatoRepositoryRest;
import com.tripleM.sendmeal_lab01.room.AccionesDAO;
import com.tripleM.sendmeal_lab01.room.AppRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

public class CrearItemActivity extends AppCompatActivity implements AppRepository.OnResultCallback {

    private Toolbar toolbar;
    private TextView imagen;
    private EditText titulo, descripcion, precio, calorias;
    private Button guardar,tomarFoto,seleccionarFoto;;
    private ImageView fotoPlato;
    private Plato plato;
    private AppRepository platoRoom;
    private PlatoRepositoryRest platoRest;
    private FirebaseStorage storage;
    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);

        titulo = findViewById(R.id.tituloPlato);
        descripcion = findViewById(R.id.descripcion);
        precio = findViewById(R.id.precio);
        calorias = findViewById(R.id.calorias);
        guardar = findViewById(R.id.guardarPlato);
        fotoPlato = findViewById(R.id.ivFoto);
        tomarFoto = findViewById(R.id.btFotoPlato);
        seleccionarFoto = findViewById(R.id.btSeleccionarFoto);
        imagen = findViewById(R.id.tvImagenPlato);
        fotoPlato = findViewById(R.id.ivFoto);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Crear Plato");
        setSupportActionBar(toolbar);

        //Persistencia con Room
        platoRoom = new AppRepository(this.getApplication(),this);

        //Persistencia con Retrofit
        platoRest=new PlatoRepositoryRest();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = "";
                String mensaje_final;

                if(titulo.getText().toString().length()==0) mensaje += "El titulo está vacío. \n";
                if(descripcion.getText().toString().length()==0) mensaje += "La descripción está vacía. \n";
                if(precio.getText().toString().length()==0) mensaje += "El precio está vacío. \n";
                if(calorias.getText().toString().length()==0) mensaje += "Las calorias están vacías. \n";

                if(mensaje.length()==0){
                    plato = new Plato(titulo.getText().toString(),descripcion.getText().toString(),Double.parseDouble(precio.getText().toString()),Integer.parseInt(calorias.getText().toString()));

                    //Guardar con Room
                    platoRoom.insertar(plato);

                    //Guardar con Retrofit
                    platoRest.crearPlato(plato,mHandler);

                    Intent iresult = new Intent();
                    iresult.putExtra("plato","Plato creado exitosamente.");
                    setResult(Activity.RESULT_OK,iresult);
                    finish();
                }else {
                    mensaje_final=mensaje.substring(0,mensaje.length()-1);
                    Toast toast1 = Toast.makeText(getApplicationContext(), mensaje_final, Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarCamara();
            }});
        seleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria();
            }});
    }


    private void lanzarCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }

    private void abrirGaleria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }
    private void guardarFoto() {
        // Creamos una referencia a nuestro Storage
        StorageReference storageRef = storage.getReference();
        // Creamos una referencia a 'images/plato_id.jpg'
        StorageReference platosImagesRef = storageRef.child("images/plato_id.jpg");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMARA_REQUEST || requestCode == GALERIA_REQUEST) && resultCode == RESULT_OK) {
            if(requestCode== CAMARA_REQUEST){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                fotoPlato.setImageBitmap(imageBitmap);
                byte[] data2 = baos.toByteArray(); // Imagen en arreglo de bytes
                //guardarFotoPlato(data2);
            }
            if(requestCode== GALERIA_REQUEST){
                try {
                    Uri imageUri = data.getData();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    fotoPlato.setImageBitmap(selectedImage);
                    byte[] data2 = baos.toByteArray(); // Imagen en arreglo de bytes
                    //guardarFotoPlato(data2);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.atras:
                finish();
                break;
            case R.id.BackButton:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onResult(List result) {
    }

    @Override
    public void onResultId(Object result) {
    }

    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<CrearItemActivity> mActivity;

        public MyHandler(CrearItemActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CrearItemActivity activity = mActivity.get();
            if (activity != null) {
                Bundle data = msg.getData();
                AccionesDAO evento = AccionesDAO.valueOf(data.getString("accion"));
                switch (evento){
                    case CREAR_PLATO:
                        Plato plato = data.getParcelable("plato");
                        System.out.println("PLATO: "+plato);
                        Toast.makeText(activity,"Plato creado",Toast.LENGTH_LONG).show();
                        activity.finish();
                        break;
                    case LISTAR_PLATOS:
                        List<Plato> platos = data.getParcelable("platos");
                        System.out.println("PLATOS: "+platos);
                        Toast.makeText(activity,"Platos listados",Toast.LENGTH_LONG).show();
                        activity.finish();
                        break;
                    case ERROR:
                        String error = data.getParcelable("error");
                        System.out.println("ERROR: "+error);
                        Toast.makeText(activity,"Error de API REST",Toast.LENGTH_LONG).show();
                        //activity.finish();
                        break;
                }
            }
        }
    }
}
