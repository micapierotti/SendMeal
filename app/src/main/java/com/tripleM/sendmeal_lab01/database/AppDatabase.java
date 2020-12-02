package com.tripleM.sendmeal_lab01.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.room.*;
import com.tripleM.sendmeal_lab01.model.*;

@Database(entities = {Plato.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AsyncTask databaseWriteExecutor;
    public abstract PlatoDao platoDao();
    private static Object DB;

    private static AppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
               "db")
                .allowMainThreadQueries().build();
    }

    public static AppDatabase getInstance(Context ctx) {
        if(DB==null){
            DB = buildDatabaseInstance(ctx);
        }
        return (AppDatabase) DB;
    }
}