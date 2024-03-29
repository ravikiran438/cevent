package com.mycompany.cevent.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.mycompany.cevent.service.MailService;

@Controller
@RequestMapping("/rest/messages")
public class MessageResource {

	private MessageRepository messageRepository;
	
	@Inject
	private MailService mailService;

	@Autowired
	public MessageResource(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Void> submitMessage(@RequestBody Message Message) {
		messageRepository.save(Message);
		mailService.sendEmail(Message.getBody());
		ResponseEntity<Void> responseEntity = new ResponseEntity<>(
				HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Message> allmessages() {
		return messageRepository.findAll(sortById());
	}

	@RequestMapping(value = "/rest/messages/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Message showMessage(@PathVariable("messageId") String messageId) {
		Message message = messageRepository.findOne(messageId);
		if (message == null) {
			throw new MessageNotFoundException(messageId);
		}
		return message;
	}
	
    /**
     * Returns a Sort object which sorts messages in ascending order by using the id (timestamp).
     * @return
     */
    private Sort sortById() {
        return new Sort(Sort.Direction.DESC, "_id");
    }

}
