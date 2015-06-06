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


public class RecordViewActivity extends AbstractViewActivity implements RecordInterface {

    @Override
    protected void setColumns() {
        columns = new String[]{"id", "title", "date", "location"};
    }

    @Override
    protected void setAlertDialogTitle() {
        alertDialogTitle = "Record";
    }

    @Override
    protected void setJobAdapter() {
        jobAdapter = new RecordAdapter(this, R.layout.record_list_item, jobList,RecordViewActivity.this);
    }

    @Override
    protected void selectData(String[] columns) {
        Cursor result = sqLiteDatabase.query(TABLE_NAME,columns,null,null,null,null,null);
        result.moveToFirst();
        while(!result.isAfterLast()) {

            int id = Integer.parseInt(result.getString(0));
            String title = result.getString(1);
            String name = result.getString(2);
            String date = result.getString(3);
            String location = result.getString(4);

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
        return RecordUpdateActivity.class;
    }

    @Override
    protected Class getThisActivityClass() {
        return this.getClass();
    }

    @Override
    public void onButtonAddNewJob(View v) {
        Intent intent = new Intent(getApplicationContext(), RecordAddActivity.class);
        Bundle bundle = getIntent().getExtras();
        intent.putExtra("userId", userId);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAddNewJobButtonText("Add a New Meeting Record");
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
    public void onRestart()
    {

        super.onRestart();


        jobAdapter.clear();
        jobAdapter.notifyDataSetChanged();
        selectData(columns);


    }
}
