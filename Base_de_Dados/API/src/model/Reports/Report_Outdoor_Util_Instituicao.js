var Sequelize = require('sequelize');
var sequelize = require('./database');
var Report_Outdoor_Util_Instituicao = sequelize.define('Report_Outdoor_Util_Instituicao', {
ID_Report_Out_Insti: {
type: Sequelize.INTEGER,
primaryKey: true,      //PK
autoIncrement: true,
},
ID_Report: Sequelize.INTEGER //PK & FK
//ID_Local: Sequelize.INTEGER, //FK
IDPessoa: Sequelize.INTEGER, //FK
ID_Util: Sequelize.INTEGER //FK
},
{
timestamps: false,
});
Report_Outdoor_Util_Instituicao.belongsTo(Local, {
  foreignKey: 'ID_Local'
});
module.exports = Report_Outdoor_Util_Instituicao