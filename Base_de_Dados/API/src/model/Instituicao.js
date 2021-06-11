var Sequelize = require('sequelize');
var sequelize = require('./database');
var Instituicao = sequelize.define('Instituicao', {
ID_Instituicao: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Nome: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Telefone: Sequelize.INTEGER,
Descricao: Sequelize.STRING,
URL_Imagem: Sequelize.STRING,
Coordenadas: Sequelize.DECIMAL,
Localizacao: Sequelize.STRING,
Codigo_Empresa: Sequelize.INTEGER
},
{
timestamps: false,
});
module.exports = Instituicao