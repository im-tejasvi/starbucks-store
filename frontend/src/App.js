import "./App.css";
import BackOffice from "./components/BackOffice";
import Cashier from "./components/Cashier";
import NavBarHome from "./components/NavBarHome";
import PaymentForm from "./components/PaymentForm";
import "react-credit-cards/es/styles-compiled.css";
import HotCoffee from "./components/HotCoffee";
import HotTeas from "./components/HotTeas";
import Cart from "./components/Cart";
import Shop1 from "./components/Shop1";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Shop2 from "./components/Shop2";
import Shop3 from "./components/Shop2";
import Shop4 from "./components/Shop4";
import Shop5 from "./components/Shop5";
import Register from "./components/Register";
import Report from "./components/Report";
import Login from "./components/Login";
import ViewReports from "./components/ViewReports";
import AdminReports from "./components/AdminReports";

function App() {
  return (
    <div className="App">
      <Switch>
        <Route path="/" component={BackOffice} exact />
        <Route path="/cashier" component={Cashier} />
        <Route path="/payment" component={PaymentForm} />
        <Route path="/menu/hotcoffee" component={Shop1} exact />
        <Route path="/menu/hotteas" component={Shop2} exact />
        <Route path="/menu/hotdrinks" component={Shop3} exact />
        <Route path="/menu/coldcoffee" component={Shop4} exact />
        <Route path="/menu/colddrinks" component={Shop5} exact />
        <Route path="/menu" component={Cart} />
        <Route path="/hotcoffee" component={HotCoffee} exact />
        <Route path="/hotteas" component={HotTeas} exact />
        <Route path="/register" component={Register} exact />
        <Route path="/login" component={Login} exact />
        <Route path="/report" component={Report} exact />
        <Route path="/viewreports/:username" component={ViewReports} exact />
        <Route path="/adminreports" component={AdminReports} exact />
      </Switch>
    </div>
  );
}

export default App;
