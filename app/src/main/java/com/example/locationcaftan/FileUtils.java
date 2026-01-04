package com.example.locationcaftan;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    public static File getFile(Context context, Uri uri) {
        String fileName = getFileName(context, uri);

        File tempFile = new File(context.getCacheDir(), fileName);
        tempFile.deleteOnExit();

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }

            outputStream.close();
            inputStream.close();

            return tempFile;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getFileName(Context context, Uri uri) {
        String result = null;

        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            result = cursor.getString(nameIndex);
            cursor.close();
        }

        if (result == null) {
            result = "image_" + System.currentTimeMillis() + ".jpg";
        }

        return result;
    }
}