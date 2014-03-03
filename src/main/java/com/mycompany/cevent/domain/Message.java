package com.mycompany.cevent.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message {

	@Id
	private String id;

	private String name;

	private String body;

	public Message() {
	}

	public Message(String name, String body) {
		super();
		this.name = name;
		this.body = body;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", name=" + name + ", body=" + body + "]";
	}
}
