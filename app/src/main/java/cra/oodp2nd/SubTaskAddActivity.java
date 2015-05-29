package cra.oodp2nd;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 현우 on 2015-05-28.
 */
public class SubTaskAddActivity extends AbstractModelActivity{
    protected int T_ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(Window.FEATURE_NO_TITLE, Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subtask_add_update);
        Bundle bundle = getIntent().getExtras();
        T_ID = bundle.getInt("T_ID");

        setSaveButton();

    }


    @Override
    protected void setSaveButton() {
        Button saveButton = (Button) findViewById(R.id.button_sub_add_update);
        saveButton.setText("Save");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.edit_text_subtask_title);
                String title = titleEditText.getText().toString();
                ContentValues addRowValue = new ContentValues();


                addRowValue.put("title", title);
                addRowValue.put("titleId", T_ID);

                sqLiteDatabase.insert("table_subtask", null, addRowValue);

                finish();

            }
        });
    }
    @Override
    protected void setUpdateButton() {

    }

    @Override
    protected Activity getThisActivity() {
        return null;
    }

    @Override
    protected int getLayout() {
        return 0;
    }


}
