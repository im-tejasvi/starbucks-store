import React, { Component } from "react";
import Filter from "../Filter";
import Products from "../Products";

import CartNew from "./CartNew";

class HomeView extends Component {
  render() {
    return (
      <div>
        <div className="content">
          <div className="main">
            <Filter></Filter>
            <Products></Products>
          </div>
          <div className="sidebar">
            <CartNew />
          </div>
        </div>
      </div>
    );
  }
}

export default HomeView;
