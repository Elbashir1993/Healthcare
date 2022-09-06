package com.feign.domain;

public class ChatRoom {
	private int id;
    private String chatId;
    private String senderId;
    private int recipientId;
    
    
	public ChatRoom(int id, String chatId, String senderId, int recipientId) {
		super();
		this.id = id;
		this.chatId = chatId;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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
	public int getRecipientId() {
		return recipientId;
	}
	/**
	 * @param recipientId the recipientId to set
	 */
	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}
    
}
