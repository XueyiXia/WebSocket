package com.websocket.utils;

/**
 * @author: xiaxueyi
 * @date: 2017-02-22
 * @time: 17:10
 * @说明: 全局静态变量
 */
public class Constants {

    /**
     * webSocket请求的ip地址和端口
     * 换上自己的地址就可以了
     */
    public static final String WEB_SOCKET_URL="ws://";

    public static final int WEB_SOCKET_TIME_OUT=30000;  //超时时间

    public static final int WEB_SOCKET_HEAR_TIME=15000; //心跳包的时间

    public static final String WEB_SOCKET_BROADCAST_ACTION ="web_socket_broadcast"; //标识广播

    public static final String WEB_SOCKET_MESSAGE="web_socket_message";     //标识WebSocket 返回的信息
}

