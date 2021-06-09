var Sequelize = require('sequelize');
var sequelize = require('../database');
var Admin = sequelize.define('Admin', {
    IDPessoa: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    Data_Nascimento: Sequelize.DATEONLY,
    Cidade: Sequelize.STRING,
    Codigo_Postal: Sequelize.INTEGER,
    Email: Sequelize.STRING,
    UNome: Sequelize.STRING,
    Localização: Sequelize.STRING,
    PNome: Sequelize.STRING,
    Password: Sequelize.TEXT,
    ID_Admin: Sequelize.INTEGER,
    ID_Instituicao: Sequelize.INTEGER //FK
},
{
timestamps: false,
});
async function meterHeranca(){
    await sequelize.sync()
    sequelize.query(`alter table public."Admins" inherit public."Pessoas";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
}
meterHeranca()

module.exports = Admin

/*
ID_Admin: {
type: Sequelize.INTEGER,
primaryKey: true,          //PK
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //PK & FK
ID_Instituicao: Sequelize.INTEGER //FK

*/