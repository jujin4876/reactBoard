package com.example.book.web;

import com.example.book.domain.Book;
import com.example.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // IOC환경에 등록
    private BookService bookService;

    @Test
    public void save_테스트() throws Exception{
        log.info("save_테스트()시작 =============================");

        Book book = new Book(null,"123","456");
        String content = new ObjectMapper().writeValueAsString(book);
        when(bookService.저장하기(book)).thenReturn(new Book(1L,"스프링 따라하기","코스"));

        ResultActions resultActions = mockMvc.perform(post("/book")
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
        when(bookService.모두가져오기()).thenReturn(books);

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
        when(bookService.한건가져오기(id)).thenReturn(new Book(1L,"스프링 따라하기","코스"));

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
        when(bookService.수정하기(id,book)).thenReturn(new Book(1L,"123","코스"));

        ResultActions resultActions = mockMvc.perform(put("/book/{id}",id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("123"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void delete_테스트() throws Exception {
        Long id = 1L;

        when(bookService.삭제하기(id)).thenReturn("ok");

        ResultActions resultActions = mockMvc.perform(delete("/book/{id}",id)
                .accept(MediaType.TEXT_PLAIN));


        resultActions
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult requestResult = resultActions.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("ok",result);

    }
}
