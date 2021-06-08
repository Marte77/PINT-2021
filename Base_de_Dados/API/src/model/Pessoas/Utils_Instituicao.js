var Sequelize = require('sequelize');
var sequelize = require('./database');
var Utils_Instituicao = sequelize.define('Utils_Instituicao', {
ID_Util: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //PK & FK
Data_Nascimento: Sequelize.DATEONLY,
Cidade: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Email: Sequelize.STRING,
UNome: Sequelize.STRING,
Localização: Sequelize.STRING,
PNome: Sequelize.STRING,
Pontos: Sequelize.INTEGER,
Ranking: Sequelize.INTEGER,
Codigo_Empresa: Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Utils_Instituicao