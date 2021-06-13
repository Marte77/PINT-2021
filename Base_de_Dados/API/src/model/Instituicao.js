var Sequelize = require('sequelize');
var sequelize = require('./database');
var Instituicao = sequelize.define('Instituicao', {
ID_Instituicao: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Nome: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Telefone: Sequelize.INTEGER,
Descricao: Sequelize.TEXT,
URL_Imagem: Sequelize.TEXT,
Longitude: Sequelize.DOUBLE,
Latitude: Sequelize.DOUBLE,
Localizacao: Sequelize.TEXT,
Codigo_Empresa: Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Instituicao