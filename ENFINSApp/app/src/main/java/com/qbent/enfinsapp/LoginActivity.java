package com.qbent.enfinsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qbent.enfinsapp.global.AuthHelper;
import com.qbent.enfinsapp.model.ApiRequest;
import com.qbent.enfinsapp.model.Login;
import com.qbent.enfinsapp.restapi.ApiCallback;
import com.qbent.enfinsapp.restapi.ApiHandler;

import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity
    implements ApiCallback {

    private AuthHelper _authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _authHelper = AuthHelper.getInstance(this);

        final EditText usernameEditText = findViewById(R.id.userName);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequest apiRequest = new ApiRequest("authenticate");
                apiRequest.set_t(new Login(usernameEditText.getText().toString(), passwordEditText.getText().toString()));
                new ApiHandler.PostAsync(LoginActivity.this).execute(apiRequest);
            }
        });
    }

    @Override
    public void onApiRequestStart() throws IOException {

    }

    @Override
    public void onApiRequestComplete(String key, String result) throws IOException {
        try {
            if (key.equals("authenticate")) {
                System.out.println(result);
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("token") != null) {
                    _authHelper.setIdToken(jsonObject.getString("token"));

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
