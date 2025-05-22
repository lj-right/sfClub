package com.jingdiansuifeng.wx.controller;

import com.jingdiansuifeng.wx.Utils.MessageUtil;
import com.jingdiansuifeng.wx.Utils.SHA1;
import com.jingdiansuifeng.wx.handler.WxChatMsgFactory;
import com.jingdiansuifeng.wx.handler.WxChatMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
public class CallBackController {

    private static final String TOKEN = "suifeng";

    @Resource
    private WxChatMsgFactory wxChatMsgFactory;

    @RequestMapping("/test")
    public String callBack() {
        return "hello wx";
    }

//    /**
//     * 微信回调
//     *
//     * @return
//     */
//    @GetMapping("/callback")
//    public String callBack(
//            @RequestParam("signature") String signature,
//            @RequestParam("timestamp") String timestamp,
//            @RequestParam("nonce") String nonce,
//            @RequestParam("echostr") String echostr) {
//
//        log.info("get验签请求参数：signature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);
//        String shaStr = SHA1.getSHA1(TOKEN, timestamp, nonce, "");
//        if (shaStr.equals(signature)) {
//            return echostr;
//        }
//        return "unknown ikun";
//    }

    @PostMapping(value = "/callback", produces = "application/xml;charset=UTF-8")
    public String callBack(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(value = "msg_signature", required = false) String msgSignature) {

        log.info("接受到微信的请求：requestBody:{},signature:{},timestamp:{},nonce:{}",
                requestBody, signature, timestamp, nonce);

        Map<String, String> msgMap = MessageUtil.parseXml(requestBody);
        String msgType = msgMap.get("MsgType");
        String event = msgMap.get("Event") == null ? "" : msgMap.get("Event");
        log.info("msgType：{},event:{},", msgType,event);

        StringBuilder sb = new StringBuilder();
        sb.append(msgType);
        if (!StringUtils.isEmpty(event)){
            sb.append(".").append(event);
        }
        String msgTypeKey = sb.toString();
        WxChatMsgHandler wxChatMsgHandler = wxChatMsgFactory.getHandlerByMsgType(msgTypeKey);
        if (Objects.isNull(wxChatMsgHandler)){
            return "Unknown";
        }
        String replyContent = wxChatMsgHandler.dealMsg(msgMap);
        log.info("回复内容：{}",replyContent);

        return replyContent;
    }
}
