package com.grupo2.iomt.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.grupo2.iomt.R;

/**
 * The type Alert activity.
 * @author Richard Ruales
 * @version 1.0
 */
public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
    }

    /**
     * Retroceder.
     *
     * @param v the v
     */
    public void retroceder(View v){
        AlertActivity.super.onBackPressed();

    }

}
