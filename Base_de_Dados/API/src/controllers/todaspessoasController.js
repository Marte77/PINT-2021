var instituicao = require('../model/Instituicao');
var pessoas = require('../model/Pessoas/Pessoas');
var utils_instituicao = require('../model/Pessoas/Utils_Instituicao');
var Util_pertence_Inst = require('../model/Util_pertence_Inst');
var outros_util = require('../model/Pessoas/Outros_Util');
var admin = require('../model/Pessoas/Admin');
var sequelize = require('../model/database');
const controllers = {}
const { Op, json } = require('sequelize');
const Pessoas = require('../model/Pessoas/Pessoas');
const Utils_Instituicao = require('../model/Pessoas/Utils_Instituicao');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const config = require('../config');
const conf = require('../conf');

//nas criacoes de pessoas, se algum der erro, ele apaga os anteriores da BD
//, garantindo que nao existam pessoas repetidas

controllers.createAdmin = async (req,res) => { //post
    sequelize.sync()
    var statusCode=200
    const {URLImagem, Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, InstituicaoIDInstituicao
    } = req.body;

    var descricao="Erro a criar Admin";
    try{
        var dataPessoa = await pessoas.create({
            Data_Nascimento : Data_Nascimento, 
            Cidade : Cidade, 
            Codigo_Postal : Codigo_Postal,
            Email : Email,
            UNome : UNome,
            Localização : Localização,
            PNome : PNome,
            Password : Password,
            Foto_De_Perfil: URLImagem
        })

        var id=dataPessoa.dataValues.IDPessoa;

        var dataAdmin = await admin.create({
                InstituicaoIDInstituicao: InstituicaoIDInstituicao, 
                PessoaIDPessoa : id ,
                Verificado:0
        })
    }catch(e){
        statusCode=500;
        console.log(e); 
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
    const {URLImagem, Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, InstituicaoIDInstituicao,//Pontos,Ranking,
        Codigo_Empresa,//ID_Util,UtilsInstituicaoIDUtil
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
            Password : Password,
            Foto_De_Perfil: URLImagem
        })
        var id=dataPessoa.dataValues.IDPessoa;  
    
        var dataUtil = await utils_instituicao.create({
            PessoaIDPessoa : id,
            Pontos : 0,
            Ranking : 0,
            Codigo_Empresa : -1, // nao precisamos, mas nao retiramos pq e demaisado tarde
            Verificado:0
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
    const { URLImagem,Data_Nascimento, Cidade, Codigo_Postal, Email, UNome, Localização, PNome, Password, //Pontos_Outro_Util , Ranking
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
            Password : Password,
            Foto_De_Perfil: URLImagem
        })

        var id=dataPessoa.dataValues.IDPessoa;
        var dataOutros_Util = await outros_util.create({
            PessoaIDPessoa : id ,
            Pontos_Outro_Util : 0,
            Ranking : 0,
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

controllers.getTop3Pessoas=async (req,res) => { //get
    var {numerotoppessoas} = req.params
    try{
        var top3outrosUtil = await outros_util.findAll({
            limit:numerotoppessoas,
            order:[/*'Ranking',*/
                ['Pontos_Outro_Util','DESC']
            ],
            //where:{
            //    Ranking:{ 
            //        [Op.ne]:0
            //    }
            //},
            include:{
                model:pessoas,
                attributes:{
                    exclude:['Password']
                },required:false
            }
        })

        var top3UtilInst = await utils_instituicao.findAll({
            limit:numerotoppessoas,
            order:[/*'Ranking',*/
                ['Pontos','DESC']
            ],
            //where:{
            //    Ranking:{
            //        [Op.ne]:0
            //    }
            //},
            include:{
                model:pessoas,
                attributes:{
                    exclude:['Password']
                },required:false
            }
            
            

        })

    }catch(e){
        console.log(e)
        res.status(500).send({desc:"Erro a fazer a selecao das pessoas",err:e.original})
    }

    var arr = new Array()
    for(let pessoa3 of top3UtilInst)
        arr.push(pessoa3)
    for(let pessoa3 of top3outrosUtil)
        arr.push(pessoa3)
    arr = organizarPessoasPorPontos(arr)
    
    var arrayresposta = new Array(), j = 0
    for(let i = arr.length-1; i>=0;i--){
        j++;
        arrayresposta.push(arr[i])
        if(j ==numerotoppessoas)
            break;
    }
    
    res.status(200).send(arrayresposta)
}

controllers.getInfoPessoa=async (req,res) => {//get
    var {idpessoa}= req.params
    try{
        var infopessoa = await outros_util.findOne({
            where:{
                PessoaIDPessoa:idpessoa
            },include:{
                model:pessoas,
                attributes:{
                    exclude:['Password']
                },required:false
            }
        })
        if(infopessoa === null)
            infopessoa = await utils_instituicao.findOne({
                where:{
                    PessoaIDPessoa:idpessoa
                },include:{
                    model:pessoas,
                    attributes:{
                        exclude:['Password']
                    },required:false
                }
            })
    }catch(e){
        console.log(e)
        res.status(500).send({desc:"Erro a pesquisar",err:e.original})
    }
    if(infopessoa === null)
        res.status(500).send({err:"Pessoa não existe ou é admin"})
    else res.status(200).send(infopessoa)
}

controllers.login = async (req,res) => {//post
    if (req.body.email && req.body.password) {
        var Email = req.body.email;
        var Password = req.body.password;
     }
    var tipo="";
    try{
        var pessoalogin = await pessoas.findOne({
            where:{
                Email:Email
            }
        }).then (function(data){ 
            return data;
        }).catch (error=>{
            console.log("Erro: "+error);
            return error;
        })
        if (Password == null){
            res.status(403),json({
                success: false,
                message: 'Campos em Branco'
            })
        } else {
            if(Email && Password && pessoalogin ){
                const isMatch = bcrypt.compareSync(Password, pessoalogin.Password);
                if (req.body.email == pessoalogin.email && isMatch){
                    res.json({success: true, message: 'Autenticação realizada com sucesso!’, token: token'});
                }
            }
        }
        if(pessoalogin === null)
            throw new Error('Email nao existe')
        if(pessoalogin.dataValues.Password !== Password)
            throw new Error('Password Incorreta')
        var tipoUtil = await utils_instituicao.findOne({
            where:{
                PessoaIDPessoa: pessoalogin.dataValues.IDPessoa
            },
            include:[pessoas]
        })
        if(tipoUtil === null) //ou é admin ou é outro util
        {
            tipoUtil = await outros_util.findOne({
                where:{
                    PessoaIDPessoa: pessoalogin.dataValues.IDPessoa
                },
                include:[pessoas]
            })
            if(tipoUtil === null) //é admin
            {
                    tipoUtil = await admin.findOne({
                    where:{
                        PessoaIDPessoa: pessoalogin.dataValues.IDPessoa
                    },
                    include:[pessoas]
                })
                tipo="Admin"
            }else tipo="Outros_Util"
        }else{
            tipo="Util_Instituicao"
        }
    }catch(e){
        console.log(e)
        res.status(500).send({ err:e.message})
        return;
    }
    
    res.status(200).send({desc:"Login com sucesso",TipoPessoa:tipo, login:true,PessoaLogin:tipoUtil})
    //TipoPessoa retorna Util_Instituicao ou Outros_Util ou Admin
}


controllers.isUtilizadorInstVerificado = async(req,res)=>{//get
    const {id} = req.params
    try {
        //retorna null se nao existir
        var isVerificado = await Utils_Instituicao.findOne({
            where:{
                ID_Util:id
            },
            attributes:['Verificado']
        })
    } catch (e) {
        console.log(e)
    }
    if(isVerificado === null)
        res.send({Estado:"Utilizador nao e util instituicao ou nao existe"})
    else res.send({Estado:isVerificado})
}

controllers.List_Utils_Espera= async (req, res) => { // para o home frontend
const data = await Admin.findAll({
 where:{
            Verificado:false
            },include:{
                model:pessoas,
                attributes:{
                    exclude:['Password']
                },required:false
            }

})
.then(function(data){
return data;
})
.catch(error => {
return error;
}); 
res.json({success : true, data : data});
}


function organizarPessoasPorPontos(arraypessoas)
{
    for(let i = 0; i<arraypessoas.length-1;i++){
        for(let j = 0; j<arraypessoas.length-i-1;j++)
        {
            let pontosJ, pontosJ1
            if(arraypessoas[j].dataValues.hasOwnProperty('ID_Outro_Util'))
                pontosJ = arraypessoas[j].dataValues.Pontos_Outro_Util
            else pontosJ= arraypessoas[j].dataValues.Pontos
            if(arraypessoas[j+1].dataValues.hasOwnProperty('ID_Outro_Util'))
                pontosJ1 = arraypessoas[j+1].dataValues.Pontos_Outro_Util
            else pontosJ1= arraypessoas[j+1].dataValues.Pontos
            if(pontosJ>pontosJ1)
            {
                let temp = arraypessoas[j]
                arraypessoas[j] = arraypessoas[j+1]
                arraypessoas[j+1] = temp
            }
        }
    }

    

    return arraypessoas
}


module.exports= controllers;