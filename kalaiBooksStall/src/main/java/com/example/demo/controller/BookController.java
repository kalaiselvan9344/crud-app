package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Books;
import com.example.demo.repository.BookRepo;

@RestController
public class BookController
{
	@Autowired
	private BookRepo bookRepo;
	
	@GetMapping("/getAllBooks") 
	public  ResponseEntity< List<Books>> getAllBooks()
	{
		try 
		{
			List <Books> bookList = new ArrayList<Books>();
			bookRepo.findAll().forEach(bookList::add);
			
			if (bookList.isEmpty())
			{
				return new ResponseEntity<>( HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(bookList,HttpStatus.OK);
		}
		
		catch(Exception e)
		{
			return new ResponseEntity<List<Books>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
	}

	 @GetMapping(value ="/getBookById/{id}") 
	 public ResponseEntity<Books> getBookById(@PathVariable Long id)
	 
	 {
		Optional<Books> bookId= bookRepo.findById(id);
		
		if(bookId.isPresent())
		{
			return new ResponseEntity<>(bookId.get(),HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 }
	
	@PostMapping("/addBook")
	public ResponseEntity<Books> addBook(@RequestBody Books book)
	{
		  Books bookobj= bookRepo.save(book);
		  
		  return new ResponseEntity<>(bookobj,HttpStatus.OK);
	}
	
	@PostMapping("/updateBookById/{id}")
	public ResponseEntity<Books> updateBookById (@PathVariable Long id, @RequestBody Books newBookData)
	
	{
		Optional<Books> oldBookData = bookRepo.findById(id);
		
		if(oldBookData .isPresent())
		{
			 Books updatedBookData = oldBookData.get();
			 
			 updatedBookData.setTitle(newBookData.getTitle());
			 updatedBookData.setAuthor(newBookData.getAuthor());
			 
			 Books bookobj= bookRepo.save(updatedBookData);
			 
			  return new ResponseEntity<>(bookobj,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/deleteBookById/{id}")
	public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id)
	
	{
		bookRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
