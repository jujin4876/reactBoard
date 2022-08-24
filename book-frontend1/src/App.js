import React from 'react';
import { Container } from 'react-bootstrap';
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Detail from './pages/book/Detail';
import Home from './pages/book/Home';
import SavForm from './pages/book/SavForm';
import UpdateForm from './pages/book/UpdateForm';
import JoinForm from './pages/user/JoinForm';
import LoginForm from './pages/user/LoginForm';

function App() {
  return (
    <div>
      <Header></Header>
      <br />
      <Container>
        <Routes>
          <Route exact={true} path="/" element={<Home />} />
          <Route exact={true} path="/saveForm" element={<SavForm />} />
          <Route exact={true} path="/book/:id" element={<Detail />} />
          <Route exact={true} path="/loginForm" element={<LoginForm />} />
          <Route exact={true} path="/joinForm" element={<JoinForm />} />
          <Route exact={true} path="/updateForm/:id" element={<UpdateForm />} />
        </Routes>
      </Container>
    </div>
  );
}

export default App;
