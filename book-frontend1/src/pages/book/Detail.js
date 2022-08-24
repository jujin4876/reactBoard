import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import { useParams, useNavigate } from 'react-router-dom';

const Detail = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [book, setBook] = useState({
    id: '',
    title: '',
    author: '',
  });

  const deleteBook = () => {
    fetch('http://localhost:8080/book/' + id, {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          navigate('/');
        } else {
          alert('삭제 실패');
        }
      });
  };

  const updateBook = () => {
    navigate('/updateForm/' + id);
  };

  useEffect(() => {
    fetch('http://localhost:8080/book/' + id)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, []);
  return (
    <div>
      <h1>책 상세보기</h1>
      <Button variant="warning" onClick={updateBook}>
        수정
      </Button>{' '}
      <Button variant="danger" onClick={deleteBook}>
        삭제
      </Button>
      <h1>{book.title}</h1>
      <hr />
      <h1>{book.author}</h1>
    </div>
  );
};

export default Detail;
