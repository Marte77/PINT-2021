var instituicao = require('./Instituicao');
var Sequelize = require('sequelize');
var sequelize = require('./database');
var Local = sequelize.define('Local', {
ID_Local: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
//ID_Instituicao: Sequelize.INTEGER, //fk
Nome: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Descricao: Sequelize.TEXT,
URL_Imagem: Sequelize.TEXT,
Localizacao: Sequelize.TEXT,
Longitude: Sequelize.DOUBLE,
Latitude: Sequelize.DOUBLE,
},
{
timestamps: false,
});
instituicao.hasMany(Local, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Local.belongsTo(instituicao);

module.exports = Local