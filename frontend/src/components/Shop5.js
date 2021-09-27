import React from "react";
import store from "./store";
import { Provider } from "react-redux";
import { BrowserRouter, Route, Link } from "react-router-dom";
import HomeView from "./views/HomeView";

class Shop5 extends React.Component {
  render() {
    return (
      <Provider store={store}>
        <BrowserRouter>
          <div className="grid-container">
            <HomeView />
          </div>
        </BrowserRouter>
      </Provider>
    );
  }
}

export default Shop5;
