var local = require('./Local');
var Sequelize = require('sequelize');
var sequelize = require('./database');
var Local_Indoor = sequelize.define('Local_Indoor', {
ID_Local_Indoor: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
//ID_Local:Sequelize.INTEGER,//fk
Nome: Sequelize.STRING,
Codigo_Postal: Sequelize.INTEGER,
Descricao: Sequelize.STRING,
Piso: Sequelize.INTEGER,
Localizacao: Sequelize.STRING,
Coordenadas: Sequelize.DECIMAL
},
{
timestamps: false,
});
local.hasMany(Local_Indoor, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Local_Indoor.belongsTo(local);  // vai retornar a FK id_local relativo ao local indoor


module.exports = Local_Indoor