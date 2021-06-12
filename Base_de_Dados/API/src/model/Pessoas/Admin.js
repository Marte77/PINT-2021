var Instituicao = require('../Instituicao');
var Pessoa = require('./Pessoas');
var Sequelize = require('sequelize');
var sequelize = require('../database');
var Admin = sequelize.define('Admin', {
    ID_Admin: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
     //Data_Nascimento: Sequelize.DATEONLY,
    //Cidade: Sequelize.STRING,
    //Codigo_Postal: Sequelize.INTEGER,
    //Email: Sequelize.STRING,
    //UNome: Sequelize.STRING,
    //Localização: Sequelize.STRING,
    //PNome: Sequelize.STRING,
    //Password: Sequelize.TEXT,
    //ID_Admin: Sequelize.INTEGER,  //falta autoincremento
    //ID_Instituicao: Sequelize.INTEGER //FK
},
{
timestamps: false,
});
Instituicao.hasMany(Admin, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Admin.belongsTo(Instituicao); // vai retornar a FK id_local

Pessoa.hasOne(Admin, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Admin.belongsTo(Pessoa); // vai retornar a FK id_local
/*async function meterHeranca(){
    await sequelize.sync()
    sequelize.query(`alter table public."Admins" inherit public."Pessoas";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
}*/
//meterHeranca()

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