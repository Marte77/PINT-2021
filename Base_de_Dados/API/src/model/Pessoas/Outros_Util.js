var Pessoa = require('./Pessoas');
var Sequelize = require('sequelize');
var sequelize = require('../database');
var Outros_Util = sequelize.define('Outros_Util', {
    ID_Outro_Util: {
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
    //ID_Outro_Util: Sequelize.INTEGER, //falta autoincremento
    Pontos_Outro_Util: Sequelize.INTEGER,
    Ranking: Sequelize.INTEGER
},
{
timestamps: false,
});
Pessoa.hasOne(Outros_Util, {foreignKey: { allowNull: false, type: Sequelize.INTEGER }});
Outros_Util.belongsTo(Pessoa); // vai retornar a FK id_local
/*async function meterHeranca(){
    await sequelize.sync()
    sequelize.query(`alter table public."Outros_Utils" inherit public."Pessoas";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
}
//meterHeranca()*/
module.exports = Outros_Util

/*
ID_Outro_Util: {
type: Sequelize.INTEGER,
primaryKey: true,            //PK
autoIncrement: true,
},
IDPessoa: Sequelize.INTEGER, //PK & FK
Pontos_Outro_Util: Sequelize.INTEGER,
Ranking: Sequelize.INTEGER
*/