package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ScheduleAddActivity extends AbstractModelActivity implements ScheduleInterface {

    private int year;
    private int month;
    private int day;


    @Override
    protected void setSaveButton() {
        Button saveButton = (Button) findViewById(R.id.button_add_update);
        saveButton.setText("Save");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_schedule_title);
                EditText dateEditText = (EditText)findViewById(R.id.edit_text_schedule_date);
                EditText locationEditText = (EditText)findViewById(R.id.edit_text_schedule_location);

                String title = titleEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String location =locationEditText.getText().toString();

                ContentValues addRowValue = new ContentValues();

                addRowValue.put("title", title);
                addRowValue.put("date", year+"/"+month+"/"+day);
                addRowValue.put("userId",userId);
                addRowValue.put("time",date.indexOf(" ")>=0 ? date.substring(date.indexOf(" ")+1) : "");
                addRowValue.put("location",location);
                sqLiteDatabase.insert(TABLE_NAME, null, addRowValue) ;


                finish();
            }
        });
    }

    @Override
    protected void setUpdateButton() {

    }

    @Override
    protected Activity getThisActivity() {
        return ScheduleAddActivity.this;
    }

    @Override
    protected EditText getEditText() {
        EditText view = (EditText)findViewById(R.id.edit_text_schedule_date);
        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add_update);


        picker.setTPicker(getThisActivity(),getEditText());

        Bundle bundle = getIntent().getExtras();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        day = bundle.getInt("day");
        userId=bundle.getString("userId");


        EditText dateEditText = (EditText)findViewById(R.id.edit_text_schedule_date);
        dateEditText.setText(year +"/"+month+"/"+day);
        EditText nameEditText = (EditText)findViewById(R.id.edit_text_schedule_name);
        nameEditText.setText(userId);

        setSaveButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        if (id == R.id.action_login_about) {
            // about
            aboutOptionDialog();
        }
        else if (id == R.id.action_login_option){
            // option
            OptionDialog();
            return  true;
        }
        else if (id == R.id.action_login_exit){
            // exit
            exitOptionDialog();
        }
        if(id== android.R.id.home) {

            // NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
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

}
