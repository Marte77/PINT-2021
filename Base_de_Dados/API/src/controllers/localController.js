//todo: fazer listagem de locais
//todo: depois ir ao mobile, apanhar esses locais e tentar fazer reports
var sequelize = require('../model/database');
var Sequelize = require('sequelize');
const {Op} = require('sequelize')
const Local = require('../model/Local');
const controllers = {}
const locais = require('../model/Local');
const Local_Indoor = require('../model/Local_Indoor');
const localindoor = require('../model/Local_Indoor')
const Report = require('../model/Reports/Report')
const Report_Outdoor_Outros_Util = require('../model/Reports/Report_Outdoor_Outros_Util')
const Report_Outdoor_Util_Instituicao = require('../model/Reports/Report_Outdoor_Util_Instituicao');
const Instituicao = require('../model/Instituicao');
const Report_Indoor = require('../model/Reports/Report_Indoor');

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


controllers.getlocais_assocInstituicao=async(req,res)=>{
    const{idInstituicao}=req.params
    var statuscode = 200;
    var errMessage="";
    try{
        var listalocaisbyinst=await locais.findAll({
            where:{
                InstituicaoIDInstituicao:idInstituicao
            }
        })
    }
    catch(e){console.log(e);errMessage = e;statuscode = 500;}
    if(statuscode === 500)
    res.status(statuscode).send({status: statuscode, err: errMessage});
    else res.status(statuscode).send({status:200, LocaisInst: listalocaisbyinst})
}

