package com.healthcare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.domain.ChatMessage;
import com.healthcare.domain.ChatRoom;
import com.healthcare.domain.Handshake;
import com.healthcare.domain.ChatNotification;

@Controller
@RestController
@CrossOrigin
@RequestMapping("/")
public class ChatController {
	@Autowired private SimpMessagingTemplate messagingTemplate;
   // @Autowired private ChatMessageService chatMessageService;
    //@Autowired private ChatRoomService chatRoomService;
	private static List<ChatRoom> chatroomLi = new ArrayList<ChatRoom>();
	// shake on method
	// check to for empty room then send the reception id to the client
	@GetMapping("/reception/{senderId}")
	public Handshake getReception(@PathVariable(value = "senderId") String sId) {
		boolean flag = true;
    	int count = 0;
    	String rId = "";
    	boolean exists = false;
        while(flag) {
        	// set chat id
        	String chatId = ""+sId+""+count;
	    	// create new room
	    	// check to for empty room
        	if(count != 0) {
        	System.out.print("chatID "+chatId);
        	}
        	if(chatroomLi.isEmpty()) {
        		ChatRoom ncr = new ChatRoom(count, chatId,sId, count);
    			chatroomLi.add(ncr);
    			rId = count+"";
    			exists = true;
    			flag = false;
        	}else {
		    	for(ChatRoom cr: chatroomLi) {
		    		// check to see if empty
		    		if(cr.getChatId().equals(chatId)) {
		    			System.out.print("chatID exist "+cr.getChatId());
		    			exists = true;
		    			count++;
		    			break;
		    		}
		    	}
        	}
	    	if(!exists) {
    			ChatRoom ncr = new ChatRoom(count, chatId,sId, count);
    			chatroomLi.add(ncr);
    			rId = count+"";
    			flag = false;
	    	}else {
	    		exists = false;
	    	}
	    	
        }
        
        return new Handshake("0", count+"");
		
	}
	
	
	
    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
      //  var chatId = chatRoomService
        //        .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        //chatMessage.setChatId(chatId.get());

        // ChatMessage saved = chatMessageService.save(chatMessage);
    	
    	
		/*
		 * messagingTemplate.convertAndSendToUser(
		 * chatMessage.getSenderName(),"/queue/messages", chatMessage);
		 */
    	messagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(), "/queue/messages", chatMessage);
    	 for(ChatRoom cr: chatroomLi) {
    		 if(cr.getRecipientId() == Integer. parseInt(chatMessage.getRecipientId())) {
    			 System.out.print("room exists "+cr.getChatId());
    		 }
    	 }
    	System.out.print(chatMessage.getContent());
    }
}
