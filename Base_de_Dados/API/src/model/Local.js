var Sequelize = require('sequelize');
var sequelize = require('./database');
var Local = sequelize.define('Local', {
ID_Local: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
ID_Instituicao: Sequelize.INTEGER, //fk
Nome: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Descricao: Sequelize.STRING,
URL_Imagem: Sequelize.STRING,
Localizacao: Sequelize.STRING,
Coordenadas: Sequelize.DECIMAL
},
{
timestamps: false,
});
module.exports = Local