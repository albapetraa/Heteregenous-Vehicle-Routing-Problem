package com.example.appson2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.appson2.algorithm.Node;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "proje.db";
    private static final int DATABASE_VERSION = 2;

    private static final String table_user = "CREATE TABLE IF NOT EXISTS users ( userId INTEGER PRIMARY KEY AUTOINCREMENT," +
            " userName VARCHAR(20), userPass VARCHAR(20));";
    private static final String table_adminTokens = "CREATE TABLE IF NOT EXISTS adminTokens ( adminToken VARCHAR(20) PRIMARY KEY );";
    private static final String table_duraklar = "CREATE TABLE IF NOT EXISTS duraklar(durakId INTEGER PRIMARY KEY, durakX REAL, durakY REAL, durakYolcu INTEGER);";
    private static final String table_aracBilgileri = "CREATE TABLE IF NOT EXISTS aracBilgileri(aracId INTEGER PRIMARY KEY, aracRota TEXT, aracCost INTEGER, aracYolcuSayisi INTEGER);";
    private static final String table_userData = "CREATE TABLE IF NOT EXISTS userData(userName TEXT PRIMARY KEY, userDurakId INTEGER, userDurakSecildiMi TEXT, userRotaOlustuMu TEXT);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(table_user);
        sqLiteDatabase.execSQL(table_adminTokens);
        sqLiteDatabase.execSQL(table_duraklar);
        sqLiteDatabase.execSQL(table_aracBilgileri);
        sqLiteDatabase.execSQL(table_userData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS adminTokens");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS duraklar");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS aracBilgileri");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS userData");

        onCreate(sqLiteDatabase);
    }

    public void addUser(String uName, String uPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", uName);
        values.put("userPass", uPass);

        long newRowId = db.insert("users", null, values);
        if (newRowId > -1)
            Log.i("DatabaseHelper", "User başarıyla kaydedildi");
        else
            Log.i("DatabaseHelper", "User kaydedilemedi");

        db.close();

    }

    public boolean userSearch(String userName, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM users WHERE userName='" + userName + "' AND userPass='" + userPassword + "';";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            Log.i("Loign", "Login başarılı");
            return true;
        }
        return false;
    }

    public boolean tokenSearch(String token) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM adminTokens WHERE adminToken='" + token + "';";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            Log.i("Admin", "Admin Girişi başarılı");
            return true;
        }
        return false;
    }

    public void yolcuMiktariDuzenle(int id, int miktar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("durakYolcu", miktar);
        db.update("duraklar", cv, "durakId" + "= ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Node> nodeal() {
        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM duraklar", null);
        ArrayList<Node> durakList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                Node n = new Node(cursorCourses.getInt(0), cursorCourses.getDouble(1), cursorCourses.getDouble(2), cursorCourses.getInt(3));
                durakList.add(n);

            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();

        return durakList;
    }

    public void kullaniciDuragiEkle(int id) {
        int n = 0;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM duraklar WHERE durakId=" + id + "", null);
        if (cursorCourses.moveToFirst()) {
            do {
                n = cursorCourses.getInt(3);

            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        cv.put("durakYolcu", n + 1);
        db.update("duraklar", cv, "durakId" + "= ?", new String[]{String.valueOf(id)});
    }

    public void aracBilgileriEkle(int id, String path, int cost, int yolcuSayisi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("aracId", id);
        values.put("aracRota", path);
        values.put("aracCost", cost);
        values.put("aracYolcuSayisi", yolcuSayisi);

        long newRowId = db.insert("aracBilgileri", null, values);
        if (newRowId > -1)
            Log.i("DatabaseHelper", "Araç Bilgileri başarıyla kaydedildi");
        else
            Log.i("DatabaseHelper", "Araç Bilgileri kaydedilemedi");

        db.close();
    }

    public void aracBilgileriSifirla() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS aracBilgileri");
        db.execSQL(table_aracBilgileri);

    }

    public void kullaniciUserDataEkle(String userName, int durakId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String table = "userData";
        String whereClause = "userName=?";
        String[] whereArgs = new String[]{String.valueOf(userName)};
        db.delete(table, whereClause, whereArgs);

        values.put("userName", userName);
        values.put("userDurakId", durakId);
        values.put("userDurakSecildiMi", "true");
        values.put("userRotaOlustuMu", "false");

        long newRowId = db.insert("userData", null, values);
        if (newRowId > -1)
            Log.i("DatabaseHelper", "UserData Bilgileri başarıyla kaydedildi");
        else
            Log.i("DatabaseHelper", "UserData Bilgileri kaydedilemedi");
    }

    public boolean userRotaBilgisiAl(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM userData WHERE userName='" + userName + "'", null);
        ArrayList<Node> durakList = new ArrayList<>();
        boolean durum = false;
        if (cursorCourses.moveToFirst()) {
            do {
                String x = cursorCourses.getString(3);
                if (x.equals("true")) {
                    durum = true;
                    return durum;
                }

            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return durum;
    }


    public ArrayList<Node> userRotaAl(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM userData WHERE userName='" + userName + "'", null);
        ArrayList<Node> durakList = new ArrayList<>();
        int userDurakId = 0;
        if (cursorCourses.moveToFirst()) {
            do {
                userDurakId = cursorCourses.getInt(1);

            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        Log.i("durak", String.valueOf(userDurakId));
        String path = "";
        Cursor aracBilgisi = db.rawQuery("SELECT aracRota FROM aracBilgileri WHERE aracRota LIKE'% " + userDurakId + " %'", null);
        if (aracBilgisi.moveToFirst()) {
            do {
                path = aracBilgisi.getString(0);
                Log.i("aracRotası", aracBilgisi.getString(0));

            } while (aracBilgisi.moveToNext());
        }
        String[] parts = path.split(" ");

        aracBilgisi.close();
        Node node0 = new Node(40.822213, 29.921675);
        durakList.add(node0);
        for (int i = 1; i < parts.length-1; i++) {
            int part = Integer.parseInt(parts[i]);
            durakList.add(durakAl(part));
        }
        durakList.add(node0);
        return durakList;
    }

    public Node durakAl(int durakId) {
        Node node = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM duraklar WHERE durakId=" + durakId + "", null);
        if (c.moveToFirst()) {
            do {
                node = new Node(c.getInt(0), c.getDouble(1), c.getDouble(2), c.getInt(3));
            } while (c.moveToNext());
        }
        return node;
    }
    public void rotalarOlustu(){
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 1; i <= 12; i++) {
            ContentValues cv = new ContentValues();
            cv.put("userRotaOlustuMu", "true");
            db.update("userData", cv, "userDurakId" + "= ?", new String[]{String.valueOf(i)});
        }

    }

}