package cra.oodp2nd;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RecordUpdateActivity extends AbstractModelActivity implements RecordInterface {

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

                String title = titleEditText.getText().toString();
                String name = NameEditText.getText().toString();
                String date = dateEditText.getText().toString();

                ContentValues updateRowValue = new ContentValues();

                updateRowValue.put("title", title);
                updateRowValue.put("name", name);
                updateRowValue.put("date", date);

                sqLiteDatabase.update(TABLE_NAME, updateRowValue, "id=" + id, null);

                Intent intent = new Intent(getApplicationContext(), RecordViewActivity.class);
                startActivity(intent);
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
    }

    private void getRecordTitle() {
        String[] columns = {"title","name","date"};

        Cursor result = sqLiteDatabase.query(TABLE_NAME, columns, "id=" + id, null, null, null, null);

        result.moveToFirst();
        String title = result.getString(0);
        String name = result.getString(1);
        String date = result.getString(2);

        EditText titleEditText = (EditText) findViewById(R.id.edit_text_record_title);
        EditText nameEditText = (EditText)findViewById(R.id.edit_text_record_name);
        EditText dateEditText = (EditText)findViewById(R.id.edit_text_record_date);

        titleEditText.setText(title);
        nameEditText.setText(name);
        dateEditText.setText(date);
        result.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_update, menu);
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
