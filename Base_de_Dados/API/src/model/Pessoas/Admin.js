var Sequelize = require('sequelize');
var sequelize = require('./database');
var Admin = sequelize.define('Admin', {
ID_Admin: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
}
IDPessoa: Sequelize.INTEGER, //PK & FK
ID_Instituicao: Sequelize.INTEGER, //FK
Data_Nascimento: Sequelize.DATEONLY,
Cidade: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Email: Sequelize.STRING,
UNome: Sequelize.STRING,
Localização: Sequelize.STRING,
PNome: Sequelize.STRING,
},
{
timestamps: false,
});
module.exports = Admin