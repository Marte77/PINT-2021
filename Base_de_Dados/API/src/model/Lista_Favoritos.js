var Sequelize = require('sequelize');
var sequelize = require('./database');
const pessoa = require('./Pessoas/Pessoas');
var Lista_Favoritos = sequelize.define('Lista_Favoritos', {
ID_Lista: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
//IDPessoa: Sequelize.INTEGER, //fk
Descricao: Sequelize.STRING
},
{
timestamps: false,
});
pessoa.hasOne(Lista_Favoritos, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Lista_Favoritos.belongsTo(pessoa);
module.exports = Lista_Favoritos