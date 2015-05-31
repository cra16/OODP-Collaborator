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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class RecordAddActivity extends AbstractModelActivity implements RecordInterface {

    ArrayList<String> PersonList = new ArrayList<String>();
    ArrayList<String> P_PersonList = new ArrayList<String>();
    ArrayList<String> ADD_PersonList = new ArrayList<String>();
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
                addRowValue.put("name", name);
                addRowValue.put("date", date);
                addRowValue.put("location", location);

                sqLiteDatabase.insert(TABLE_NAME, null, addRowValue);/*
                sqLiteDatabase.query(TABLE_NAME,new String[]{"id"},"")
                addRowValue = new ContentValues();

                for(int position=0; position<ADD_PersonList.size(); position++) {
                    addRowValue.put("userId", ADD_PersonList.get(position));
                    addRowValue.put("recordId", );

                    sqLiteDatabase.insert("table_member_presented", null, addRowValue);
                }*/
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
        setSaveButton();
        setDatePicker();
        setPersonButton();
        ShowPerson();
        adapter = new PersonAdapter(this, R.layout.task_list_item,P_PersonList);

        list = (ListView) findViewById(R.id.record_view);
        list.setAdapter(adapter);

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
                contentView = vi.inflate(R.layout.task_list_item, null);
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
