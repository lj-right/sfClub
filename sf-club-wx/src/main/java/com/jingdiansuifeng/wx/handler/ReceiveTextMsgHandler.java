package com.jingdiansuifeng.wx.handler;
import com.jingdiansuifeng.wx.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ReceiveTextMsgHandler implements WxChatMsgHandler {

    private static final String KEY_WORD = "验证码";

    private static final String LOGIN_PREFIX = "loginCode";

    @Resource
    private RedisUtil redisUtil;

    @Override
    public WeChatMsgTypeEnum getMsgType() {
        return WeChatMsgTypeEnum.TEXT_MSG;
    }

    @Override
    public String dealMsg(Map<String, String> messageMap) {
        log.info("接收到文本消息事件！");
        String content = messageMap.get("Content");
        if (!content.equals(KEY_WORD)) {
            return "";
        }
        String fromUserName = messageMap.get("FromUserName");
        String toUserName = messageMap.get("ToUserName");
        Random random = new Random();
        int verificationCode = random.nextInt(900000)+100000;

        String codeKey = redisUtil.buildKey(LOGIN_PREFIX, String.valueOf(verificationCode));
        redisUtil.setNx(codeKey, fromUserName, 5L, TimeUnit.MINUTES);

        String CodeContent = "【随风Club】您的注册验证码为：" + verificationCode + "该验证码5分钟内有效，请及时验证。";
        String replyContent = "<xml>\n" +
                " <ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                " <FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                " <CreateTime>123456</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content><![CDATA[" + CodeContent + "]]></Content>\n" +
                "</xml>";

        return replyContent;
    }
}
