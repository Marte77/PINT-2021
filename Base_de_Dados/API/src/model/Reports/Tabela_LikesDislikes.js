var Sequelize = require('sequelize');
var sequelize = require('./database');
var Tabela_LikesDislikes = sequelize.define('Tabela_LikesDislikes', {
IDPessoa: Sequelize.INTEGER, //pk & fk
ID_Report: Sequelize.INTEGER, //pk & fk
Like: Sequelize.BOOLEAN,
Dislike: Sequelize.BOOLEAN
},
{
timestamps: false,
});
module.exports = Tabela_LikesDislikes