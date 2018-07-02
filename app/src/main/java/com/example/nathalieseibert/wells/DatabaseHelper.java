package com.example.nathalieseibert.wells;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyExternalDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    SQLiteDatabase db;

    private static String DATABASE_PATH; //= "/data/data/com.example.nathalieseibert.wells/databases/";
    public static final String USER_TABLE = "Benutzer";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.context = context;
        createDb();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createDb(){
        boolean dbExist = checkDbExist();

        if(!dbExist){
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private boolean checkDbExist(){
        SQLiteDatabase sqLiteDatabase = null;

        try{
            String path = DATABASE_PATH + DATABASE_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception ex){
        }

        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
            return true;
        }

        return false;
    }

    private void copyDatabase(){
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);

            String outFileName = DATABASE_PATH + DATABASE_NAME;

            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] b = new byte[1024];
            int length;

            while ((length = inputStream.read(b)) > 0){
                outputStream.write(b, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private SQLiteDatabase openDatabase(){
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public  void close(){
        if(db != null){
            db.close();
        }
    }

    public boolean checkUserExist(String email, String password){
        String[] columns = {"Email"};
        db = openDatabase();

        String selection = "Email=? and Passwort = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    //nanni
    public boolean insertdata(String email, String pass, String name, String age, String weight, String height) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("Passwort", pass);
        contentValues.put("Benutzername", name);
        contentValues.put("Age", age);
        contentValues.put("Gewicht", weight);
        contentValues.put("Groesse", height);
        long ins = db.insert(DatabaseHelper.USER_TABLE, null, contentValues);
        System.out.println("successful - db insert " + String.valueOf(ins));
        if(ins == 1) return false;
        else return true;
    }

    public boolean checkmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Benutzer where Email=?", new String[] {email});
        if(cursor.getCount()>0) return false;
        else return true;
    }

    public boolean updateData(String email, String age, String weight, String height){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
       // contentValues.put("Passwort", pass);
       // contentValues.put("Benutzername", name);
        contentValues.put("Age", age);
        contentValues.put("Gewicht", weight);
        contentValues.put("Groesse", height);
        db.update(USER_TABLE, contentValues, "Email = ?",new String[] {email});
        return true;
    }


}