var Sequelize = require('sequelize');
var sequelize = require('../database');
var Admin = sequelize.define('Admin', {
ID_Admin: {
type: Sequelize.INTEGER,
primaryKey: true,          //PK
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //PK & FK
ID_Instituicao: Sequelize.INTEGER //FK
},
{
timestamps: false,
});
module.exports = Admin