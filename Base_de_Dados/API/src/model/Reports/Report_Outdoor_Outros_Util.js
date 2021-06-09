var Sequelize = require('sequelize');
var sequelize = require('../database');
var Report_Outdoor_Outros_Util = sequelize.define('Report_Outdoor_Outros_Util', {
ID_Report_Out_Util: {
type: Sequelize.INTEGER,
primaryKey: true,             //PK
autoIncrement: true,
},
ID_Report: Sequelize.INTEGER, //PK & FK
//ID_Local: Sequelize.INTEGER, //FK
IDPessoa: Sequelize.INTEGER, //FK
ID_Outro_Util: Sequelize.INTEGER //FK
},
{
timestamps: false,
});
module.exports = Report_Outdoor_Outros_Util