package com.email;

import java.sql.Timestamp;

public class Email {
    private int id;
    private String subject;
    private String sender;
    private String content;  // Assuming you want to store email content
    private Timestamp sentDate;
    private String recipient;
    private Timestamp deleted_at;

    public Email(int id, String sender, String subject, String content, Timestamp sentDate) {
        this.id = id;
        this.subject = subject;
        this.sender = sender;
        this.content = content;
        this.sentDate = sentDate;
       // this.deleted_at=deleted_at
    }
    public Email(int id, String sender, String recipient,String subject, String content, Timestamp deleted_at) {
        this.id = id;
        this.subject = subject;
        this.sender = sender;
        this.recipient=recipient;
        this.content = content;
        //this.sentDate = sentDate;
        this.deleted_at=deleted_at;
    }

    // Getter methods

    public String getBody() {
    	return content;
    }

	public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }

    public String getRecipient() {
        return recipient;
    }

    // Setter methods

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String content) {
        this.content = content;
    }
}
