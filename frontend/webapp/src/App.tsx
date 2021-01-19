import React, { useEffect, useState} from 'react'
import {
   Button,
} from "@patternfly/react-core";
import { Route, Switch } from 'react-router'
import { BrowserRouter, Link } from 'react-router-dom'
import AboutPage from './AboutPage'
import BrowsePage from './BrowsePage'
import SellPage from './SellPage'
import OfferingPage from './OfferingPage'

import './App.css';
import LoginModal from './LoginModal';
import UserInfo from './UserInfo'
import { getUserToken, logout } from './userService';

var prevScrollpos = window.pageYOffset;
window.onscroll = function() {
  const currentScrollPos = window.pageYOffset;
  const navbar = document.getElementById("navbar")
  if (!navbar) {
     return;
  } else if (prevScrollpos > currentScrollPos) {
    navbar.style.top = "0";
  } else {
    navbar.style.top = "-60px";
  }
  prevScrollpos = currentScrollPos;
}

function App() {
   const [loginModalOpen, setLoginModalOpen] = useState(false)
   const [userToken, setUserToken] = useState<string>()
   useEffect(() => {
      setUserToken(getUserToken())
   }, [loginModalOpen])
   return (
      <BrowserRouter>
         <header id="navbar">
            <div className="container">
               <Link to="/" id="title">Vehicle Market</Link>
               <Link to="/browse">Browse</Link>
               <Link to="/sell">Sell</Link>
               <Link to="/about">About us</Link>
               <div id="loginbuttons">
                  { userToken !== undefined && <>
                     <UserInfo token={ userToken } />
                     <Button variant="link" onClick={ () => logout().finally(() => setUserToken(undefined)) } >Logout</Button>
                  </> }
                  { userToken === undefined && <>
                     <LoginModal isOpen={ loginModalOpen } onClose={ () => setLoginModalOpen(false) } />
                     <Button variant="link" onClick={ () => setLoginModalOpen(true) }>Login</Button>
                     <Button variant="link">Register</Button>
                  </> }
               </div>
            </div>
         </header>
         <section id="content">
            <div className="container">
               <Switch>
                  <Route exact path="/" component={BrowsePage} />
                  <Route exact path="/browse" component={BrowsePage} />
                  <Route exact path="/sell" component={SellPage} />
                  <Route exact path="/about" component={AboutPage} />
                  <Route path="/offering/:offerId" component={OfferingPage} />
               </Switch>
            </div>
         </section>
      </BrowserRouter>
   )
}

export default App;
