package com.mycompany.cevent.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.cevent.domain.Message;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, String> {

	public List<Message> findAll(Sort sort);
}
