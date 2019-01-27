package com.andrius.pathtracking.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

    public static String readFile(File file) {
        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
        } catch (IOException e) {
            AppLog.d(e.toString());
            return "";
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e ) {
                AppLog.d(e.toString());
            }
        }
        return new String(bytes);
    }
}
