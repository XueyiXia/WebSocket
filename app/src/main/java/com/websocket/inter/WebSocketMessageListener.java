package com.websocket.inter;

/**
 * 消息回调
 */
public interface WebSocketMessageListener {

    public void onWebSocketMessage(Object object,boolean result);
}
