import React, {Component} from 'react';
import {Container, FormControl, Nav, Navbar} from "react-bootstrap";
import logo from './logo1.png'
import {BrowserRouter as Router, Link, Routes,} from "react-router-dom";
import {Route} from "react-router";
import Home from "../pages/Home";
import Settings from "../pages/Settings";
import Function from "../pages/Function";

class Header extends Component {
    render() {
        return (
            <>
            <Navbar collapseOnSelect expand="md" bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand href="/">
                        <img
                        src={logo}
                        height="30"
                        width="30"
                        className="d-inline-block align-top"
                        alt="logo"/>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsisve-navbar-nav" />
                    <Navbar.Collapse id="responssive-navbar-nav">
                        <Nav className="mr-auto">
                            <Nav.Link href="/">Home</Nav.Link>
                            <Nav.Link href="/function">Function</Nav.Link>
                            <Nav.Link href="/settings">Settings</Nav.Link>
                        </Nav>
                        </Navbar.Collapse>
                </Container>
            </Navbar>
                <Router>
                    <Routes>
                        <Route path="/" element={<Home/>}/>
                        <Route path="/function" element={<Function/>}/>
                        <Route path="/settings" element={<Settings/>}/>
                    </Routes>
                </Router>
            </>
        );
    }
}

export default Header;