var Sequelize = require('sequelize');
var sequelize = require('./database');
var Alertas = sequelize.define('Alertas', {
ID_alerta: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
Descricao: Sequelize.STRING
},
{
timestamps: false,
});
module.exports = Alertas