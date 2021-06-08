var Sequelize = require('sequelize');
var sequelize = require('./database');
var List_Instituicao = sequelize.define('List_Instituicao', {
ID_Lista: {
type: Sequelize.INTEGER,
primaryKey: true, //fk tambem
autoIncrement: true,
},
ID_Instituicao: Sequelize.INTEGER //pk,fk
},
{
timestamps: false,
});
module.exports = List_Instituicao