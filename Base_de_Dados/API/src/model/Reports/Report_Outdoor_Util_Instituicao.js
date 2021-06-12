var Report = require('./Report');
var local = require('../Local');
var insti_utilizador = require('../Pessoas/Utils_Instituicao');
var Sequelize = require('sequelize');
var sequelize = require('../database');
var Report_Outdoor_Util_Instituicao = sequelize.define('Report_Outdoor_Util_Instituicao', {
    ID_Report_Out_Insti: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    //Descricao: Sequelize.STRING,
    //Nivel_Densidade: Sequelize.INTEGER,
    //N_Likes: Sequelize.INTEGER,
    //N_Dislikes: Sequelize.INTEGER,
    //Data: Sequelize.DATE,
    //ID_Report: Sequelize.INTEGER,
    //ID_Local: Sequelize.INTEGER, //FK
    //IDPessoa: Sequelize.INTEGER, //FK
    //ID_Util: Sequelize.INTEGER //FK
},
{
timestamps: false,
});
Report.hasOne(Report_Outdoor_Util_Instituicao, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Report_Outdoor_Util_Instituicao.belongsTo(Report); // vai retornar a FK id_local

local.hasMany(Report_Outdoor_Util_Instituicao, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Report_Outdoor_Util_Instituicao.belongsTo(local); // vai retornar a FK id_local

insti_utilizador.hasMany(Report_Outdoor_Util_Instituicao, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Report_Outdoor_Util_Instituicao.belongsTo(insti_utilizador); //vai retornar a fk IDPessoa
/*
async function meterHeranca(){
    await sequelize.sync()
    sequelize.query(`alter table public."Report_Outdoor_Util_Instituicaos" inherit public."Reports";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
}
//meterHeranca()*/

module.exports = Report_Outdoor_Util_Instituicao

/*
ID_Report_Out_Insti: {
type: Sequelize.INTEGER,
primaryKey: true,      //PK
autoIncrement: true,
},
ID_Report: Sequelize.INTEGER, //PK & FK
//ID_Local: Sequelize.INTEGER, //FK
IDPessoa: Sequelize.INTEGER, //FK
ID_Util: Sequelize.INTEGER //FK

*/