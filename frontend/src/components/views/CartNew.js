import React, { Component } from "react";
import Fade from "react-reveal/Fade";
import { connect } from "react-redux";
import Modal from "react-modal";
import Zoom from "react-reveal/Zoom";
import formatCurrency from "../../utils";
import { clearOrder, createOrder } from "../actions/orderActions";
import { removeFromCart } from "../actions/cartActions";
import axios from "axios";
import config from "../../config.json";
import { Route, withRouter } from "react-router-dom";
const api = config.BACKEND_URL;

class CartNew extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      email: "",
      address: "",
      username: "",
      password: "",
      id: "1",
      paymentmethod: "2",
      showCheckout: false,
      orderjson: "hello",
      zipCode: 94040,
      firstname: "",
      lastname: "",
      phonenumber: "",
      city: "",
      state: "",
    };
    this.handleClick = this.handleClick.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }
  handleInput = (e) => {
    e.preventDefault();

    this.setState({ [e.target.name]: e.target.value });
    window.localStorage.setItem("firstname", this.state.firstname);
    window.localStorage.setItem("lastname", this.state.lastname);
    window.localStorage.setItem("email", this.state.email);
    window.localStorage.setItem("zipcode", this.state.zipCode);
    window.localStorage.setItem("address", this.state.address);
    window.localStorage.setItem("password", this.state.password);
    window.localStorage.setItem(
      "orderContent",
      JSON.stringify(this.props.cartItems)
    );
    window.localStorage.setItem(
      "orderTotal",
      parseInt(this.props.cartItems.reduce((a, c) => a + c.price * c.count, 0))
    );
    window.localStorage.setItem("city", this.state.city);
    window.localStorage.setItem("phonenumber", this.state.phonenumber);
    window.localStorage.setItem("state", this.state.state);
  };
  handleClick = (e) => {
    this.props.history.push("/payment");
  };

  createOrder = (orderContent, price) => {
    console.log(window.localStorage.getItem("orderTotal"));
    console.log(this.state.id);
    console.log(this.state.paymentmethod);

    axios
      .post(
        api + "/create/order",
        { orderContent: window.localStorage.getItem("orderContent") },
        {
          params: {
            userID: this.state.id,
            paymentMethod: this.state.paymentmethod,
            orderTotal: window.localStorage.getItem("orderTotal"),
          },
        }
      )
      .then((response) => {
        console.log(response);
        window.localStorage.setItem("orderID", response.data);
      });
    // this.props.history.push('/payment');
    // const order = {
    //   name: this.state.name,
    //   email: this.state.email,
    //   address: this.state.address,
    //   cartItems: this.props.cartItems,
    //   total: this.props.cartItems.reduce((a, c) => a + c.price * c.count, 0),
    // };
    // this.props.createOrder(order);
  };
  onSubmit(e) {
    e.preventDefault();
    this.createOrder();
    //   Swal.fire(
    //   'Success!',
    //   'You have successfully registered! Please proceed to login',
    //   'success'
    // )
  }
  closeModal = () => {
    this.props.clearOrder();
  };
  render() {
    const { cartItems, order } = this.props;
    return (
      <div>
        {cartItems.length === 0 ? (
          <div className="cart cart-header">Cart is empty</div>
        ) : (
          <div className="cart cart-header">
            You have {cartItems.length} in the cart{" "}
          </div>
        )}

        {order && (
          <Modal isOpen={true} onRequestClose={this.closeModal}>
            <Zoom>
              <button className="close-modal" onClick={this.closeModal}>
                x
              </button>
              <div className="order-details">
                <h3 className="success-message">Your order has been placed.</h3>
                <h2>Order {order._id}</h2>
                <ul>
                  <li>
                    <div>Name:</div>
                    <div>{order.name}</div>
                  </li>
                  <li>
                    <div>Email:</div>
                    <div>{order.email}</div>
                  </li>
                  <li>
                    <div>Address:</div>
                    <div>{order.address}</div>
                  </li>
                  <li>
                    <div>Date:</div>
                    <div>{order.createdAt}</div>
                  </li>
                  <li>
                    <div>Total:</div>
                    <div>{formatCurrency(order.total)}</div>
                  </li>
                  <li>
                    <div>Cart Items:</div>
                    <div>
                      {order.cartItems.map((x) => (
                        <div>
                          {x.count} {" x "} {x.title}
                        </div>
                      ))}
                    </div>
                  </li>
                </ul>
              </div>
            </Zoom>
          </Modal>
        )}
        <div>
          <div className="cart">
            <Fade left cascade>
              <ul className="cart-items">
                {cartItems.map((item) => (
                  <li key={item._id}>
                    <div>
                      <img src={item.image} alt={item.title}></img>
                    </div>
                    <div>
                      <div>{item.title}</div>
                      <div className="right">
                        {formatCurrency(item.price)} x {item.count}{" "}
                        <button
                          className="button"
                          onClick={() => this.props.removeFromCart(item)}
                        >
                          Remove
                        </button>
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            </Fade>
          </div>
          {cartItems.length !== 0 && (
            <div>
              <div className="cart">
                <div className="total">
                  <div>
                    Total:{" "}
                    {formatCurrency(
                      cartItems.reduce((a, c) => a + c.price * c.count, 0)
                    )}
                  </div>
                  <button
                    onClick={() => {
                      this.setState({ showCheckout: true });
                      console.log(cartItems);
                      console.log(
                        formatCurrency(
                          cartItems.reduce((a, c) => a + c.price * c.count, 0)
                        )
                      );
                    }}
                    className="button primary"
                  >
                    Proceed
                  </button>
                </div>
              </div>
              {this.state.showCheckout && (
                <Fade right cascade>
                  <div className="cart">
                    {/* (event)=> {this.createOrder(event, JSON.stringify(cartItems), parseInt( cartItems.reduce((a, c) => a + c.price * c.count, 0)))} */}
                    {/* onSubmit={this.onSubmit} */}
                    <form onSubmit={this.onSubmit}>
                      <ul className="form-container">
                        <li>
                          <label>Email</label>
                          <input
                            name="email"
                            type="text"
                            required
                            onChange={this.handleInput}
                          ></input>
                        </li>
                        <li>
                          <label>Password</label>
                          <input
                            name="password"
                            type="password"
                            required
                            onChange={this.handleInput}
                          ></input>
                        </li>
                        <li>
                          <label>First Name</label>
                          <input
                            name="firstname"
                            type="text"
                            required
                            onChange={this.handleInput}
                          ></input>
                        </li>
                        <li>
                          <label>Last Name</label>
                          <input
                            name="lastname"
                            type="text"
                            required
                            onChange={this.handleInput}
                          ></input>
                        </li>
                        <li>
                          <label>Address</label>
                          <input
                            name="address"
                            type="text"
                            required
                            onChange={this.handleInput}
                          ></input>
                        </li>
                        <li>
                          <label>Zipcode</label>
                          <input
                            name="zipCode"
                            placeholder={this.state.zipCode}
                            type="number"
                            maxLength="5"
                            required
                            onChange={this.handleInput}
                          ></input>
                          <label>City</label>
                          <input
                            name="city"
                            type="text"
                            required
                            onChange={this.handleInput}
                          ></input>
                          <label>Phone Number</label>
                          <input
                            name="phonenumber"
                            type="text"
                            required
                            onChange={this.handleInput}
                          ></input>
                          <label>State</label>
                          <input
                            name="state"
                            type="text"
                            required
                            onChange={this.handleInput}
                          ></input>
                          {console.log(this.state.email)}
                          {console.log(this.state.password)}
                          {console.log(this.state.zipCode)}
                          {console.log(this.state.address)}
                          {console.log(this.state.firstname)}
                          {console.log(this.state.lastname)}
                          {console.log(this.state.city)}
                          {console.log(this.state.phonenumber)}
                          {console.log(this.state.state)}
                        </li>
                        <li>
                          <a
                            href="/payment"
                            className="button primary"
                            type="submit"
                          >
                            Checkout
                          </a>
                        </li>
                      </ul>
                    </form>
                  </div>
                </Fade>
              )}
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default connect(
  (state) => ({
    order: state.order.order,
    cartItems: state.cart.cartItems,
  }),
  { removeFromCart, createOrder, clearOrder }
)(CartNew);
