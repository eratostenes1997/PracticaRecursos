package com.paprika.practicarecursos;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;

    // Versión Android
    private TextView versionAndroid;
    private int versionSDK;

    private ProgressBar pbLevelBaterry;
    private TextView tvLevelBaterry;
    private IntentFilter baterryFilter;

    private TextView tvConexion;
    private ConnectivityManager conexion;

    private CameraManager cameraManager;
    private String cameraId;
    private Button onFlash;
    private Button offFlash;

    // File
    private EditText nameFile;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();

        onFlash.setOnClickListener(this::onLigth);
        offFlash.setOnClickListener(this::offLigth);

        baterryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    private void checkConnection(){

    }

    @Override
    protected void onResume() {
        super.onResume();

        String versionSO = Build.VERSION.RELEASE;
        versionSDK = Build.VERSION.SDK_INT;
        versionAndroid.setText("versión SO: "+versionSO+"  /SDK: " +versionSDK);
    }

    private void initObjects() {
        this.context = getApplicationContext();
        this.activity = this;
        this.versionAndroid = findViewById(R.id.tvVersionAndroid);
        this.pbLevelBaterry = findViewById(R.id.pbLevelBattery);
        this.tvLevelBaterry = findViewById(R.id.tvLevelBatteryLB);
        this.tvConexion = findViewById(R.id.tvState);
        this.nameFile = findViewById(R.id.etNameFile);
        this.onFlash = findViewById(R.id.btnOn);
        this.offFlash = findViewById(R.id.btnOff);
    }

    private void onLigth(View view) {

    }

    private void offLigth(View view) {

    }
}
