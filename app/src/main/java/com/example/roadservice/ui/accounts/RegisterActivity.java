package com.example.roadservice.ui.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.accounts.RegisterRequest;
import com.example.roadservice.backend.io.accounts.RegisterResponse;
import com.example.roadservice.backend.threads.accounts.RegisterThread;
import com.example.roadservice.ui.RSAppCompatActivity;
import com.example.roadservice.ui.accounts.structs.RegisterData;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends RSAppCompatActivity {
    private ThreadPoolExecutor threadPoolExecutor;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(v -> register());

        Button loginBtn = findViewById(R.id.gotoLoginBtn);
        loginBtn.setOnClickListener(v -> startLogin());

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new RegisterHandler(Looper.getMainLooper(), this);

        setTitle("ثبت‌نام");
    }

    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void success() {
        Toast.makeText(
                this,
                getString(R.string.register_success_message),
                Toast.LENGTH_LONG
        ).show();
        startLogin();
    }

    private void register() {
        RegisterData data = collectData();
        if (data == null) return;
        RegisterRequest reqData = new RegisterRequest(
                data.firstName,
                data.lastName,
                data.phoneNumber,
                data.password
        );
        RegisterThread thread = new RegisterThread(handler, reqData);
        threadPoolExecutor.execute(thread);
        Log.d("SHIT", "Registering");
    }

    private RegisterData collectData() {
        RegisterData data = new RegisterData();
        TextInputLayout tmp;
        String error;
        boolean isValid = true;

        tmp = findViewById(R.id.phoneNumberText);
        data.phoneNumber = tmp.getEditText().getText().toString();
        error = getPhoneError(data.phoneNumber);
        isValid &= setError(tmp, error);

        tmp = findViewById(R.id.passwordText);
        data.password = tmp.getEditText().getText().toString();
        error = getPasswordError(data.password);
        isValid &= setError(tmp, error);

        tmp = findViewById(R.id.firstNameText);
        data.firstName = tmp.getEditText().getText().toString();
        error = getFirstNameError(data.firstName);
        isValid &= setError(tmp, error);

        tmp = findViewById(R.id.lastNameText);
        data.lastName = tmp.getEditText().getText().toString();
        error = getLastNameError(data.lastName);
        isValid &= setError(tmp, error);

        if (!isValid)
            return null;
        return data;
    }

    private boolean setError(TextInputLayout tmp, String error) {
        if (error == null) {
            tmp.setErrorEnabled(false);
            return true;
        }
        tmp.setError(error);
        return false;
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
        if (password.length() < 6)
            return getString(R.string.error_password_short);
        return null;
    }

    private String getFirstNameError(String firstName) {
        if (firstName.length() == 0)
            return getString(R.string.error_required);
        return null;
    }

    private String getLastNameError(String lastName) {
        if (lastName.length() == 0)
            return getString(R.string.error_required);
        return null;
    }

    private static class RegisterHandler extends Handler {
        private final WeakReference<RegisterActivity> target;

        RegisterHandler(Looper looper, RegisterActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterActivity target = this.target.get();
            if (target == null)
                return;
            Log.d("SHIT", "Received msg");
            if (msg.arg1 == RegisterResponse.CODE) {
                Log.d("SHIT", "Received msg");
                RegisterResponse resp = (RegisterResponse) msg.obj;
                if (!resp.status) {
                    // TODO show errors
                    return;
                }
                target.success();
            }
        }
    }
}