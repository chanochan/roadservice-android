package com.example.roadservice.ui.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadservice.R;
import com.example.roadservice.ui.accounts.structs.LoginData;
import com.example.roadservice.ui.issues.citizen.CitizenDashboardActivity;
import com.example.roadservice.ui.issues.specialist.SpecialistDashboardActivity;
import com.example.roadservice.ui.issues.team.TeamDashboardActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("HIIII");
                login();
            }
        });

        Button registerBtn = findViewById(R.id.gotoRegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

        TextView forgetPasswordText = findViewById(R.id.forgotPasswordQuestion);
        forgetPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoForgetPassword();
            }
        });
    }

    private void startRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private LoginData collectData() {
        LoginData data = new LoginData();
        EditText tmp = findViewById(R.id.phoneText);
        data.phoneNumber = tmp.getText().toString();
        tmp = findViewById(R.id.passwordText);
        data.password = tmp.getText().toString();
        return data;
    }

    private void login() {
        LoginData data = collectData();
        System.out.println("YEEEES");
        System.out.println(data.phoneNumber.length());
        System.out.println("NOOOO");
        if (data.phoneNumber.startsWith("0912"))
            gotoDashboard(SpecialistDashboardActivity.class);
        else if (data.phoneNumber.startsWith("0919"))
            gotoDashboard(TeamDashboardActivity.class);
        else if (data.phoneNumber.startsWith("0936"))
            gotoDashboard(CitizenDashboardActivity.class);
    }

    private void gotoDashboard(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    private void gotoForgetPassword() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }
}