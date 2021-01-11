import React from 'react'
import {
   Button,
} from "@patternfly/react-core";
import { Route, Switch } from 'react-router'
import { BrowserRouter, Link } from 'react-router-dom'
import Browse from './Browse'
import About from './About'
import Sell from './Sell'

import './App.css';

function App() {
   return (
      <BrowserRouter>
         <header id="navbar">
            <div className="container">
               <Link to="/browse">Browse</Link>
               <Link to="/sell">Sell</Link>
               <Link to="/about">About us</Link>
               <div id="loginbuttons">
                  <Button variant="link">Login</Button>
                  <Button variant="link">Register</Button>
               </div>
            </div>
         </header>
         <section id="content">
            <div className="container">
               {/* <Switch>
                  <Route exact path="/" component={Browse} />
                  <Route exact path="/browse" component={Browse} />
                  <Route exact path="/sell" component={Sell} />
                  <Route exact path="/about" component={About} />
               </Switch> */}
               <Browse />
            </div>
         </section>
      </BrowserRouter>
   )
}

export default App;
