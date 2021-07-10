//todo: fazer listagem de locais
//todo: depois ir ao mobile, apanhar esses locais e tentar fazer reports
var sequelize = require('../model/database');
const controllers = {}
const locais = require('../model/Local');
const Local_Indoor = require('../model/Local_Indoor');
const localindoor = require('../model/Local_Indoor')
controllers.listarLocais = async(req,res)=>{
    var statuscode = 200;
    var errMessage="";
    try{
        var listaLocais = await locais.findAll({});
    }catch(e){console.log(e);errMessage = e;statuscode = 500; }
    if(statuscode === 500)
        res.status(statuscode).send({status: statuscode, err: errMessage});
    else res.status(statuscode).send({status:200, Locais: listaLocais})
}

controllers.getLocalbyId = async(req,res)=>{ //get
    var statuscode = 200;
    var errMessage="";
    var {id} = req.params;
    try{
        var localNome = await locais.findByPk(id)
    }
    catch(e){console.log(e);errMessage = e;statuscode = 500; }
    if(statuscode === 500)
        res.status(statuscode).send({status: statuscode, mensagem: errMessage});
    else res.status(statuscode).send({status:200, Local: localNome})
}

//todo:criar locais e locaisindoor
controllers.criarLocal = async(req,res)=>{//post
    const{nome, codigopostal,descricao,urlimagem,localizacao, longitude, latitude, idinstituicao} = req.body
    try{
        var novoLocal = await locais.create({
            Nome:nome,
            Codigo_Postal: codigopostal,
            Descricao:descricao,
            URL_Imagem: urlimagem,
            Localizacao:localizacao,
            Longitude:longitude,
            Latitude:latitude,
            InstituicaoIDInstituicao:idinstituicao
        })
    }catch(e){
        console.log(e)
        res.status(500).send({desc:"erro a criar novo local", err:e.original})
    }
    res.send({Local:novoLocal})
}

controllers.criarLocalIndoor = async(req,res)=>{//post
    const{nome, descricao, piso, idlocal} = req.body
    try{
        var novoLocal = await localindoor.create({
            Nome:nome,
            Descricao:descricao,
            Piso: piso,
            LocalIDLocal:idlocal
        })
    }catch(e){
        console.log(e)
        res.status(500).send({desc:"erro a criar novo local indoor", err:e.original})
    }
    res.send({LocalIndoor:novoLocal})
}

controllers.apagarLocalIndoor = async(req,res)=>{
    const {idLocalIndoor, idLocal} = req.params
    try {
        var localApagado = await localindoor.destroy({
            where:{
                LocalIDLocal:idLocal,
                ID_Local_Indoor:idLocalIndoor
            }
        })
    } catch (e) {
        console.log(e)
        res.status(500).send({sucesso:false,desc:"erro a apagar local indoor", err:e.original})
    }
    res.send({sucesso:true})
}

controllers.getListaLocaisIndoor = async(req,res)=>{
    const {idLocal} = req.params
    try {
        var listaLocaisIndoor = await Local_Indoor.findAll({
            where:{
                LocalIDLocal: idLocal
            }
        })
    } catch (e) {
        console.log(e)
        res.status(500).send({desc:"erro a pesquisar",err:e.original})
    }
    res.send({LocaisIndoor:listaLocaisIndoor})
}

module.exports = controllers;