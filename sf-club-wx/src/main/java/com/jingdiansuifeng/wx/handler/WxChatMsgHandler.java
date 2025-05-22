package com.jingdiansuifeng.wx.handler;

import java.util.Map;

public interface WxChatMsgHandler {

    WeChatMsgTypeEnum getMsgType();

    String dealMsg(Map<String, String> messageMap);
}
