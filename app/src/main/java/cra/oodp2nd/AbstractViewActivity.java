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


public abstract class AbstractViewActivity extends Activity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {


    public static DatabaseHelper myDBHelper;
    protected SQLiteDatabase sqLiteDatabase;
    protected JobFactory JFactory= new JobFactory();
    protected String userId;

    protected List<AbstractJob> jobList; // Jofb 오브젝트를 담는 배열
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
    protected abstract Class getJobUpdateActivityClass();
    protected abstract Class getThisActivityClass();

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
        setuserId();
        selectData(columns);

        setJobAdapter();
        displayJobList();

        jobListView.setOnItemClickListener(this);

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
        alertDlg.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

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

                // TODO: Make intent dynamically call activity
                onRestart();
            }
        });


        /*alertDlg.setNegativeButton("Modify", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = jobList.get(selectedPos).getId();
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), getJobUpdateActivityClass());
                intent.putExtra("p_id", position);
                startActivity(intent);
            }
        });*/

        alertDlg.setMessage("Select an action");
        alertDlg.show();
        return true;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Integer selectedPos = position;
        int Item_position = jobList.get(selectedPos).getId();
        Intent intent = new Intent(getApplicationContext(), getJobUpdateActivityClass());
        intent.putExtra("p_id", Item_position);
        startActivity(intent);

    }

    protected final void setuserId()
    {
        Bundle bundle = getIntent().getExtras();
        this.userId=bundle.getString("userId");
    }
}
