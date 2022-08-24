package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

//기능을 정의할 수있고, 트랜잭션을 관리할 수있음
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional //서비스 함수가 종료 될 때 commit 할지 롤백할지 트랜잭션 관리
    public Book 저장하기(Book book){
       return bookRepository.save(book);
    }

    @Transactional(readOnly = true)// JPA 변경감지라는 내부 기능
    public Book 한건가져오기(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디를 확인해주세여"));
    }

    @Transactional(readOnly = true)
    public List<Book> 모두가져오기(){
        return bookRepository.findAll();
    }

    @Transactional
    public Book 수정하기(Long id, Book book){
        Book bookentity = bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("아이디를 확인해주세요"));

        bookentity.setTitle(book.getTitle());
        bookentity.setAuthor(book.getAuthor());
        return bookentity;
    }
    @Transactional
    public String 삭제하기(Long id){
        bookRepository.deleteById(id);
        return "ok";
    }

}
