var sequelize = require('../model/database');
const {Op} = require('sequelize')
const controllers = {}
const utilizadoresInst = require('../model/Pessoas/Utils_Instituicao');
const util_pert_inst = require('../model/Util_pertence_Inst');
var pessoas = require('../model/Pessoas/Pessoas');
const Instituicao = require('../model/Instituicao');

controllers.get_utilizadores =  async (req,res) => {

    const{idInstituicao}=req.params
    var statuscode = 200;
    var errMessage="";

    try{
        var arrayutilizadores=new Array();
        var listautils_inst=await util_pert_inst.findAll({
            where:{
                InstituicaoIDInstituicao:idInstituicao
            }
        })
        for(let util_inst of listautils_inst)
        {
            var listautilizadores=await  utilizadoresInst.findAll({
                include:[pessoas],
                where:{
                     ID_Util:util_inst.dataValues.UtilsInstituicaoIDUtil,
                     Verificado:true 
                }
            })
            for (let utilizadores of listautilizadores)
            {
                arrayutilizadores.push(utilizadores)
            }
        }
    }

    catch(e){console.log(e);errMessage = e;statuscode = 500;}
    if(statuscode === 500)
    res.status(statuscode).send({status: statuscode, err: errMessage});
    else res.status(statuscode).send({status:200, ListaUtilizadores: arrayutilizadores})
    
}

controllers.getUtilNoverify =  async (req,res) => {

    const{idInstituicao}=req.params
    var statuscode = 200;
    var errMessage="";

    try{
        var arrayutilizadores=new Array();
        var listautils_inst=await util_pert_inst.findAll({
            where:{
                InstituicaoIDInstituicao:idInstituicao
            }
        })
        for(let util_inst of listautils_inst)
        {
            var listautilizadores=await  utilizadoresInst.findAll({
                include:[pessoas],
                where:{
                     ID_Util:util_inst.dataValues.UtilsInstituicaoIDUtil,
                     Verificado:false 
                }
            })
            for (let utilizadores of listautilizadores)
            {
                arrayutilizadores.push(utilizadores)
            }
        }
    }

    catch(e){console.log(e);errMessage = e;statuscode = 500;}
    if(statuscode === 500)
    res.status(statuscode).send({status: statuscode, err: errMessage});
    else res.status(statuscode).send({status:200, ListaUtilizadores: arrayutilizadores})
    
}


controllers.deleteutil= async (req, res) => {
    const { id } = req.body;
    const del=await util_pert_inst.destroy({
        where:
        {
            UtilsInstituicaoIDUtil:id
        }
    })
    const deleteu=await utilizadoresInst.destroy({
        where:{
            ID_Util:id
        }
        
    })
    res.json({success:true,deleted:del,deleted2:deleteu,message:"Deleted successful"});
}

controllers.count_utilizadores_verify =  async (req,res) => {

    const{idInstituicao}=req.params
    var statuscode = 200;
    var errMessage="";

    try{
        var arrayutilizadores=new Array();
        var listautils_inst=await util_pert_inst.findAll({
            where:{
                InstituicaoIDInstituicao:idInstituicao
            }
        })
        for(let util_inst of listautils_inst)
        {
            var listautilizadores=await  utilizadoresInst.findAll({
                
                where:{
                     ID_Util:util_inst.dataValues.UtilsInstituicaoIDUtil,
                     Verificado:true 
                }
            })
            for (let utilizadores of listautilizadores)
            {
                arrayutilizadores.push(utilizadores)
            }
        }
        var count=arrayutilizadores.length;
    }

    catch(e){console.log(e);errMessage = e;statuscode = 500;}
    if(statuscode === 500)
    res.status(statuscode).send({status: statuscode, err: errMessage});
    else res.status(statuscode).send({status:200, NumeroUtilizadores: count})
    
}


controllers.count_utilizadores_NOverify =  async (req,res) => {

    const{idInstituicao}=req.params
    var statuscode = 200;
    var errMessage="";

    try{
        var arrayutilizadores=new Array();
        var listautils_inst=await util_pert_inst.findAll({
            where:{
                InstituicaoIDInstituicao:idInstituicao
            }
        })
        for(let util_inst of listautils_inst)
        {
            var listautilizadores=await  utilizadoresInst.findAll({
                
                where:{
                     ID_Util:util_inst.dataValues.UtilsInstituicaoIDUtil,
                     Verificado:false 
                }
            })
            for (let utilizadores of listautilizadores)
            {
                arrayutilizadores.push(utilizadores)
            }
        }
        var count=arrayutilizadores.length;
    }

    catch(e){console.log(e);errMessage = e;statuscode = 500;}
    if(statuscode === 500)
    res.status(statuscode).send({status: statuscode, err: errMessage});
    else res.status(statuscode).send({status:200, NumeroUtilizadores: count})
    
}

controllers.editutilizador= async (req,res) => {
    const{idutil}=req.params;
    const data= await utilizadoresInst.update({
        Verificado:'true'
    },
    {
        where:{ID_Util:idutil}
    
    })
    .then( function(data){
        return data;
        })
        .catch(error => {
        return error;
        })
        res.json({success:true, data:data, message:"Updated successful"});
        
}

module.exports = controllers;
