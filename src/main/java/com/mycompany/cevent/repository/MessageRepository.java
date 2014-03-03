package com.mycompany.cevent.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.cevent.domain.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {

	public List<Message> findAll();
}
