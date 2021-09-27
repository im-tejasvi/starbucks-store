import React from "react";
import Cards from "react-credit-cards";
import { Button } from "react-bootstrap";
import { withRouter } from "react-router-dom";
import NavBarHome from "./NavBarHome";
import Swal from "sweetalert2";
import axios from "axios";
import config from "../config.json";
const api = config.BACKEND_URL;
class PaymentForm extends React.Component {
  state = {
    cvc: "",
    expiry: "",
    focus: "",
    name: "",
    number: "",
    isChecked: false,
    notes: "",
    transactionCurrency: "USD",
  };
  async createOrder() {
    console.log(this.state.email, this.state.password, this.state.username);
    axios
      .post(
        api + "payments/process",
        {},
        {
          params: {
            userName: this.state.username,
            email: this.state.email,
            password: this.state.password,
            cvv: this.state.cvc,
            cardnumber: this.state.number,
            firstname: window.localStorage.getItem("firstname"),
            lastname: window.localStorage.getItem("lastname"),
            email: window.localStorage.getItem("email"),
            zip: window.localStorage.getItem("zipcode"),
            address: window.localStorage.getItem("address"),
            city: window.localStorage.getItem("city"),
            state: window.localStorage.getItem("state"),
            phonenumber: window.localStorage.getItem("phonenumber"),
            expmonth: this.state.expiry.substring(0, 2),
            expyear: this.state.expiry.substring(2, 4),
            notes: this.state.notes,
            transactionCurrency: this.state.transactionCurrency,
            orderNumber: window.localStorage.getItem("orderID"),
            transactionAmount: window.localStorage.getItem("orderTotal"),
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
        this.props.history.push("/cashier");
      });
  }

  handleInputFocus = (e) => {
    this.setState({ focus: e.target.name });
  };
  submitForm(e) {
    e.preventDefault();
    this.createOrder();
    Swal.fire(
      "Success!",
      "Card Information saved! View your order here",
      "success"
    );
  }

  handleInputChange = (e) => {
    const { name, value } = e.target;

    this.setState({ [name]: value });
  };
  toggleCheckboxChange = () => {
    this.setState(({ isChecked }) => ({
      isChecked: !isChecked,
    }));
  };

  render() {
    const { isChecked } = this.state;
    return (
      <div id="PaymentForm">
        <NavBarHome />
        <h3>Hello {window.localStorage.getItem("username")}!</h3>
        <h3>Please Enter A Debit or Credit Card</h3>
        <Cards
          cvc={this.state.cvc}
          expiry={this.state.expiry}
          focused={this.state.focus}
          name={this.state.name}
          number={this.state.number}
        />
        <br></br>
        <form
          style={{ textAlign: "center" }}
          onSubmit={this.submitForm.bind(this)}
        >
          <input
            type="tel"
            name="number"
            maxLength="16"
            placeholder="Card Number"
            onChange={this.handleInputChange}
            onFocus={this.handleInputFocus}
            required
          />

          <br></br>
          <br></br>
          <input
            type="name"
            name="name"
            placeholder="Name"
            onChange={this.handleInputChange}
            onFocus={this.handleInputFocus}
            required
          />
          <br></br>
          <br></br>
          <input
            type="expiry"
            name="expiry"
            placeholder="Expiry"
            maxLength="4"
            onChange={this.handleInputChange}
            onFocus={this.handleInputFocus}
            required
          />
          <br></br>
          <br></br>
          <input
            type="cvc"
            name="cvc"
            placeholder="CVC"
            maxLength="3"
            onChange={this.handleInputChange}
            onFocus={this.handleInputFocus}
            required
          />
          <br></br>
          <br></br>
          <label name="showCard">
            <h3>Remember Card Info</h3>
          </label>
          <input
            style={{ margin: ".5rem" }}
            type="checkbox"
            name="showCard"
            onChange={this.toggleCheckboxChange}
            checked={isChecked}
          />
          <br></br>
          <Button type="submit">Submit</Button>
          {/* {console.log(this.state.cvc, this.state.expiry, this.state.name, this.state.number, this.state.isChecked)} */}
        </form>
      </div>
    );
  }
}
export default withRouter(PaymentForm);
