var report = require('./Report');
var pessoa = require('../Pessoas/Pessoas');
var Sequelize = require('sequelize');
var sequelize = require('../database');
var Tabela_LikesDislikes = sequelize.define('Tabela_LikesDislikes', {
Like: Sequelize.BOOLEAN,
Dislike: Sequelize.BOOLEAN
},
{
timestamps: false,
});
pessoa.belongsToMany(report, { through: Tabela_LikesDislikes });
report.belongsToMany(pessoa, { through: Tabela_LikesDislikes });
module.exports = Tabela_LikesDislikes