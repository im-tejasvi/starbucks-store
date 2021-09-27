import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";
import NavBarHome from "./NavBarHome";
import Swal from "sweetalert2";
import axios from "axios";
import config from "../config.json";
const api = config.BACKEND_URL;
export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
    };
    this.onSubmit = this.onSubmit.bind(this);
  }
  handleInput = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };
  onSubmit(e) {
    e.preventDefault();
    this.createLogin();
  }
  async createLogin() {
    console.log(this.state.email, this.state.password, this.state.username);
    axios
      .post(
        api + "Auth/login/userName",
        {},
        {
          params: {
            userName: this.state.username,
            password: this.state.password,
          },
        }
      )
      .then((response) => {
        if (response.data.Status === "200") {
          Swal.fire("Success!", "You have successfully logged in!", "success");
          window.localStorage.setItem("username", this.state.username);
          this.props.history.push("/menu");
        } else {
          Swal.fire(
            "Error!",
            "Failed to login user, please enter the correct credentials!",
            "error"
          );
        }
        // console.log(response);
        //   Swal.fire(
        //     'Success!',
        //     'You have successfully created a Post!',
        //     'success'
        //   )
      });
  }
  render() {
    return (
      <div>
        <NavBarHome />
        <h2 style={{ paddingLeft: "20px" }}>Login Below</h2>
        <Form onSubmit={this.onSubmit} style={{ paddingLeft: "20px" }}>
          <Form.Group controlId="formBasicEmail">
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="username"
              placeholder="Enter username"
              name="username"
              onChange={this.handleInput}
              required
            />
            <Form.Text className="text-muted">
              We'll never share your username with anyone else.
            </Form.Text>
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Password"
              name="password"
              onChange={this.handleInput}
              required
            />
          </Form.Group>
          {/* <Form.Group controlId="formBasicCheckbox">
    <Form.Check type="checkbox" label="Check me out" />
  </Form.Group> */}
          <Button variant="primary" type="submit">
            Login
          </Button>
          {console.log(this.state.username)}
          {console.log(this.state.password)}
        </Form>
      </div>
    );
  }
}
