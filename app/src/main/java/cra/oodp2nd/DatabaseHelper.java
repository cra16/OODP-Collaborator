package cra.oodp2nd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DataBase = "Project_DB";

    private static DatabaseHelper myDBHelper =null;
    private SQLiteDatabase myDatabase=null;
    public static DatabaseHelper getInstance(Context context)//singleton
    {
        if(myDBHelper ==null)
            myDBHelper = new DatabaseHelper(context, DataBase, null,1);

        return myDBHelper;
    }

    private DatabaseHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory, version);
    }


    /*
        public long QueryInsert(String TableName,ContentValues RowValue)
        {
            return myDatabase.insert(TableName,null,RowValue);
        }

        public Cursor QuerySelect(String TableName, String[] columns, String selection, String[] selectionArgs, String GroupBy, String having, String orderBy)
        {
            return myDatabase.query(TableName, columns, selection, selectionArgs, GroupBy, having, orderBy);
        }

        public int QueryUpdate( String TableName, ContentValues UpRowValue,String whereClause, String[] whereArgs)
        {
            return myDatabase.update(TableName, UpRowValue, whereClause, whereArgs);
        }
        public int Querydelete(String TableName, String whereClause, String[] whereArgs)
        {
            return myDatabase.delete(TableName, whereClause, whereArgs);
        }
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists TaskTable (id integer primary key, title text);");
        db.execSQL("create table if not exists ScheduleTable (id integer primary key, title text);");
        db.execSQL("create table if not exists MeetingRecordTable (id integer primary key, title text) ;");
        db.execSQL("create table if not exists subTaskTable (id integer primary key, titleId integer, title text, " +
                "clear boolean, state text);");
        db.execSQL("create table if not exists memberTable (id integer primary key, userId text unique, password text);");
        db.execSQL("create table if not exists memberPresentedTable (id integer primary key, recordID integer, title text, userId text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TaskTable");
        db.execSQL("DROP TABLE IF EXISTS ScheduleTable");
        db.execSQL("DROP TABLE IF EXISTS MeetingRecordTable");
        db.execSQL("DROP TABLE IF EXISTS subTaskTable");
        db.execSQL("DROP TABLE IF EXISTS memberTable");
        db.execSQL("DROP TABLE IF EXISTS memberPresentedTable");
        onCreate(db);
    }
}