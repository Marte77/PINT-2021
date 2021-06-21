var Sequelize = require('sequelize');
var sequelize = require('../database');
var Pessoas = sequelize.define('Pessoas', {
IDPessoa: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Data_Nascimento: Sequelize.DATEONLY,
Cidade: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Email: {type:Sequelize.STRING, unique: true},
UNome: Sequelize.STRING,
Localização: Sequelize.STRING,
PNome: Sequelize.STRING,
Password: Sequelize.TEXT,
Foto_De_Perfil:Sequelize.TEXT
},
{
timestamps: false,
});
//SUPER CLASSE HERANÇA PARA OS OUTROS TIPOS DE PESSOAS

module.exports = Pessoas