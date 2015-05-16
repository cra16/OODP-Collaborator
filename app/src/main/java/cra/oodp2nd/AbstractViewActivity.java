package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractViewActivity extends Activity implements AdapterView.OnItemLongClickListener {

    // Job 오브젝트를 담는 배열
    protected List<AbstractJob> jobList;

    protected String tableName;
    protected String createTableQuery = "create table if not exists "+ tableName + " (id integer primary key, title text);";

    protected ListView jobListView;

    protected String alertDialogTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper.myDBHelper.CreateTable(createTableQuery);
        selectData();
        displayJobList();
        jobListView.setOnItemLongClickListener(this);
    }

    protected abstract void displayJobList();

    protected abstract void selectData();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    public abstract void onButtonAddNewJob(View v);
}
