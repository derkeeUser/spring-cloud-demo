//package com.userservice.userdemo.user.service;
//
//import com.userservice.userdemo.user.entity.UserMsg;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
///**
// * @BelongsProject: scdemo
// * @BelongsPackage: com.userservice.userdemo.user.service
// * @Author: chenxiaoming
// * @CreateTime: 2019-03-11 18:11
// * @Description: ...
// */
//@Component
//public class UserMsgSender {
//    protected static Logger LOGGER = LoggerFactory.getLogger(UserMsgSender.class);
//
//    /**
//     * 消息的发送接口
//     */
//    private Source source;
//
//    @Autowired
//    public UserMsgSender(Source source) {
//        this.source = source;
//    }
//
//    public void sendMsg(UserMsg userMsg) {
//        LOGGER.info("发送用户消息：{}", userMsg);
//        this.source.output().send(MessageBuilder.withPayload(userMsg).build());
//    }
//}