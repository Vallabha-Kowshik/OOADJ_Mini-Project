package com.PooValBha.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.PooValBha.bookstore.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	@Query(value = "SELECT * FROM books WHERE name LIKE %:term%", nativeQuery = true)
	List<Book> findByNameContaining(@Param("term") String term);

}
