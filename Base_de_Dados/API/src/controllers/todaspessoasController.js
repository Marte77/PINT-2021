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
const Admin = require('../model/Pessoas/Admin');
//const config = require('../config');
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
        var email = await Pessoas.findOne({
            where:{Email:Email}
        })
        if(email !== null)
            throw new Error('O email inserido ja existe')
        var dataPessoa = await pessoas.create({
            Data_Nascimento : Data_Nascimento, 
            Cidade : Cidade, 
            Codigo_Postal : Codigo_Postal,
            Email : Email,
            UNome : UNome,
            Localização : Localização,
            PNome : PNome,
            Password : Password,
            Foto_De_Perfil: URLImagem,
            createdAt:new Date(),
            updatedAt:new Date() 
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
        if(e.toString() === 'Error: O email inserido ja existe')
            res.status(statusCode).send({status:statusCode,desc:"erro a criar", err:e.toString()})

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
        var email = await Pessoas.findOne({
            where:{Email:Email}
        })
        if(email !== null)
            throw new Error('O email inserido ja existe')
        var dataPessoa = await pessoas.create({
            Data_Nascimento : Data_Nascimento, 
            Cidade : Cidade, 
            Codigo_Postal : Codigo_Postal,
            Email : Email,
            UNome : UNome,
            Localização : Localização,
            PNome : PNome,
            Password : Password,
            Foto_De_Perfil: URLImagem,
            createdAt:new Date(),
            updatedAt:new Date() 
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
        if(e.toString() === 'Error: O email inserido ja existe')
            res.status(statusCode).send({status:statusCode,desc:"erro a criar", err:e.toString()})
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
        var email = await Pessoas.findOne({
            where:{Email:Email}
        })
        if(email !== null)
            throw new Error('O email inserido ja existe')
        var dataPessoa = await pessoas.create({
            Data_Nascimento : Data_Nascimento, 
            Cidade : Cidade, 
            Codigo_Postal : Codigo_Postal,
            Email : Email,
            UNome : UNome,
            Localização : Localização,
            PNome : PNome,
            Password : Password,
            Foto_De_Perfil: URLImagem,
            createdAt:new Date(),
            updatedAt:new Date() 
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
        if(e.toString() === 'Error: O email inserido ja existe')
            res.status(statusCode).send({status:statusCode,desc:"erro a criar", err:e.toString()})
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
    await atualizarRanking()
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
    await atualizarRanking()
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
    if (req.body.Email && req.body.Password) {
        var emailutil = req.body.Email;
        var passwordutil = req.body.Password;
    }
    var tipo="";
    try{
        var pessoalogin = await pessoas.findOne({
            where:{
                Email:emailutil
            }
        })

        if (passwordutil == null){
            res.status(403).json({
                success: false,
                message: 'Campos em Branco'
            })
        } else {
            if(emailutil && passwordutil && pessoalogin ){
                const isMatch = await bcrypt.compare(passwordutil, pessoalogin.dataValues.Password);
                if (!isMatch){
                    throw new Error('Password Incorreta')
                }else{
                    var token = jwt.sign({Email:emailutil},conf.jwtSecret,{expiresIn:'1h'})
                }
            }
        }
        if(pessoalogin === null)
            throw new Error('Email nao existe')
        /*if(pessoalogin.dataValues.Password !== Password)
            throw new Error('Password Incorreta')*/
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
    
        res.status(200).send({login:true,PessoaLogin:tipoUtil,token:token,TipoPessoa:tipo})
    
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
    try{
        var arrayNovo = new Array()
        var adminSemVerificao = await Admin.findAll({
            where:{
                Verificado:false
            },
            include:{
                model:pessoas,
                attributes:{
                    exclude:['Password']
                },required:false
            }
        })
        for(let pessoa of adminSemVerificao)
            arrayNovo.push(pessoa)
        var utilinstSemvERIF  = await Utils_Instituicao.findAll({
            where:{
                Verificado:false
            },include:{
                model:pessoas,
                attributes:{
                    exclude:['Password']
                },required:false
            }
        })
        for(let pessoa of utilinstSemvERIF)
            arrayNovo.push(pessoa)
    }catch(e){
        console.log(e)
        res.status(500).send({desc:"erro a selecionar", err:e.original})
    }
    res.send({Utils:arrayNovo})
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

async function atualizarRanking(){
    console.log("atualizar ranking")
    let querystring = 'do '+
    '$do$ '+
        'declare cursorranking cursor for '+
        'with cte_ranking as('+
            'select "PessoaIDPessoa" as idp,rank() over (ORDER by "Pontuacao" desc), "tipo" from '+
            '(select "PessoaIDPessoa","Pontos" as "Pontuacao",'+"'UI'"+' as "tipo" from "Utils_Instituicaos" '+
            'union select "PessoaIDPessoa","Pontos_Outro_Util" as "Pontuacao",'+"'IO' "+ 
            'as "tipo" from "Outros_Utils") as tabelaranking) '+
        'select * from cte_ranking;'+
        'begin '+
            //--open cursorranking;
            ' for linha in cursorranking loop '+
                ' if linha.tipo = '+"'IO'"+' then '+//--é outro util
                    ' update "Outros_Utils" set "Ranking" = linha.rank where "PessoaIDPessoa" = linha.idp; '+
                ' else '+
                    'update "Utils_Instituicaos" set "Ranking" = linha.rank where "PessoaIDPessoa" = linha.idp; '+
                'end if; '+
            'end loop; '+
            //--close cursorranking;
            //--deallocate cursorranking;
        'end '+
    '$do$; '
    
    await sequelize.query(querystring)
}
module.exports= controllers;