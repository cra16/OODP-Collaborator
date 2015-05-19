package cra.oodp2nd;

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


public class TaskUpdateActivity extends AbstractModelActivity implements TaskInterface {

    protected int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add_update);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("p_id");
        getTaskTitle();

        setUpdateButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_update, menu);
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
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_task_title);
                String title = titleEditText.getText().toString();
                ContentValues updateRowValue = new ContentValues();

                updateRowValue.put("title", title);
                sqLiteDatabase.update(TABLE_NAME, updateRowValue, "id=" + id, null);

                Intent intent = new Intent(getApplicationContext(), TaskViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getTaskTitle() {
        String[] columns = {"title"};

        Cursor result = sqLiteDatabase.query(TABLE_NAME, columns, "id=" + id, null, null, null, null);

        result.moveToFirst();
        String title = result.getString(0);

        EditText titleEditText = (EditText) findViewById(R.id.edit_text_task_title);
        titleEditText.setText(title);

        result.close();
    }

}
