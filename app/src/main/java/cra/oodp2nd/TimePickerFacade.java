package cra.oodp2nd;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.EditText;

/**
 * Created by 현우 on 2015-06-08.
 */
public class TimePickerFacade implements PickerFacade {

    @Override
    public void setPicker(final Activity act,  final EditText edit) {


        edit.setOnClickListener(new View.OnClickListener() {

            int mhour;
            int mminute;
            @Override
            public void onClick(View v) {
                Dialog TimePicker = new TimePickerDialog(act, TimeListener,mhour,mminute,false);
                TimePicker.show();
            }
            TimePickerDialog.OnTimeSetListener TimeListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                    String date = edit.getText().toString().substring(0, edit.getText().toString().indexOf(" ")>=0
                            ?  edit.getText().toString().indexOf(" ") : edit.getText().toString().length());
                    mhour = hourOfDay;
                    mminute = minute;

                    edit.setText(date +" "+ mhour +" : " +mminute);
                }
            };
        });
    }
}
