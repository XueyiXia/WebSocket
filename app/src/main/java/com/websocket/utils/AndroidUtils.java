package com.websocket.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.websocket.AppLoader;

/**
 * @author: xiaxueyi
 * @date: 2017-03-04
 * @time: 16:33
 * @说明:
 */
public class AndroidUtils {

    public static void closeInput(View view){
        if(view==null){
            return;
        }

        InputMethodManager methodManager= (InputMethodManager)AppLoader.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromInputMethod(view.getWindowToken(),0);
        view.clearFocus();

    }
}

