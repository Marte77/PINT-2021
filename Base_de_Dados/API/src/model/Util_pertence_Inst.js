var instituicao = require('./Instituicao');
var utils = require('./Pessoas/Utils_instituicao');
var Sequelize = require('sequelize');
var sequelize = require('./database');
var Util_pertence_Inst = sequelize.define('Util_pertence_Inst', {
//ID_Instituicao: Sequelize.INTEGER, //pk & fk
//IDPessoa: Sequelize.INTEGER, //pk & fk
//ID_Util: Sequelize.INTEGER //pk & fk
},
{
timestamps: false,
});
instituicao.belongsToMany(utils, { through: Util_pertence_Inst });
utils.belongsToMany(instituicao, { through: Util_pertence_Inst });
module.exports = Util_pertence_Inst