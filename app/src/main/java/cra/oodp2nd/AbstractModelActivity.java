package cra.oodp2nd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;


public abstract class AbstractModelActivity extends Activity {

    public static DatabaseHelper myDBHelper;
    protected SQLiteDatabase sqLiteDatabase;
    protected String userId;
//    protected Button saveButton;
//    protected Button updateButton;

    protected abstract void setSaveButton();
    protected abstract void setUpdateButton();
    protected abstract Activity getThisActivity();
    protected abstract int getLayout();
    protected final void setDatePicker()
    {

       final EditText dateEditText = (EditText)findViewById(getLayout());

        dateEditText.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();

            int myear=c.get(Calendar.YEAR);
            int mmonth=c.get(Calendar.MONTH);
            int mday=c.get(Calendar.DATE);

            @Override
            public void onClick(View v) {
                Dialog datepicker = new DatePickerDialog(getThisActivity(), DateListener, myear, mmonth, mday);
                datepicker.show();
            }
            DatePickerDialog.OnDateSetListener DateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myear = year;
                    mmonth = monthOfYear;
                    mday = dayOfMonth;
                    dateEditText.setText(String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth));
                }
            };

        });
    };
    protected final void setTimePicker()
    {
        final EditText dateEditText = (EditText)findViewById(getLayout());
        final String date = dateEditText.getText().toString();
        dateEditText.setOnClickListener(new View.OnClickListener() {

        int mhour;
        int mminute;
            @Override
            public void onClick(View v) {
                Dialog TimePicker = new TimePickerDialog(getThisActivity(), TimeListener,mhour,mminute,false);
                TimePicker.show();
            }
            TimePickerDialog.OnTimeSetListener TimeListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mhour = hourOfDay;
                    mminute = minute;

                    dateEditText.setText(date +" "+ mhour +" : " +mminute);
                }
            };
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDBHelper = DatabaseHelper.getInstance(this);
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        setuserId();

    }

    protected final void setuserId()
    {
        Bundle bundle = getIntent().getExtras();
        this.userId=bundle.getString("userId");
    }

}
