package cra.oodp2nd;
import android.app.Activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupVariables();
        HDB = DatabaseHelper.getInstance(this);
        DB = HDB.getWritableDatabase();
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String tableName = "memberTable";
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
