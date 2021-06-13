var sequelize = require('../model/database');
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
    try{var reportNovo = await Report.create({
        Descricao: DescricaoReport,
        Nivel_Densidade:NivelDensidade,
        N_Likes: n_LikesDislikes,
        N_Dislikes: n_LikesDislikes,
        Data:dataAgr
    })}catch(e){console.log(e);statusCode = 500}

    try{var reportOutdoorOutrosNovo = await Report_Outdoor_Outros_Util.create(
        {
            ReportIDReport: reportNovo.dataValues.ID_Report,
            LocalIDLocal: IDLocal,
            OutrosUtilIDOutroUtil: idOutroUtil
        }
    )}catch(e){console.log(e);statusCode = 500}
    if(statusCode === 500)
        res.send({status:statusCode, desc:"erro a criar"})
    else res.send({status:statusCode,Report:reportNovo,ReportOut:reportOutdoorOutrosNovo })
}   


module.exports = controllers;