package com.websocket.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.websocket.activities.WebSocketMessageHandler;
import com.websocket.bean.WebSocketChatBean;
import com.websocket.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketHandler;
import de.tavendo.autobahn.WebSocketOptions;

/**
 * @author: xiaxueyi
 * @date: 2017-02-22
 * @time: 16:51
 * @说明:
 */
public class WebSocketService extends Service{

    private static final String TAG="WebSocketService";

    /**
     * WebSocket 连接类
     */
    private WebSocketConnection mWebSocketConnection=new WebSocketConnection();

    /**
     * 自定义消息回调类
     */
    public WebSocketMessageHandler mWebSocketHandler=null;

    private Timer mTimer=new Timer();   //时间定时器

    private TimerTask mTimerTask=null;   //任务执行器


    @Override
    public IBinder onBind(Intent intent) {
        return new WebSocketBinder();
    }

    @Override
    public void onCreate() {
        sendHeart(Constants.WEB_SOCKET_HEAR_TIME);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 发送心跳包，查询WebSocket 是否还在连接，time 表示心跳的毫秒数
     * @param time
     */
    private void sendHeart(int time){

        if(mTimerTask==null){
            //执行任务
            mTimerTask=new TimerTask() {
                @Override
                public void run() {
                    try {
                        //判断WebSocket 是否连接，如果在线中，发送消息到服务器
                        if(mWebSocketConnection!=null&&mWebSocketConnection.isConnected()){
                            if(mWebSocketHandler!=null){
                                mWebSocketHandler.onOpen();
                            }
                            Log.d(TAG,"WebSocket 已经连接");
                        }else{
                            //反之再次重联WebSocket
                            mWebSocketConnection.disconnect();
                            ConnectionWebSocket();
                            Log.d(TAG,"WebSocket 重连中");
                        }
                    }catch (Exception e){
                        System.out.print(e);
                    }

                }
            };
        }

        //表示0秒后执行此任务,每次间隔多少（根据time 的值决定）秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
        mTimer.schedule(mTimerTask,0,time);
    }


    /**
     * 连接WebSocket
     */
    private void ConnectionWebSocket(){
        try {
            if(mWebSocketHandler==null){
                mWebSocketHandler=new WebSocketMessageHandler(mWebSocketConnection);
            }
            WebSocketOptions options=new WebSocketOptions();
            options.setSocketConnectTimeout(Constants.WEB_SOCKET_TIME_OUT);  //设置连接超时的时间
            options.setSocketReceiveTimeout(Constants.WEB_SOCKET_TIME_OUT); //设置接收超时时间
            mWebSocketConnection.connect(Constants.WEB_SOCKET_URL,mWebSocketHandler,options);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 实现Activity 与Service 交互接口
     */
    public class WebSocketBinder extends Binder {

        public WebSocketService getService(){
            return WebSocketService.this;
        }
    }
}

