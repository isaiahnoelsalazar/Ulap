package com.salazarisaiahnoel.ulap;

import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;

import com.salazarisaiahnoel.ulap.fragments.LoginFragment;
import com.salazarisaiahnoel.ulap.fragments.RegisterFragment;
import com.salazarisaiahnoel.ulap.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {

    public static SplashFragment splashFragment;
    public static LoginFragment loginFragment;
    public static RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setNavigationBarColor(getColor(R.color.white));

        splashFragment = new SplashFragment();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        splashFragment.setExitTransition(new Slide(Gravity.START));
        loginFragment.setEnterTransition(new Slide(Gravity.END).setStartDelay(250));
        loginFragment.setExitTransition(new Slide(Gravity.START));
        registerFragment.setEnterTransition(new Slide(Gravity.BOTTOM).setStartDelay(250));
        registerFragment.setExitTransition(new Slide(Gravity.BOTTOM));

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, splashFragment).commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, loginFragment).commit();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().setStatusBarColor(getColor(R.color.blue));
                        getWindow().setNavigationBarColor(getColor(R.color.black));
                        loginFragment.setEnterTransition(new Slide(Gravity.TOP).setStartDelay(250));
                        loginFragment.setExitTransition(new Slide(Gravity.TOP));
                    }
                }, 250);
            }
        }, 1500);
    }

    @Override
    public void onBackPressed() {
        if (!(getSupportFragmentManager().findFragmentById(R.id.main_frame_layout) instanceof LoginFragment)){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, loginFragment).commit();
        } else {
            super.onBackPressed();
        }
    }
}