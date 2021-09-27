import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";
import axios from "axios";
export default class NewOrder extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      password: "",
      id: "1",
    };
  }
  // async createOrder(){
  //     console.log(this.state.email, this.state.password, this.state.username)
  //     axios.post(``,{},{
  //       params: {
  //         userID: this.state.id
  //         }
  //     }).then(response =>{
  //       console.log(response);
  //     //   Swal.fire(
  //     //     'Success!',
  //     //     'You have successfully created a Post!',
  //     //     'success'
  //     //   )
  //     //   this.props.history.push('/')
  //     })
  //   }

  render() {
    return <div></div>;
  }
}
