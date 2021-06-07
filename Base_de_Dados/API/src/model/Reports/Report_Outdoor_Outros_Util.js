var Sequelize = require('sequelize');
var sequelize = require('./database');
var Report_Outdoor_Outros_Util = sequelize.define('Report_Outdoor_Outros_Util', {
ID_Report_Out_Util: {
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
module.exports = Report_Outdoor_Outros_Util