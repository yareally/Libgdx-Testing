package com.badlogic.androidgames.framework.impl;

import android.content.res.AssetManager;
import android.os.Environment;
import com.badlogic.androidgames.framework.FileIO;

import java.io.*;

public class AndroidFileIO implements FileIO
{
    public AssetManager assets;
    public String       externalStoragePath;

    /**
     * 
     * @param assets
     */
    public AndroidFileIO(AssetManager assets)
    {
        this.assets = assets;
        this.externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
    }

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @Override
    public InputStream readAsset(String fileName) throws IOException
    {
        return this.assets.open(fileName);
    }

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @Override
    public InputStream readFile(String fileName) throws IOException
    {
        return new FileInputStream(this.externalStoragePath + fileName);
    }

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream writeFile(String fileName) throws IOException
    {
        return new FileOutputStream(this.externalStoragePath + fileName);
    }
} 
