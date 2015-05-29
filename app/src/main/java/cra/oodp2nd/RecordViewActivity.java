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
        columns = new String[]{"id", "title","name", "date", "location"};
    }

    @Override
    protected void setAlertDialogTitle() {
        alertDialogTitle = "Record";
    }

    @Override
    protected void setJobAdapter() {
        jobAdapter = new RecordAdapter(this, R.layout.record_list_item, jobList);
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
        getMenuInflater().inflate(R.menu.menu_record_view, menu);
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

    protected class RecordAdapter extends JobAdapter {

        public RecordAdapter(Context context, int textViewResourceId, List<AbstractJob> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.record_list_item, null);
            }
            RecordJob recordJob = (RecordJob) jobList.get(position);
            if(recordJob != null) {
                TextView id = (TextView) v.findViewById(R.id.record_list_item_id);
                TextView title = (TextView) v.findViewById(R.id.record_list_item_title);

                if (id != null) {
                    id.setText(Integer.toString(recordJob.getId()));
                }
                if (title != null) {
                    title.setText(recordJob.getTitle());
                }

            }
            return  v;
        }
    }
}
