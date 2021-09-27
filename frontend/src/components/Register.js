import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";
import NavBarHome from "./NavBarHome";
import axios from "axios";
import Swal from "sweetalert2";
import config from "../config.json";
const api = config.BACKEND_URL;
export default class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      email: "",
      cvc: "",
      expiry: "",
      focus: "",
      name: "",
      number: "",
    };
    this.onSubmit = this.onSubmit.bind(this);
  }
  async createOrder() {
    console.log(this.state.email, this.state.password, this.state.username);
    axios
      .post(
        api + "Auth/newUser",
        {},
        {
          params: {
            userName: this.state.username,
            email: this.state.email,
            password: this.state.password,
          },
        }
      )
      .then((response) => {
        if (response.data.Status === "200") {
          Swal.fire(
            "Success!",
            "You have successfully registered! Please proceed to login",
            "success"
          );
          this.props.history.push("/login");
        } else {
          Swal.fire(
            "Error!",
            "Failed to register user, you already have an account!",
            "error"
          );
        }
        console.log(response);
        //   Swal.fire(
        //     'Success!',
        //     'You have successfully created a Post!',
        //     'success'
        //   )
      });
  }

  handleInput = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };
  onSubmit(e) {
    e.preventDefault();
    this.createOrder();
    //   Swal.fire(
    //   'Success!',
    //   'You have successfully registered! Please proceed to login',
    //   'success'
    // )
    window.localStorage.setItem("user", this.state.username);
  }
  render() {
    return (
      <div>
        <NavBarHome />
        <h2 style={{ paddingLeft: "20px" }}>Register Below</h2>
        <Form onSubmit={this.onSubmit} style={{ paddingLeft: "20px" }}>
          <Form.Group controlId="formBasicEmail">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter email"
              name="email"
              onChange={this.handleInput}
              required
            />
            <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text>
          </Form.Group>
          <Form.Group controlId="formBasicEmail">
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="username"
              placeholder="Enter username"
              name="username"
              onChange={this.handleInput}
              required
            />
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
            Submit
          </Button>
        </Form>
      </div>
    );
  }
}
