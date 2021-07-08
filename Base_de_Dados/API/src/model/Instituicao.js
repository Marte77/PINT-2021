var Sequelize = require('sequelize');
var sequelize = require('./database');
var Instituicao = sequelize.define('Instituicao', {
ID_Instituicao: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Nome: Sequelize.STRING,
Email: Sequelize.TEXT,
Codigo_Postal: Sequelize.INTEGER,
Telefone: Sequelize.INTEGER,
Descricao: Sequelize.TEXT,
URL_Imagem: Sequelize.TEXT,
Longitude: Sequelize.DOUBLE,
Latitude: Sequelize.DOUBLE,
Localizacao: Sequelize.TEXT,
Codigo_Empresa: Sequelize.INTEGER,
Lotacao_Pouco:Sequelize.INTEGER,
Lotacao_Moderado:Sequelize.INTEGER,
Lotacao_Elevado:Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Instituicao