package com.paprika.practicarecursos;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GuardarArchivo {
    public static void guardarArchivo(Context context, String fileName, String contenido) {
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(dir, fileName + ".txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(contenido.getBytes());
            fos.close();
            Toast.makeText(context, "Archivo guardado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
