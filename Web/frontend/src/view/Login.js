import React, { Component } from "react";
import logo from "./img/logo.png"; // import imagem
//import './App.css';
import '../index.css';
export default class Login extends Component {
    raiseInvoiceClicked(){
    const url = '/admin/home';
    window.open(url, '_blank');}
    render(){
        return (
    <div className="App">
      <div className="outer">
        <div className="inner">
            <form>
            <div className="cor_form">
            <img src={logo} alt="logo" border="0" width="100" height="100" class="alinhar_centro"/>
                <br/>
                <h5>Crowd Zero</h5>

                <div className="form-group">
                    <label>Email de Administrador:</label>
                    <input type="email" className="form-control" placeholder="Insira o seu email" />
                </div>

                <div className="form-group">
                    <label>Password:</label>
                    <input type="password" className="form-control" placeholder="Insira a sua password "/>
                </div>

                <p className="pedir-acesso">
                    Ainda não tem uma conta? <a href="/registar">Pedir acesso</a>
                    </p>
                <br/>
                <button type="submit" className="btn btn-dark btn-lg btn-block" onClick={this.raiseInvoiceClicked}> Iniciar sessão </button>  
                 </div>
            </form>
            </div>
            </div>
            </div>
            
        );
    }
}
