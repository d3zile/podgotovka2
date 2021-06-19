package com.example.podgotovka2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText log_email, log_pass;
    TextView log_reg;
    Button log_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_email = findViewById(R.id.log_email);
        log_pass = findViewById(R.id.log_pass);
        log_reg = findViewById(R.id.log_reg);
        log_btn = findViewById(R.id.log_btn);


        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(log_email.getText().toString()) || TextUtils.isEmpty(log_pass.getText().toString())) {
                    String message = "Заполните все поля";
                    Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
                } else {
                    loginUser();
                }
            }
        });

    }

    public void loginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(log_email.getText().toString());
        loginRequest.setPassword(log_pass.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {

                    String message = "Вы успешно вошли!";
                    Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String message = "Что-то пошло не так!";
                    Toast.makeText(Login.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}