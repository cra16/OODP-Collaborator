package cra.oodp2nd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        userId= bundle.getString("userId");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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

    public void onButtonShowTasks(View view) {
        Intent intent = new Intent(this, TaskViewActivity.class);
        Bundle bundle = getIntent().getExtras();
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void onButtonShowRecords(View view) {
        Intent intent = new Intent(this, RecordViewActivity.class);
        Bundle bundle = getIntent().getExtras();
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void onButtonShowSchedules(View view) {
        Intent intent = new Intent(this, CalendarSchedule.class);
        Bundle bundle = getIntent().getExtras();
        intent.putExtra("userId", userId);

        startActivity(intent);
    }
}
