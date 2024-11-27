package com.example.backendproject.controller;


import com.example.backendproject.domain.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;
import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

//    @MessageMapping("/message")
//    @SendTo("/chatroom/public")
//    public Messages receiveMessage(@Payload Messages message){
//        System.out.println(message);
//        return message;
//    }
//
//    @MessageMapping("/private-message")
//    public Messages recMessage(@Payload Messages message){
//        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
//        System.out.println(message.toString());
//        return message;
//    }

    @MessageMapping("/message")
    public Messages sendMessage(@Payload Messages message, Principal principal) {
        String senderName = principal == null ? "client-" + UUID.randomUUID() : principal.getName();
        message.setSenderName(senderName);

        // Tin nhắn sẽ gửi đến "staff"
        simpMessagingTemplate.convertAndSendToUser("staff", "/private", message);
        return message;
    }

    @MessageMapping("/reply")
    public Messages replyMessage(@Payload Messages message) {
        // Nhân viên trả lời khách hàng (theo tên khách hàng gửi đến)
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        return message;
    }
}