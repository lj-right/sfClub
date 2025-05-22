package com.jingdiansuifeng.wx.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SubscribeMsgHandler implements WxChatMsgHandler{
    @Override
    public WeChatMsgTypeEnum getMsgType() {
        return WeChatMsgTypeEnum.SUBSCRIBE;
    }

    @Override
    public String dealMsg(Map<String, String> messageMap) {
        log.info("触发用户关注事件！");
        String fromUserName = messageMap.get("FromUserName");
        String toUserName = messageMap.get("ToUserName");
        String subscribeContent = "欢迎来到公众号随风Lab";
        String content = "<xml>\n" +
                " <ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                " <FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                " <CreateTime>123456</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content><![CDATA["+subscribeContent+"]]></Content>\n" +
                "</xml>";
        return content;
    }
}
