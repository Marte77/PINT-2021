var instituicao = require('../model/Instituicao');
var pessoas = require('../model/Pessoas/Pessoas');
var utils_instituicao = require('../model/Pessoas/Utils_Instituicao');
var Util_pertence_Inst = require('../model/Util_pertence_Inst');
var outros_util = require('../model/Pessoas/Outros_Util');
var admin = require('../model/Pessoas/Admin');
var sequelize = require('../model/database');
const moment = require('../../node_modules/moment')
const controllers = {}


//nas criacoes de pessoas, se algum der erro, ele apaga os anteriores da BD
//, garantindo que nao existam pessoas repetidas

controllers.createAdmin = async (req,res) => { //post
    sequelize.sync()
    var statusCode=200
    const { Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, InstituicaoIDInstituicao
    } = req.body;

    var descricao="Erro a criar Admin";
    try{
        var dataPessoa = await pessoas.create({
            Data_Nascimento : stringDataNasc, 
            Cidade : Cidade, 
            Codigo_Postal : Codigo_Postal,
            Email : Email,
            UNome : UNome,
            Localização : Localização,
            PNome : PNome,
            Password : Password
        })

        var id=dataPessoa.dataValues.IDPessoa;
        var dataAdmin = await admin.create({
                InstituicaoIDInstituicao: InstituicaoIDInstituicao, 
                PessoaIDPessoa : id 
        })
    }catch(e){
        statusCode=500;
        //console.log(e); 
        var msg=e.original
        
        if(dataPessoa!= undefined)
        {
            dataPessoa.destroy(); //apagar pessoa que foi criada pois o admin nao foi criado
            descricao = descricao + " Dados Pessoa Corretos"
        }
    }
    if(statusCode===500)
        res.status(statusCode).send({status:statusCode, desc:descricao,err:msg})
    else res.status(statusCode).send({status:statusCode,Pessoa: dataPessoa,Admin:dataAdmin})
    
}

controllers.createUtil_Instituicao = async (req,res) => { //post
    sequelize.sync()
    const { Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, InstituicaoIDInstituicao,Pontos,Ranking,Codigo_Empresa,ID_Util,UtilsInstituicaoIDUtil
    } = req.body;
    var statusCode = 200;
    var descricao = "Erro a criar UtilInst"
    try{
        var dataPessoa = await pessoas.create({
            Data_Nascimento : Data_Nascimento, 
            Cidade : Cidade, 
            Codigo_Postal : Codigo_Postal,
            Email : Email,
            UNome : UNome,
            Localização : Localização,
            PNome : PNome,
            Password : Password
        })
        var id=dataPessoa.dataValues.IDPessoa;  
    
        var dataUtil = await utils_instituicao.create({
            PessoaIDPessoa : id,
            Pontos : 0,
            Ranking : 0,
            Codigo_Empresa : Codigo_Empresa
        })

        var id_util=dataUtil.dataValues.ID_Util; 
        var dataUtil_pertence_Inst = await Util_pertence_Inst.create({
            InstituicaoIDInstituicao : InstituicaoIDInstituicao,
            UtilsInstituicaoIDUtil : id_util
        })
    }catch(e){
        console.log(e);
        var msgErr = e.original
        statusCode =500;
        if(dataPessoa != undefined){
            descricao =descricao + " Dados Pessoa Corretos"
            if(dataUtil != undefined)
            {
                descricao = descricao + " Dados Util Corretos"
                dataUtil.destroy();
                
            }
            dataPessoa.destroy();
        }
    }
    
    if(statusCode===500)
        res.status(statusCode).send({status: statusCode, desc:descricao,err:msgErr})
    else res.status(statusCode).send({status: statusCode, Pessoa:dataPessoa, Utilizador:dataUtil, Util_pertence_Inst:dataUtil_pertence_Inst})
}

controllers.createOutros_Util = async (req,res) => { //post
    // data
    sequelize.sync()
    const { Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, Pontos_Outro_Util , Ranking
    } = req.body;
    var statusCode = 200;
    try{
        var dataPessoa = await pessoas.create({
            Data_Nascimento : Data_Nascimento, 
            Cidade : Cidade, 
            Codigo_Postal : Codigo_Postal,
            Email : Email,
            UNome : UNome,
            Localização : Localização,
            PNome : PNome,
            Password : Password
        })

        var id=dataPessoa.dataValues.IDPessoa;
        var dataOutros_Util = await outros_util.create({
            PessoaIDPessoa : id ,
            Pontos_Outro_Util : Pontos_Outro_Util,
            Ranking : Ranking,
        })
    }catch(e){
        console.log(e);
        statusCode=500; 
        var msgErr = e.original;
        var descricao="Erro a criar Outro Util"
        if(dataPessoa != undefined){
            descricao = descricao + " Dados Pessoa Corretos"
            dataPessoa.destroy();
        }
    }
    
    if(statusCode===500)
        res.status(statusCode).send({status:statusCode, desc:descricao,err:msgErr})
    else res.status(statusCode).send({status:statusCode,Pessoa:dataPessoa,Outros_Util:dataOutros_Util})
}


module.exports= controllers;