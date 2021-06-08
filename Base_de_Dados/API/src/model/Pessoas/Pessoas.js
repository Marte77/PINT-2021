var Sequelize = require('sequelize');
var sequelize = require('./database');
var Pessoas = sequelize.define('Pessoas', {
IDPessoa: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Data_Nascimento: Sequelize.DATEONLY,
Cidade: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Email: Sequelize.STRING,
UNome: Sequelize.STRING,
Localização: Sequelize.STRING,
PNome: Sequelize.STRING,
Password: Sequelize.TEXT
},
{
timestamps: false,
});
Pessoas.hasOne(Utils_Instituicao);
Pessoas.hasOne(Admin);
Pessoas.hasOne(Outros_Util);
module.exports = Pessoas