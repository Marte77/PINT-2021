const express = require('express');
const app = express();
const Pessoas = require('./model/Pessoas/Pessoas');
/*const Admin = require('./model/Pessoas/Admin');
const Outros_Util = require('./model/Pessoas/Outros_Util');
const Utils_Instituicao = require('./model/Pessoas/Utils_Instituicao');
const Report = require('./model/Reports/Report');
const Report_Indoor = require('./model/Reports/Report_Indoor');
const Report_Outdoor_Outros_Util = require('./model/Reports/Report_Outdoor_Outros_Util');
const Report_Outdoor_Util_Instituicao = require('./model/Reports/Report_Outdoor_Util_Instituicao');
const Tabela_LikesDislikes = require('./model/Reports/Tabela_LikesDislikes');
const Alertas = require('./model/Alertas');
const Comentarios = require('./model/Comentarios');
const Instituicao = require('./model/Instituicao');
const List_Instituicao = require('./model/List_Instituicao');
const Lista_Favoritos = require('./model/Lista_Favoritos');
const Local = require('./model/Local');
const Local_Indoor = require('./model/Local_Indoor');
const Tipo_Alertas = require('./model/Tipo_Alertas');
const Util_pertence_Inst = require('./model/Util_pertence_Inst');*/
//Configurações
app.set('port', process.env.PORT|| 3000);
//Middlewares
app.use(express.json());

app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Authorization, X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Allow-Request-Method');
    res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, DELETE');
    res.header('Allow', 'GET, POST, OPTIONS, PUT, DELETE');
    next();
    }); 
app.get('/Pessoas',(req,res)=>{
//res.send("Hello World");
});
app.get('/Admin',(req,res)=>{
//res.send("Rota TESTE.");
});
app.get('/Outros_Util',(req,res)=>{
//res.send("Hello World");
});
app.get('/Utils_Instituicao',(req,res)=>{
//res.send("Hello World");
});
app.get('/Report',(req,res)=>{
//res.send("Hello World");
});
app.get('/Report_Indoor',(req,res)=>{
//res.send("Hello World");
});
app.get('/Report_Outdoor_Outros_Util',(req,res)=>{
//res.send("Hello World");
});
app.get('/Report_Outdoor_Util_Instituicao',(req,res)=>{
//res.send("Hello World");
});
app.get('/Tabela_LikesDislikes',(req,res)=>{
//res.send("Hello World");
});
app.get('/Alertas',(req,res)=>{
//res.send("Hello World");
});
app.get('/Comentarios',(req,res)=>{
//res.send("Hello World");
});

app.get('/Instituicao',(req,res)=>{
//res.send("Hello World");
});
app.get('/List_Instituicao',(req,res)=>{
//res.send("Hello World");
});
app.get('/Lista_Favoritos',(req,res)=>{
//res.send("Hello World");
});
app.get('Local',(req,res)=>{
//res.send("Hello World");
});
app.get('Local_Indoor',(req,res)=>{
//res.send("Hello World");
});
app.get('Tipo_Alertas',(req,res)=>{
//res.send("Hello World");
});
app.get('Util_pertence_Inst',(req,res)=>{
//res.send("Hello World");
});
app.listen(app.get('port'),()=>{
console.log("Start server on port "+app.get('port'))
})
