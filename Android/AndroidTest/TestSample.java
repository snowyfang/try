package com.package.name;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Rule
public Timeout globalTimeout = new Timeout(10, TimeUnit.MINUTES);

@RunWith(AndroidJUnit4.class)
public class OnlineSilentLivenessApiInputTest extends C {
    public static final String FILES_PATH = InstrumentationRegistry.getContext().getFilesDir().getAbsolutePath() + "/";
    public static final String LICENSE_FILE_NAME = "licenes.lic";
    
    boolean mInputData = false;
    AssetManager assetMan = InstrumentationRegistry.getContext().getAssets();
    String[] mFiles;

    @Before
    public void setAssets() {
        Context context = InstrumentationRegistry.getContext();
        FileUtil.copyAssetsToFile(context, LICENSE_FILE_NAME,
                context.getFilesDir().getAbsolutePath() + "/" + LICENSE_FILE_NAME);
    }

    @Test
    public void inputPassTest() {

        Size mpreviewsize = new Size(800, 400);
        Rect containerRect = new Rect(0, 100, 600, 400);
        int cameraOrientation = 90;
        final Object lock = new Object();
        {
            try {
                mFiles = assetMan.list("munname/passPicture");
                Log.e("test", "mFiles: " + mFiles.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       Listener listener = new Listener() {

            @Override
            public void onInit() {
                Assert.assertEquals(true, true);
                Log.e("test", "onInitis running ! ");
                mInputData = true;
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        };

        try {

            Api.init(InstrumentationRegistry.getContext(), key, secret,
                    FILES_PATH + LICENSE_FILE_NAME, FILES_PATH + MODEL_FILE_NAME, listener);
            synchronized (lock) {
                lock.wait();
            }

            Log.e("test", "mInputData afterlock = " + mInputData);
            while (mInputData) {
                Log.e("test", "while start!!!");
                if (mFiles == null) {
                    Assert.assertEquals(false, true);
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                    Log.e("test", "mFiles is null");
                } else {
                    Log.e("test", "mFiles length = " + mFiles.length);
                    for (String fileName : mFiles) {
                        if (mInputData && !"INFO".equals(fileName)) {
                            Api.inputData(
                                    FileToByteData.fileTobyte(assetMan, "munname/passPicture/" + fileName),
                                    PixelFormat.NV21, mpreviewsize, containerRect, true, cameraOrientation);
                            Log.e("test", "inputDataing!filename: " + fileName);
                            SystemClock.sleep(200);
                        } else {
                            return;
                        }
                    }
                }
            }
            synchronized (lock) {
                lock.wait();
            }
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), false, true);
        }
    }

   
    @After
    public void cancelTest() {
        Api.cancel();
        SystemClock.sleep(2000);
    }
}
