//todo: fazer listagem de locais
//todo: depois ir ao mobile, apanhar esses locais e tentar fazer reports
var sequelize = require('../model/database');
const controllers = {}
const locais = require('../model/Local')
const localindoor = require('../model/Local_Indoor')
controllers.listarLocais = async(req,res)=>{
    var statuscode = 200;
    var errMessage="";
    try{
        var listaLocais = await locais.findAll({});
    }catch(e){console.log(e);errMessage = e;statuscode = 500; }
    if(statuscode === 500)
        res.status(statusCode).send({status: statuscode, err: errMessage});
    else res.status(statusCode).send({status:200, Locais: listaLocais})
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
        res.status(statusCode).send({status: statuscode, mensagem: errMessage});
    else res.status(statusCode).send({status:200, Local: localNome})
}
//todo:criar locais e locaisindoor


module.exports = controllers;