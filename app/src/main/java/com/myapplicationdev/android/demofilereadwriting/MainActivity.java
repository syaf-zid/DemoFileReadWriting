package com.myapplicationdev.android.demofilereadwriting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnWrite, btnRead;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        textView = findViewById(R.id.textView);

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        // * Start of SECTION C *//
        // Data Persistence -- Internal Storage
        // String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";

        // File folder_I = new File(folderLocation_I);
        // if(!folder_I.exists()) {
            // boolean result = folder_I.mkdir();
            // if(result) {
                // Log.d("File Read/Write", "Folder created");
            // }
        // }

        try {
            String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
            File targetFile_I = new File(folderLocation_I, "test.txt");
            FileWriter writer_I = new FileWriter(targetFile_I, true);
            writer_I.write("test data" + "\n");
            writer_I.flush();
            writer_I.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Data Persistence -- File Reading
        String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Test";
        File targetFile = new File(folderLocation, "data.txt");

        if(targetFile.exists()) {
            String data = "";
            try {
                FileReader reader = new FileReader(targetFile);
                BufferedReader br = new BufferedReader(reader);

                String line = br.readLine();
                while(line != null) {
                    data += line + "\n";
                    line = br.readLine();
                }

                br.close();
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            Log.d("Content", data);
        }
    }
}