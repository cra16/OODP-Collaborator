package cra.oodp2nd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String dbInstance = "Project_DB";
    private static DatabaseHelper myDBHelper;

    public static DatabaseHelper getInstance(Context context)//singleton
    {

        if(myDBHelper ==null)
            myDBHelper = new DatabaseHelper(context, dbInstance, null,1);

        return myDBHelper;
    }

    private DatabaseHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists table_task (id integer primary key, userId text,title text);");
        db.execSQL("create table if not exists table_schedule(id integer primary key, userId text, title text, date date, time text);");
        db.execSQL("create table if not exists table_record (id integer primary key, userId text, title text,  date date, location text);");
        db.execSQL("create table if not exists table_subtask (id integer primary key, userId text, title text, titleId integer," + "clear integer, state text);");
        db.execSQL("create table if not exists table_member (id integer primary key, userId text unique, password text);");
        db.execSQL("create table if not exists table_member_presented (id integer primary key, recordID integer, title text, userId text);");
        db.execSQL("create table if not exists table_record_file (id integer primary key, recordId integer, fileName text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS table_task");
        db.execSQL("DROP TABLE IF EXISTS table_schedule");
        db.execSQL("DROP TABLE IF EXISTS table_record");
        db.execSQL("DROP TABLE IF EXISTS table_subtask");
        db.execSQL("DROP TABLE IF EXISTS table_member");
        db.execSQL("DROP TABLE IF EXISTS table_member_presented");
        db.execSQL("DROP TABLE IF EXISTS table_record_file");
        onCreate(db);
    }
}