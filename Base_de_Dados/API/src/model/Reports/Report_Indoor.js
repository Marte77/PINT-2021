var Sequelize = require('sequelize');
var sequelize = require('./database');
var Report_Indoor = sequelize.define('Report_Indoor', {
ID_Report_Indoor: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
ID_Local_Indoor: Sequelize.INTEGER, //fk
IDPessoa: Sequelize.INTEGER, //fk
ID_Util: Sequelize.INTEGER, //fk
Descricao: Sequelize.STRING,
Nivel_Densidade: Sequelize.INTEGER,
N_Likes: Sequelize.INTEGER,
N_Dislikes: Sequelize.INTEGER,
Data: Sequelize.DATE
},
{
timestamps: false,
});
module.exports = Report_Indoor