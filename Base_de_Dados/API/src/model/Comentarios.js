var Sequelize = require('sequelize');
var sequelize = require('./database');
var Comentarios = sequelize.define('Comentarios', {
ID_Comentario: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //fk
ID_Local: Sequelize.INTEGER, //fk
Descricao: Sequelize.STRING,
Classificacao: Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Comentarios