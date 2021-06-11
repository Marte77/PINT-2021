var Sequelize = require('sequelize');
var sequelize = require('../database');
var Report = sequelize.define('Report', {
ID_Report: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Descricao: Sequelize.STRING,
Nivel_Densidade: Sequelize.INTEGER,
N_Likes: Sequelize.INTEGER,
N_Dislikes: Sequelize.INTEGER,
Data: Sequelize.DATE
},
{
timestamps: false,
});
//SUPER HERANÇA PARA OS OUTROS REPORTS OUTROS REPORTS


module.exports = Report