package com.feign.domain;

import java.util.Date;

public class ChatMessage {
	private String id;
	   private String chatId;
	   private String senderId;
	   private String recipientId;
	   private String senderName;
	   private String recipientName;
	   private String content;
	   private Date timestamp;
	   private MessageStatus status;
	   
	   
	public ChatMessage( String senderId, String recipientId, String senderName, String recipientName,
			String content, Date timestamp) {
		super();
		
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the chatId
	 */
	public String getChatId() {
		return chatId;
	}
	/**
	 * @param chatId the chatId to set
	 */
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	/**
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}
	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	/**
	 * @return the recipientId
	 */
	public String getRecipientId() {
		return recipientId;
	}
	/**
	 * @param recipientId the recipientId to set
	 */
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
	}
	/**
	 * @param recipientName the recipientName to set
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the status
	 */
	public MessageStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(MessageStatus status) {
		this.status = status;
	}
	   
	   
}
