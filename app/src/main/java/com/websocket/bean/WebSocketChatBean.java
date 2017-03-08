package com.websocket.bean;

import java.io.Serializable;

/**
 * @author: xiaxueyi
 * @date: 2017-02-23
 * @time: 10:57
 * @说明:
 */
public class WebSocketChatBean implements Serializable {

    private String name;

    private String content;

    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

