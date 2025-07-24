package com.jingdiansuifeng.wx.controller;

import com.jingdiansuifeng.wx.Utils.MessageUtil;
import com.jingdiansuifeng.wx.Utils.SHA1;
import com.jingdiansuifeng.wx.handler.WxChatMsgFactory;
import com.jingdiansuifeng.wx.handler.WxChatMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
public class CallBackController {

    private static final String TOKEN = "suifeng";

    @Resource
    private WxChatMsgFactory wxChatMsgFactory;


//    /**
//     * 微信回调
//     *
//     * @return
//     */
//    @GetMapping("/callback2")
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

//     1. 处理微信服务器验证(GET请求)
//    @GetMapping("/callback")
//    public String verifyServer(
//            @RequestParam("signature") String signature,
//            @RequestParam("timestamp") String timestamp,
//            @RequestParam("nonce") String nonce,
//            @RequestParam("echostr") String echostr){
//
//        log.info("微信验证请求: signature={}, timestamp={}, nonce={}",
//                signature, timestamp, nonce);
//
//        // 验证签名
//        String shaStr = SHA1.getSHA1(TOKEN, timestamp, nonce, "");
//        if (shaStr != null && shaStr.equals(signature)) {
//            return echostr; // 验证成功返回echostr
//        }
//
//        log.error("签名验证失败: 本地生成={}, 微信传入={}", shaStr, signature);
//        return "signature verification failed";
//    }

//    @PostMapping(value = "/callback", produces = "application/xml;charset=UTF-8")
//    public String callBack(
//            @RequestBody String requestBody,
//            @RequestParam("signature") String signature,
//            @RequestParam("timestamp") String timestamp,
//            @RequestParam("nonce") String nonce,
//            @RequestParam(value = "msg_signature", required = false) String msgSignature) {
//
//        log.info("接受到微信的请求：requestBody:{},signature:{},timestamp:{},nonce:{}",
//                requestBody, signature, timestamp, nonce);
//
//        Map<String, String> msgMap = MessageUtil.parseXml(requestBody);
//        String msgType = msgMap.get("MsgType");
//        String event = msgMap.get("Event") == null ? "" : msgMap.get("Event");
//        log.info("msgType：{},event:{},", msgType, event);
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(msgType);
//        if (!StringUtils.isEmpty(event)) {
//            sb.append(".").append(event);
//        }
//        String msgTypeKey = sb.toString();
//        WxChatMsgHandler wxChatMsgHandler = wxChatMsgFactory.getHandlerByMsgType(msgTypeKey);
//        if (Objects.isNull(wxChatMsgHandler)) {
//            return "Unknown";
//        }
//        String replyContent = wxChatMsgHandler.dealMsg(msgMap);
//        log.info("回复内容：{}", replyContent);
//
//        return replyContent;
//    }

    @RequestMapping(value = ("/callback"),method = {RequestMethod.GET, RequestMethod.POST})
    public String handleCallback(
            HttpServletRequest request, // 获取原始请求对象
            @RequestBody(required = false) String requestBody, // 添加注解，允许为空
            @RequestParam(value = "signature", required = false) String signature,
            @RequestParam(value = "timestamp", required = false) String timestamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestParam(value = "echostr", required = false) String echostr,
            @RequestParam(value = "msg_signature", required = false) String msgSignature) {

        // 记录完整请求信息（调试用）
        logRequestDetails(request);

        //处理GET验证请求
        if ("GET".equalsIgnoreCase(request.getMethod())){
            String shaStr = SHA1.getSHA1(TOKEN, timestamp, nonce, "");
            if (shaStr != null && shaStr.equals(signature)) {
                return echostr; // 验证成功返回echostr
            }
        }

        // 3. 处理POST消息请求
        return processWeChatMessage(requestBody);
    }

    private void logRequestDetails(HttpServletRequest request) {
        log.debug("请求方法: {}", request.getMethod());
        log.debug("请求URL: {}", request.getRequestURL());

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            log.debug("参数 {} = {}", param, request.getParameter(param));
        }
    }

    private boolean validateSignature(String signature, String timestamp, String nonce) {
        if (signature == null || timestamp == null || nonce == null) {
            log.warn("缺少必要参数: signature={}, timestamp={}, nonce={}", signature, timestamp, nonce);
            return false;
        }

        String shaStr = SHA1.getSHA1(TOKEN, timestamp, nonce, "");
        return shaStr != null && shaStr.equals(signature);
    }

    private String processWeChatMessage(String requestBody) {
        if (requestBody == null || requestBody.isEmpty()) {
            log.error("收到空消息体");
            return "empty message";
        }

        log.info("处理微信消息: requestBody={}", requestBody);

        Map<String, String> msgMap = MessageUtil.parseXml(requestBody);
        if (msgMap == null || msgMap.isEmpty()) {
            log.error("XML解析失败");
            return "parse error";
        }

        String msgType = msgMap.get("MsgType");
        String event = msgMap.getOrDefault("Event", "");
        log.info("消息类型: msgType={}, event={}", msgType, event);

        String msgTypeKey = StringUtils.isEmpty(event) ? msgType : msgType + "." + event;
        WxChatMsgHandler handler = wxChatMsgFactory.getHandlerByMsgType(msgTypeKey);

        if (handler == null) {
            log.warn("未找到消息处理器: {}", msgTypeKey);
            return "unsupported message type";
        }

        try {
            String replyContent = handler.dealMsg(msgMap);
            log.info("生成回复内容: {}", replyContent);
            return replyContent;
        } catch (Exception e) {
            log.error("消息处理异常", e);
            return "server error";
        }
    }
}
