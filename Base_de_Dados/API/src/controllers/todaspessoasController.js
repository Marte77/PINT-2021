var instituicao = require('../model/Instituicao');
var pessoas = require('../model/Pessoas/Pessoas');
var utils_instituicao = require('../model/Pessoas/Utils_Instituicao');
var Util_pertence_Inst = require('../model/Util_pertence_Inst');
var outros_util = require('../model/Pessoas/Outros_Util');
var admin = require('../model/Pessoas/Admin');
var outros_util = require('../model/Pessoas/Outros_Util');
var utils_instituicao = require('../model/Pessoas/Utils_Instituicao');
var sequelize = require('../model/database');
const controllers = {}


controllers.createAdmin = async (req,res) => { //post
   // data
   sequelize.sync()
    const { Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, InstituicaoIDInstituicao
    } = req.body;
    const dataPessoa = await pessoas.create({
    Data_Nascimento : Data_Nascimento, 
    Cidade : Cidade, 
    Codigo_Postal : Codigo_Postal,
    Email : Email,
    UNome : UNome,
    Localização : Localização,
    PNome : PNome,
    Password : Password
    })
    //console.log(dataPessoa.dataValues);
    var id=dataPessoa.dataValues.IDPessoa;

    const dataAdmin = await admin.create({
    InstituicaoIDInstituicao: InstituicaoIDInstituicao, 
    PessoaIDPessoa : id 
    })
    res.status(200).json({
    success: true,
    message:"Admin Registado",
    Pessoa: dataPessoa,
    Admin: dataAdmin
    });
    }

    controllers.createUtil_Instituicao = async (req,res) => { //post
   sequelize.sync()
    const { Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, InstituicaoIDInstituicao,Pontos,Ranking,Codigo_Empresa,ID_Util,UtilsInstituicaoIDUtil
    } = req.body;
    const dataPessoa = await pessoas.create({
    Data_Nascimento : Data_Nascimento, 
    Cidade : Cidade, 
    Codigo_Postal : Codigo_Postal,
    Email : Email,
    UNome : UNome,
    Localização : Localização,
    PNome : PNome,
    Password : Password
    })
    //console.log(dataPessoa.dataValues);
    var id=dataPessoa.dataValues.IDPessoa;  

    const dataUtil = await utils_instituicao.create({
    //InstituicaoIDInstituicao: InstituicaoIDInstituicao, 
    PessoaIDPessoa : id,
    Pontos : Pontos,
    Ranking : Ranking,
    Codigo_Empresa : Codigo_Empresa
    })
     var id_util=dataUtil.dataValues.ID_Util;  
    const dataUtil_pertence_Inst = await Util_pertence_Inst.create({
    InstituicaoIDInstituicao : InstituicaoIDInstituicao,
    UtilsInstituicaoIDUtil : id_util
    })
    res.status(200).json({
    success: true,
    message:"Utilizador Instituicao Registado",
    Pessoa: dataPessoa,
    Utilizador: dataUtil,
    Util_pertence_Inst:dataUtil_pertence_Inst
    });
    }

   controllers.createOutros_Util = async (req,res) => { //post
   // data
   sequelize.sync()
    const { Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, Pontos_Outro_Util , Ranking
    } = req.body;
    const dataPessoa = await pessoas.create({
    Data_Nascimento : Data_Nascimento, 
    Cidade : Cidade, 
    Codigo_Postal : Codigo_Postal,
    Email : Email,
    UNome : UNome,
    Localização : Localização,
    PNome : PNome,
    Password : Password
    })
    console.log(dataPessoa.dataValues);
    var id=dataPessoa.dataValues.IDPessoa;

    const dataOutros_Util = await outros_util.create({
    PessoaIDPessoa : id ,
    Pontos_Outro_Util : Pontos_Outro_Util,
    Ranking : Ranking,
    })
    res.status(200).json({
    success: true,
    message:"Outros_Util Registado",
    Pessoa: dataPessoa,
    Outros_Util: dataOutros_Util,
    });
    }


module.exports= controllers;