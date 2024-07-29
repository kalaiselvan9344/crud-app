package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Books;

@Repository
public interface BookRepo extends JpaRepository<Books,Long> {

}
