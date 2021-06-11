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
    ID_Report_Out_Util:Sequelize.INTEGER,
    ID_Local_RepOutdoorOutrosUtil: Sequelize.INTEGER, //FK
    IDPessoa_RepOutdoorOutrosUtil: Sequelize.INTEGER, //FK
    ID_Outro_Util_RepOutdoorOutrosUtil: Sequelize.INTEGER //FK
},
{
timestamps: false,
});

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