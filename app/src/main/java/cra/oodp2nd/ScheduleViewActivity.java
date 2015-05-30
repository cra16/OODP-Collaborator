package cra.oodp2nd;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class ScheduleViewActivity extends AbstractViewActivity implements ScheduleInterface {
    String date;



    @Override
    protected void setColumns() {
        columns = new String[]{"id", "title", "date","time"};
    }

    @Override
    protected void setAlertDialogTitle() {
        alertDialogTitle = "Schedule";
    }

    @Override
    protected void setJobAdapter() {
        jobAdapter = new ScheduleAdapter(this, R.layout.record_list_item, jobList);
    }

    @Override
    protected void selectData(String[] columns) {
        Bundle bundle = getIntent().getExtras();
        if (getIntent().getExtras()!=null  ) {
            date = "\""+bundle.getInt("year") + "/" + bundle.getInt("month") + "/" + bundle.getInt("day")+"\"";
        }

        Cursor result = sqLiteDatabase.query(TABLE_NAME, columns,"date="+date,null,null,null,null);
        result.moveToFirst();
        while(!result.isAfterLast()) {

            int id = Integer.parseInt(result.getString(0));
            String title = result.getString(1);
            String date = result.getString(2);
            String time = result.getString(3);
            jobList.add(JFactory.create(result,alertDialogTitle));

            result.moveToNext();
        }
        result.close();
    }

    @Override
    protected void dbDeleteSingleJob(int position) {
        sqLiteDatabase.delete(TABLE_NAME, "id=" + position, null);
    }

    @Override
    protected Class getJobUpdateActivityClass() {
        return ScheduleUpdateActivity.class;
    }

    @Override
    protected Class getThisActivityClass() {
        return this.getClass();
    }

    @Override
    public void onButtonAddNewJob(View v) {
        Intent intent = new Intent(getApplicationContext(), ScheduleAddActivity.class);
        Bundle bundle = getIntent().getExtras();
        intent.putExtra("userId", userId);
        intent.putExtra("year",bundle.getInt("year"));
        intent.putExtra("month",bundle.getInt("month"));
        intent.putExtra("day", bundle.getInt("day"));

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setAddNewJobButtonText("Add a New Meeting Schedule");

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

    protected class ScheduleAdapter extends JobAdapter {
        public ScheduleAdapter(Context context, int textViewResourceId, List<AbstractJob> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 v = vi.inflate(R.layout.schedule_list_item, null);
            }
            ScheduleJob scheduleJob = (ScheduleJob) jobList.get(position);
            if(scheduleJob != null) {
                TextView id = (TextView) v.findViewById(R.id.schedule_list_item_id);
                TextView title = (TextView) v.findViewById(R.id.schedule_list_item_title);
                if (id != null) {
                    id.setText(Integer.toString(scheduleJob.getId()));
                }
                if (title != null) {
                    title.setText(scheduleJob.getTitle());
                }
            }
            return  v;
        }
    }
    @Override
    public void onRestart()
    {

        super.onRestart();
        Bundle bundle = getIntent().getExtras();
        date = bundle.getInt("year") +"/" + bundle.getInt("month")+"/"+bundle.getInt("day");

        jobAdapter.clear();
        jobAdapter.notifyDataSetChanged();
        selectData(columns);


    }

}
