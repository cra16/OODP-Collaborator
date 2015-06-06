package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class TaskUpdateActivity extends AbstractModelActivity implements TaskInterface {

    protected int id;
    ArrayList<AbstractJob> subJobList = new ArrayList<AbstractJob>();
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
        adapter= new SubTaskAdapter(this, R.layout.task_list_item, subJobList,TaskUpdateActivity.this);


        list = (ListView) findViewById(R.id.sub_task_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Integer selectedPos = position;
                int Item_position = subJobList.get(selectedPos).getId();
                Intent intent = new Intent(getApplicationContext(), SubTaskUpdateActivity.class);
                intent.putExtra("p_id", Item_position);
                startActivity(intent);

            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Integer selectedPos = position;
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
                alertDlg.setTitle("aa");

                alertDlg.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = subJobList.get(selectedPos).getId();

                        dialog.dismiss();
                        sqLiteDatabase.delete("table_subtask", "id =" + position, null);
                        // TODO: Refresh jobListView instead recall this activity

                        // TODO: Make intent dynamically call activity
                        onRestart();


                    }
                });
                alertDlg.setMessage("Select an action");
                alertDlg.show();
                return true;
            }
        });

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
                EditText nameEditText = (EditText)findViewById(R.id.edit_text_task_name);

                String title = titleEditText.getText().toString();
                String name = nameEditText.getText().toString();
                ContentValues updateRowValue = new ContentValues();

                updateRowValue.put("title", title);
                updateRowValue.put("userId", userId);
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
        String name = result.getString(1);

        EditText titleEditText = (EditText) findViewById(R.id.edit_text_task_title);
        EditText nameEditText = (EditText)findViewById(R.id.edit_text_task_name);
        titleEditText.setText(title);
        nameEditText.setText(name);


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


        String[] columns = new String[]{"id","userId", "title", "titleId", "clear", "state"};
        Cursor result = sqLiteDatabase.query("table_subtask", columns, "titleId=" + id, null, null, null, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            subJobList.add(jobFactory.create(result,"Sub"));
            result.moveToNext();
        }

        result.close();




    }

        @Override
        public void onRestart()
        {
            super.onRestart();

            adapter.clear();

            ShowSubTask();
            adapter.notifyDataSetChanged();
          }



}
