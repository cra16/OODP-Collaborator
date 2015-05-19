package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractViewActivity extends Activity implements AdapterView.OnItemLongClickListener {

    public static DatabaseHelper myDBHelper;
    protected SQLiteDatabase sqLiteDatabase;

    protected List<AbstractJob> jobList; // Job 오브젝트를 담는 배열
    protected ListView jobListView;
    protected JobAdapter jobAdapter;
    protected String alertDialogTitle;
    protected String[] columns;
    protected Button addNewJobButton;

    protected abstract void setColumns();
    protected abstract void setAlertDialogTitle();
    protected abstract void setJobAdapter();
    protected abstract void selectData(String[] columns);
    protected abstract void dbDeleteSingleJob(int position);

    public abstract void onButtonAddNewJob(View v);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);
        // TODO: Rearrange and rename methods, or extract method, make them plain

        myDBHelper = DatabaseHelper.getInstance(this);
        sqLiteDatabase = myDBHelper.getWritableDatabase();

        setColumns();
        setAlertDialogTitle();
        setJobListView();

        setJobList();

        selectData(columns);

        setJobAdapter();
        displayJobList();

        jobListView.setOnItemLongClickListener(this);

        // addNewJobButton listener 추가
        addNewJobButton = (Button) findViewById(R.id.BUTTON_ADD_NEW_JOB);
        addNewJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonAddNewJob(v);
            }
        });
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
                dbDeleteSingleJob(position);
                // TODO: Refresh jobListView instead recall this activity
//                jobList.remove(selectedPos);
//                setJobAdapter();
//                displayJobList();
//                jobListView.invalidate();
                Intent intent = new Intent(getApplicationContext(), TaskViewActivity.class);
                startActivity(intent);
            }
        });

        alertDlg.setNegativeButton("Modify", new DialogInterface.OnClickListener(){

            @Override
            public void onClick( DialogInterface dialog, int which ) {
                int position = jobList.get(selectedPos).getId();
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), TaskUpdateActivity.class);
                intent.putExtra("p_id", position);
                startActivity(intent);
            }
        });

        alertDlg.setMessage("Select an action");
        alertDlg.show();
        return false;
    }

    protected final void displayJobList() {
        jobListView.setAdapter(jobAdapter);
    }

    protected final void setJobListView() {
        jobListView = (ListView)findViewById(R.id.JOB_LIST_VIEW);
    }

    protected final void setJobList(){
        jobList = new ArrayList<>();
    }

    protected final void setAddNewJobButtonText(String text) {
        addNewJobButton.setText(text);
    }

    protected class JobAdapter extends ArrayAdapter<AbstractJob> {

        protected List<AbstractJob> jobList;

        public JobAdapter(Context context, int textViewResourceId, List<AbstractJob> objects) {
            super(context, textViewResourceId, objects);
            jobList = objects;
        }
    }
}
