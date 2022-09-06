package com.healthcare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        	// set chat id
        	
	    	// create new room
	    	// check to for empty room
			/*
			 * if(count != 0) { System.out.print("chatID "+chatId); }
			 */
        	// create the rooms on first request
    	System.out.println(sId+"");
        	if(chatroomLi.isEmpty()) {
        		for(int i=0; i <= 5; i++) {
        			String chatId = "1"+i;
	        		ChatRoom ncr = new ChatRoom(count, chatId,"1", i);
	        		ncr.setActive(false);
	        		ncr.setOnline(false);
	    			chatroomLi.add(ncr);
	    			System.out.println("room created "+ncr.getChatId());    			rId = count+"";
        		}
        	}
        	
        	if(Integer.parseInt(sId) == 1) {
        		// the sender is user
        		// assign user to active chat room
        		System.out.println("user chat");
        		for(ChatRoom cr: chatroomLi) {
        			if(cr.isOnline() && !cr.isActive()) {
        				cr.setActive(true);
        				return new Handshake("0", cr.getRecipientId()+"");
        			}
        		}
        		return new Handshake("1", "0");
        	}else if(Integer.parseInt(sId) == 2) {
        		// the sender is admin
        		// assign an empty room
        		// assign user to active chat room
        		System.out.println("admin chat");
        		for(ChatRoom cr: chatroomLi) {
        			System.out.println("room exists");
        			if(!cr.isOnline()) {
        				System.out.println("room is not online");
        				cr.setOnline(true);
        				cr.setActive(false);
        				return new Handshake("0", cr.getRecipientId()+"");
        			}
        		}
        		return new Handshake("1", "0");
        		
        	}else {
        		return new Handshake("1", "0");
        		
        	}
		
	}
	
	
	@GetMapping("/reception/terminate/{rId}")
	public ResponseEntity<Handshake> closeRoom(@PathVariable(value = "rId") String rid){
		for(ChatRoom cr: chatroomLi) {
			if(cr.getRecipientId() == Integer.parseInt(rid)) {
				cr.setActive(false);
				return ResponseEntity.ok().body(new Handshake("0", cr.getRecipientId()+""));
			}
		}
		
		return ResponseEntity.badRequest().body(new Handshake("1", rid));
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
    	for(ChatRoom cr: chatroomLi) {
			if(cr.getRecipientId() == Integer.parseInt(chatMessage.getRecipientId())) {
				if(cr.isActive()) {
					messagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(), "/queue/messages", chatMessage);
				}
			}
		}
    	//messagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(), "/queue/messages", chatMessage);
		/*
		 * for(ChatRoom cr: chatroomLi) { if(cr.getRecipientId() == Integer.
		 * parseInt(chatMessage.getRecipientId())) {
		 * System.out.print("room exists "+cr.getChatId()); }else {
		 * System.out.println("rec: "+chatMessage.getRecipientId()); } }
		 */
    	System.out.print(chatMessage.getContent());
    }
    @GetMapping("/testApi")
    public ResponseEntity<String> testApiGate() {
    	return ResponseEntity.ok().body("working");
    }
}
