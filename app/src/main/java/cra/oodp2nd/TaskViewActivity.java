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
import android.widget.TextView;

import java.util.List;


public class TaskViewActivity extends AbstractViewActivity implements TaskInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        userId=bundle.getString("userId");
        setAddNewJobButtonText("Add New Task");
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
    public void onButtonAddNewJob(View v) {
        Intent intent = new Intent(getApplicationContext(), TaskAddActivity.class);
        Bundle bundle = getIntent().getExtras();
        intent.putExtra("userId", userId);
        startActivity(intent);

    }

    @Override
    public void setColumns() {
        columns = new String[]{"id", "userId","title"};
    }

    @Override
    public void setAlertDialogTitle() {
        alertDialogTitle = "Task";
    }

    @Override
    protected void setJobAdapter() {
        jobAdapter = new TaskAdapter(this, R.layout.task_list_item, jobList);
    }

    @Override
    protected void selectData(String[] columns) {
        Cursor result = sqLiteDatabase.query(TABLE_NAME,columns,"userId="+"\""+userId+"\"",null,null,null,null,null);
        result.moveToFirst();
        while(!result.isAfterLast()) {

            int id = Integer.parseInt(result.getString(0));
            String title = result.getString(1);
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
        return TaskUpdateActivity.class;
    }

    @Override
    protected Class getThisActivityClass() {
        return this.getClass();
    }

    protected class TaskAdapter extends JobAdapter {
            public TaskAdapter(Context context, int textViewResourceId, List<AbstractJob> objects) {
                super(context, textViewResourceId, objects);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if(v == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.task_list_item, null);
                }
                TaskJob taskJob = (TaskJob) jobList.get(position);
                if(taskJob != null) {
                    TextView id = (TextView) v.findViewById(R.id.task_list_item_id);
                    TextView title = (TextView) v.findViewById(R.id.task_list_item_title);
                    if (id != null) {
//                    Log.i("i", taskJob.getId() + "");
                        id.setText(Integer.toString(taskJob.getId()));
                    }
                    if (title != null) {
                        title.setText(taskJob.getTitle());
                    }
                }
                return  v;
            }


    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        jobAdapter.clear();
        jobAdapter.notifyDataSetChanged();
        selectData(columns);
    }
}
