package cra.oodp2nd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

/**
 * Created by 현우 on 2015-05-29.
 */
public class CalendarSchedule extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        CalendarView can = (CalendarView) findViewById(R.id.calendView);

        can.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(getApplicationContext(),ScheduleViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
