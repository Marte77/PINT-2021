var Sequelize = require('sequelize');
var sequelize = require('./database');
var Outros_Util = sequelize.define('Outros_Util', {
ID_Outro_Util: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Pontos_Outro_Util: Sequelize.INTEGER,
Ranking: Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Outros_Util