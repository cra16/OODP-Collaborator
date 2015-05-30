package cra.oodp2nd;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ScheduleUpdateActivity extends AbstractModelActivity implements ScheduleInterface {

    protected int id;

    @Override
    protected void setSaveButton() {

    }

    @Override
    protected void setUpdateButton() {
        Button updateButton = (Button) findViewById(R.id.button_add_update);
        updateButton.setText("Modify");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_schedule_title);
                EditText dateEditText = (EditText)findViewById(R.id.edit_text_schedule_date);


                String title = titleEditText.getText().toString();
                String datetext = dateEditText.getText().toString();
                String date = datetext.substring(0, datetext.indexOf(" ")>=0?datetext.indexOf(" "):datetext.length());
                String time = datetext.substring(datetext.indexOf(" ")+1);
                ContentValues updateRowValue = new ContentValues();

                updateRowValue.put("title", title);
                updateRowValue.put("date", date);
                updateRowValue.put("time", time);
                sqLiteDatabase.update(TABLE_NAME, updateRowValue, "id=" + id, null);

                finish();
            }
        });
    }

    @Override
    protected Activity getThisActivity() {
        return ScheduleUpdateActivity.this;
    }

    @Override
    protected int getLayout() {
        return R.id.edit_text_schedule_date;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add_update);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("p_id");
        getScheduleTitle();

        setTimePicker();
        setUpdateButton();
    }

    private void getScheduleTitle() {
        String[] columns = {"title","date","time"};

        Cursor result = sqLiteDatabase.query(TABLE_NAME, columns, "id=" + id, null, null, null, null);

        result.moveToFirst();
        String title = result.getString(0);
        String date = result.getString(1);
        String time = result.getString(2);

        EditText titleEditText = (EditText) findViewById(R.id.edit_text_schedule_title);
        EditText dateEditText = (EditText)findViewById(R.id.edit_text_schedule_date);
        titleEditText.setText(title);
        dateEditText.setText(date+" " + time);

        result.close();
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


}
