package cra.oodp2nd;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TaskAddActivity extends AbstractModelActivity implements TaskInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add_update);
        setSaveButton();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule_update_activty, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id== android.R.id.home) {

            // NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setSaveButton() {
        Button saveButton = (Button) findViewById(R.id.button_add_update);
        saveButton.setText("Save");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_task_title);
                EditText nameEditText = (EditText) findViewById(R.id.edit_text_task_name);

                String title = titleEditText.getText().toString();
                String name = nameEditText.getText().toString();
                ContentValues addRowValue = new ContentValues();

                addRowValue.put("title", title);
                addRowValue.put("userId",userId);
                addRowValue.put("name", name);

                sqLiteDatabase.insert(TABLE_NAME, null, addRowValue);


                finish();
            }
        });
    }





    @Override
    protected void setUpdateButton() {

    }

    @Override
    protected Activity getThisActivity() {
        return TaskAddActivity.this;
    }

    @Override
    protected int getLayout() {
        return 0;
    }



}
