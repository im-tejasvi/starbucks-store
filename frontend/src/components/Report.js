import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";
import NavBarHome from "./NavBarHome";
import axios from "axios";
import Swal from "sweetalert2";
import config from "../config.json";
const api = config.BACKEND_URL;
export default class Report extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      text: "",
    };
    this.onSubmit = this.onSubmit.bind(this);
  }
  async createUserReport() {
    axios
      .post(
        api + "/create/userReport",
        {},
        {
          params: {
            username: window.localStorage.getItem("username"),
            reportContent: this.state.text,
          },
        }
      )
      .then((response) => {
        console.log(response);
        //   Swal.fire(
        //     'Success!',
        //     'You have successfully created a Post!',
        //     'success'
        //   )
        this.props.history.push(
          `/viewreports/${window.localStorage.getItem("username")}`
        );
      });
  }
  onSubmit(e) {
    e.preventDefault();
    this.createUserReport();
    Swal.fire("Success!", "You have successfully made a post!", "success");
  }
  handleInput = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };
  render() {
    return (
      <div>
        <NavBarHome />
        <h2 style={{ paddingLeft: "20px" }}>File A Report</h2>
        <Form onSubmit={this.onSubmit} style={{ paddingLeft: "20px" }}>
          <Form.Group controlId="exampleForm.ControlInput1">
            <Form.Label>Username</Form.Label>
            <Form.Control
              onChange={this.handleInput}
              type="username"
              name="username"
              placeholder={window.localStorage.getItem("username")}
              disabled
            />
          </Form.Group>
          <Form.Group controlId="exampleForm.ControlSelect1">
            <Form.Label>Report Type</Form.Label>
            <Form.Control as="select">
              <option>Finding Drinks</option>
              <option>Store Locations</option>
              <option>Prices on Items</option>
              <option>Store hours</option>
              <option>Other(describe below)</option>
            </Form.Control>
          </Form.Group>

          <Form.Group controlId="exampleForm.ControlTextarea1">
            <Form.Label>
              Please briefly describe what you need help with
            </Form.Label>
            <Form.Control
              as="textarea"
              name="text"
              onChange={this.handleInput}
              rows={3}
            />
          </Form.Group>
          <Button variant="primary" type="submit">
            Submit
          </Button>
          {console.log(this.state.username, this.state.text)}
        </Form>
      </div>
    );
  }
}
