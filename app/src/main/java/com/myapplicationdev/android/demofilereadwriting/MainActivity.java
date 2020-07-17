package com.myapplicationdev.android.demofilereadwriting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
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

    String folderLocation;
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

        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";

        File folder = new File(folderLocation);
        if(!folder.exists()) {
            boolean result = folder.mkdir();

            if(result) {
                Log.d("File Read/Write", "Folder created");
            }
        }

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File targetFile = new File(folderLocation, "section_d.txt");
                    FileWriter writer = new FileWriter(targetFile, true);
                    writer.write("Hello World!" + "\n");
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File targetFile = new File(folderLocation, "section_d.txt");

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
                    textView.setText(data);
                }
            }
        });

    }
}