import React, { Component } from "react";
import NavBarHome from "./NavBarHome";
import data from "../data.json";
import Products from "./Products";
import { Button, Card, Dropdown } from "react-bootstrap";
import "./Cart.css";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
export default class Cart extends Component {
  constructor(props) {
    super(props);
    this.state = {
      products: data.products,
    };
  }

  render() {
    return (
      <div>
        <NavBarHome />
        <div>
          <h3 style={{ textAlign: "left", paddingLeft: "20px" }}> Menu</h3>
          <br></br>
          <h5 style={{ paddingLeft: "20px" }}>Drinks</h5>
          <br></br>
          {this.state.products
            ? this.state.products.map((product) => {
                return (
                  <li
                    key={product._id}
                    style={{ listStyle: "none", paddingLeft: "20px" }}
                  >
                    <div>
                      <b>{product.title}</b>

                      <a href="#">
                        <img
                          className="circular"
                          src={product.image}
                          height={"200px"}
                          alt={product.title}
                        ></img>
                      </a>
                      {/* ${product.price} */}
                    </div>

                    <Link to={`/menu/${product._id}`}>
                      {" "}
                      <Button variant="info"> View Drinks</Button>
                    </Link>
                  </li>
                );
              })
            : "Loading"}
        </div>
      </div>
    );
  }
}
