var Sequelize = require('sequelize');
var sequelize = require('./database');
var Alertas = sequelize.define('Alertas', {
ID_alerta: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //fk
ID_Admin: Sequelize.INTEGER, //fk
ID_Local: Sequelize.INTEGER, //fk
ID_TipoAlerta: Sequelize.INTEGER, //fk
Descricao: Sequelize.STRING
},
{
timestamps: false,
});
module.exports = Alertas