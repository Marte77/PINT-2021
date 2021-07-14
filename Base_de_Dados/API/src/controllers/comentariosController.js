var sequelize = require('../model/database');
const controllers = {}
const Comentario = require('../model/Comentarios');
const e = require('express');
const Pessoas = require('../model/Pessoas/Pessoas');
const Local = require('../model/Local');

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
    const { Descricao,Classificacao,IDLocal,IDPessoa}= req.body
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


controllers.get_comentarios =  async (req,res) => {

    const{idInstituicao}=req.params
    var statuscode = 200;
    var errMessage="";

    try{
        var arraycomentarios=new Array();
        var listalocais=await Local.findAll({
            where:{
                InstituicaoIDInstituicao:idInstituicao
            }
        })
        for(let local of listalocais)
        {
            var listacomentarios=await  Comentario.findAll({
                include:[Local,Pessoas],
                where:{
                     LocalIDLocal:local.dataValues.ID_Local    
                }
            })
            for (let comentario of listacomentarios)
            {
                arraycomentarios.push(comentario)
            }
        }
    }

    catch(e){console.log(e);errMessage = e;statuscode = 500;}
    if(statuscode === 500)
    res.status(statuscode).send({status: statuscode, err: errMessage});
    else res.status(statuscode).send({data: arraycomentarios})
    
}
    
controllers.getTodosComentariosLocal = async(req,res)=>{
    const {IDLocal} = req.params
    try{
        var comentariostodos = await Comentario.findAll({
            where:{
                LocalIDLocal:IDLocal
            },
            include:[Pessoas]
        })
    }catch(e){
        console.log(e)
        res.status(500).send({desc:"Erro a pesquisar", err:e.original})
    }
    res.send({Comentarios: comentariostodos})
}
module.exports = controllers;