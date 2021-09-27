import React, { Component } from "react";
import axios from "axios";
import config from "../config.json";
import { Table } from "react-bootstrap";
import NavBarHome from "./NavBarHome";
const api = config.BACKEND_URL;

export default class ViewReports extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
    };
  }
  componentDidMount() {
    axios
      .get(
        api + `/get/userReports/${window.localStorage.getItem("username")}`,
        {},
        {}
      )
      .then((res) => {
        const data = res.data;

        this.setState({ data });
        console.log(this.state.data);
      });
    // console.log(config);
  }

  render() {
    return (
      <div>
        <NavBarHome />
        <h2 style={{ textAlign: "center" }}>Your Reports</h2>
        <br></br>
        {this.state.data.length > 0 ? (
          <>
            <div style={{ width: "50%", margin: "auto" }}>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>ID #</th>

                    <th>Post Content</th>

                    <th>Username</th>

                    <th>Resolved Status</th>

                    <th>Replies</th>
                  </tr>
                </thead>
                <tbody>
                  {this.state.data.map((post, i) => {
                    return (
                      <>
                        <tr>
                          <td>{i + 1}</td>
                          <td>
                            <span>{post.reportContent}</span>
                          </td>

                          <td>{post.username}</td>
                          <td>{post.resolved ? "✅" : "❌"}</td>
                          <td>{post.response ? post.response : "N/A"}</td>
                        </tr>
                      </>
                    );
                  })}
                </tbody>
              </Table>
            </div>
          </>
        ) : (
          <p></p>
        )}
        {console.log(this.state.data)}
      </div>
    );
  }
}
