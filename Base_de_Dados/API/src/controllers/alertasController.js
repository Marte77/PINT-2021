//var instituicao = require('../model/instituicao');
//var pessoas = require('../model/Pessoas/Pessoas');
//var utils_instituicao = require('../model/Pessoas/Utils_Instituicao');
//var Util_pertence_Inst = require('../model/Util_pertence_Inst');
//var outros_util = require('../model/Pessoas/Outros_Util');
var admin = require('../model/Pessoas/Admin');
//var outros_util = require('../model/Pessoas/Outros_Util');
//var utils_instituicao = require('../model/Pessoas/Utils_Instituicao');
var alerta = require('../model/Alertas');
var tipo_alerta = require('../model/Tipo_Alertas');
var sequelize = require('../model/database');
const locais = require('../model/Local');
const tipoalertas=require('../model/Tipo_Alertas');

const controllers = {}

controllers.createTipoAlerta = async (req,res) => { //post
   // data
   sequelize.sync()
   const { Tipo_Alerta } = req.body;
   var statusCode = 200;
   try{
      var dataTipo_Alerta = await tipo_alerta.create({
         Tipo_Alerta: Tipo_Alerta
      })
   }catch(e){
      var msgErr = e.original;
      console.log(e)
      statusCode = 500;
   }
   if(statusCode === 500)
      res.status(statusCode).send({status:statusCode, desc:"Erro a criar TipoAlerta", err:msgErr})
   else res.status(statusCode).send({status:statusCode,TipoAlerta:dataTipo_Alerta})


   
}

controllers.getlistaalertas_byinstituicao= async(req,res)=>{ //get
   {
      const{idInstituicao}=req.params
      var statuscode = 200;
      var errMessage="";
      try{
         var arrayalertas=new Array();
         var listalocais=await locais.findAll({
            where:{
               InstituicaoIDInstituicao:idInstituicao
            }
         })
         for(let local of listalocais){
            var listaalertas=await alerta.findAll({
               include:[locais,tipo_alerta],
               
               where:
               {
                  LocalIDLocal:local.dataValues.ID_Local
               }
            })
            for(let alerta of listaalertas){
               arrayalertas.push(alerta)
            }
      }
      
      }
      catch(e){
         console.log(e);errMessage = e;statuscode = 500;
      }
      if(statuscode === 500)
      res.status(statuscode).send({status: statuscode, err: errMessage});
      else res.status(statuscode).send({status:200, Alertas: arrayalertas})
      
  }
}


controllers.gettipoalertas = async(req,res)=>{
   
      const todostipos=await tipoalertas.findAll({

      })

      .then(function(data){
         return data;
         })
         .catch(error =>{
         return error;
         })
         res.json({ ListipoAlertas: todostipos });
}

controllers.getTotalAlertasPorLocalDeInstituicao = async(req,res)=>{
   const {IDInstituicao} = req.params
   try {
      let todoslocais = await locais.findAll({
         where:{
            InstituicaoIDInstituicao:IDInstituicao
         }
      })
      var arrayTotAlertas = new Array()
      for(let local of todoslocais){
         let alertas = await alerta.count({
            where:{
               LocalIDLocal:local.dataValues.ID_Local
            }
         })
         arrayTotAlertas.push({
            NomeLocal:local.Nome,
            NumeroAlertas:alertas,
            ID_Local:local.ID_Local
         })
      }

   } catch (e) {
      console.log(e)
      res.status(500).send({desc:"Erro a selecionar", err:e.original})
   }
   res.send({alertas:arrayTotAlertas})
}

controllers.createAlerta = async (req,res) => { //post
   // data
   sequelize.sync()
   const { Descricao, LocalIDLocal, AdminIDAdmin, TipoAlertaIDTipoAlerta
   } = req.body;
   var statusCode = 200;
   try{
      var dataAlerta = await alerta.create({
         Descricao: Descricao,
         LocalIDLocal:LocalIDLocal,
         AdminIDAdmin:AdminIDAdmin,
         TipoAlertaIDTipoAlerta:TipoAlertaIDTipoAlerta
      })
   }catch(e){
      var msgErr = e.original;
      console.log(e);
      statusCode =500;
   }
   
   if(statusCode ===500)
      res.status(500).send({status:statusCode, desc:"Erro a criar Alerta", err:msgErr})
   else res.status(200).send({status:statusCode,Alerta:dataAlerta})
}


controllers.createAlerta_web = async (req,res) => { //post
   // data
   sequelize.sync()
   const { Descricao,dataalerta,LocalIDLocal, AdminIDAdmin, TipoAlertaIDTipoAlerta} = req.body;
   var statusCode = 200;
   try{
      var dataAlerta = await alerta.create({
         Descricao: Descricao,
         Data:dataalerta,
         LocalIDLocal:LocalIDLocal,
         AdminIDAdmin:AdminIDAdmin,
         TipoAlertaIDTipoAlerta:TipoAlertaIDTipoAlerta
      })
   }catch(e){
      var msgErr = e.original;
      console.log(e);
      statusCode =500;
   }
   
   if(statusCode ===500)
      res.status(500).send({status:statusCode, desc:"Erro a criar Alerta", err:msgErr})
   else res.status(200).send({status:statusCode,Alerta:dataAlerta})
}

module.exports= controllers;