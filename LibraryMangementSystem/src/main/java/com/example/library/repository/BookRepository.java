package com.example.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.entities.Book;


public interface BookRepository extends JpaRepository<Book, Integer> {
	


}
