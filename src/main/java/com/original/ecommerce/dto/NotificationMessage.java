package com.original.ecommerce.dto;

public class NotificationMessage {
    private String content;
    private String timestamp;
    public NotificationMessage() {
    	
    }
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
    
}

