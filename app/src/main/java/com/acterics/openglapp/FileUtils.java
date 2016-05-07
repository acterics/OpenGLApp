package com.acterics.openglapp;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by User on 25.04.2016.
 */
public class FileUtils {

    public static String readTextFromRaw(Context context, int resourceId) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream =
                        context.getResources().openRawResource(resourceId);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\r\n");
                }
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Resources.NotFoundException nfex) {
            nfex.printStackTrace();
        }

        return stringBuilder.toString();

    }

    public static ArrayList<Float> parseModel(String source) {
        ArrayList<Float> vertices = new ArrayList<>();
        String sValue;
        float value;

        source = source.replaceAll("\n", " ");

        for (int i = 0; i < source.length(); ) {
            try {
                sValue = source.substring(i, source.indexOf(' ', i));
                i = source.indexOf(' ', i) + 1;
            } catch (StringIndexOutOfBoundsException e) {
                sValue = source.substring(i);
                i = source.length();
            }

            value = Float.valueOf(sValue);
            vertices.add(value);

        }


        return vertices;
    }
}
