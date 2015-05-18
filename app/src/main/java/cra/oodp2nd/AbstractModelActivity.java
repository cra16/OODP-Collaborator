package cra.oodp2nd;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public abstract class AbstractModelActivity extends Activity {

    protected DatabaseHelper myDBHelper;
    protected String tableName;
    protected String createTableQuery;
//    protected Button saveButton;
//    protected Button updateButton;

    protected abstract void setTableName();
    protected abstract void setCreateTableQuery(String tableName);
    protected abstract void setSaveButton();
    protected abstract void setUpdateButton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDBHelper = DatabaseHelper.getInstance(this);
        setTableName();
        setCreateTableQuery(tableName);
    }

}
