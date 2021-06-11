var instituicao = require('./instituicao');
var lista_favoritos = require('./lista_favoritos');
var Sequelize = require('sequelize');
var sequelize = require('./database');
var List_Instituicao = sequelize.define('List_Instituicao', {
},
{
timestamps: false,
});
instituicao.belongsToMany(lista_favoritos, { through: List_Instituicao });
lista_favoritos.belongsToMany(instituicao, { through: List_Instituicao });
module.exports = List_Instituicao