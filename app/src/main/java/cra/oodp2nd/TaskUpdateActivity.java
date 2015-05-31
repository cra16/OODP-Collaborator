package cra.oodp2nd;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class TaskUpdateActivity extends AbstractModelActivity implements TaskInterface {

    protected int id;
    protected int titleId;
    ArrayList<SubTaskJob> subJobList = new ArrayList<SubTaskJob>();
    ArrayList<AbstractJob> JobList = new ArrayList<AbstractJob>();
    SubTaskAdapter adapter;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add_update);
        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("p_id");

        getTaskTitle();

        setUpdateButton();
        setSubTaskButton();
        ShowSubTask();
        adapter= new SubTaskAdapter(this, R.layout.task_list_item, subJobList);


        list = (ListView) findViewById(R.id.sub_task_view);
        list.setAdapter(adapter);


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
    protected void setSaveButton() {

    }

    @Override
    protected void setUpdateButton() {
        Button updateButton = (Button) findViewById(R.id.button_add_update);
        updateButton.setText("Modify");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_task_title);
                String title = titleEditText.getText().toString();
                ContentValues updateRowValue = new ContentValues();

                updateRowValue.put("title", title);
                sqLiteDatabase.update(TABLE_NAME, updateRowValue, "id=" + id, null);

                finish();
            }
        });
    }
    @Override
    protected Activity getThisActivity() {
        return TaskUpdateActivity.this;
    }

    @Override
    protected int getLayout() {
        return 0;
    }


    private void getTaskTitle() {
        String[] columns = {"title,userId"};

        Cursor result = sqLiteDatabase.query(TABLE_NAME, columns, "id=" + id, null, null, null, null);

        result.moveToFirst();
        String title = result.getString(0);

        EditText titleEditText = (EditText) findViewById(R.id.edit_text_task_title);
        titleEditText.setText(title);

        result.close();
    }

    public void setSubTaskButton() {
        Button SubTaskButton = (Button) findViewById(R.id.sub_task_button);

        SubTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskUpdateActivity.this, SubTaskAddActivity.class);
                intent.putExtra("T_ID", id);
                startActivity(intent);
            }
        });

    }

    protected void ShowSubTask() {


        String[] columns = new String[]{"id", "title","userId"};

        Cursor result = sqLiteDatabase.query("table_task", columns, "id=" + id, null, null, null, null);

        result.moveToFirst();
        JobList.add(new TaskJob(result));
        TaskJob taskJob = (TaskJob) JobList.get(0);

        columns = new String[]{"id","userId", "title", "titleId", "clear", "state"};
        result = sqLiteDatabase.query("table_subtask", columns, "titleId=" + id, null, null, null, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            subJobList.add(new SubTaskJob(result));
            result.moveToNext();
        }

        result.close();




    }
   protected class SubTaskAdapter extends ArrayAdapter<SubTaskJob> {

        ArrayList<SubTaskJob> list;

       public SubTaskAdapter(Context context, int resource, ArrayList<SubTaskJob> objects) {
            super(context, resource, objects);
            this.list = objects;
        }

        public View getView(int position, View contentView, ViewGroup parent) {
            if (contentView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                contentView = vi.inflate(R.layout.task_list_item, null);
            }

            SubTaskJob subTask = list.get(position);

            if (subTask != null) {
                TextView id = (TextView) contentView.findViewById(R.id.task_list_item_id);
                TextView title = (TextView) contentView.findViewById(R.id.task_list_item_title);

                if (id != null) {
                    id.setText(Integer.toString(subTask.getId()));
                }
                if (title != null) {
                    title.setText(subTask.getTitle());
                }
            }
            return contentView;
        }
    }
        @Override
        public void onRestart()
        {
            adapter.clear();
            ShowSubTask();
            adapter.notifyDataSetChanged();
            super.onRestart();
        }



}

