const Sequelize = require('sequelize');
const sequelize = require('../database');
const bcrypt = require('bcrypt');

var Pessoas = sequelize.define('Pessoas', {
    IDPessoa: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    Data_Nascimento: Sequelize.DATEONLY,
    Cidade: Sequelize.STRING,
    Codigo_Postal: Sequelize.INTEGER,
    Email: {
        type:Sequelize.STRING, 
        allowNull: false,
        unique: true
    },
    UNome: Sequelize.STRING,
    Localização: Sequelize.STRING,
    PNome: Sequelize.STRING,
    Password:{
        type: Sequelize.STRING,
        allowNull: false
    }, 
    Foto_De_Perfil:Sequelize.TEXT
    },
    {
    timestamps: false,
});

Pessoas.beforeCreate((user, options) => {
    return bcrypt.hash(user.password, 10)
    .then(hash => {
        user.password = hash;
    })
    .catch(err => {
        throw new Error();
    });
});

//SUPER CLASSE HERANÇA PARA OS OUTROS TIPOS DE PESSOAS

module.exports = Pessoas