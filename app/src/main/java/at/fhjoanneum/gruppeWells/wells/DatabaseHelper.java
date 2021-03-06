package at.fhjoanneum.gruppeWells.wells;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("ALL")
class DatabaseHelper extends SQLiteOpenHelper {

    static final String LIST_TABLE = "Liste";
    private static final String USER_TABLE = "Benutzer";
    private static final String DRINK_TABLE = "Trinkverhalten";
    private static final String COL_EMAIL = "Email";
    private static final String COL_PASS = "Passwort";
    private static final String COL_BENUTZERNAME = "Benutzername";
    private static final String COL_AGE = "Age";
    private static final String COL_GEWICHT = "Gewicht";
    private static final String COL_GROESSE = "Groesse";
    private static final String COL_IST = "mlIST";
    private static final String COL_SOLL = "mlSOLL";
    private static final String COL_DATE = "Datum";
    private static final String DATABASE_NAME = "MyExternalDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_PATH; //= "/data/data/com.example.gruppeWells.wells/databases/";
    private final Context context;
    private SQLiteDatabase db;


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
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createDb() {
        boolean dbExist = checkDbExist();

        if (!dbExist) {
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private boolean checkDbExist() {
        SQLiteDatabase sqLiteDatabase = null;

        try {
            String path = DATABASE_PATH + DATABASE_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception ignored) {
        }

        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            return true;
        }

        return false;
    }

    private void copyDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);

            String outFileName = DATABASE_PATH + DATABASE_NAME;

            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] b = new byte[1024];
            int length;

            while ((length = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SQLiteDatabase openDatabase() {
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public boolean checkUserExist(String email, String password) {
        String[] columns = {"Email"};
        db = openDatabase();

        String selection = "Email=? and Passwort = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        close();

        return count > 0;
    }

    //nanni
    public boolean insertdata(String email, String pass, String name, String age, String weight, String height) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PASS, pass);
        contentValues.put(COL_BENUTZERNAME, name);
        contentValues.put(COL_AGE, age);
        contentValues.put(COL_GEWICHT, weight);
        contentValues.put(COL_GROESSE, height);
        long ins = db.insert(DatabaseHelper.USER_TABLE, null, contentValues);
        System.out.println("successful - db insert " + String.valueOf(ins));
        return ins != 1;
    }

    public boolean insertml(String mail, String istml, String sollml, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EMAIL, mail);
        contentValues.put(COL_IST, istml);
        contentValues.put(COL_SOLL, sollml);
        contentValues.put(COL_DATE, date);

        long ins = db.insert(DatabaseHelper.DRINK_TABLE, null, contentValues);
        System.out.println("successful - db insert " + String.valueOf(ins));
        return ins != 1;

    }

    public boolean checkmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Benutzer where Email=?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
        //        cursor.close();
//        return cursor.getCount() <= 0;
    }


    public boolean checkpass(String email, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Benutzer where Email=? and Passwort =?", new String[]{email, pass});
        if (cursor.getCount() > 0) return true;
        else return false;
    }


    public boolean updateData(String email, String age, String weight, String height) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AGE, age);
        contentValues.put(COL_GEWICHT, weight);
        contentValues.put(COL_GROESSE, height);
        db.update(USER_TABLE, contentValues, "Email = ?", new String[]{email});
        return true;
    }


    public Integer deleteFroeignKeyDataListe(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "Email=?";
        return db.delete(LIST_TABLE, selection, new String[]{email});

    }

    public Integer deleteFroeignKeyDataTrinken(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "Email=?";
        return db.delete(DRINK_TABLE, selection, new String[]{email});

    }

    public Integer deleteData(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "Email=? and Passwort = ?";
        return db.delete(USER_TABLE, selection, new String[]{email, pass});

    }

// --Commented out by Inspection START (12.07.2018 11:17):
//    public Cursor getAllData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT NameTrinkbrunnen FROM " + LIST_TABLE, null);
//        return res;
//
//
//    }
// --Commented out by Inspection STOP (12.07.2018 11:17)

    public Cursor getBenutzerdataalter(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        //noinspection UnnecessaryLocalVariable
        Cursor res = db.rawQuery("SELECT Age FROM Benutzer where Email=?", new String[]{email});
        return res;
    }

    public Cursor getMl(String email, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT mlIst FROM Trinkverhalten where Email=? and Datum=?", new String[]{email, date});
        return res;

    }

    public Cursor getBenutzerdatagroesse(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Groesse FROM Benutzer where Email=?", new String[]{email});
        return res;
    }

    public Cursor getBenutzerdatagewicht(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Gewicht FROM Benutzer where Email=?", new String[]{email});
        return res;
    }


    public boolean PWupdate(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PASS, pass);
        db.update(USER_TABLE, contentValues, "Email = ?", new String[]{email});
        return true;
    }
}