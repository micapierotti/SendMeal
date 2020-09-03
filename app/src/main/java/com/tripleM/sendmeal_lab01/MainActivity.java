package com.tripleM.sendmeal_lab01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    EditText etNombre,etPassword,etPassword2, etEmail, etNumeroTarjeta, etCCV,etMes,etAnio,etCBU,etAlias;
    Button btnRegistrar;
    RadioGroup rg1;
    RadioButton rb1,rb2;
    Switch sCargaInicial;
    CheckBox cbAcepto;
    SeekBar sbMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.nombre);
        etPassword = findViewById(R.id.contrasenia);
        etPassword2 = findViewById(R.id.contrasenia2);
        etEmail = findViewById(R.id.email);
        etNumeroTarjeta = findViewById(R.id.nrotarjeta);
        etCCV = findViewById(R.id.ccv);
        etMes = findViewById(R.id.fecha_mes);
        etAnio = findViewById(R.id.fecha_anio);
        etCBU = findViewById(R.id.cbu);
        etAlias = findViewById(R.id.cbualias);
        btnRegistrar = findViewById(R.id.registrar);
        rg1 = findViewById(R.id.rg1);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        sCargaInicial = findViewById(R.id.carga_inicial);
        sbMonto = findViewById(R.id.barra);
        cbAcepto = findViewById(R.id.term_cond);

        etCCV.setEnabled(false);
        etMes.setEnabled(false);
        etAnio.setEnabled(false);
        sbMonto.setVisibility(View.GONE);
        btnRegistrar.setEnabled(false);

        RadioGroup.OnCheckedChangeListener radioListenerRG1 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                activarRegistrar(etNombre,etPassword,etPassword2,etEmail,etCCV,etMes,etAnio,cbAcepto,btnRegistrar);
                switch (i){
                    case R.id.rb1:
                        rb2.setEnabled(false);
                        break;
                    case R.id.rb2:
                        rb1.setEnabled(false);
                        break;
                }
            }
        };

        etNumeroTarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                if(cs.length()!=0){
                    etCCV.setEnabled(true);
                    etMes.setEnabled(true);
                    etAnio.setEnabled(true);

                    activarRegistrar(etNombre,etPassword,etPassword2,etEmail,etCCV,etMes,etAnio,cbAcepto,btnRegistrar);
                }else{
                    etCCV.setEnabled(false);
                    etCCV.setText("");
                    etMes.setEnabled(false);
                    etMes.setText("");
                    etAnio.setEnabled(false);
                    etAnio.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void activarRegistrar(EditText etNombre,EditText etPassword,EditText etPassword2,EditText  etEmail, EditText  etCCV,
                                 EditText etMes,EditText etAnio, CheckBox cbAcepto,Button registrar) {
        if(etNombre.getText().toString().compareTo("")!=0&&etPassword.getText().toString().compareTo("")!=0
                &&etPassword2.getText().toString().compareTo("")!=0&&etEmail.getText().toString().compareTo("")!=0
                &&etCCV.getText().toString().compareTo("")!=0&&etMes.getText().toString().compareTo("")!=0
                &&etAnio.getText().toString().compareTo("")!=0&&cbAcepto.isChecked()){
            registrar.setEnabled(true);
        }else{
            registrar.setEnabled(false);
        }

    }
}