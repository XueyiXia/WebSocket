package com.websocket.activities;

import android.content.Intent;

import com.websocket.AppLoader;
import com.websocket.utils.Constants;

import de.tavendo.autobahn.WebSocketConnection;

/**
 * @author: xiaxueyi
 * @date: 2017-02-23
 * @time: 15:50
 * @说明: 消息处理回调，自定义
 */
public class WebSocketMessageHandler extends de.tavendo.autobahn.WebSocketHandler {

    private WebSocketConnection mWebSocketConnection=null;

    public WebSocketMessageHandler(WebSocketConnection webSocketConnection){
        this.mWebSocketConnection=webSocketConnection;
    }


    @Override
    public void onOpen() {  //打开WebSocket的时候调用
        /**
         * 给服务器发送参数，这个需要沟通好
         */
        if(mWebSocketConnection!=null){
            mWebSocketConnection.sendTextMessage("");
        }
        super.onOpen();
    }

    @Override
    public void onClose(int code, String reason) {  //WebSocket 关闭的时候调用
        super.onClose(code, reason);
    }

    @Override
    public void onTextMessage(String payload) { //WebSocket 数据返回的时候调用
        System.out.print(payload);
        //使用广播，把WebSocket 服务器返回的信息回调回去
        Intent intent=new Intent();
        intent.setAction(Constants.WEB_SOCKET_BROADCAST_ACTION);
        intent.putExtra(Constants.WEB_SOCKET_MESSAGE,payload);
        AppLoader.getInstance().sendBroadcast(intent);
        super.onTextMessage(payload);
    }
}

