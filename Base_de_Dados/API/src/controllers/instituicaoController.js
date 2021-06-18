var sequelize = require('../model/database');
const controllers = {}
const instituicao = require('../model/instituicao')

controllers.createInstituicao = async (req,res) => { //post
    let statusCode = 200;
    const { Nome, Codigo_Postal, Telefone, Descricao, URL_Imagem, Longitude, Latitude, Localizacao, Codigo_Empresa}= req.body
    try{
        var NovaInstituicao = await instituicao.create({
            Nome: Nome,
            Codigo_Postal : Codigo_Postal,
            Telefone: Telefone,
            Descricao: Descricao,
            URL_Imagem : URL_Imagem,
            Longitude : Longitude,
            Latitude : Latitude,
            Localizacao : Localizacao,
            Codigo_Empresa : Codigo_Empresa
        })
    }catch(e){
        console.log(e);
        statusCode = 500;
        var msgErr = e.original;
    }
    if(statusCode === 500)
        res.status(statusCode).send({status:statusCode, desc:"Erro a criar", err:msgErr})
    else res.status(statusCode).send({status:statusCode,instituicao:NovaInstituicao })
} 



//todo: listar instituicoes

module.exports = controllers;