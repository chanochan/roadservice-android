package com.example.roadservice.ui.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.accounts.RegisterRequest;
import com.example.roadservice.backend.io.accounts.RegisterResponse;
import com.example.roadservice.backend.threads.accounts.RegisterThread;
import com.example.roadservice.ui.accounts.structs.RegisterData;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

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
        // TODO show success message
        startLogin();
    }

    private void register() {
        RegisterData data = collectData();
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
        EditText tmp = findViewById(R.id.phoneNumberText);
        data.phoneNumber = tmp.getText().toString();
        tmp = findViewById(R.id.passwordText);
        data.password = tmp.getText().toString();
        tmp = findViewById(R.id.firstNameText);
        data.firstName = tmp.getText().toString();
        tmp = findViewById(R.id.lastNameText);
        data.lastName = tmp.getText().toString();
        return data;
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