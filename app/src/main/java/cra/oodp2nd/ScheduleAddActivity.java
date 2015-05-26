package cra.oodp2nd;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ScheduleAddActivity extends AbstractModelActivity implements ScheduleInterface {

    @Override
    protected void setSaveButton() {
        Button saveButton = (Button) findViewById(R.id.button_add_update);
        saveButton.setText("Save");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_schedule_title);
                String title = titleEditText.getText().toString();
                ContentValues addRowValue = new ContentValues();

                addRowValue.put("title", title);
                sqLiteDatabase.insert(TABLE_NAME, null, addRowValue) ;

                Intent intent = new Intent(getApplicationContext(), ScheduleViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpdateButton() {

    }

    @Override
    protected Activity getThisActivity() {
        return ScheduleAddActivity.this;
    }

    @Override
    protected int getLayout() {
        return R.id.edit_text_schedule_date;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add_update);
        setSaveButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule_add, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
