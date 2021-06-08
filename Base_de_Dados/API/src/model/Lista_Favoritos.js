var Sequelize = require('sequelize');
var sequelize = require('./database');
var Lista_Favoritos = sequelize.define('Lista_Favoritos', {
ID_Lista: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER //fk
Descricao: Sequelize.STRING
},
{
timestamps: false,
});
module.exports = Lista_Favoritos