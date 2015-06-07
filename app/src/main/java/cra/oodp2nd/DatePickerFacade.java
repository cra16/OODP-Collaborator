package cra.oodp2nd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by 현우 on 2015-06-08.
 */
public class DatePickerFacade implements PickerFacade {

    @Override
    public void setPicker(final Activity act, final EditText edit) {

            edit.setOnClickListener(new View.OnClickListener() {
                Calendar c = Calendar.getInstance();

                int myear=c.get(Calendar.YEAR);
                int mmonth=c.get(Calendar.MONTH);
                int mday=c.get(Calendar.DATE);

                @Override
                public void onClick(View v) {
                    Dialog datepicker = new DatePickerDialog(act, DateListener, myear, mmonth, mday);
                    datepicker.show();
                }
                DatePickerDialog.OnDateSetListener DateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myear = year;
                        mmonth = monthOfYear;
                        mday = dayOfMonth;
                        edit.setText(String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth));
                    }
                };

            });
        };
    }