controllers.getlocaisindoor_byinstituicao= async(req,res)=>{ //get
{
    const{idInstituicao}=req.params
    var statuscode = 200;
    var errMessage="";
    try{
        var arraylocaisindoor = new Array();
        var listalocais = await locais.findAll({
            where:{
                InstituicaoIDInstituicao:idInstituicao
            }
        })
        for(let local of listalocais){
            var locaisindoor = await Local_Indoor.findAll({
                include:[locais],
                where:{
                    LocalIDLocal:local.dataValues.ID_Local
                }
            })
            for(let localindoor of locaisindoor){
                arraylocaisindoor.push(localindoor)
            }
        
}
        }
    
    catch(e){console.log(e);errMessage = e;statuscode = 500;}
    if(statuscode === 500)
    res.status(statuscode).send({status: statuscode, err: errMessage});
    else res.status(statuscode).send({status:200, LocaisIndor: arraylocaisindoor})
    
}
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

controllers.CriarLocal_WEB= async(req,res)=>{

    const{nome, codigopostal,descricao,urlimagem,localizacao, longitude, latitude, idinstituicao} = req.body

    
        const novoLocal = await locais.create({
            Nome:nome,
            Codigo_Postal: codigopostal,
            Descricao:descricao,
            URL_Imagem: urlimagem,
            Localizacao:localizacao,
            Longitude:longitude,
            Latitude:latitude,
            InstituicaoIDInstituicao:idinstituicao
        })
        .then(function(novoLocal){  return novoLocal;   })
            .catch(error =>{ console.log("Erro: "+error)
              return error;
            })
            // return res
            res.status(200).json({success: true,   message:"Registado",  novoLocal: novoLocal  });
            
}


controllers.CriarLocalindoor_WEB= async(req,res)=>{

        const{nome, descricao,piso,idlocal} = req.body
        
            const novoLocal = await localindoor.create({
                Nome:nome,
                Descricao: descricao,
                Piso:piso,
                LocalIDLocal: idlocal,
            })
            .then(function(novoLocal){  return novoLocal;   })
                .catch(error =>{ console.log("Erro: "+error)
                    return error;
                })
                // return res
                res.status(200).json({success: true,   message:"Registado",  novoLocal: novoLocal  });
                
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

/**
 * 
 * vai devolver todos os locais e a sua percentagem relativamente ao numero de reports de grande densidade 
 */
controllers.getPercentagemDeReportsDeCadaLocal = async(req,res)=>{//post
    const {tempo, tipoTempo,niveldensidade} = req.body //tipotempo = hh - horas, mm - minutos, dd - dias
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

    
    if(typeof(niveldensidade) !== 'number'){
        res.status(500).send({desc:'Numero de densidade nao e um numero'})
        return;
    }else if(niveldensidade > 3 || niveldensidade < 1){
        res.status(500).send({desc:'Numero de densidade invalido'})
        return;
    }
    dataAgr = dataAgr.toISOString()
    var listalocaisEReports = new Array()
    var nReportsTotal = 0
    try {
        var listaDeLocais = await Local.findAll()
        for(let locale of listaDeLocais){//obter lista de reports, contar numero de reports, guardar num json, meter na lista
            let reportoutros=await Report_Outdoor_Outros_Util.findAll({
                include:{
                    model:Report,
                    where:{
                        Nivel_Densidade:{
                            [Op.eq]:niveldensidade
                        },Data:{
                            [Op.gte]:dataAgr
                        }
                    }
                },
                where:{
                    LocalIDLocal:locale.dataValues.ID_Local
                }
            })
            let reportutils=await Report_Outdoor_Util_Instituicao.findAll({
                include:{
                    model:Report,
                    where:{
                        Nivel_Densidade:{
                            [Op.eq]:niveldensidade
                        },Data:{
                            [Op.gte]:dataAgr
                        }
                    }
                },
                where:{
                    LocalIDLocal:locale.dataValues.ID_Local
                }
            })
            nReportsTotal = nReportsTotal +reportutils.length + reportoutros.length
            
            let localjson = {
                NomeLocal:locale.dataValues.Nome, IDLocal:locale.dataValues.ID_Local,
                NumeroReports:(reportutils.length + reportoutros.length)
            }
            console.log(localjson)
            listalocaisEReports.push(localjson)
        }
        
    } catch (e) {
        console.log(e)
        res.status(500).send({desc:'Erro a selecionar',err:e.original})
    }
    var resposta = new Array();
    if(nReportsTotal>0){
        for(let local of listalocaisEReports){
        let percentagemlocal = (local.NumeroReports / nReportsTotal) *100
        resposta.push({
            IDLocal:local.IDLocal,
            NomeLocal:local.NomeLocal,
            PercentagemLocal:percentagemlocal
        })
        }
    }
    res.send({Resultado:resposta, NumeroReportsTotal:nReportsTotal})
}


controllers.getlocalout= async (req,res) => {
    const{idlocal}=req.params;
    const data= await locais.findAll({
        where:{
            ID_Local:idlocal
        }
    })
    .then(function(data){
        return data;
        })
        .catch(error =>{
        return error;
        })
        res.json({ success: true, data: data });
        
}

controllers.editlocal= async (req,res) => {
    const{idlocal}=req.params;
    const{nome,cp, descri,urlimg, locali,long,lati}=req.body;
    const data= await locais.update({
        Nome:nome,
        Codigo_Postal:cp,
        Descricao:descri,
        URL_Imagem:urlimg,
        Localizacao:locali,
        Longitude:long,
        Latitude:lati
    },
    {
        where:{ID_Local:idlocal}
    
    })
    .then( function(data){
        return data;
        })
        .catch(error => {
        return error;
        })
        res.json({success:true, data:data, message:"Updated successful"});
        
}

controllers.getlocalint= async (req,res) => {
    const{idlocal}=req.params;
    const data= await localindoor.findAll({
        where:{
            ID_Local_Indoor:idlocal
        }
    })
    .then(function(data){
        return data;
        })
        .catch(error =>{
        return error;
        })
        res.json({ success: true, data: data });
        
}

controllers.editlocalint= async (req,res) => {
    const{idlocal}=req.params;
    const{nome,descri, piso}=req.body;
    const data= await localindoor.update({
        Nome:nome,
        Descricao:descri,
        Piso:piso
    },
    {
        where:{ID_Local_Indoor:idlocal}
    
    })
    .then( function(data){
        return data;
        })
        .catch(error => {
        return error;
        })
        res.json({success:true, data:data, message:"Updated successful"});
        
}

controllers.deletelocal= async (req, res) => {
    const { id } = req.body;
    const del=await locais.destroy({
        where:
        {
            ID_Local:id
        }
    })
    res.json({success:true,deleted:del,message:"Deleted successful"});

}
controllers.deletelocalint= async (req, res) => {
    const { id } = req.body;
    const del=await localindoor.destroy({
        where:
        {
            ID_Local_Indoor:id
        }
    })
    res.json({success:true,deleted:del,message:"Deleted successful"});

}

async function getNReportsOutDoorParaCadaDensidade(local,dataliminf,datalimsup){
    let array = new Array()
    for(let i =0;i<3;i++){
        let nreportsutilsinstout = await Report_Outdoor_Util_Instituicao.count({
            where:{
                LocalIDLocal:local.dataValues.ID_Local
            },include:{
                model:Report,
                where:{
                    Data:{
                        [Op.between]:[dataliminf,datalimsup]
                    },
                    Nivel_Densidade:i+1
                }
            }
        })
        let nreportsoutrosutilt = await Report_Outdoor_Util_Instituicao.count({
            where:{
                LocalIDLocal:local.dataValues.ID_Local
            },include:{
                model:Report,
                where:{
                    Data:{
                        [Op.between]:[dataliminf,datalimsup]
                    },
                    Nivel_Densidade:i+1
                }
            }
        })
        array.push([i+1,nreportsoutrosutilt+nreportsutilsinstout])
    }
    return array
} 

async function getNReportsIndoorParaCadaDensidade(localindor,dataliminf,datalimsup){
    let array = new Array()
    for(let i =0;i<3;i++){
        let nreports = await Report_Indoor.count({
            where:{
                LocalIndoorIDLocalIndoor:localindor.dataValues.ID_Local_Indoor
            },include:{
                model:Report,
                where:{
                    Data:{
                        [Op.between]:[dataliminf,datalimsup]
                    },
                    Nivel_Densidade:i+1
                }
            }
        })
        array.push([i+1,nreports])
    }
    return array
}

controllers.getdadostabeladalotacao = async (req,res)=>{
let obtermediareportsoutdooroutrosutil = 'select AVG("Nivel_Densidade") from "Report_Outdoor_Outros_Utils" inner join "Reports" on "ID_Report" = "ReportIDReport" where "Data" between :dataliminf and :datalimsup and "LocalIDLocal" = :idlocal;'
let obtermediareportsoutdoorutilinst = 'select AVG("Nivel_Densidade") from "Report_Outdoor_Util_Instituicaos" inner join "Reports" on "ID_Report" = "ReportIDReport" where "Data" between :dataliminf and :datalimsup and "LocalIDLocal" = :idlocal;'
let obtermediareportsindoor = 'select AVG("Nivel_Densidade") from "Report_Indoors" inner join "Reports" on "ID_Report" = "ReportIDReport" where "Data" between :dataliminf and :datalimsup and "LocalIndoorIDLocalIndoor" = :idlocalindoor'
    const {idinstituicao,dataliminf,datalimsup} = req.params
    let arrayZonasCrowdZero = new Array()
    arrayZonasCrowdZero.push({ZonasOutdoor:[],ZonasIndoor:[]})
    let arrayZonasIndoor = new Array()
    let arrayZonasOutdoor = new Array()
    try{
        var locais = await Local.findAll({
            where:{
                InstituicaoIDInstituicao:idinstituicao
            }
        })
        var locaisindoor = new Array()
        for(let local of locais){
            let localindoor = await Local_Indoor.findAll({
                where:{
                    LocalIDLocal:local.dataValues.ID_Local
                }
            })
            locaisindoor.push(localindoor)
        }
        
        for(let local of locais){
            let arrayzonascrowdzerooutdoor = arrayZonasCrowdZero[0].ZonasOutdoor
            let repoutoutrosutil = await sequelize.query(obtermediareportsoutdooroutrosutil,{
                type:Sequelize.QueryTypes.SELECT,
                replacements:{
                    idlocal:local.ID_Local,
                    datalimsup:datalimsup,
                    dataliminf:dataliminf
                }
            })
            let reportutilsinstout = await sequelize.query(obtermediareportsoutdoorutilinst,{
                type:Sequelize.QueryTypes.SELECT,
                replacements:{
                    idlocal:local.ID_Local,
                    datalimsup:datalimsup,
                    dataliminf:dataliminf
                }
            })
            let arrayNReports = await getNReportsOutDoorParaCadaDensidade(local,dataliminf,datalimsup)
            if(reportutilsinstout[0].avg === null && repoutoutrosutil[0].avg === null){
                arrayzonascrowdzerooutdoor.push({Local:local,Media:0, Nreports:arrayNReports})
            }else if(reportutilsinstout[0].avg === null && repoutoutrosutil[0].avg !== null){
                if(repoutoutrosutil[0].avg <1.5){
                    arrayzonascrowdzerooutdoor.push({Local:local,Media:repoutoutrosutil[0].avg, Nreports:arrayNReports})
                }else arrayZonasOutdoor.push({Local:local,Media:repoutoutrosutil[0].avg, Nreports:arrayNReports})
            }else if(reportutilsinstout[0].avg !== null && repoutoutrosutil[0].avg === null){
                if(reportutilsinstout[0].avg <1.5){
                    arrayzonascrowdzerooutdoor.push({Local:local,Media:reportutilsinstout[0].avg, Nreports:arrayNReports})
                }else arrayZonasOutdoor.push({Local:local,Media:reportutilsinstout[0].avg, Nreports:arrayNReports})
            }else{
                mediatot = (reportutilsinstout[0].avg +repoutoutrosutil[0].avg )/2
                if(mediatot<1.5)
                    arrayzonascrowdzerooutdoor.push({Local:local,Media:mediatot, Nreports:arrayNReports})
                else arrayZonasOutdoor.push({Local:local,Media:mediatot, Nreports:arrayNReports})
            }
            
        }
        if(locaisindoor.length !== 0)
            //for(let i = 0;i<locaisindoor.length;i++)
            //if(i === 1)
            //    console.log(locaisindoor[i].length)
            for(let arrlocalindor of locaisindoor){
                for(let localindor of arrlocalindor){
                    let arrayzonascrowdzeroindoor = arrayZonasCrowdZero[0].ZonasIndoor
                    let nreportstotal = await getNReportsIndoorParaCadaDensidade(localindor, dataliminf,datalimsup)
                    let media = await sequelize.query(obtermediareportsindoor,{
                        type:Sequelize.QueryTypes.SELECT,
                        replacements:{
                            idlocalindoor:localindor.ID_Local_Indoor,
                            datalimsup:datalimsup,
                            dataliminf:dataliminf
                        }
                    })
                    if(media[0].avg === null)
                        arrayzonascrowdzeroindoor.push({LocalIndoor:localindor,Media:0,Nreports:nreportstotal})
                    else if(media[0].avg < 1.5)
                        arrayzonascrowdzeroindoor.push({LocalIndoor:localindor,Media:media[0].avg,Nreports:nreportstotal})
                    else arrayZonasIndoor.push({LocalIndoor:localindor,Media:media[0].avg,Nreports:nreportstotal})
                }
            }

    }catch(e){
        console.log(e)
        res.status(500).send({desc: "Erro a selecionar", er:e.original})
    }
    res.send({ZonasCrowdZero:arrayZonasCrowdZero,ZonasNotCrowdZero:{ZonasOutdoor:arrayZonasOutdoor,ZonasIndoor:arrayZonasIndoor}})
}


module.exports = controllers;