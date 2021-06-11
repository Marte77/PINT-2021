var Sequelize = require('sequelize');
var sequelize = require('../database');
var Report_Outdoor_Util_Instituicao = sequelize.define('Report_Outdoor_Util_Instituicao', {
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
    ID_Report_Out_Insti: Sequelize.INTEGER,
    ID_Local: Sequelize.INTEGER, //FK
    IDPessoa: Sequelize.INTEGER, //FK
    ID_Util: Sequelize.INTEGER //FK
},
{
timestamps: false,
});

async function meterHeranca(){
    await sequelize.sync()
    sequelize.query(`alter table public."Report_Outdoor_Util_Instituicaos" inherit public."Reports";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
}
//meterHeranca()

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