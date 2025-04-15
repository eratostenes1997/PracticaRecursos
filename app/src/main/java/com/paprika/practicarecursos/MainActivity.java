package com.paprika.practicarecursos;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Context context;

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

    private EditText nameFile;

    private Button btnOpenCamera;
    private Button btnOpenBluetooth;
    private Button btnSaveFile;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
        initBatteryReceiver();
        onFlash.setOnClickListener(this::onLigth);
        offFlash.setOnClickListener(this::offLigth);
        btnSaveFile.setOnClickListener(this::saveInfoToFile);
        btnOpenCamera.setOnClickListener(this::openCameraActivity);
        btnOpenBluetooth.setOnClickListener(this::openBluetoothActivity);
    }

    private void initObjects() {
        context = getApplicationContext();
        versionAndroid = findViewById(R.id.tvVersionAndroid);
        pbLevelBaterry = findViewById(R.id.pbLevelBattery);
        tvLevelBaterry = findViewById(R.id.tvLevelBatteryLB);
        tvConexion = findViewById(R.id.tvState);
        nameFile = findViewById(R.id.etNameFile);
        onFlash = findViewById(R.id.btnOn);
        offFlash = findViewById(R.id.btnOff);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        btnOpenBluetooth = findViewById(R.id.btnOpenBluetooth);
        btnSaveFile = findViewById(R.id.btnSaveFile);
    }


    private void initBatteryReceiver() {
        baterryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broReceiver, baterryFilter);
    }

    BroadcastReceiver broReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int levelBattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            pbLevelBaterry.setProgress(levelBattery);
            tvLevelBaterry.setText("level battery  " + levelBattery + "%");
        }
    };

    private void checkConnection() {
        try {
            conexion = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conexion != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Network activeNetwork = conexion.getActiveNetwork();
                    if (activeNetwork != null) {
                        tvConexion.setText("State ON");
                    } else {
                        tvConexion.setText("State OFF or No Info");
                    }
                } else {
                    NetworkInfo networkInfo = conexion.getActiveNetworkInfo();
                    boolean stateNet = networkInfo != null && networkInfo.isConnectedOrConnecting();
                    if (stateNet) {
                        tvConexion.setText("State ON");
                    } else {
                        tvConexion.setText("State OFF or No Info");
                    }
                }
            }
        } catch (Exception e) {
            Log.d("CONEXION", e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String versionSO = Build.VERSION.RELEASE;
        versionSDK = Build.VERSION.SDK_INT;
        versionAndroid.setText("versión SO: " + versionSO + "  /SDK: " + versionSDK);
        checkConnection();
    }

    private void onLigth(View view) {
        try {
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (Exception e) {
            Log.i("linterna", e.getMessage());
        }
    }

    private void offLigth(View view) {
        try {
            cameraManager.setTorchMode(cameraId, false);
        } catch (Exception e) {
            Log.i("linterna", e.getMessage());
        }
    }

    private void saveInfoToFile(View view) {
        String fileName = nameFile.getText().toString().trim();

        if (fileName.isEmpty()) {
            Toast.makeText(this, "Enter a file name", Toast.LENGTH_SHORT).show();
            return;
        }

        String battery = tvLevelBaterry.getText().toString();
        String androidVersion = "versión SO: " + Build.VERSION.RELEASE + " / SDK: " + Build.VERSION.SDK_INT;

        String contenido = "Nombre del estudiante: " + fileName + "\n"
                + "Nivel de batería: " + battery + "\n"
                + "Versión Android: " + androidVersion;

        GuardarArchivo.guardarArchivo(this, fileName, contenido);
    }

    private void openCameraActivity(View view) {
        startActivity(new Intent(this, CameraActivity.class));
    }

    private void openBluetoothActivity(View view) {
        startActivity(new Intent(this, BluetoothActivity.class));
    }
}
