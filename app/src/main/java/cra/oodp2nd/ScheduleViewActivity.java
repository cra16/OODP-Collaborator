package cra.oodp2nd;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ScheduleViewActivity extends AbstractViewActivity implements ScheduleInterface {

    @Override
    protected void setColumns() {

    }

    @Override
    protected void setAlertDialogTitle() {

    }

    @Override
    protected void setJobAdapter() {

    }

    @Override
    protected void selectData(String[] columns) {

    }

    @Override
    protected void dbDeleteSingleJob(int position) {

    }

    @Override
    protected Class getJobUpdateActivityClass() {
        return null;
    }

    @Override
    protected Class getThisActivityClass() {
        return null;
    }

    @Override
    public void onButtonAddNewJob(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schdule_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schdule_view, menu);
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
}
