package com.package.name;

import android.content.res.AssetManager;

import java.io.InputStream;

public class FileToByteData {

    /**
     * read file to byte.读取byte格式文件为byte流
     * @param assetMan AssetManager instance, get feature of asset
     * @param path file path that you want to read
     * @return byte data
     */
    public static byte[] fileTobyte(AssetManager assetMan,String path) {
        try {
            InputStream in = assetMan.open(path);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
