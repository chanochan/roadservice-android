package com.example.roadservice.ui.accounts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.roadservice.R;
import com.example.roadservice.backend.RetrofitInstance;
import com.example.roadservice.backend.io.accounts.LoginRequest;
import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.threads.accounts.LoginThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.ui.MainActivity;
import com.example.roadservice.ui.RSAppCompatActivity;
import com.example.roadservice.ui.accounts.structs.LoginData;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends RSAppCompatActivity {
    private static final String TAG = "LoginActivity";
    private ThreadPoolExecutor threadPoolExecutor;
    private LoginHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ورود");

        findViewById(R.id.loginBtn).setOnClickListener(v -> login());
        findViewById(R.id.gotoRegisterBtn).setOnClickListener(v -> startRegister());
        findViewById(R.id.forgotPasswordQuestion).setOnClickListener(v -> gotoForgetPassword());

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new LoginHandler(Looper.getMainLooper(), this);
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
        String phoneError = getPhoneError(data.phoneNumber);
        if (phoneError != null) {
            tmp.setError(phoneError);
            return null;
        } else
            tmp.setErrorEnabled(false);

        tmp = findViewById(R.id.loginPasswordText);
        data.password = tmp.getEditText().getText().toString();
        String passwordError = getPasswordError(data.password);
        if (passwordError != null) {
            tmp.setError(passwordError);
            return null;
        } else
            tmp.setErrorEnabled(false);

        return data;
    }

    private String getPhoneError(String phoneNumber) {
        if (phoneNumber.length() == 0)
            return getString(R.string.error_required);
        if (phoneNumber.length() != 11 || !phoneNumber.startsWith("09"))
            return getString(R.string.login_error_phone_length);
        return null;
    }

    private String getPasswordError(String password) {
        if (password.length() == 0)
            return getString(R.string.error_required);
        return null;
    }

    private void login() {
        LoginData data = collectData();
        if (data == null) return;
        LoginRequest reqData = new LoginRequest(data.phoneNumber, data.password);
        LoginThread thread = new LoginThread(handler, reqData);
        threadPoolExecutor.execute(thread);
    }

    private void gotoForgetPassword() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitInstance.BASE_URL + "password/forgot/"));
        startActivity(intent);
    }

    private void onDone() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void onFailed() {
        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
    }

    private static class LoginHandler extends Handler {
        private static final String TAG = "LoginHandler";
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
                LoginResponse resp = (LoginResponse) msg.obj;
                if (resp == null) {
                    Log.d(TAG, "Null response");
                    target.onFailed();
                    return;
                }
                Database.setToken(resp.token);
                target.onDone();
            }
        }
    }
}