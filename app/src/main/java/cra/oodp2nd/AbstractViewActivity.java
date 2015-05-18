package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractViewActivity extends Activity implements AdapterView.OnItemLongClickListener {

    public DatabaseHelper myDBHelper;

    private String createTableQuery;

    protected String tableName;
    protected List<AbstractJob> jobList; // Job 오브젝트를 담는 배열
    protected ListView jobListView;
    protected String alertDialogTitle;
    protected String[] columns;
    protected JobAdapter jobAdapter;

    protected abstract void setColumns();
    protected abstract void setAlertDialogTitle();
    protected abstract void setJobAdapter();
    protected abstract void setTableName();
    protected abstract void inflateJobList(Cursor cursor);

    public abstract void onButtonAddNewJob(View v);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract_view);

        myDBHelper = DatabaseHelper.getInstance(this);

        setColumns();
        setAlertDialogTitle();
        setJobListView();

        setTableName();
        setCreateTableQuery(tableName);
        setJobList();

        myDBHelper.CreateTable(createTableQuery);
        selectData(columns);

        setJobAdapter();
        displayJobList();

        jobListView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds jobList to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Integer selectedPos = position;
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
        alertDlg.setTitle(alertDialogTitle);
        alertDlg.setPositiveButton("Delete", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = jobList.get(selectedPos).getId();

                dialog.dismiss();
                DatabaseHelper.myDBHelper.Querydelete(tableName, "id=" + position, null);
                displayJobList();
            }
        });

        alertDlg.setNegativeButton("Modify", new DialogInterface.OnClickListener(){

            @Override
            public void onClick( DialogInterface dialog, int which ) {
                int position = jobList.get(selectedPos).getId();
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("p_id", position);
                startActivity(intent);
            }
        });

        alertDlg.setMessage("Select an action");
        alertDlg.show();
        return false;
    }


    protected final void selectData(String[] columns){

        Cursor result = DatabaseHelper.myDBHelper.QuerySelect(tableName,columns,null,null,null,null,null);
        result.moveToFirst();
        while(!result.isAfterLast()) {
            inflateJobList(result);
            result.moveToNext();
        }
        result.close();
    }

    protected void displayJobList() {
        jobListView.setAdapter(jobAdapter);
    }

    protected void setCreateTableQuery(String tableName) {
        this.createTableQuery = "create table if not exists "+ tableName + " (id integer primary key, title text);";
    }

    protected void setJobListView(){
        jobListView = (ListView)findViewById(R.id.task_list_view);
    }

    protected void setJobList(){
        jobList = new ArrayList<>();
    }

    protected class JobAdapter extends ArrayAdapter<AbstractJob> {

        protected List<AbstractJob> jobList;

        public JobAdapter(Context context, int textViewResourceId, List<AbstractJob> objects) {
            super(context, textViewResourceId, objects);
            jobList = objects;
        }
    }
}
