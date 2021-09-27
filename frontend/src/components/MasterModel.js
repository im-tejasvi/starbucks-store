import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import axios from "axios";
import config from "../config.json";
import Swal from "sweetalert2";
const api = config.BACKEND_URL;
export default class MasterModel extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      show: false,
      message: "",
      id: this.props.id,
      username: "testacc5",
    };
    this.handleChange = this.handleChange.bind(this);
  }
  handleModal() {
    this.setState({
      show: !this.state.show,
    });
    // console.log(this.state.message);
    // console.log(this.props.id);
  }
  handleModal2() {
    this.setState({
      show: !this.state.show,
    });
    console.log(this.state.message);
    console.log(this.props.id);
    this.replyToPost();
  }
  handleChange(event) {
    this.setState({ message: event.target.value });
  }

  //  async componentDidMount(){
  //       await this.replyToPost('abc', "hello", "martinvlad");

  //   }

  async replyToPost() {
    axios
      .post(
        api + `/respond/userReport`,
        {},
        {
          params: {
            reportID: this.props.id,
            replyContent: this.state.message,
          },
        }
      )
      .then((response) => {
        if (response.data) {
          Swal.fire(
            "Success!",
            "You have successfully created a Post!",
            "success"
          );
        }

        console.log(response);
      });
  }
  render() {
    return (
      <div>
        <Button
          block
          onClick={() => {
            this.handleModal();
          }}
          size="sm"
        >
          Reply
        </Button>
        <Modal show={this.state.show}>
          <Modal.Header>Enter Your Reply Below</Modal.Header>
          <Modal.Body>
            {console.log(this.state.message)}
            <input
              type="text"
              value={this.state.message}
              onChange={this.handleChange}
              size="50"
            ></input>
          </Modal.Body>
          <Modal.Footer>
            <Button
              onClick={() => {
                this.handleModal2();
              }}
            >
              Submit Reply
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }
}
