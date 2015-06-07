package cra.oodp2nd;

import android.app.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public abstract class AbstractModelActivity extends Activity {

    protected JobFactory jobFactory = new JobFactory();
    public static DatabaseHelper myDBHelper;
    protected SQLiteDatabase sqLiteDatabase;
    protected String userId;
    protected PickerMaker picker;
//    protected Button saveButton;
//    protected Button updateButton;

    protected abstract void setSaveButton();
    protected abstract void setUpdateButton();
    protected abstract Activity getThisActivity();
    protected abstract EditText getEditText();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDBHelper = DatabaseHelper.getInstance(this);
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        setuserId();
        picker = new PickerMaker();


        int which = LoginActivity.OptionInformaiton.option_color;
        // OK button, to Main Activity
        if (which == 0) { // Blue
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        } else if (which == 1) { // Green
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        } else if (which == 2) { // Purple
            getWindow().getDecorView().setBackgroundColor(Color.GRAY);
        } else { // default
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }
    }

    protected final void setuserId()
    {
        Bundle bundle = getIntent().getExtras();
        this.userId=bundle.getString("userId");

    }


}

