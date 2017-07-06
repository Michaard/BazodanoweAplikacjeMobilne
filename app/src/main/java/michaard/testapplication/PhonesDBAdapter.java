package michaard.testapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PhonesDBAdapter {
    private static final String DEBUG_TAG="SQLiteTodoManager";
    private SQLiteDatabase database;
    private Context context;
    private DatabaseHelper dbHelper;

    private static final String DB_NAME="phones_database.db";
    private static final int DB_VERSION=2;
    private static final String DB_TABLE="Telefony";

    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_NAME = "nazwa";
    public static final String KEY_NAME_OPTIONS = "TEXT NOT NULL";
    public static final int NAME_COLUMN = 2;
    public static final String KEY_BRAND = "marka";
    public static final String KEY_BRAND_OPTIONS = "TEXT NOT NULL";
    public static final int BRAND_COLUMN=1;

    private static final String DB_CREATE_TABLE=
            "CREATE TABLE " + DB_TABLE + " (" + KEY_ID + " " + ID_OPTIONS
                    + ", " + KEY_BRAND + " " + KEY_BRAND_OPTIONS
                    + ", " + KEY_NAME + " " + KEY_NAME_OPTIONS + " )";

    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+DB_TABLE;

    PhonesDBAdapter(Context context){
        this.context=context;
    }

    public void open(){
        dbHelper=new DatabaseHelper(context,DB_NAME,null,DB_VERSION);
        try {
            database = dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            database=dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        dbHelper.close();
    }

    public long insertPhone(String brand,String name){
        ContentValues newValues=new ContentValues();
        newValues.put(KEY_BRAND,brand);
        newValues.put(KEY_NAME,name);
        return database.insert(DB_TABLE,null,newValues);
    }

    public boolean updateData(Phone phone){
        int id=phone.getId();
        String brand=phone.getBrand();
        String name=phone.getName();
        return updateData(id,brand,name);
    }

    public boolean updateData(int id,String brand,String name){
        String where=KEY_ID+"="+id;
        ContentValues updateVales=new ContentValues();
        updateVales.put(KEY_BRAND,brand);
        updateVales.put(KEY_NAME,name);
        return database.update(DB_TABLE,updateVales,where,null)>0;
    }

    public boolean deleteData(int id){
        String where=KEY_ID+"="+id;
        return database.delete(DB_TABLE,where,null)>0;
    }

    public void deleteAllData(){
        database.execSQL(DROP_TABLE);
    }

    public Cursor getAllData(){
        String[] columns={KEY_ID,KEY_BRAND,KEY_NAME};
        return database.query(DB_TABLE,columns,null,null,null,null,null);
    }

    public Phone getData(int id){
        String[] columns={KEY_ID,KEY_BRAND,KEY_NAME};
        String where=KEY_ID+"="+id;
        Cursor cursor=database.query(DB_TABLE,columns,where,null,null,null,null);
        Phone phone=null;
        if(cursor!=null && cursor.moveToFirst()){
            String brand=cursor.getString(BRAND_COLUMN);
            String name=cursor.getString(NAME_COLUMN);
            phone=new Phone(id,brand,name);
        }
        return phone;
    }

    public class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context,name,factory,version);
        }

        public void onCreate(SQLiteDatabase database){
            database.execSQL(DB_CREATE_TABLE);

            Log.d(DEBUG_TAG,"Database creating...");
            Log.d(DEBUG_TAG,"Table "+DB_TABLE+" ver."+DB_VERSION+" created");
        }

        public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVerson){
            database.execSQL(DROP_TABLE);

            Log.d(DEBUG_TAG,"Database updating...");
            Log.d(DEBUG_TAG,"Table "+DB_TABLE+" updated from ver."+oldVersion+" to ver."+newVerson);
            Log.d(DEBUG_TAG,"All data is lost.");

            onCreate(database);
        }
    }
}
