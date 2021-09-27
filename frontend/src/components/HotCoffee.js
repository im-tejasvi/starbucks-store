import React, { Component } from "react";
import NavBarHome from "./NavBarHome";
import hotcoffee from "../hotcoffee.json";
import { Button, Card, Dropdown } from "react-bootstrap";
export default class HotCoffee extends Component {
  constructor(props) {
    super(props);
    this.state = {
      hot: hotcoffee.products,
      size: "",
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({ size: event.target.value });
  }

  handleSubmit(event) {
    console.log(this.state);
    event.preventDefault();
  }

  render() {
    return (
      <div>
        <NavBarHome />
        <h3 style={{ textAlign: "left" }}>Menu</h3>
        <br></br>
        <h5>Hot Coffees</h5>
        {console.log(this.state.hot)}
        <div>
          <h3 style={{ textAlign: "left" }}> Menu</h3>
          <br></br>
          <h5>Drinks</h5>
          <br></br>
          {this.state.hot
            ? this.state.hot.map((product) => {
                return (
                  <li key={product._id} style={{ listStyle: "none" }}>
                    <div>
                      <b>{product.title}</b>

                      <a href="#">
                        <img
                          class="circular"
                          src={product.image}
                          height={"200px"}
                          alt={product.title}
                        ></img>
                      </a>
                      {/* ${product.price} */}
                      <Button
                        onClick={() => {
                          console.log(product._id, this.state.size);
                        }}
                      >
                        Add to Cart
                      </Button>
                    </div>
                  </li>
                );
              })
            : "Loading"}
          <form onSubmit={this.handleSubmit}>
            <strong>Select Size:</strong>
            {console.log(this.state.size)}
            <select value={this.state.size} onChange={this.handleChange}>
              <option value="small">Small</option>
              <option value="medium">Medium</option>
              <option value="large">Large</option>
              <option value="extra-large">Extra Large</option>
            </select>

            <input type="submit" value="Submit" />
          </form>
        </div>
      </div>
    );
  }
}
