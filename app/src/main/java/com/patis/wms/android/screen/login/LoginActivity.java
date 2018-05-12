package com.patis.wms.android.screen.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.patis.wms.android.App;
import com.patis.wms.android.MainActivity;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.WorkerDTO;

import java.io.IOException;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {



    private EditText edLogin;
    private EditText edPassword;
    private Button btnLogin;

    UserLoginTask loginTask;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edLogin = findViewById(R.id.edLogin);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);



        if(App.local().getString("password") != null){
            loginTask = new UserLoginTask(App.local().getString("login"), App.local().getString("password"));
            loginTask.execute((Void[]) null);
        }


        btnLogin.setOnClickListener(e -> {
            loginTask = new UserLoginTask(edLogin.getText().toString(), edPassword.getText().toString());
//            progressDialog = new ProgressDialog(LoginActivity.this);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Авторизация...");
//            progressDialog.show();
            loginTask.execute((Void[]) null);
        });


    }


    void login(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }





    public class UserLoginTask extends AsyncTask<Void, Void, Long> {

        private final String mLogin;
        private final String mPassword;




        UserLoginTask(String login, String password) {
            mLogin = login;
            mPassword = password;
        }

        @Override
        protected Long doInBackground(Void... params) {

            WorkerDTO workerDTO = null;
            try {
                workerDTO = App.getBackendApi().authorization(mLogin, mPassword).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(workerDTO != null){
                return workerDTO.getId();
            }else{
                return 0L;
            }


        }

        @Override
        protected void onPostExecute(final Long workerId) {

//            progressDialog.dismiss();

            if(workerId != 0){
                App.local().putString("login", mLogin);
                App.local().putString("password", mPassword);
                App.local().putLong("currentUserId", workerId);
                login();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

}

