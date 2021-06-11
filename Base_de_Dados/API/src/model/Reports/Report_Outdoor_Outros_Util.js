var local = require('../Local');
var outro_utilizador = require('../Pessoas/Outros_Util');
var Sequelize = require('sequelize');
var sequelize = require('../database');
var Report_Outdoor_Outros_Util = sequelize.define('Report_Outdoor_Outros_Util', {
    ID_Report: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    Descricao: Sequelize.STRING,
    Nivel_Densidade: Sequelize.INTEGER,
    N_Likes: Sequelize.INTEGER,
    N_Dislikes: Sequelize.INTEGER,
    Data: Sequelize.DATE,
    //ID_Report:Sequelize.INTEGER,
    //ID_Local: Sequelize.INTEGER, //FK
    //IDPessoa: Sequelize.INTEGER, //FK
    //ID_Outro_Util: Sequelize.INTEGER //FK que basicamente o que vem é o ID pessoa
},
{
timestamps: false,
});
local.hasMany(Report_Outdoor_Outros_Util, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Report_Outdoor_Outros_Util.belongsTo(local);  // vai retornar a FK id_local

outro_utilizador.hasMany(Report_Outdoor_Outros_Util, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Report_Outdoor_Outros_Util.belongsTo(outro_utilizador);  //vai retornar a fk IDPessoa

async function meterHeranca(){
    await sequelize.sync()
    sequelize.query(`alter table public."Report_Outdoor_Outros_Utils" inherit public."Reports";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
}
//meterHeranca()

module.exports = Report_Outdoor_Outros_Util

/*
ID_Report_Out_Util: {
type: Sequelize.INTEGER,
primaryKey: true,             //PK
autoIncrement: true,
},
ID_Report: Sequelize.INTEGER, //PK & FK
//ID_Local: Sequelize.INTEGER, //FK
IDPessoa: Sequelize.INTEGER, //FK
ID_Outro_Util: Sequelize.INTEGER //FK

*/