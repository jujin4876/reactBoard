package com.example.book.service;

import com.example.book.domain.BookRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

    @InjectMocks //BookService 객체가 만들어질 때 BookServiceUnitTest 파일에 @Mock로 등록된 모든 애들을 주입받는다
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
}
