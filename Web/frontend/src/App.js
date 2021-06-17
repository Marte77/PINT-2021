import React from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import Login from "./view/login";
import SignUp from "./view/registar";

function App() {
  return (<Router>
    <div className="App">
      
      <div className="outer">
        <div className="inner">
          <Switch>
            <Route exact path='/' component={Login} />
            <Route path="/login" component={Login} />
            <Route path="/registar" component={SignUp} />
          </Switch>
        </div>
      </div>
    </div></Router>
  );
}

export default App;
