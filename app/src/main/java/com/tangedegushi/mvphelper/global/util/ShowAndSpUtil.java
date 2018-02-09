package com.tangedegushi.mvphelper.global.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tangedegushi.mvphelper.global.MyApplication;

/**
 * Created by zq on 2017/8/28.
 */

public class ShowAndSpUtil {

    private static Toast toast;

    public static void showToast(String msg){
        if (toast == null){
            toast = Toast.makeText(MyApplication.getInstence(),msg,Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }

}
