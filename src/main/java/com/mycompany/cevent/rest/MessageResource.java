package com.mycompany.cevent.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.cevent.domain.Message;
import com.mycompany.cevent.repository.MessageRepository;

@Controller
@RequestMapping("/messages")
public class MessageResource {

	private MessageRepository messageRepository;

	@Autowired
	public MessageResource(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Void> submitMessage(@RequestBody Message Message) {
		messageRepository.save(Message);
		ResponseEntity<Void> responseEntity = new ResponseEntity<>(
				HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Message> allmessages() {
		return messageRepository.findAll();
	}

	@RequestMapping(value = "/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Message showMessage(@PathVariable("messageId") String messageId) {
		Message message = messageRepository.findOne(messageId);
		if (message == null) {
			throw new MessageNotFoundException(messageId);
		}
		return message;
	}

}
