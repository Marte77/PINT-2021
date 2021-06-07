var Sequelize = require('sequelize');
var sequelize = require('./database');
var Pessoas = sequelize.define('Pessoas', {
ID: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Data_Nascimento: Sequelize.DATE,
Cidade: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Email: Sequelize.STRING,
UNome: Sequelize.STRING,
Localização: Sequelize.STRING,
PNome: Sequelize.STRING,
},
{
timestamps: false,
});
module.exports = Pessoas