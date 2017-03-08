package com.websocket.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.websocket.utils.Constants;

/**
 * @author: xiaxueyi
 * @date: 2017-02-24
 * @time: 09:39
 * @说明: WebSocket 广播接收者
 */
public class WebSocketBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG="broadcastReceiver";

    private Handler handler=null;

    public WebSocketBroadcastReceiver(Handler handler){
        this.handler=handler;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if(handler==null){
                return;
            }

            if(intent.getAction().equals(Constants.WEB_SOCKET_BROADCAST_ACTION)){
                Message message=new Message();
                message.obj=intent.getStringExtra(Constants.WEB_SOCKET_MESSAGE);
                message.what=200;
                handler.sendMessage(message);
            }

            Log.d(TAG,"接收到WebSocket 返回的信息："+intent.getStringExtra(Constants.WEB_SOCKET_MESSAGE));
        }catch (Exception e){
            Log.d(TAG,"异常信息："+e.getMessage());

        }

    }
}

