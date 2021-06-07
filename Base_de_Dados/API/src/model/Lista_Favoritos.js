var Sequelize = require('sequelize');
var sequelize = require('./database');
var Lista_Favoritos = sequelize.define('Lista_Favoritos', {
ID_Lista: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
ID: Sequelize.INTEGER //SERIA O ID DA PESSOA
Descricao: Sequelize.STRING
},
{
timestamps: false,
});
module.exports = Lista_Favoritos