package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
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

import java.util.List;


public abstract class AbstractViewActivity extends Activity implements AdapterView.OnItemLongClickListener {

    // Job 오브젝트를 담는 배열
    private List<AbstractJob> jobList;

    private String tableName;
    private String createTableQuery = "create table if not exists "+ tableName + " (id integer primary key, title text);";

    private ListView jobListView;

    private String alertDialogTitle;

    private String[] columns;

    public abstract void setColumns();
    public abstract void setAlertDialogTitle();
    public abstract void setJobListView();
    public abstract void setTableName();
    public abstract void setJobList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract_view);

        setColumns();
        setAlertDialogTitle();
        setJobListView();
        setTableName();
        setJobList();

        DatabaseHelper.myDBHelper.CreateTable(createTableQuery);
        selectData(columns);
        displayJobList();
        jobListView.setOnItemLongClickListener(this);
    }

    protected final void displayJobList(){
        ArrayAdapter<AbstractJob> Adapter;

        // TODO: 어댑터 수정
        Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList);
        ListView list = (ListView)findViewById(R.id.task_list_view);

        list.setAdapter(Adapter);
    }

    protected abstract void inflateJobList(Cursor cursor);

    protected final void selectData(String[] columns){

        Cursor result = DatabaseHelper.myDBHelper.QuerySelect(tableName,columns,null,null,null,null,null);
        result.moveToFirst();
        while(!result.isAfterLast()){
            inflateJobList(result);
//            arr_id_list.add(result.getString(0));
//            arrayList.add(result.getString(1));
//            for(int i = 0; i<result.getColumnCount();i++) {
//                jobList.add(new AbstractJob() {
//                    @Override
//                    public void setTitle(String title) {
//                        super.setTitle(result.getString());
//                    }
//                })
//                jobList.add(result.getString(i));
//                result.
            }
            result.moveToNext();
        }
        result.close();
    }

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
