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
    const { Descricao, Nivel_Densidade,idLocal,}
    let n_LikesDislikes = 0
    let dataAgr = new Date()
    dataAgr = dataAgr.toISOString()

}