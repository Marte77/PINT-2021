var Local = require('./Local');
var lista_favoritos = require('./Lista_Favoritos');
var Sequelize = require('sequelize');
var sequelize = require('./database');
var List_LocalFavorito = sequelize.define('List_LocalFavorito', {},
{
timestamps: false,
});
Local.belongsToMany(lista_favoritos, 
    {
        through: List_LocalFavorito,
        foreignKey:lista_favoritos.primaryKeys.ID_Lista.fieldName
    }
    );
lista_favoritos.belongsToMany(Local, 
    {
        through: List_LocalFavorito,
        //Local.
        foreignKey:Local.primaryKeys.ID_Local.fieldName
    }
    );

module.exports = List_LocalFavorito