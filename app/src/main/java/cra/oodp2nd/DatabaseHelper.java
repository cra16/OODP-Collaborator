package cra.oodp2nd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wbqd on 15. 5. 16..
 */
public class DatabaseHelper {
    static final String DataBase = "Project_DB";


    public static DatabaseHelper myDBHelper =null;
    private SQLiteDatabase myDatabase=null;
    public static DatabaseHelper getInstance(Context context)//singleton
    {
        if(myDBHelper ==null) {
            myDBHelper = new DatabaseHelper(context);
        }

        return myDBHelper;
    }

    private DatabaseHelper(Context context)
    {
        myDatabase=context.openOrCreateDatabase(DataBase,context.MODE_PRIVATE,null);
    }


    public void CreateTable(String SQL)
    {
        try{
            myDatabase.execSQL(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    public void DatabaseDestory()
    {
        myDatabase.close();
    }
}
