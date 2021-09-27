import React, { Component } from "react";
import "../components/Cashier.css";
import { Button } from "react-bootstrap";
import starbuckslogo from "../components/starbuckslogo.jpeg";
import axios from "axios";
import config from "../config.json";
import Swal from "sweetalert2";
const api = config.BACKEND_URL;
export default class Cashier extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
    };
  }

  componentDidMount() {
    // axios.get(api + '/cashier/get/latestOrder',{},{
    console.log();
    this.setState({
      data: JSON.parse(window.localStorage.getItem("orderContent"))[0],
    });
    // })
    //   .then(res => {

    //     //this.setState({ data: JSON.parse(data.orderContent) });
    //     //console.log(this.state.data)

    //   })
    console.log(this.state.data);
  }
  render() {
    return (
      <>
        {this.state.data ? <div>A</div> : <div>B</div>}
        <div>
          <h1>
            <b>Welcome to Starbucks Reserved!</b>
          </h1>
          <div className="center">
            <h4>
              <u>Starbucks Reserved Order</u>
            </h4>
            <br></br>
            <h5>ID : {window.localStorage.getItem("orderTotal")}</h5>
            <h5>Items: {this.state.data.title}</h5>
            <h5>Size: Venti</h5>
            <h5>Total: ${window.localStorage.getItem("orderTotal")}</h5>
            {/* <h5>Total: </h5>{this.state.data.map((item)=>{
   return( <p>
      {item.orderTotal}$
    </p>
   ) })} */}

            <h5>Register: 51959232</h5>
            <h5>Status: Ready for Payment</h5>
          </div>
          <br></br>
          <h3>
            Select Store:{" "}
            <select name="store" id="store">
              <option value="dub-c">Dub-C</option>
              <option value="saab">Starbucks 1</option>
              <option value="mercedes">Starbucks 2</option>
              <option value="audi">Starbucks 4</option>
            </select>
          </h3>

          <br></br>

          <div className="centerimage">
            <img
              alt="panda"
              className="photo"
              src={starbuckslogo}
              style={{ alignContent: "center" }}
            />
          </div>
          <br></br>
          <div className="flex-gap">
            <Button
              onClick={() => {
                Swal.fire(
                  "Success!",
                  "Processed and completed order!",
                  "success"
                );
              }}
              variant="success"
            >
              Complete Order
            </Button>{" "}
          </div>
        </div>
      </>
    );
  }
}
