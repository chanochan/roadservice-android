package com.example.roadservice.ui.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.accounts.ChangePasswordRequest;
import com.example.roadservice.backend.io.accounts.ChangePasswordResponse;
import com.example.roadservice.backend.threads.accounts.ChangePasswordThread;
import com.example.roadservice.ui.RSAppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChangePasswordActivity extends RSAppCompatActivity {
    private static final String TAG = "ChangePasswordActivity";
    private ThreadPoolExecutor executor;
    private ChangePasswordHandler handler;
    private TextInputLayout oldLayout, newLayout, repeatLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setupNavigationDrawer();
        setTitle("تغییر گذرواژه");
        navigationView.setCheckedItem(R.id.passwordNavigationItem);

        findViewById(R.id.changePasswordButton).setOnClickListener(v -> submit());

        oldLayout = findViewById(R.id.oldPasswordInput);
        newLayout = findViewById(R.id.newPasswordInput);
        repeatLayout = findViewById(R.id.repeatPasswordInput);

        executor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new ChangePasswordHandler(Looper.getMainLooper(), this);
    }

    private void submit() {
        String oldPassword, newPassword, repeatPassword;
        oldPassword = oldLayout.getEditText().getText().toString();
        newPassword = newLayout.getEditText().getText().toString();
        repeatPassword = repeatLayout.getEditText().getText().toString();

        boolean isValid = true;

        String error = getPasswordError(newPassword, oldPassword);
        if (error != null) {
            newLayout.setError(error);
            isValid = false;
        } else
            newLayout.setErrorEnabled(false);

        if (!newPassword.equals(repeatPassword)) {
            repeatLayout.setError(getString(R.string.error_password_mismatch));
            isValid = false;
        } else
            repeatLayout.setErrorEnabled(false);

        if (!isValid)
            return;

        oldLayout.setErrorEnabled(false);

        ChangePasswordRequest req = new ChangePasswordRequest(oldPassword, newPassword);
        ChangePasswordThread thread = new ChangePasswordThread(handler, req);
        executor.execute(thread);
    }

    private String getPasswordError(String newPassword, String oldPassword) {
        if (newPassword.length() == 0)
            return getString(R.string.error_required);
        if (newPassword.length() < 6)
            return getString(R.string.error_password_short);
        if (oldPassword.equals(newPassword))
            return getString(R.string.error_password_match);
        return null;
    }

    private void onDone() {
        oldLayout.setErrorEnabled(false);

        Intent intent = new Intent(this, getDashboardClass());
        startActivity(intent);
        finish();
    }

    private void onFailure() {
        oldLayout.setError(getString(R.string.error_password_wrong));
    }

    private static class ChangePasswordHandler extends Handler {
        private static final String TAG = "ChangePasswordHandler";
        private final WeakReference<ChangePasswordActivity> target;

        ChangePasswordHandler(Looper looper, ChangePasswordActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            ChangePasswordActivity target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 == ChangePasswordResponse.CODE) {
                // TODO wrong password match
                ChangePasswordResponse resp = (ChangePasswordResponse) msg.obj;
                if (resp == null) {
                    Log.d(TAG, "Empty response");
                    return;
                }
                if (resp.status) {
                    target.onDone();
                    return;
                } else
                    target.onFailure();
                Log.d(TAG, "False status");
            }
        }
    }
}