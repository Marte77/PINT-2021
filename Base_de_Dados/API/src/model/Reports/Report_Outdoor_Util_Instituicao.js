var Sequelize = require('sequelize');
var sequelize = require('./database');
var Report_Outdoor_Util_Instituicao = sequelize.define('Report_Outdoor_Util_Instituicao', {
ID_Report_Out_Insti: {
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
module.exports = Report_Outdoor_Util_Instituicao