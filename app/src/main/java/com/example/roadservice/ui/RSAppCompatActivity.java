package com.example.roadservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.roadservice.R;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Profile;
import com.example.roadservice.ui.accounts.ChangePasswordActivity;
import com.example.roadservice.ui.issues.citizen.CitizenDashboardActivity;
import com.example.roadservice.ui.issues.specialist.SpecialistDashboardActivity;
import com.example.roadservice.ui.issues.team.TeamDashboardActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class RSAppCompatActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        MaterialToolbar appBar = findViewById(R.id.topAppBar);
        if (appBar == null)
            return;
        appBar.setTitle(title);
    }

    public void setupNavigationDrawer() {
        MaterialToolbar toolBar = findViewById(R.id.topAppBar);
        if (toolBar == null) return;

        drawerLayout = findViewById(R.id.drawerLayout);
        if (drawerLayout == null) return;
        toolBar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView = findViewById(R.id.navigationView);
        if (navigationView == null) return;
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.isChecked()) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            Class<?> dest;
            if (item.getItemId() == R.id.dashboardNavigationItem) {
                String role = Database.getProfile().getRole();
                if (role.equals("CZ"))
                    dest = CitizenDashboardActivity.class;
                else if (role.equals("SM"))
                    dest = TeamDashboardActivity.class;
                else
                    dest = SpecialistDashboardActivity.class;
            } else if (item.getItemId() == R.id.passwordNavigationItem) {
                dest = ChangePasswordActivity.class;
            } else if (item.getItemId() == R.id.logoutNavigationItem) {
                Database.setToken(null);
                dest = MainActivity.class;
            } else
                dest = this.getClass();
            Intent intent = new Intent(this, dest);
            startActivity(intent);
            finish();
            return true;
        });

        View navigationHeader = navigationView.getHeaderView(0);
        Profile profile = Database.getProfile();
        TextView nameTextView = navigationHeader.findViewById(R.id.navNameText);
        nameTextView.setText(profile.getName());
        TextView phoneTextView = navigationHeader.findViewById(R.id.navPhoneText);
        phoneTextView.setText(profile.getPhone());
    }

    public Class<?> getDashboardClass() {
        Profile profile = Database.getProfile();
        if (profile == null)
            return MainActivity.class;
        switch (profile.getRole()) {
            case "CZ":
                return CitizenDashboardActivity.class;
            case "SM":
                return TeamDashboardActivity.class;
            case "CE":
                return SpecialistDashboardActivity.class;
            default:
                return MainActivity.class;
        }
    }

    public void openDashboard() {
        Intent intent = new Intent(this, getDashboardClass());
        startActivity(intent);
        finish();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null)
            view = new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
