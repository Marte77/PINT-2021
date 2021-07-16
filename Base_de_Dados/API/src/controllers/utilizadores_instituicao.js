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

module.exports = controllers;
