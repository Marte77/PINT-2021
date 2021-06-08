var Sequelize = require('sequelize');
var sequelize = require('./database');
var Report_Indoor = sequelize.define('Report_Indoor', {
ID_Report_Indoor: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
ID_Report: Sequelize.INTEGER //PK & FK
ID_Local_Indoor: Sequelize.INTEGER, //FK
IDPessoa: Sequelize.INTEGER, //FK
ID_Util: Sequelize.INTEGER //FK
},
{
timestamps: false,
});
module.exports = Report_Indoor