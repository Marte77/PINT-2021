import React from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Admin from "./layouts/Admin";
import Login from "./view/login";
import SignUp from "./view/registar";
import entrar from "./views/Dashboard";
import AdminLayout from "layouts/Admin.js";

function App() {
  return (<Router>
    <div className="App">
      
      <div className="outer">
        <div className="inner">
          <Switch>
            <Route exact path='/' component={Login} />
            <Route path="/login" component={Login} />
            <Route path="/registar" component={SignUp} />
            //após o login, entra na aba
            <Route path="/admin" render={(props) => <AdminLayout {...props} />} />
          </Switch>

        </div>

      </div>

            
    </div>
    </Router>
  );
}
export default App;
