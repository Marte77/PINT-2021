var sequelize = require('../model/database');
const Instituicao = require('../model/Instituicao');
const controllers = {}
const instituicao = require('../model/Instituicao')

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

controllers.updateinstituicao=async(req,res)=>{

    const{idInstituicao}=req.params
    const{nomeinst,emailinst,telefoneinst,poucoinst,moderadoinst,elevadoinst,descrinst}=req.body;
    const data=await Instituicao.update({
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

    

module.exports = controllers;