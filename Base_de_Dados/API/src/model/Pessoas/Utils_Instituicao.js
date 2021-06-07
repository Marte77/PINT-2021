var Sequelize = require('sequelize');
var sequelize = require('./database');
var Utils_Instituicao = sequelize.define('Utils_Instituicao', {
ID_Util: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Pontos: Sequelize.INTEGER,
Ranking: Sequelize.INTEGER,
Codigo_Empresa: Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Utils_Instituicao