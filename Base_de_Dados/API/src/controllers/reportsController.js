var sequelize = require('../model/database');
const {Op} = require('sequelize')
const controllers = {}
const Report = require('../model/Reports/Report')
const Report_Indoor = require('../model/Reports/Report_Indoor')
const Report_Outdoor_Outros_Util = require('../model/Reports/Report_Outdoor_Outros_Util')
const Report_Outdoor_Util_Instituicao = require('../model/Reports/Report_Outdoor_Util_Instituicao')
const Local = require('../model/Local')
const Local_Indoor = require('../model/Local_Indoor')
const Instituicao = require('../model/Instituicao')

controllers.criarReportOutdoorOutrosUtil = async (req,res) => { //post
    const { DescricaoReport,NivelDensidade,IDLocal,idOutroUtil}= req.body
    let n_LikesDislikes = 0
    let statusCode = 200;
    let dataAgr = new Date()
    dataAgr = dataAgr.toISOString()
    try{
        var reportNovo = await Report.create({
            Descricao: DescricaoReport,
            Nivel_Densidade:NivelDensidade,
            N_Likes: n_LikesDislikes,
            N_Dislikes: n_LikesDislikes,
            Data:dataAgr
        })
        var reportOutdoorOutrosNovo = await Report_Outdoor_Outros_Util.create({
            ReportIDReport: reportNovo.dataValues.ID_Report,
            LocalIDLocal: IDLocal,
            OutrosUtilIDOutroUtil: idOutroUtil
        })
    }catch(e){
        console.log(e);
        statusCode = 500
        var msgErr = e.original;
        var descricao="Erro a criar report"
        if(reportNovo != undefined){
            descricao = descricao+ " Dados reportGeral Corretos"
            reportNovo.destroy();
        }
    }
    if(statusCode === 500)
        res.status(500).send({status:statusCode, desc:descricao, err:msgErr})
    else res.send({status:statusCode,Report:reportNovo,ReportOut:reportOutdoorOutrosNovo })
} 
controllers.criarReportOutdoorUtilInstituicao = async (req,res) => { //post
    const { DescricaoReport,NivelDensidade,IDLocal,idUtilInst}= req.body
    let n_LikesDislikes = 0
    let statusCode = 200;
    let dataAgr = new Date()
    dataAgr = dataAgr.toISOString()
    try{
        var reportNovo = await Report.create({
            Descricao: DescricaoReport,
            Nivel_Densidade:NivelDensidade,
            N_Likes: n_LikesDislikes,
            N_Dislikes: n_LikesDislikes,
            Data:dataAgr
        })
        var reportOutdoorUtilInstituicaoNovo = await Report_Outdoor_Util_Instituicao.create({
            ReportIDReport: reportNovo.dataValues.ID_Report,
            LocalIDLocal: IDLocal,
            UtilsInstituicaoIDUtil: idUtilInst
        })

    }catch(e){
        console.log(e);
        statusCode = 500
        var msgErr = e.original;
        var descricao = "Erro a criar report"
        if(reportNovo!=undefined){
            //deu erro no outro
            descricao = descricao + " Dados ReportGeral Corretos"
            reportNovo.destroy();
        }
    }

    
    if(statusCode === 500)
        res.send({status:statusCode, desc:descricao,err:msgErr})
    else res.send({status:statusCode,Report:reportNovo,ReportOut:reportOutdoorUtilInstituicaoNovo })
}  
controllers.criarReportIndoor = async (req,res) => { //post
    const { DescricaoReport,NivelDensidade,IDLocalIndoor,idUtilInst}= req.body
    let n_LikesDislikes = 0
    let statusCode = 200;
    let dataAgr = new Date()
    dataAgr = dataAgr.toISOString()
    try{
        var reportNovo = await Report.create({
            Descricao: DescricaoReport,
            Nivel_Densidade:NivelDensidade,
            N_Likes: n_LikesDislikes,
            N_Dislikes: n_LikesDislikes,
            Data:dataAgr
        })
        var reportIndoor = await Report_Indoor.create({
            ReportIDReport: reportNovo.dataValues.ID_Report,
            LocalIndoorIDLocalIndoor: IDLocalIndoor,
            UtilsInstituicaoIDUtil: idUtilInst
        })
    
    }catch(e){
        console.log(e);
        statusCode = 500
        var msgErr = e.original;
        var descricao = "Erro a criar report"
        if(reportNovo!=undefined){
            //deu erro no outro
            descricao = descricao + " Dados ReportGeral Corretos"
            reportNovo.destroy();
        }
    }

    if(statusCode === 500)
        res.send({status:statusCode, desc:descricao,msgErr})
    else res.send({status:statusCode,Report:reportNovo,ReportIn:reportIndoor })
}    

controllers.getListaReportsOutdoorLocal = async (req,res)=>{ //get 
    const {idlocal} = req.params;
    const {tempo, tipoTempo} = req.body //tipotempo = hh - horas, mm - minutos, dd - dias
    let dataAgr = new Date()
    switch(tipoTempo){
        case 'hh':{
            dataAgr.setHours(dataAgr.getHours() - tempo)
            break;
        }
        case 'mm':{
            dataAgr.setMinutes(dataAgr.getMinutes() - tempo)
            break;
        }
        case 'dd':{
            dataAgr.setDate(dataAgr.getDate() - tempo)
            break;
        }
        default:{
            res.status(500).send({desc:"Tipo Invalido",err:"TipoInvalido"})
            return;
        }
    }
    dataAgr = dataAgr.toISOString()
    console.log(dataAgr)
    
    try{
        var reportsOutrosUtil = await Report_Outdoor_Outros_Util.findAll({
            include:{
                model:Report, 
                where:{
                    Data:{
                        [Op.gte]:dataAgr
                    }
                }
            },
            where:{
                //Data:dataAgr,
                LocalIDLocal:idlocal
            }
        })
        var reportsUtilInst = await Report_Outdoor_Util_Instituicao.findAll({
            include:[Report],
            where:{
                //Data:dataAgr,
                LocalIDLocal:idlocal
            }
        })
    }catch(e){
        console.log(e)
        res.status(500).send({desc:"Erro a selecionar",err:e.original})
    }
    var resposta = {ReportsOutrosUtil:{},ReportsUtilInst:{}};
    resposta.ReportsOutrosUtil = reportsOutrosUtil
    resposta.ReportsUtilInst = reportsUtilInst
    res.status(200).send({Reports:resposta})
    
}



module.exports = controllers;