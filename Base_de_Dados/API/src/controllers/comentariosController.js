var sequelize = require('../model/database');
const controllers = {}
const Comentario = require('../model/Comentarios')

controllers.criarComentario = async (req,res) => { //post
    const { Descricao,Classificacao,IDLocal,IDPessoa}= req.body
    let statusCode = 200;
    let dataAgr = new Date()
    dataAgr = dataAgr.toISOString()
    try{var Comentarios = await Comentario.create({
        Descricao: Descricao,
        Classificacao:Classificacao,
        Data: dataAgr,
        LocalIDLocal: IDLocal,
        PessoaIDPessoa:IDPessoa
    })}catch(e){console.log(e);statusCode = 500}

    if(statusCode === 500)
        res.send({status:statusCode, desc:"Erro a criar"})
    else res.send({status:statusCode,Comentario: Comentarios })
} 

module.exports = controllers;