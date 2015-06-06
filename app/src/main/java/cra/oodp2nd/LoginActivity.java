package cra.oodp2nd;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    protected static SQLiteDatabase DB;
    protected DatabaseHelper HDB;

    private EditText userId;
    private EditText password;
    private Button login;
    private Button signUp;
    private TextView textViewLoginLocked;
    private TextView textViewAttemptsLeft;
    private TextView textViewRemaingLoginAttempts;
    int remainingLoginAttempts = 3;

    public static class OptionInformaiton {
        static int option_color = -1;
        // 추후 옵션이 추가되면 여기에 넣어서 다른 클래스들로도 공유하면 됨
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupVariables();
        HDB = DatabaseHelper.getInstance(this);
        DB = HDB.getWritableDatabase();

        int which = LoginActivity.OptionInformaiton.option_color;
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

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String tableName = "table_member";
                String[] columns = new String[]{"id", "userId", "password"};
                if(userId.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please input ID/PS correctly",Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] params = new String[]{userId.getText().toString()};
                Cursor result = DB.query(tableName, columns, "userID=?" , params, null, null, null);
                result.moveToFirst();
                try{
                    if (userId.getText().toString().equals(result.getString(1)) && password.getText().toString().equals(result.getString(2))) {
                        Toast.makeText(getApplicationContext(), "The User \'" + userId.getText().toString() + "\' login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userId", userId.getText().toString());
                        startActivity(intent);
                        return;
                    }
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No valid user", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Login Failed. Please retry", Toast.LENGTH_SHORT).show();
                remainingLoginAttempts--;
                textViewAttemptsLeft.setVisibility(View.VISIBLE);
                textViewRemaingLoginAttempts.setVisibility(View.VISIBLE);
                textViewRemaingLoginAttempts.setText(Integer.toString(remainingLoginAttempts));

                if(remainingLoginAttempts == 0){
                    login.setEnabled(false);
                    textViewLoginLocked.setVisibility(View.VISIBLE);
                    textViewLoginLocked.setBackgroundColor(Color.RED);
                    textViewLoginLocked.setText("LOGIN LOCKED");
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupVariables() {
        userId = (EditText) findViewById(R.id.editTextUserId);
        password = (EditText) findViewById(R.id.editTextPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        signUp = (Button) findViewById(R.id.buttonSignUp);
        textViewLoginLocked = (TextView) findViewById(R.id.textViewUserId);
        textViewAttemptsLeft = (TextView) findViewById(R.id.textViewAttemptsLeft);
        textViewRemaingLoginAttempts = (TextView) findViewById(R.id.textViewRemainingLoginAttempts);
        textViewRemaingLoginAttempts.setText(Integer.toString(remainingLoginAttempts));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
                OptionInformaiton.option_color = which;
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                which = OptionInformaiton.option_color;
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
