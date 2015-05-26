package cra.oodp2nd;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class RecordAddActivity extends AbstractModelActivity implements RecordInterface {

    @Override
    protected void setSaveButton() {
        Button saveButton = (Button) findViewById(R.id.button_add_update);
        saveButton.setText("Save");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText titleEditText = (EditText) findViewById(R.id.edit_text_record_title);
                EditText nameEditText = (EditText)findViewById(R.id.edit_text_record_name);
                EditText dateEditText = (EditText)findViewById(R.id.edit_text_record_date);

                String title = titleEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String date= dateEditText.getText().toString();

                ContentValues addRowValue = new ContentValues();

                addRowValue.put("title", title);
                addRowValue.put("name", name);
                addRowValue.put("date", date);
                sqLiteDatabase.insert(TABLE_NAME, null, addRowValue) ;

                Intent intent = new Intent(getApplicationContext(), RecordViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpdateButton() {

    }

    @Override
    protected void setDatePicker() {
        final EditText dateEditText = (EditText)findViewById(R.id.edit_text_record_date);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();

            int myear=c.get(Calendar.YEAR);
            int mmonth=c.get(Calendar.MONTH);
            int mday=c.get(Calendar.DATE);

            @Override
            public void onClick(View v) {

                Dialog datepicker = new DatePickerDialog(RecordAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myear = year;
                        mmonth = monthOfYear;
                        mday = dayOfMonth;
                        dateEditText.setText(String.valueOf(year) + "."+ String.valueOf(monthOfYear+1) + "." + String.valueOf(dayOfMonth));
                    }
                }, myear, mmonth, mday);

                datepicker.show();
            }


        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_add_update);
        setDatePicker();
        setSaveButton();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_add, menu);
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
