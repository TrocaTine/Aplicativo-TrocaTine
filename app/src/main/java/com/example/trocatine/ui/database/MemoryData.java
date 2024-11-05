package com.example.trocatine.ui.database;


import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class MemoryData {
    public static void saveLastMsgTs(String data, String chatId, Context context){
        try{
            FileOutputStream fileOutputStream = context.openFileOutput(chatId+".txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    public static String getLastMsgTS(String chatId, Context context){
        String data = "0";
        try {
            FileInputStream fis = context.openFileInput(chatId+".txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
        }catch (IOException io){
            io.printStackTrace();
        }
        return data;
    }
    public static void saveData(String data, Context context){
        try{
            FileOutputStream fileOutputStream = context.openFileOutput("datata.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public static String getData(Context context){
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("datata.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
        }catch (IOException io){
            io.printStackTrace();
        }
        return data;
    }

    public static void saveName(String data, Context context){
        try{
            FileOutputStream fileOutputStream = context.openFileOutput("name.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public static String getName(Context context){
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("name.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
        }catch (IOException io){
            io.printStackTrace();
        }
        return data;
    }



}
