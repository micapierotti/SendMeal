package com.tripleM.sendmeal_lab01.room;

import android.content.Context;
import android.os.AsyncTask;
import androidx.room.*;
import com.tripleM.sendmeal_lab01.model.*;

import java.util.concurrent.Executor;

@Database(entities = {Plato.class,Pedido.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase DB;
    private static String DATABASE_NAME="database";

    public static AppDatabase getInstance(Context ctx) {
        if(DB==null) {
            DB = Room.databaseBuilder(ctx,
                    AppDatabase.class,
                    DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return DB;
    }

    public abstract PlatoDAORoom platoDao();
    public abstract PedidoDAORoom pedidoDao();
}