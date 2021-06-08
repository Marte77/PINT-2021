var Sequelize = require('sequelize');
var sequelize = require('./database');
var Report = sequelize.define('Report', {
ID_Report: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //fk1
ID_Util: Sequelize.INTEGER, //fk1
Descricao: Sequelize.STRING,
Nivel_Densidade: Sequelize.INTEGER,
N_Likes: Sequelize.INTEGER,
N_Dislikes: Sequelize.INTEGER,
Data: Sequelize.DATE
},
{
timestamps: false,
});
module.exports = Report