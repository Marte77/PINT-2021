var Sequelize = require('sequelize');
var sequelize = require('./database');
var Outros_Util = sequelize.define('Outros_Util', {
ID_Outro_Util: {
type: Sequelize.INTEGER,
primaryKey: true,            //PK
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //PK & FK
Pontos_Outro_Util: Sequelize.INTEGER,
Ranking: Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Outros_Util