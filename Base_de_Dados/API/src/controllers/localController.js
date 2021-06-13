//todo: fazer listagem de locais
//todo: depois ir ao mobile, apanhar esses locais e tentar fazer reports
var sequelize = require('../model/database');
const controllers = {}
const locais = require('../model/Local')

controllers.listarLocais = async(req,res)=>{
    var statuscode = 200;
    var errMessage="";
    try{
        var listaLocais = await locais.findAll({});
    }catch(e){console.log(e);errMessage = e;statuscode = 500; }
    //console.log(listaLocais)
    if(statuscode === 500)
        res.send({status: statuscode, mensagem: e});
    else res.send({status:200, Locais: listaLocais})
}




module.exports = controllers;