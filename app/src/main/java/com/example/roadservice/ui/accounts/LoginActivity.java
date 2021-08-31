package com.example.roadservice.ui.accounts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.accounts.LoginRequest;
import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.threads.accounts.LoginThread;
import com.example.roadservice.ui.MainActivity;
import com.example.roadservice.ui.RSAppCompatActivity;
import com.example.roadservice.ui.accounts.structs.LoginData;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends RSAppCompatActivity {
    ThreadPoolExecutor threadPoolExecutor;
    private LoginHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginBtn).setOnClickListener(v -> {
            System.out.println("HIIII");
            login();
        });
        findViewById(R.id.gotoRegisterBtn).setOnClickListener(v -> startRegister());
        findViewById(R.id.forgotPasswordQuestion).setOnClickListener(v -> gotoForgetPassword());

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new LoginHandler(Looper.getMainLooper(), this);

        setTitle("ورود");
    }

    private void startRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private LoginData collectData() {
        LoginData data = new LoginData();
        TextInputLayout tmp = findViewById(R.id.loginPhoneText);
        data.phoneNumber = tmp.getEditText().getText().toString();
        tmp = findViewById(R.id.loginPasswordText);
        data.password = tmp.getEditText().getText().toString();
        Log.d("SHIT", data.phoneNumber + " " + data.password);
        return data;
    }

    private void login() {
        LoginData data = collectData();
        LoginRequest reqData = new LoginRequest(data.phoneNumber, data.password);
        LoginThread thread = new LoginThread(handler, reqData);
        threadPoolExecutor.execute(thread);
    }

    private void gotoForgetPassword() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    private void loginDone() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private static class LoginHandler extends Handler {
        private final WeakReference<LoginActivity> target;

        LoginHandler(Looper looper, LoginActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 == LoginResponse.CODE) {
                // TODO handle login errors
                LoginResponse resp = (LoginResponse) msg.obj;
                if (resp == null) {
                    Log.d("SHIT", "Empty response");
                    return;
                }
                SharedPreferences sp = target.getSharedPreferences(
                        target.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("AUTH", resp.token);
                editor.apply();
                target.loginDone();
            }
        }
    }
}