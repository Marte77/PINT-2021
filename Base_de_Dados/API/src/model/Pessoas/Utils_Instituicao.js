var Pessoa = require('./Pessoas');
var Sequelize = require('sequelize');
var sequelize = require('../database');
var Utils_Instituicao = sequelize.define('Utils_Instituicao', {
    ID_Util: {
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
    //ID_Util: Sequelize.INTEGER,  //falta autoincremento
    Pontos: Sequelize.INTEGER,
    Ranking: Sequelize.INTEGER,
    Codigo_Empresa: Sequelize.INTEGER,
    /*Verificado: Sequelize.BOOLEAN*///todo fazer isto
},
{
timestamps: false,
});

Pessoa.hasOne(Utils_Instituicao, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Utils_Instituicao.belongsTo(Pessoa); // vai retornar a FK id_local

/*async function meterHeranca(){
    await sequelize.sync()
    sequelize.query(`alter table public."Utils_Instituicaos" inherit public."Pessoas";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});   
}
//meterHeranca()*/
module.exports = Utils_Instituicao

/**
 * ID_Util: {
    type: Sequelize.INTEGER,
    primaryKey: true,          //PK
    autoIncrement: true,
    },
    IDPessoa: Sequelize.INTEGER, //PK & FK
    Pontos: Sequelize.INTEGER,
    Ranking: Sequelize.INTEGER,
    Codigo_Empresa: Sequelize.INTEGER
 * 
 * 
 */