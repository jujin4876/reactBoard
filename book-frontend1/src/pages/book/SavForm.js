import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const SavForm = () => {
  const navigate = useNavigate();
  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  const changeValue = (e) => {
    setBook({
      ...book,
      [e.target.name]: e.target.value,
    });
  };

  const submitBook = (e) => {
    e.preventDefault(); //자기 할일만함
    fetch('http://localhost:8080/book', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(book),
    })
      .then((res) => {
        if (res.status === 201) {
          return res.json;
        } else {
          return null;
        }
      })
      .then((res) => {
        if (res !== null) {
          //this.props.history.push('/');
          //history.push('/');
          navigate('/');
        } else {
          alert('책 등록에 실패 했습니다.');
        }
      });
  };
  return (
    <Form onSubmit={submitBook}>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Title</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter Title"
          onChange={changeValue}
          name="title"
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Author</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter Author"
          name="author"
          onChange={changeValue}
        />
      </Form.Group>
      <Button variant="dark" type="submit">
        Submit
      </Button>
    </Form>
  );
};

export default SavForm;
