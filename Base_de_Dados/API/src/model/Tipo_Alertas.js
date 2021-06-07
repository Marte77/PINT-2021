var Sequelize = require('sequelize');
var sequelize = require('./database');
var Tipo_Alertas = sequelize.define('Tipo_Alertas', {
ID_TipoAlerta: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Tipo_Alerta: Sequelize.STRING
},
{
timestamps: false,
});
module.exports = Tipo_Alertas