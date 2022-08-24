import React from 'react';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const BookItem = (props) => {
  const { id, title, author } = props.book;
  return (
    <Card>
      <Card.Body>
        <Card.Title>제목 : {title}</Card.Title>
        <Card.Title>저자 : {author}</Card.Title>
        <Link to={'/book/' + id} className="btn btn-dark">
          상세보기
        </Link>
      </Card.Body>
    </Card>
  );
};

export default BookItem;
