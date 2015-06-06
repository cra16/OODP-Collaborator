package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecordUpdateActivity extends AbstractModelActivity implements RecordInterface {

    ArrayList<PersonJob> PersonList = new ArrayList<PersonJob>();
    ArrayList<PersonJob> P_PersonList = new ArrayList<PersonJob>();
    ArrayList<PersonJob> ADD_PersonList = new ArrayList<PersonJob>();
    PersonAdapter adapter;

    ListView list;
    protected int id;

    @Override
    protected void setSaveButton() {

    }

    @Override
    protected void setUpdateButton() {
        Button updateButton = (Button) findViewById(R.id.button_add_update);
        updateButton.setText("Modify");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_record_title);
                EditText NameEditText = (EditText)findViewById(R.id.edit_text_record_name);
                EditText dateEditText = (EditText)findViewById(R.id.edit_text_record_date);
                EditText locationEditText = (EditText)findViewById(R.id.edit_text_record_location);

                String title = titleEditText.getText().toString();
                String name = NameEditText.getText().toString();
                String datetext = dateEditText.getText().toString();
                String date = datetext.substring(0, datetext.indexOf(" ") >= 0 ? datetext.indexOf(" ") : datetext.length());
                String time = datetext.substring(datetext.indexOf(" ") + 1);
                String location = locationEditText.getText().toString();

                ContentValues updateRowValue = new ContentValues();

                updateRowValue.put("title", title);
                updateRowValue.put("userId", name);
                updateRowValue.put("date", date);
       //         updateRowValue.put("time", time);
                updateRowValue.put("location", location);

                sqLiteDatabase.update(TABLE_NAME, updateRowValue, "id=" + id, null);

                ContentValues addRowValue = new ContentValues();



                for(int position=0; position<ADD_PersonList.size(); position++) {
                    addRowValue.put("userId", (ADD_PersonList.get(position)).getUserId());
                    addRowValue.put("recordId", id);

                    sqLiteDatabase.insert("table_member_presented", null, addRowValue);
                }

                finish();
            }
        });
    }

    @Override
    protected Activity getThisActivity() {
        return RecordUpdateActivity.this;
    }

    @Override
    protected int getLayout() {
        return R.id.edit_text_record_date;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_add_update);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("p_id");
        getRecordTitle();

        setUpdateButton();
        setDatePicker();
        setPersonButton();
        ShowPerson();
        adapter = new PersonAdapter(this, android.R.layout.simple_list_item_multiple_choice, P_PersonList, RecordUpdateActivity.this);

        list = (ListView) findViewById(R.id.record_view);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Integer selectedPos = position;
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
                alertDlg.setTitle("냠");
                alertDlg.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = P_PersonList.get(selectedPos).getId();

                        dialog.dismiss();
                        sqLiteDatabase.delete("table_member_presented", "id=" + position, null);
                        // TODO: Refresh jobListView instead recall this activity
//                jobList.remove(selectedPos);
//                setJobAdapter();
//                displayJobList();
//                jobListView.invalidate();

                        // TODO: Make intent dynamically call activity
                        P_PersonList.remove(P_PersonList.get(selectedPos));


                        onRestart();
                    }
                });
                alertDlg.setMessage("is it true");
                alertDlg.show();
                return false;
            }
        });
    }

    private void getRecordTitle() {
        String[] columns = {"title","userId","date","location"};

        Cursor result = sqLiteDatabase.query(TABLE_NAME, columns, "id=" + id, null, null, null, null);

        result.moveToFirst();
        String title = result.getString(0);
        String name = result.getString(1);
        String date = result.getString(2);
        String location = result.getString(3);

        EditText titleEditText = (EditText) findViewById(R.id.edit_text_record_title);
        EditText nameEditText = (EditText)findViewById(R.id.edit_text_record_name);
        EditText dateEditText = (EditText)findViewById(R.id.edit_text_record_date);
        EditText locationEditText = (EditText)findViewById(R.id.edit_text_record_location);

        titleEditText.setText(title);
        nameEditText.setText(name);
        dateEditText.setText(date);
        locationEditText.setText(location);
        result.close();
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
            P_PersonList.clear();
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

    public void setPersonButton() {
        Button PersonButton = (Button) findViewById(R.id.person_button);

        PersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                        RecordUpdateActivity.this);
                alertBuilder.setTitle("추가할 사람을 선택해주세요");

                // List Adapter 생성
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        RecordUpdateActivity.this,
                        android.R.layout.select_dialog_singlechoice);



                for(int position=0; position<PersonList.size(); position++)
                    adapter.add(PersonList.get(position).getUserId());

                alertBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user = adapter.getItem(which);

                       ADD_PersonList.add(PersonList.get(which));
                       P_PersonList.add(PersonList.get(which));
                        dialog.dismiss();
                        onRestart();
                    }
                });

                alertBuilder.show();
            }
        });

    }



    protected void ShowPerson() {

        String[] columns = new String[]{"id","userId"};

        Cursor result = sqLiteDatabase.query("table_member", columns,null, null, null, null, null);

        result.moveToFirst();
        while (!result.isAfterLast()) {
            PersonList.add(new PersonJob(result));
            result.moveToNext();
        }

        columns = new String[]{"id","userId","recordId"};

        result = sqLiteDatabase.query("table_member_presented", columns,"recordId="+ id, null, null, null, null);
        result.moveToFirst();


        while (!result.isAfterLast()) {
            P_PersonList.add(new PersonJob(result));
            result.moveToNext();
        }
        result.close();
    }



    @Override
    public void onRestart()
    {
        super.onRestart();


        adapter.notifyDataSetChanged();


    }
}
