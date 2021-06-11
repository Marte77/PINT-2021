var Sequelize = require('sequelize');
var sequelize = require('../database');
var Report_Indoor = sequelize.define('Report_Indoor', {
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
    ID_Report_Indoor: Sequelize.INTEGER,
    ID_Local_Indoor: Sequelize.INTEGER, //FK
    IDPessoa_RepIndoor: Sequelize.INTEGER, //FK
    ID_Util_RepIndoor: Sequelize.INTEGER //FK
},
{
timestamps: false,
});


async function meterHeranca(){
    await sequelize.sync()
    sequelize.query(`alter table public."Report_Indoors" inherit public."Reports";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
}
//meterHeranca()


module.exports = Report_Indoor

/*
ID_Report_Indoor: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
},
ID_Report: Sequelize.INTEGER, //PK & FK
ID_Local_Indoor: Sequelize.INTEGER, //FK
IDPessoa: Sequelize.INTEGER, //FK
ID_Util: Sequelize.INTEGER //FK

*/