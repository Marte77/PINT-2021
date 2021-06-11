var tipo_alerta = require('./Tipo_Alertas');
var admin = require('./Pessoas/Admin');
var local = require('./Local');
var Sequelize = require('sequelize');
var sequelize = require('./database');
var Alertas = sequelize.define('Alertas', {
ID_alerta: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
//IDPessoa: Sequelize.INTEGER, //fk
//ID_Admin: Sequelize.INTEGER, //fk
//ID_Local: Sequelize.INTEGER, //fk
//ID_TipoAlerta: Sequelize.INTEGER, //fk
Descricao: Sequelize.STRING
},
{
timestamps: false,
});
local.hasMany(Alertas, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Alertas.belongsTo(local);

admin.hasMany(Alertas, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Alertas.belongsTo(admin);

tipo_alerta.hasMany(Alertas, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Alertas.belongsTo(tipo_alerta);

module.exports = Alertas