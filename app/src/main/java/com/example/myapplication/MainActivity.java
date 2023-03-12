package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.myapplication.Screen.LoginFragment;
import com.example.myapplication.Screen.SplashFragment;
import com.example.myapplication.model.ThuThu;

import myapplication.R;

public class MainActivity extends AppCompatActivity {
    public static ThuThu admin;
    public static boolean dieuKien = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin = new ThuThu();
        if (dieuKien) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SplashFragment()).commit();
            dieuKien = false;
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();

        }

    }

    public static void hideSoftKeyboard(Activity activity)
    {
        View view = activity.getCurrentFocus();
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
}