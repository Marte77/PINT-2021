var Sequelize = require('sequelize');
var sequelize = require('./database');
var Utils_Instituicao = sequelize.define('Utils_Instituicao', {
ID_Util: {
type: Sequelize.INTEGER,
primaryKey: true,          //PK
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //PK & FK
Pontos: Sequelize.INTEGER,
Ranking: Sequelize.INTEGER,
Codigo_Empresa: Sequelize.INTEGER
},
{
timestamps: false,
});
Utils_Instituicao.belongsTo(Pessoas);
module.exports = Utils_Instituicao