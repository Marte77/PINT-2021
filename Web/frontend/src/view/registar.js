import React, { Component } from "react";


// CSS Modules, react-datepicker-cssmodules.css
// import 'react-datepicker/dist/react-datepicker-cssmodules.css';

export default class SignUp extends Component {
    render() {
        return (
            <form>
                <h3> Criar conta</h3>
                <div className="form-group">
                    <label>Primeiro nome:</label>
                    <input type="text" className="form-control" placeholder="Exemplo: João" />
                </div>

                <div className="form-group">
                    <label>Ultimo nome:</label>
                    <input type="text" className="form-control" placeholder="Exemplo: Almeida" />
                </div>

                <div className="form-group">
                    <label>Seu Email:</label>
                    <input type="email" className="form-control" placeholder="Ex: Joaoalmeida01@gmail.com" />
                </div>
                
                <div className="form-group">
                    <label>Cidade:</label>
                    <input type="text" className="form-control" placeholder="Ex: Viseu" />
                </div>

                <div className="form-group">
                    <label>Código Postal:</label>
                    <input type="text" className="form-control" placeholder="Ex: 3680-150" />
                </div>

                 <div className="form-group">
                    <label>Localização:</label>
                    <input type="text" className="form-control" placeholder="Morada atual" />
                </div>

                <div className="form-group">
                    <label>Password:</label>
                    <input type="password" className="form-control" placeholder="Insira uma senha segura" />
                </div>
          
          <div className="form-group">
                    <label>Data nascimento:</label>
                    <input type="date" className="form-control" />
                </div>
                <p className="pedir-acesso">
                    Já tem uma conta?  <a href="/login">Entra aqui!</a>
                </p>
                <br/>
                <button type="submit" className="btn btn-dark btn-lg btn-block">Submeter pedido</button>
                <br/>
            </form>
        );
    }
}