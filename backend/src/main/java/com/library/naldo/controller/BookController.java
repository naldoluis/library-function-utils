package com.library.naldo.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.library.naldo.domain.Book;
import com.library.naldo.service.impl.IPageService;
import com.library.naldo.service.impl.IService;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins="http://localhost:3000")
public class BookController implements Resource<Book> {

	@Autowired
	private IService<Book> service;

	@Autowired
	private IPageService<Book> bookPageService;

	@Override
	public ResponseEntity<Page<Book>> findAll(Pageable pageable, String searchText) {
		return ResponseEntity.ok(bookPageService.findAll(pageable, searchText));
	}

	@Override
	public ResponseEntity<Page<Book>> findAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		return ResponseEntity.ok(bookPageService.findAll(PageRequest.of(pageNumber, pageSize, sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending())));
	}

	@Override
	public ResponseEntity<Book> findById(Long id) {
		return ResponseEntity.ok(service.findById(id).get());
	}

	@Override
	public ResponseEntity<Book> save(Book book) {
		return new ResponseEntity<>(service.saveOrUpdate(book), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Book> update(Book book) {
		return ResponseEntity.ok(service.saveOrUpdate(book));
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		return ResponseEntity.ok(service.deleteById(id));
	}

	@GetMapping("/languages")
	public  ResponseEntity<Set<String>> findAllLanguages() {
		return ResponseEntity.ok(new TreeSet<>(Arrays.asList("French", "Portuguese", "English", "Russian", "Hindi", "Arabic", "Spanish", "Chinese")));
	}

	@GetMapping("/genres")
	public  ResponseEntity<Set<String>> findAllGenres() {
		return ResponseEntity.ok(new TreeSet<>(Arrays.asList("Technology", "Science", "History", "Fantasy", "Biography", "Horror", "Romance")));
	}
}