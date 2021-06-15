var local = require('./Local');
var lista_favoritos = require('./Lista_Favoritos');
var Sequelize = require('sequelize');
var sequelize = require('./database');
var List_LocalFavorito = sequelize.define('List_LocalFavorito', {
},
{
timestamps: false,
});
local.belongsToMany(lista_favoritos, { through: List_LocalFavorito });
lista_favoritos.belongsToMany(local, { through: List_LocalFavorito });
module.exports = List_LocalFavorito