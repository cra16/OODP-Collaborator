package cra.oodp2nd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by 현우 on 2015-05-29.
 */
public class CalendarSchedule extends Activity implements View.OnClickListener {
    Calendar mCalendar;
    GridView mGridView;
    int[] mToday = new int[3];
    final DisplayMetrics metrics = new DisplayMetrics();

    TextView  text;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        Button Prev = (Button) findViewById(R.id.Prev);
        Button Next = (Button) findViewById(R.id.Next);
        text = (TextView)findViewById(R.id.monthOfYear);

        mCalendar = Calendar.getInstance();
        mToday[0] = mCalendar.get(Calendar.DAY_OF_MONTH);
        mToday[1] = mCalendar.get(Calendar.MONTH); // zero based
        mToday[2] = mCalendar.get(Calendar.YEAR);

        text.setText(mToday[2] +"년 " + (mToday[1]+1)+"월");

        Prev.setOnClickListener(this);
        Next.setOnClickListener(this);


// get display metrics
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


// set adapter
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setAdapter(new MonthAdapter(this, mToday[1], mToday[2], metrics) {
            @Override
            protected void onDate(int[] date, int position, View item) {
                final int[] day = date;
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ScheduleViewActivity.class);
                        Bundle bundle = getIntent().getExtras();
                        intent.putExtra("userId", bundle.getString("userId"));
                        intent.putExtra("year", day[2]);
                        intent.putExtra("month", day[1]+1);
                        intent.putExtra("day", day[0]);
                        startActivity(intent);
                    }
                });

            }


        });
/*
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Intent intent = new Intent(getApplicationContext(), ScheduleViewActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month + 1);
                intent.putExtra("day", dayOfMonth);

                startActivity(intent);
            }
        });
*/
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule_update_activty, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {

            // NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        int[] NextMonth = mToday;

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        switch (v.getId()) {

            case R.id.Next:


                NextMonth[1]=(mCalendar.get(Calendar.MONTH)>=12 ? 1: mCalendar.get(Calendar.MONTH));
                NextMonth[2]=mCalendar.get(Calendar.YEAR)+(NextMonth[1]>=12 ? 1:0);
                mCalendar.set(mCalendar.get(Calendar.YEAR)+(NextMonth[1]>=12 ? 1:0), mCalendar.get(Calendar.MONTH)+(NextMonth[1]>=12 ? 0:1), 1);

                text.setText(NextMonth[2] +"년 " + (NextMonth[1]+1)+"월");

                setGridView(mGridView, NextMonth, metrics);



                break;
            case R.id.Prev:
//여기좀 이상함 로직 다시 세워야할듯

                NextMonth[1]=(mCalendar.get(Calendar.MONTH)==0 ? 11 : (mCalendar.get(Calendar.MONTH)));
                NextMonth[2]=mCalendar.get(Calendar.YEAR)-(NextMonth[1]==0 ? 1:0);

                mCalendar.set(mCalendar.get(Calendar.YEAR)-(NextMonth[1]==0 ? 1:0), mCalendar.get(Calendar.MONTH)-(NextMonth[1]==0 ? 0:1), 1);

                text.setText(NextMonth[2] +"년 " + (NextMonth[1]+1)+"월");

                setGridView(mGridView, NextMonth, metrics);

                break;

        }
    }

    public void setGridView(GridView g, int[] mToday, DisplayMetrics metrics) {


        g.setAdapter(new MonthAdapter(this, mToday[1], mToday[2], metrics) {
            @Override
            protected void onDate(int[] date, int position, View item) {
                final int[] day = date;

                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ScheduleViewActivity.class);
                        Bundle bundle = getIntent().getExtras();
                        intent.putExtra("userId", bundle.getString("userId"));
                        intent.putExtra("year", day[2]);
                        intent.putExtra("month", day[1] + 1);
                        intent.putExtra("day", day[1]);
                        startActivity(intent);
                    }
                });
//                onRestart();

            }
        });


    }
    @Override
    public void onRestart()//임시대책이요..
    {
        super.onRestart();

        mCalendar = Calendar.getInstance();
        mToday[0] = mCalendar.get(Calendar.DAY_OF_MONTH);
        mToday[1] = mCalendar.get(Calendar.MONTH); // zero based
        mToday[2] = mCalendar.get(Calendar.YEAR);

        text.setText(mToday[2] +"년 " + (mToday[1]+1)+"월");



        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setAdapter(new MonthAdapter(this, mToday[1], mToday[2], metrics) {
            @Override
            protected void onDate(int[] date, int position, View item) {
                final int[] day = date;
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ScheduleViewActivity.class);
                        Bundle bundle = getIntent().getExtras();
                        intent.putExtra("userId", bundle.getString("userId"));
                        intent.putExtra("year", day[2]);
                        intent.putExtra("month", day[1] + 1);
                        intent.putExtra("day", day[0]);
                        startActivity(intent);
                    }
                });

            }


        });

    }
}

