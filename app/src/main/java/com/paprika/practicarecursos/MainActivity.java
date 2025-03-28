package com.paprika.practicarecursos;

import android.os.Bundle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Context context;
    private Activity activity;
    private TextView tvVersionAndroid;
    private TextView tvNivel;
    private static final int codigo_respuesta_bt = 1;
    private static final int codigo_habilitatBT = 100;
    private BluetoothAdapter BTadaptador;
    private boolean bandera = false;
    private IntentFilter batteryFilter;
    private EditText nombreArchivo;
    private ImageButton btnGuardarArchivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvVersionAndroid = findViewById(R.id.tvVersionAndroid);
        tvNivel = findViewById(R.id.tvNivel);
        nombreArchivo = findViewById(R.id.etNombreArchivo);
        btnGuardarArchivo = findViewById(R.id.btnGuardarArchivo);
        BTadaptador = BluetoothAdapter.getDefaultAdapter();
    }
}
