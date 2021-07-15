var sequelize = require('../model/database');
const {Op} = require('sequelize')
const Instituicao = require('../model/Instituicao');
const controllers = {}
const instituicao = require('../model/Instituicao')
const Local = require('../model/Local');
const Report = require('../model/Reports/Report');
const Report_Indoor = require('../model/Reports/Report_Indoor');
const Report_Outdoor_Outros_Util = require('../model/Reports/Report_Outdoor_Outros_Util');
const Report_Outdoor_Util_Instituicao = require('../model/Reports/Report_Outdoor_Util_Instituicao');
controllers.createInstituicao = async (req,res) => { //post
    let statusCode = 200;
    const { Nome, Codigo_Postal,Email, Telefone, Descricao, URL_Imagem, Longitude, Latitude, Localizacao, Codigo_Empresa
    ,LotacaoPouca, LotacaoMedia, LotacaoAlta}= req.body
    try{
        var NovaInstituicao = await instituicao.create({
            Nome: Nome,
            Email:Email,
            Codigo_Postal : Codigo_Postal,
            Telefone: Telefone,
            Descricao: Descricao,
            URL_Imagem : URL_Imagem,
            Longitude : Longitude,
            Latitude : Latitude,
            Localizacao : Localizacao,
            Codigo_Empresa : Codigo_Empresa,
            LotacaoPouca:LotacaoPouca,
            LotacaoMedia:LotacaoMedia, 
            LotacaoAlta:LotacaoAlta
        })
    }catch(e){
        console.log(e);
        statusCode = 500;
        var msgErr = e.original;
    }
    if(statusCode === 500)
        res.status(statusCode).send({status:statusCode, desc:"Erro a criar", err:msgErr})
    else res.status(statusCode).send({status:statusCode,instituicao:NovaInstituicao })
} 

controllers.getInstituicao = async (req,res) => {//get
    const{idInstituicao}=req.params
       var statuscode = 200;
       var errMessage="";
    try {
        var instituicoes =await Instituicao.findAll({
            where:{
                ID_Instituicao:idInstituicao
              }
        })
        
    } catch (e) {
        res.status(500).send({desc:"Erro a selecionar", err:e.toString()})
    }
    res.send({Instituicoes:instituicoes})

}

controllers.getListaInstituicoes = async(req,res)=>{
    try {
        var instituicoes = await Instituicao.findAll()
    } catch (e) {
        console.log(e)
        res.status(500).send({desc:"Erro a selecionar", err:e.original})
    }
    res.send({Instituicoes:instituicoes})
}   

controllers.updateinstituicao=async(req,res)=>{

    const{idInstituicao}=req.params
    const{nomeinst,emailinst,telefoneinst,poucoinst,moderadoinst,elevadoinst,descrinst}=req.body;
    const data=await instituicao.update({
        Nome:nomeinst,
        Email:emailinst,
        Telefone:telefoneinst,
        Descricao:descrinst,
        Lotacao_Pouco:poucoinst,
        Lotacao_Moderado:moderadoinst,
        Lotacao_Elevado:elevadoinst
    },
    {
        where: { ID_Instituicao: idInstituicao}
    })
    .then(function(data)
    {
        return data;
    })
    .catch(error => {
        return error;
        })
        res.json({success:true, data:data, message:"Updated successful"});
        
}

//outra forma de fazer update
/*controllers.updateinstituicao=async(req,res)=>{

    const{idInstituicao}=req.params
    const{nomeinst,emailinst,telefoneinst,poucoinst,moderadoinst,elevadoinst,descrinst}=req.body;
    try{
        var a= await instituicao.findOne({
            where:{
                ID_Instituicao: idInstituicao
            }
        })
        a.Nome=nomeinst,
        a.Email=emailinst,
        a.Telefone=telefoneinst,
        a.Descricao=descrinst,
        a.Lotacao_Pouco=poucoinst,
        a.Lotacao_Moderado=moderadoinst,
        a.Lotacao_Elevado=elevadoinst
        await a.save()
    }   
    catch (e) {
        console.log(e)
        res.send({desc:"Erro a fazer update",err:e.original})
    }
    res.send({Instituicoes:a})

}*/

controllers.getNReportsXDiasInstituicao = async(req,res)=>{
    const {idinstituicao,nDias} = req.params
    let dataAgr = new Date()
    var arrayDiasNReports = new Array()
    try {
        var locaisinst = await Local.findAll({
            where:{
                InstituicaoIDInstituicao:idinstituicao
            }
        })
        

        for(let i = 0;i<nDias; i++){
            let nTotalReports = 0
            let densidademedia = 0
            let datadia = new Date(new Date().setHours(25,0,0,0))
            let datadiasup = new Date(new Date().setHours(25,0,0,0))
            datadia.setDate(datadia.getDate()-1)
            datadia.setDate(datadia.getDate()-i)
            datadiasup.setDate(datadiasup.getDate()-i+1)
            datadia = (datadia.toISOString()).split('T')[0]
            datadiasup = datadiasup.toISOString().split('T')[0]
            for(let local of locaisinst){
                let reportsoututilinst = await Report_Outdoor_Util_Instituicao.findAll({
                    where:{
                        LocalIDLocal:local.dataValues.ID_Local,
                    },
                    include:[{
                        model:Report,
                        where:{
                            Data:{
                                [Op.between]:[datadia,datadiasup]
                            }
                        }
                    }]
                })
                /*let reportsindutilinst = await Report_Indoor.count({
                    where:{
                        LocalIDLocal:local.dataValues.ID_Local,
                    },
                    include:{
                        model:Report,
                        where:{
                            Data:{
                                [Op.and]:{
                                    [Op.gte]:{
                                        datadia
                                    },
                                    [Op.lt]:{
                                        datadiasup
                                    }
                                }
                            }
                        }
                    }
                })*/
                let reportsoutrosutil = await Report_Outdoor_Outros_Util.findAll({
                    where:{
                        LocalIDLocal:local.dataValues.ID_Local,
                    },
                    include:[{
                        model:Report,
                        where:{
                            Data:{
                                [Op.between]:[datadia,datadiasup]
                            }
                        }
                    }]
                })
                nTotalReports = nTotalReports+ reportsoutrosutil.length + reportsoututilinst.length
                //todo talvez fazer densidade media?
                //densidademedia = soma das densidades 
            }
            //densidademedia = densidademedia / nTotalReports
            let dia = new Date(new Date().setHours(25,0,0,0))
            dia.setDate(dia.getDate()-1-i)
            arrayDiasNReports.push({
                NReports:nTotalReports,
                dia:dia.getDate(),
                diasemana:dia.getDay()
                //densidademedia: densidademedia
            })
        }
    } catch (e) {
        console.log(e)
        res.status(500).send({desc:"Erro a selecionar", er:e.original})
    }
    res.send({res:arrayDiasNReports})
}

    

module.exports = controllers;