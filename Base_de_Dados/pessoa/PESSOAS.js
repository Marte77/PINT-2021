var Sequelize = require('sequelize');
var sequelize = require('../basededados');
var PESSOAS = sequelize.define('PESSOAS', {
ID: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
DATA_NASCIMENTO: Sequelize.DATE,
CIDADE: Sequelize.STRING(15),
CODIGO_POSTAL: Sequelize.INTEGER,
EMAIL: Sequelize.STRING(30),
UNOME: Sequelize.STRING(15),
LOCALIZACAO: Sequelize.STRING(20),
PNOME: Sequelize.STRING(15),
},
{
timestamps: false,
});
module.exports = PESSOAS