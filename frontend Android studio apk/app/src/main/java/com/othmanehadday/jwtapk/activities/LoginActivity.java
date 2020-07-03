package com.othmanehadday.jwtapk.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.JWT;
import com.othmanehadday.jwtapk.R;
import com.othmanehadday.jwtapk.model.User;
import com.othmanehadday.jwtapk.services.AuthentcationService;
import com.othmanehadday.jwtapk.services.SharedPref;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewGoRegister, textViewErrorLogin;
    private ProgressDialog progressdialog;

    public int status_code;
    public String jwtToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPref sharedPref = new SharedPref(getApplicationContext());


        if (sharedPref.isAuthenticated()) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }


        editTextUsername = findViewById(R.id.editTextUsernameLogin);
        editTextPassword = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewGoRegister = findViewById(R.id.textViewGoToRegister);
        textViewErrorLogin = findViewById(R.id.textViewErrorLogin);

        buttonLogin.setOnClickListener((event) -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            if (username == null || username.isEmpty()) {
                editTextUsername.setError("Username feild is empty");
                return;
            }
            if (password == null || password.isEmpty()) {
                editTextPassword.setError("Password feild is empty");
                return;
            }


            progressdialog = new ProgressDialog(LoginActivity.this);
            progressdialog.setMessage("Loading..."); // Setting Message
            progressdialog.setTitle("ProgressDialog"); // Setting Title
            progressdialog.setCancelable(false);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressdialog.show(); // Display Progress Dialog

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            //ok http post called
            loginOkHttp(LoginActivity.this, user, sharedPref);


        });

        textViewGoRegister.setOnClickListener((event) -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }


    public void loginOkHttp(Context context, User user, SharedPref sharedPref) {
        Log.d("okhttp", "Post fun called");
        String url = AuthentcationService.host + "/login";

        OkHttpClient client = new OkHttpClient();
        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        JSONObject data = new JSONObject();
        try {
            data.put("username", user.getUsername());
            data.put("password", user.getPassword());
        } catch (JSONException e) {
            Log.d("okhttp", "json exception");
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, data.toString());
        Log.d("okhttp", "request body created");

        Request newReq = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(newReq).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("okhttp", "excepton request----------" + e.getMessage());
                e.printStackTrace();


                progressdialog.dismiss();

                runOnUiThread(() -> {
                    textViewErrorLogin.setVisibility(View.VISIBLE);
                    textViewErrorLogin.setText("conxion error");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("okhttp", "got response");
                Log.d("okhttp", "response : " + response.code());
                Log.d("okhttp", "response : " + response.header("authorization"));

                status_code = response.code();
                jwtToken = response.header("authorization");
                progressdialog.dismiss();

                if (status_code == 200) {
                    if (jwtToken != null) {
                        if (jwtToken.contains("Bearer ")) {
                            jwtToken = jwtToken.replace("Bearer ", "");
                        }
                        JWT jwt = new JWT(jwtToken);
                        if (!jwt.isExpired(0) && "auth0".equals(jwt.getIssuer())) {
                            Log.d("okhttp", "jwt expired ");
                        }
                    }

                    sharedPref.storeToken(jwtToken);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (status_code == 403) {
                    Log.d("okhttp", "information not valid ");

                    runOnUiThread(() -> {
                        textViewErrorLogin.setVisibility(View.VISIBLE);
                        textViewErrorLogin.setText("Bad Credentials ...");

                    });
                }

            }
        });
    }

}
