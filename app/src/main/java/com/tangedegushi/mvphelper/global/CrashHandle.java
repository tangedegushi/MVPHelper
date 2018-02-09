package com.tangedegushi.mvphelper.global;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zq on 2017/8/24.
 */

public class CrashHandle implements Thread.UncaughtExceptionHandler {

    private Context mContext;

    private static CrashHandle INSTANCE = new CrashHandle();

    private Map<String, String> infos = new HashMap<>();

    public static CrashHandle getInstance(){return INSTANCE; }

    public void init(Context context){
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        collectDeviceInfo();
        saveCrashInfo2File(e);
    }

    public void collectDeviceInfo() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Logger.e("an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 将错误信息上传到服务器
     *
     * @param ex
     * @return
     */
    private void saveCrashInfo2File(Throwable ex) {

        final StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        //将日志保存至本地
        if (getSDCardPath()!=null){
            try {
                long timestamp = System.currentTimeMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String time = formatter.format(new Date());
                String fileName = "zzq-crash-" + time + "-" + timestamp + ".log";
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String path = getSDCardPath()+File.separator;
                    FileOutputStream fos = new FileOutputStream(path + fileName);
                    fos.write(sb.toString().getBytes());
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e( "an error occured while writing file...", e);
            }
        }
        //上传日志到服务器
//        new Thread(new Runnable() {
//            public void run() {
//                Message msg = Message.obtain();
//                try {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("content", sb.toString());
//                    msg.obj = Utils.sendPostResquest(mContext,
//                            Utils.FEEDBACKPATH, params, Utils.ENCORDING);
//                    MyLog.i(TAG, "msg.obj--->" + msg.obj);
//                } catch (Exception e) {
//                    MyLog.i(TAG, "catch ");
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }

        if (sdcardDir != null) {
            return sdcardDir.toString();
        } else {
            return null;
        }
    }

}
