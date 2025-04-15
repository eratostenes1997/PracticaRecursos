package com.paprika.practicarecursos;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    Button btnEnable;
    Button btnDisable;
    Button btnPaired;
    ListView listView;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        initViews();
        initBluetooth();
        btnEnable.setOnClickListener(this::onEnableClick);
        btnDisable.setOnClickListener(this::onDisableClick);
        btnPaired.setOnClickListener(this::onPairedClick);
    }

    void initViews() {
        btnEnable = findViewById(R.id.btnEnable);
        btnDisable = findViewById(R.id.btnDisable);
        btnPaired = findViewById(R.id.btnPaired);
        listView = findViewById(R.id.listDevices);
    }

    void initBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 101);
            }
        }
    }

    void onEnableClick(android.view.View view) {
        enableBluetooth();
    }

    void onDisableClick(android.view.View view) {
        disableBluetooth();
    }

    void onPairedClick(android.view.View view) {
        showPairedDevices();
    }

    void enableBluetooth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Se requieren permisos para activar el Bluetooth", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, 1);
        }
    }

    void disableBluetooth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Se requieren permisos para desactivar el Bluetooth", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        }
    }

    void showPairedDevices() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Se requieren permisos para ver los dispositivos vinculados", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        ArrayList<String> list = new ArrayList<>();
        for (BluetoothDevice device : devices) {
            list.add(device.getName() + "\n" + device.getAddress());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permisos de Bluetooth concedidos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permisos de Bluetooth denegados", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
