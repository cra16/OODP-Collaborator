package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 현우 on 2015-06-06.
 */
public class SubTaskUpdateActivity extends AbstractModelActivity{
    protected int T_ID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask_add_update);

        Bundle bundle = getIntent().getExtras();
        T_ID = bundle.getInt("p_id");

        getSubTask();
        setUpdateButton();

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
                // �� ����Ʈ ���� ��
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
    protected void setSaveButton() {
    }
    @Override
    protected void setUpdateButton() {
        Button saveButton = (Button) findViewById(R.id.button_sub_add_update);
        saveButton.setText("Modify");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_subtask_title);
                EditText nameEditText = (EditText) findViewById(R.id.edit_text_subtask_name);
                String title = titleEditText.getText().toString();
                String name = nameEditText.getText().toString();

                CheckBox check = (CheckBox) findViewById(R.id.checkbox_completed);

                ContentValues updateRowValue = new ContentValues();

                int checked = 0;
                if (check.isChecked())
                    checked = 1;

                updateRowValue.put("title", title);
                updateRowValue.put("userId", name);
                updateRowValue.put("clear", checked);
                sqLiteDatabase.update("table_subtask", updateRowValue, "id=" + T_ID, null);

                finish();

            }
        });

    }

    @Override
    protected Activity getThisActivity() {
        return null;
    }

    @Override
    protected EditText getEditText() {
        return null;
    }


    protected void getSubTask()
    {
        String[] columns = {"title","userId","titleId","clear"};

        Cursor result = sqLiteDatabase.query("table_subtask", columns, "id=" + T_ID, null, null, null, null);

        result.moveToFirst();
        String title = result.getString(0);
        String name = result.getString(1);
        EditText nameEditText = (EditText) findViewById(R.id.edit_text_subtask_name);
        EditText titleEditText = (EditText) findViewById(R.id.edit_text_subtask_title);
        CheckBox check = (CheckBox) findViewById(R.id.checkbox_completed);
        check.setChecked(result.getInt(2)==1);
        nameEditText.setText(name);

        titleEditText.setText(title);

        result.close();

    }

}

