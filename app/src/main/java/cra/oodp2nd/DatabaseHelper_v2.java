package cra.oodp2nd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wbqd on 15. 6. 7..
 *
 * Class that wraps the common database operations.
 */
public class DatabaseHelper_v2 {
    private static final String dbName = "Project_DB";
    private static final String tableTask = "table_task";
    private static final String tableSchedule = "table_schedule";
    private static final String tableRecord = "table_record";
    private static final String tableSubtask = "table_subtask";
    private static final String tableMember = "table_member";
    private static final String tableMemberPresented = "table_member_presented";
    private static final String tableRecordFile = "table_record_file";
    private static DatabaseHelper_v2 mySQLiteOpenHelper;
    private SQLiteOpenHelper _openHelper;

    public static DatabaseHelper_v2 getInstance(Context context) {
        if (mySQLiteOpenHelper == null)
            mySQLiteOpenHelper = new DatabaseHelper_v2(context);
        return mySQLiteOpenHelper;
    }

    /**
     * Construct a new database helper object
     * @param context The current context for the application or activity
     */
    private DatabaseHelper_v2(Context context) {
        _openHelper = new MySQLiteOpenHelper(context, dbName, null, 1);
    }

    /**
     * This is an internal class that handles the creation of all database tables
     */
    class MySQLiteOpenHelper extends SQLiteOpenHelper {
        private MySQLiteOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, dbName, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists " + tableTask + " (id integer primary key, userId text,title text);");
            db.execSQL("create table if not exists " + tableSchedule + " (id integer primary key, userId text, title text, date date, time text);");
            db.execSQL("create table if not exists " + tableRecord + " (id integer primary key, userId text, title text,  date date, location text);");
            db.execSQL("create table if not exists " + tableSubtask + " (id integer primary key, userId text, title text, titleId integer," + "clear integer, state text);");
            db.execSQL("create table if not exists " + tableMember + " (id integer primary key, userId text unique, password text);");
            db.execSQL("create table if not exists " + tableMemberPresented + " (id integer primary key, recordID integer, title text, userId text);");
            db.execSQL("create table if not exists " + tableRecordFile + " (id integer primary key, recordId integer, fileName text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + tableTask);
            db.execSQL("DROP TABLE IF EXISTS " + tableSchedule);
            db.execSQL("DROP TABLE IF EXISTS " + tableRecord);
            db.execSQL("DROP TABLE IF EXISTS " + tableSubtask);
            db.execSQL("DROP TABLE IF EXISTS " + tableMember);
            db.execSQL("DROP TABLE IF EXISTS " + tableMemberPresented);
            db.execSQL("DROP TABLE IF EXISTS " + tableRecordFile);
            onCreate(db);
        }
    }

    /**
     * Return a cursor object with all rows in the table tableName
     * @param tableName The table name for method to lookup for
     * @return A cursor suitable for use in a SimpleCursorAdapter
     */
    public Cursor getAll(String tableName, String userId) {
        SQLiteDatabase db = _openHelper.getReadableDatabase();
        if (isNull(db)) return null;
        return db.rawQuery("SELECT * FROM " + tableName + " WHERE userId = ?", new String[]{userId});
    }

    /**
     * Return row values for a single task with the specified id
     * @param id The unique id for the row to fetch
     * @return All column values are stored as properties in the ContentValues object
     */
    public ContentValues getTask(long id) {
        SQLiteDatabase db = _openHelper.getReadableDatabase();
        if (isNull(db)) return null;
        ContentValues row = new ContentValues();
        Cursor cur = db.rawQuery("SELECT * FROM table_task WHERE id = ?", new String[]{String.valueOf(id)});
        if (cur.moveToNext()) {
            row.put("id", cur.getInt(0));
            row.put("userId", cur.getString(1));
            row.put("title", cur.getString(2));
        }
        cur.close();
        db.close();
        return row;
    }

    /**
     * Add a new task to the task table
     * @param userId The userId value for the new row
     * @param title The title value for the new row
     * @return The unique id of the newly added row
     */
    public long addTask(String userId, String title) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (isNull(db)) return 0;
        ContentValues row = new ContentValues();
        row.put("userId", userId);
        row.put("title", title);
        long id = db.insert(tableTask, null, row);
        db.close();
        return id;
    }

    /**
     * Delete the specified row from the database table
     * @param id The unique id for the row to delete
     */
    public void deleteTask(long id) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (isNull(db)) return;
        db.delete(tableTask, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Updates a row in the database table with new column values, without changing the unique id of the row.
     * @param id The unique id of the row to update
     * @param userId The user ID
     * @param title The new title value
     */
    public void updateTask(long id, String userId, String title) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (isNull(db)) return;
        ContentValues row = new ContentValues();
        row.put("title", title);
        row.put("userId", title);
        db.update(tableTask, row, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Test if SQLiteDatabase is null or not
     * @param db The SQLiteDatabase to test null or not
     * @return boolean The boolean value of test
     */
    private boolean isNull(SQLiteDatabase db) {
        if (db == null) {
            Log.e("error", "Problem to open a db");
            return true;
        }
        return false;
    }
}
