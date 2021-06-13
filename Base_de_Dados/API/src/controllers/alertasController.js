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
const controllers = {}

controllers.createTipoAlerta = async (req,res) => { //post
   // data
   sequelize.sync()
    const { Tipo_Alerta
    } = req.body;
    const dataTipo_Alerta = await tipo_alerta.create({
       Tipo_Alerta: Tipo_Alerta
    })
    res.status(200).json({
    success: true,
    message:"Tipo Alerta Registado",
    Tipo_Alerta: dataTipo_Alerta
    });
    }

    controllers.createAlerta = async (req,res) => { //post
   // data
   sequelize.sync()
    const { Descricao, LocalIDLocal, AdminIDAdmin, TipoAlertaIDTipoAlerta
    } = req.body;
    const dataAlerta = await alerta.create({
        Descricao: Descricao,
       LocalIDLocal:LocalIDLocal,
       AdminIDAdmin:AdminIDAdmin,
       TipoAlertaIDTipoAlerta:TipoAlertaIDTipoAlerta
    })
    res.status(200).json({
    success: true,
    message:"Alerta Registado",
    Alerta: dataAlerta
    });
    }
module.exports= controllers;