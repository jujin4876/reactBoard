package com.example.book.web;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
통합테스트 모든 Bean을 똑같이 IoC 올리고 테스트하는 것
* WebEnvironment.MOCK 실제 톰켓을 올리는게 아니라 다른 톰켓으로 테스트
* WebEnvironment.RANDOM_POR = 실제 톰켓으로 테스트
* */
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 실제 톰켓을 올리는게 아니라 다른 톰켓으로 테스트
public class BookControllerIntegrateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void init(){
        entityManager.createNativeQuery("Alter table book alter column id restart with 1").executeUpdate();
    }


    @Test
    public void save_테스트() throws Exception{
        log.info("save_테스트()시작 =============================");

        Book book = new Book(null,"스프링 따라하기","456");
        String content = new ObjectMapper().writeValueAsString(book);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링 따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void findAll_테스트() throws Exception{
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L,"스프링 따라하기","코스"));
        books.add(new Book(2L,"스프링 따라하기2","코스2"));
        bookRepository.saveAll(books);

        ResultActions resultActions = mockMvc.perform(get("/book"));
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("스프링 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_테스트() throws Exception {
        Long id = 1L;

        bookRepository.save(new Book(null,"스프링 따라하기","코스"));

        ResultActions resultActions = mockMvc.perform(get("/book/{id}",id)
                .contentType(MediaType.APPLICATION_JSON_UTF8));


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("스프링 따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void update_테스트() throws Exception {
        Long id = 1L;
        Book book = new Book(null,"123","456");
        String content = new ObjectMapper().writeValueAsString(book);
        bookRepository.save(new Book(null,"스프링 따라하기","코스"));

        ResultActions resultActions = mockMvc.perform(put("/book/{id}",id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("123"))
                .andDo(MockMvcResultHandlers.print());

    }



}
