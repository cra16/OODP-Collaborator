package cra.oodp2nd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RecordViewActivity extends AbstractViewActivity implements RecordInterface {

    @Override
    protected void setColumns() {
        columns = new String[]{"id","userId", "title", "date", "location"};
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
        Cursor result = sqLiteDatabase.query(TABLE_NAME,columns,"userId="+"\""+userId+"\"",null,null,null,null);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_about) {
            // about
            aboutOptionDialog();
        }
        else if (id == R.id.action_option){
            // option
            OptionDialog();
            return  true;
        }
        else if (id == R.id.action_exit){
            // exit
            exitOptionDialog();
        }
        else if (id == android.R.id.home){
            //
            finish();
            return true;
        }
        else if (id == R.id.action_add){
            Intent intent = new Intent(getApplicationContext(), RecordAddActivity.class);
            Bundle bundle = getIntent().getExtras();
            intent.putExtra("userId", userId);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

   private void aboutOptionDialog() {
        new AlertDialog.Builder(this).setTitle("About Collaborator").setMessage("Developer : Team OODP E").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    private void OptionDialog() {
        final String items[] = {"Blue", "Green", "Gray", "White"};
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Select Color");
        ab.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 각 리스트 선택 시
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
                LoginActivity.OptionInformaiton.option_color = which;
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                which = LoginActivity.OptionInformaiton.option_color;
                // OK button, to Main Activity
                if (which == 0) { // Blue
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } else if (which == 1) { // Green
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                } else if (which == 2) { // Purple
                    getWindow().getDecorView().setBackgroundColor(Color.GRAY);
                } else { // default
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // CANCEL button
            }
        });
        ab.show();
    }

    private void exitOptionDialog(){
        new AlertDialog.Builder(this).setTitle("Exit").setMessage("Exit the Program.").setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
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
