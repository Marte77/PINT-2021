var sequelize = require('../model/database');
const {Op} = require('sequelize')
const controllers = {}
const Report = require('../model/Reports/Report')
const Report_Indoor = require('../model/Reports/Report_Indoor')
const Report_Outdoor_Outros_Util = require('../model/Reports/Report_Outdoor_Outros_Util')
const Report_Outdoor_Util_Instituicao = require('../model/Reports/Report_Outdoor_Util_Instituicao')
const Outro_Util = require('../model/Pessoas/Outros_Util')
const Util_Instituicao = require('../model/Pessoas/Utils_Instituicao')
const Pessoas = require('../model/Pessoas/Pessoas')
const Local = require('../model/Local')
const Local_Indoor = require('../model/Local_Indoor')
const Instituicao = require('../model/Instituicao');
const Tabela_LikesDislikes = require('../model/Reports/Tabela_LikesDislikes');

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

controllers.getListaReportsOutdoorLocal = async (req,res)=>{ //put
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
    
    try{
        var reportsOutrosUtil = await Report_Outdoor_Outros_Util.findAll({
            include:[{
                model:Report, 
                where:{
                    Data:{
                        [Op.gte]:dataAgr
                    }
                }
            },{
                model:Outro_Util,
                include:{
                    model:Pessoas,
                    attributes:{
                        exclude:['Password']
                    },required:false
                }
            }],
            where:{
                LocalIDLocal:idlocal
            }
        })
        var reportsUtilInst = await Report_Outdoor_Util_Instituicao.findAll({
            include:[{
                model:Report, 
                where:{
                    Data:{
                        [Op.gte]:dataAgr
                    }
                }
            },{model:
                Util_Instituicao,
                include:{
                    model:Pessoas,
                    attributes:{
                        exclude:['Password']
                    },required:false
                }
            }],
            where:{
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

controllers.getDensidadeMediaLocal = async(req,res)=>{ //put
    const {idlocal} = req.params
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

    try{
        var reportsOutrosUtil = await Report_Outdoor_Outros_Util.findAll({
            include:[{
                model:Report, 
                where:{
                    Data:{
                        [Op.gte]:dataAgr
                    }
                }
            }],
            where:{
                LocalIDLocal:idlocal
            }
        })
        var reportsUtilInst = await Report_Outdoor_Util_Instituicao.findAll({
            include:[{
                model:Report, 
                where:{
                    Data:{
                        [Op.gte]:dataAgr
                    }
                }
            }],
            where:{
                LocalIDLocal:idlocal
            }
        })
    }catch(e){
        console.log(e)
        res.status(500).send({desc:"Erro a selecionar",err:e.original})
        return;
    }

    var totalReports = reportsOutrosUtil.length + reportsUtilInst.length
    if(totalReports === 0){
        res.send({numeroReports:0})
        return;
    }

    var somaDensidade = 0;
    for(let a of reportsOutrosUtil)
        somaDensidade = somaDensidade + a.dataValues.Report.dataValues.Nivel_Densidade
    for(let a of reportsUtilInst)
        somaDensidade = somaDensidade + a.dataValues.Report.dataValues.Nivel_Densidade
    
    res.send({numeroReports:totalReports,media:(Math.round(somaDensidade/totalReports))})
}

controllers.getNumeroReportsFeitos = async(req,res)=>{//get
    const {id} = req.params
    try {
        var tipoPessoa = "Outro_Util"
        var pessoa = await Outro_Util.findOne({
            where:{
                PessoaIDPessoa:id
            }
        })
        if(pessoa === null)
        {
            pessoa = await Util_Instituicao.findOne({
                where:{
                    PessoaIDPessoa:id
                }
            })
            tipoPessoa = "Util_Instituicao"
            if(pessoa === null)
                throw new Error('A pessoa não existe')
        }
        var nreports, ntotalreports
        if(tipoPessoa === "Outro_Util"){
            nreports = await Report_Outdoor_Outros_Util.findAll({
                where:{
                    OutrosUtilIDOutroUtil: pessoa.dataValues.ID_Outro_Util
                }
            })
            ntotalreports = nreports.length
        }else{
            nreports = await Report_Indoor.findAll({
                where:{
                    UtilsInstituicaoIDUtil:pessoa.dataValues.ID_Util
                }
            })
            ntotalreports = nreports.length
            nreports = await Report_Outdoor_Util_Instituicao.findAll({
                where:{
                    UtilsInstituicaoIDUtil:pessoa.dataValues.ID_Util
                }
            })
            ntotalreports =ntotalreports + nreports.length
        }        
        var {count,rows} = await Tabela_LikesDislikes.findAndCountAll({
            where:{
                PessoaIDPessoa:id
            }
        })
    } catch (e) {
        console.log(e)
        res.status(500).send({
            desc:"erro a pesquisar",
            err: e.toString()
        })
    }
    var {nLikesAdicionaOuSubtrai} = require('./numero_Like.json')
    res.send({
        NLikes:count,
        Numero_Reports: ntotalreports,

        Pessoa:pessoa
    })

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

controllers.getListaReportsIndoor = async (req,res)=>{//put
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
    var reportsIndoor = new Array();
    try{
        var listaLocaisIndoor = await Local_Indoor.findAll({
            where:{
                LocalIDLocal:idlocal
            }
        })
        if(listaLocaisIndoor.length === 0)
            throw new Error('Este local nao tem locais indoor')
        for(let local of listaLocaisIndoor){
            let reportsdolocalindoor = await Report_Indoor.findAll({
                where:{
                    LocalIndoorIDLocalIndoor: local.dataValues.ID_Local_Indoor
                },include:[{
                    model:Report, 
                    where:{
                        Data:{
                            [Op.gte]:dataAgr
                        }
                    }
                },{
                    model:Util_Instituicao,
                    include:{
                        model:Pessoas,
                        attributes:{
                            exclude:['Password']
                        },required:false
                    }
                },{
                    model:Local_Indoor,
                }]
            })
            if(reportsdolocalindoor.length !== 0){
                for(let reportdolocal of reportsdolocalindoor)
                    reportsIndoor.push(reportdolocal)
            }
        }
    }catch(e){
        console.log(e)
        if(e.toString() === 'Error: Este local nao tem locais indoor' )
            res.status(200).send({desc:'local nao tem locais indoor'})
        else res.status(500).send({desc:"Erro a selecionar",err:e.original})
    }
    //let resposta = await JSON.stringify(reportsIndoor)
    
    res.status(200).send({ReportsIndoor:reportsIndoor})
}

module.exports = controllers;