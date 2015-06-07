package cra.oodp2nd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class RecordAddActivity extends AbstractModelActivity implements RecordInterface {

    ArrayList<String> PersonList = new ArrayList<String>();
    ArrayList<String> P_PersonList = new ArrayList<String>();
    ArrayList<String> ADD_PersonList = new ArrayList<String>();

    ListView fileListView;
    ArrayAdapter<String> fileListAdapter;
    ArrayList<String> listValues;
    PersonAdapter adapter;

    ListView list;
    protected int id;

    @Override
    protected void setSaveButton() {
        Button saveButton = (Button) findViewById(R.id.button_add_update);
        saveButton.setText("Save");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText titleEditText = (EditText) findViewById(R.id.edit_text_record_title);
                EditText nameEditText = (EditText)findViewById(R.id.edit_text_record_name);
                EditText dateEditText = (EditText)findViewById(R.id.edit_text_record_date);
                EditText locationEditText =(EditText)findViewById(R.id.edit_text_record_location);

                String title = titleEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String date= dateEditText.getText().toString();
                String location = locationEditText.getText().toString();


                ContentValues addRowValue = new ContentValues();

                addRowValue.put("title", title);
                addRowValue.put("userId", name);
                addRowValue.put("date", date);
                addRowValue.put("location", location);

                long recordId = sqLiteDatabase.insert(TABLE_NAME, null, addRowValue);/*
                sqLiteDatabase.query(TABLE_NAME,new String[]{"id"},"")
                addRowValue = new ContentValues();

                for(int position=0; position<ADD_PersonList.size(); position++) {
                    addRowValue.put("userId", ADD_PersonList.get(position));
                    addRowValue.put("recordId", );

                    sqLiteDatabase.insert("table_member_presented", null, addRowValue);
                }*/

                ContentValues addFileRowValue = new ContentValues();

                for (int i=0; i<fileListView.getCount(); i++) {
                    addFileRowValue.put("recordId", recordId);
                    addFileRowValue.put("fileName", fileListView.getItemAtPosition(i).toString());
                }

                sqLiteDatabase.insert(FILE_TABLE_NAME, null, addFileRowValue);

                finish();
            }
        });
    }

    @Override
    protected void setUpdateButton() {

    }

    @Override
    protected Activity getThisActivity() {
        return RecordAddActivity.this;
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
        id=bundle.getInt("p_id");

        EditText name = (EditText) findViewById(R.id.edit_text_record_name);
        name.setText(userId);

        setSaveButton();
        setDatePicker();
        setPersonButton();
        ShowPerson();
        adapter = new PersonAdapter(this, android.R.layout.simple_list_item_multiple_choice,P_PersonList);

        list = (ListView) findViewById(R.id.record_view);
        list.setAdapter(adapter);

        fileListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listValues);

        //set fileListAdapter to ListView
        fileListView.setAdapter(fileListAdapter);
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
        } else if (id == R.id.action_record_upload) {
            final Activity activityForButton = this;

            Intent fileExploreIntent = new Intent(FileBrowserActivity.INTENT_ACTION_SELECT_FILE,
                    null,
                    activityForButton,
                    cra.oodp2nd.FileBrowserActivity.class);
            fileExploreIntent.putExtra(FileBrowserActivity.showCannotReadParameter, false);
            startActivityForResult(fileExploreIntent, REQUEST_CODE_PICK_FILE);

            return true;
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
                        RecordAddActivity.this);
                alertBuilder.setTitle("추가할 사람을 선택해주세요");

                // List Adapter 생성
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        RecordAddActivity.this,
                        android.R.layout.select_dialog_singlechoice);


                for(int position=0; position<PersonList.size(); position++)
                     adapter.add(PersonList.get(position));

                alertBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user = adapter.getItem(which);

                        ADD_PersonList.add(user);
                        dialog.dismiss();
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
            PersonList.add(new String(result.getString(1)));
            result.moveToNext();
        }
        columns = new String[]{"id","recordId","userId"};

        result = sqLiteDatabase.query("table_member_presented", columns,"recordId="+id, null, null, null, null);
        result.moveToFirst();


        while (!result.isAfterLast()) {
            P_PersonList.add(new String(result.getString(2)));
            result.moveToNext();
        }
        result.close();
    }
    protected class PersonAdapter extends ArrayAdapter<String> {

        ArrayList<String> list;

        public PersonAdapter(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            this.list = objects;
        }

        public View getView(int position, View contentView, ViewGroup parent) {
            if (contentView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                contentView = vi.inflate(android.R.layout.simple_list_item_multiple_choice, null);
            }

            String Person = P_PersonList.get(position);

            if (Person != null) {
                TextView id = (TextView) contentView.findViewById(R.id.task_list_item_id);

                if (id != null) {
                    id.setText(Person);
                }
            }

            return contentView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_DIR) {
            if (resultCode == RESULT_OK) {
                String newDir = data.getStringExtra(
                        FileBrowserActivity.returnDirectoryParameter);
                Toast.makeText(this, "Received DIRECTORY path from file browser: \n" + newDir,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Received NO result from file browser",
                        Toast.LENGTH_LONG).show();
            }

        }

        if (requestCode == REQUEST_CODE_PICK_FILE) {
            if (resultCode == RESULT_OK) {

                String source = data.getStringExtra(FileBrowserActivity.returnFileParameter);
                File file = new File(source);

                Toast.makeText(this, "Received FILE path from file browser:\n" + source,
                        Toast.LENGTH_LONG).show();

                String destination = this.getFilesDir().toString()+"/"+file.getName();
                File destinationFile = new File(destination);
                File sourceFile = new File(source);

                try{
                    FileUtils.copyFile(sourceFile, destinationFile);
                    Toast.makeText(this, "Copy file to "+ destination, Toast.LENGTH_LONG).show();

                    //listValues[listValues.length] = file.getName();
                    listValues.add(file.getName());

                    fileListAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, listValues);


                    //set Adapter to ListView
                    fileListView.setAdapter(fileListAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "Received NO result from file browser",
                        Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        adapter.clear();
        PersonList.clear();
        P_PersonList.clear();
        ShowPerson();
        adapter.notifyDataSetChanged();
    }
}
