var Sequelize = require('sequelize');
var sequelize = require('./database');
var Outros_Util = sequelize.define('Outros_Util', {
ID_Outro_Util: {
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
Pontos_Outro_Util: Sequelize.INTEGER,
Ranking: Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Outros_Util