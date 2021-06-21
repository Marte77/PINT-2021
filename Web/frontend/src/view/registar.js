import React, { Component } from "react";
import axios from 'axios';
//import './login.css';
// CSS Modules, react-datepicker-cssmodules.css
// import 'react-datepicker/dist/react-datepicker-cssmodules.css';
import '../index.css';
export default class login extends Component {
constructor(props){
super(props);
this.state = {
campoPnome:"",
campoUnome: "",
campoEmail:"",
campoCidade:"",
campoCpostal:"",
campoPassword:"",
campoData:""
}
}

    render() {
        return (
            <div className="App">
      <div className="outer">
        <div className="inner">
            <form>
                <h3> Criar conta</h3>
                <div className="form-group">
                    <label>Primeiro nome:</label>
                    <input type="text" className="form-control" placeholder="Exemplo: João" value={this.state.campoPnome} onChange={(value)=> 
    this.setState({campoPnome:value.target.value})} />
                </div>

                <div className="form-group">
                    <label>Ultimo nome:</label>
                    <input type="text" className="form-control" placeholder="Exemplo: Almeida" value={this.state.campoUnome} onChange={(value)=> 
    this.setState({campoUnome:value.target.value})} />
                </div>

                <div className="form-group">
                    <label>Seu Email:</label>
                    <input type="email" className="form-control" placeholder="Ex: Joaoalmeida01@gmail.com" value={this.state.campoEmail} onChange={(value)=> 
    this.setState({campoEmail:value.target.value})}  />
                </div>
                
                <div className="form-group">
                    <label>Cidade:</label>
                    <input type="text" className="form-control" placeholder="Ex: Viseu" value={this.state.campoCidade} onChange={(value)=> 
    this.setState({campoCidade:value.target.value})} />
                </div>

                <div className="form-group">
                    <label>Código Postal:</label>
                    <input type="text" className="form-control" placeholder="Ex: 3680-150" value={this.state.campoCpostal} onChange={(value)=> 
    this.setState({campoCpostal:value.target.value})} />
                </div>

                 <div className="form-group">
                    <label>Localização:</label>
                    <input type="text" className="form-control" placeholder="Morada atual" value={this.state.campoLocalizacao} onChange={(value)=> 
    this.setState({campoLocalizacao:value.target.value})}  />
                </div>

                <div className="form-group">
                    <label>Password:</label>
                    <input type="password" className="form-control" placeholder="Insira uma senha segura" value={this.state.campoPassword} onChange={(value)=> 
    this.setState({campoPassword:value.target.value})} />
                </div>
          
          <div className="form-group">
                    <label>Data nascimento:</label>
                    <input type="date" className="form-control" value={this.state.campoData} onChange={(value)=> 
    this.setState({campoData:value.target.value})} />
                </div>
                <p className="pedir-acesso">
                    Já tem uma conta?  <a href="/login">Entra aqui!</a>
                </p>
                <br/>
                <button type="submit" className="btn btn-dark btn-lg btn-block" class="btn btn-primary" 
    onClick={()=>this.sendSave()}>Submeter pedido</button>
                <br/>
            </form>
            </div>
            </div>
            </div>
        );
    }
sendSave(){
   if (this.state.campoCidade==="") {
    alert("Campo genero vazio!")
    }
    else if (this.state.campoData==="") {
    alert("Campo foto vazio!")
    }
    else if (this.state.campoPassword==="") {
    alert("Campo titulo vazio!")
    }
    else if (this.state.campoLocalizacao==="") {
    alert("Campo descrição vazio!")
    }
    else {
    const baseUrl = "http://localhost:3000/Pessoas/createAdmin"
    const datapost = {
PNome : this.state.campoPnome,
UNome : this.state.campoUnome,
Email : this.state.campoEmail,
Cidade : this.state.campoCidade,
Localização : this.state.campoLocalizacao,
Codigo_Postal: this.state.campoCpostal,
Password: this.state.campoPassword,
Data_Nascimento : this.state.campoData,
InstituicaoIDInstituicao : 1,
/*Verificado : false*/
    }
    console.log(datapost);
    axios.post(baseUrl,datapost)
    .then(response=>{
    if (response.data.success===true) {
    alert(response.data.message)
    }
    else {
    alert(response.data.message)
    }
    }).catch(error=>{
    alert("Error 34 "+error)
    })
    }
    }
}
 //export default login;