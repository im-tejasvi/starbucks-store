import React, { Component } from "react";
import axios from "axios";
import config from "../config.json";
import { Table, Button } from "react-bootstrap";
import NavBarHome from "./NavBarHome";
import Modal from "./MasterModel";
const api = config.BACKEND_URL;

export default class AdminReports extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      resolved: [],
      unresolved: [],
    };
  }
  componentDidMount() {
    axios.get(api + "/get/allReports", {}, {}).then((res) => {
      const data = res.data;
      const resolved = data.filter((report) => {
        return report.resolved === true;
      });
      const unresolved = data.filter((report) => {
        return report.resolved === false;
      });
      this.setState({ resolved });
      this.setState({ unresolved });
      this.setState({ data });
      console.log(this.state.data);
    });
    // console.log(config);
    // console.log(123);
  }

  render() {
    return (
      <div>
        <NavBarHome />
        <h2 style={{ textAlign: "center" }}>Help Desk Reports</h2>
        <br></br>
        {this.state.data.length > 0 ? (
          <>
            <div style={{ width: "50%", margin: "auto" }}>
              <h3 style={{ textAlign: "center" }}>Unresolved tickets</h3>

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
                  {this.state.unresolved.map((post, i) => {
                    return (
                      <>
                        <tr>
                          <td>{i + 1}</td>
                          <td>
                            <span>{post.reportContent}</span>
                          </td>

                          <td>{post.username}</td>
                          <td>{post.resolved ? "✅" : "❌"}</td>
                          <td>
                            <Modal id={post.id} />
                          </td>
                        </tr>
                      </>
                    );
                  })}
                </tbody>
              </Table>
            </div>
            <h3 style={{ textAlign: "center" }}>Resolved tickets</h3>
            <div style={{ width: "50%", margin: "auto" }}>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>ID #</th>

                    <th>Post Content</th>

                    <th>Username</th>

                    <th>Resolved Status</th>

                    <th>Response</th>
                  </tr>
                </thead>
                <tbody>
                  {this.state.resolved.map((post, i) => {
                    return (
                      <>
                        <tr>
                          <td>{i + 1}</td>
                          <td>
                            <span>{post.reportContent}</span>
                          </td>

                          <td>{post.username}</td>
                          <td>{post.resolved ? "✅" : "❌"}</td>
                          <td>{post.response}</td>
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
