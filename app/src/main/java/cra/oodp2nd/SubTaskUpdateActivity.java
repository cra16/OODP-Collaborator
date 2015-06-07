package cra.oodp2nd;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

                CheckBox check = (CheckBox)findViewById(R.id.checkbox_completed);

                ContentValues updateRowValue = new ContentValues();

                int checked=0;
                if(check.isChecked())
                    checked=1;

                updateRowValue.put("title", title);
                updateRowValue.put("userId", name);
                updateRowValue.put("clear",  checked);
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

