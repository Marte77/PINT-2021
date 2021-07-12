var sequelize = require('../model/database');
const controllers = {}
const Comentario = require('../model/Comentarios');
const e = require('express');

controllers.criarComentario = async (req,res) => { //post
    const { Descricao,Classificacao,IDLocal,IDPessoa}= req.body
    let statusCode = 200;
    let dataAgr = new Date()
    dataAgr = dataAgr.toISOString()
    try{
        var jaFezComent = await Comentario.count({
            where:{
                LocalIDLocal:IDLocal, PessoaIDPessoa:IDPessoa
            }
        })
        if(jaFezComent === 0)
            var Comentarios = await Comentario.create({
                Descricao: Descricao,
                Classificacao:Classificacao,
                Data: dataAgr,
                LocalIDLocal: IDLocal,
                PessoaIDPessoa:IDPessoa
            })
    }catch(e){
        console.log(e);
        statusCode = 500;
        var msgErr = e.original
    }

    if(statusCode === 500)
        res.status(statusCode).send({status:statusCode, desc:"Erro a criar"})
    else if(jaFezComent !== 0){
        res.status(500).send({desc:'Esta pessoa ja deu a sua opiniao neste local'})
    } 
    else{res.status(statusCode).send({status:statusCode,Comentario: Comentarios })}
} 

controllers.getComentario= async(req,res)=>{ //post
    const {IDLocal,IDPessoa}= req.params
    try{
        var comentario = await Comentario.findOne({
            where:{
                LocalIDLocal:IDLocal, PessoaIDPessoa:IDPessoa
            }
        })
    }catch(e){
        console.log(e)
        res.status(500).send({desc:'Erro a selecionar', err:e.original})
    }
    if(comentario !== null)
        res.send({Comentario:comentario})
    else res.status(500).send({desc:'Esta pessoa nao deu opiniao sobre este local'})
}

controllers.updateComentario = async(req,res)=>{ //post
    const { Descricao,Classificacao,IDLocal,IDPessoa, Data}= req.body
    let dataAgr = new Date()
    dataAgr = dataAgr.toISOString()
    try {
        var jaFezComent = await Comentario.count({
            where:{
                LocalIDLocal:IDLocal, PessoaIDPessoa:IDPessoa
            }
        })
        if(jaFezComent !==0)
        {
            var comentario = await Comentario.findOne({
                where:{
                    Data:Data,
                    LocalIDLocal:IDLocal,
                    PessoaIDPessoa:IDPessoa
                }
            })
            comentario.Descricao = Descricao
            comentario.Classificacao = Classificacao
            comentario.Data = dataAgr
            await comentario.save()
        }
    } catch (e) {
        console.log(e)
        res.send({desc:"Erro a fazer update",err:e.original})
    }
    if(jaFezComent === 0)
        res.status(500).send({desc:'Esta pessoa nao deu opiniao sobre este local'})
    else res.send({Comentario:comentario})
}

controllers.apagarComentario = async(req,res)=>{
    const {IDLocal,IDPessoa}= req.params
    try{
        var comentario = await Comentario.findOne({
            where:{
                LocalIDLocal:IDLocal, PessoaIDPessoa:IDPessoa
            }
        })
        if(comentario !== null)
        await comentario.destroy()
    }catch(e){
        console.log(e)
        res.status(500).send({desc:'Erro a apagar', err:e.original})
    }
    if(comentario !== null)
        res.send({desc:'Apagado com sucesso'})
    else res.status(500).send({desc:'Esta pessoa nao deu opiniao sobre este local'})
}

module.exports = controllers;