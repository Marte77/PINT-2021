const express = require('express');
const app = express();
//const list_instituicaos= require('./model/List_LocalFavorito');
//const list_favoritos= require('./model/Lista_Favoritos');
//const comentarios= require('./model/Comentarios');
//const herancas = require('./colocarHerancas');
const todaspessoasRoute = require('./routes/todaspessoasRoute');
const alertasRoute = require('./routes/alertasRoute');
const reportsRoute = require('./routes/reportesRoute')
const localRoute = require('./routes/localsRoute')
const likedislikeRoute = require('./routes/likedislikeRoute')
const comentariosRoute = require('./routes/comentariosRoute')
const favoritosRoute = require('./routes/favoritosRoute')
const instituicaoRoute = require('./routes/instituicaoRoute')
const utilizadores_inst = require('./routes/utilizadores_instituicao')

const middleware = require('./middleware');


//Configurações
app.set('port', process.env.PORT|| 3000);
//app.set('port', 3001);
//Middlewares
app.use(express.json());


app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Authorization, X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Allow-Request-Method');
    res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, DELETE');
    res.header('Allow', 'GET, POST, OPTIONS, PUT, DELETE');
    next();
    }); 
app.get('/',(req,res)=>res.send({res:"ola"}))
app.use('/Pessoas', todaspessoasRoute);
app.use('/Report',reportsRoute);
app.use('/Alertas', alertasRoute);
app.use('/Locais',localRoute);
app.use('/LikeDislike',likedislikeRoute);
app.use('/Comentarios',comentariosRoute);
app.use('/Favoritos',favoritosRoute);
app.use('/Instituicao',instituicaoRoute);
app.use('/Utilizadores',utilizadores_inst);
  /*  Instituicao.create({Nome:'nome',Codigo_Postal:3670222, Telefone:123,Descricao:'123',URL_Imagem:'123',Coordenadas:'123',Localizacao:123,Codigo_Empresa:123})
Admin.create({Data_Nascimento:'01/01/2001', Cidade:36702222, Codigo_Postal:123,Email:'123',UNome:'123',Localização:'123',PNome:'123', Password:'123',ID_Instituicao:1})
Utils_Instituicao.create({Data_Nascimento:'01/01/2001', Cidade:36702222, Codigo_Postal:123,Email:'123',UNome:'123',Localização:'123',PNome:'123', Password:'123',Pontos:1,Ranking:1,Codigo_Empresa:1})
Outros_Util.create({Data_Nascimento:'01/01/2001', Cidade:36702222, Codigo_Postal:123,Email:'123',UNome:'123',Localização:'123',PNome:'123', Password:'123',Pontos_Outro_Util:1,Ranking:1})
Admin.create({Data_Nascimento:'01/01/2001', Cidade:36702222, Codigo_Postal:123,Email:'123',UNome:'123',Localização:'123',PNome:'123', Password:'123',ID_Instituicao:1})
Utils_Instituicao.create({Data_Nascimento:'01/01/2001', Cidade:36702222, Codigo_Postal:123,Email:'123',UNome:'123',Localização:'123',PNome:'123', Password:'123',Pontos:1,Ranking:1,Codigo_Empresa:1})
Outros_Util.create({Data_Nascimento:'01/01/2001', Cidade:36702222, Codigo_Postal:123,Email:'123',UNome:'123',Localização:'123',PNome:'123', Password:'123',Pontos_Outro_Util:1,Ranking:1})
*/

app.listen(app.get('port'),()=>{
console.log("Start server on port "+app.get('port'))
})