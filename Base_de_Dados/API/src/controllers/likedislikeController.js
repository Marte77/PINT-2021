
var sequelize = require('../model/database');
const Outros_Util = require('../model/Pessoas/Outros_Util');
const Utils_Instituicao = require('../model/Pessoas/Utils_Instituicao');
const Report = require('../model/Reports/Report');
const Report_Outdoor_Outros_Util = require('../model/Reports/Report_Outdoor_Outros_Util');
const Pessoas = require('../model/Pessoas/Pessoas');
const Report_Indoor = require('../model/Reports/Report_Indoor');
const Report_Outdoor_Util_Instituicao = require('../model/Reports/Report_Outdoor_Util_Instituicao');
const Tabela_LikesDislikes = require('../model/Reports/Tabela_LikesDislikes');

const controllers = {}

const nLikesAdicionaOuSubtrai = 2; //a cada 2 likes ou dislikes subtrai ou adiciona 1 ponto

controllers.criarLikeDislike = async (req,res) => { //post
    let statusCode = 200;
    let isUtilInst = true;
    const { Like,Dislike,IDPessoa,IDReport}= req.body
    if(Like == Dislike)
    {
        res.status(500).send({err:"Nao pode dar like e dislike ao mesmo tempo"})
        return;
    }

    let pontuacao = 0;
    try{
        var LikeDislikeNovo = await Tabela_LikesDislikes.create({
            Like: Like,
            Dislike : Dislike,
            PessoaIDPessoa: IDPessoa,
            ReportIDReport: IDReport
        })

        let tipoReport
        var reportInteragido = await Report_Indoor.findOne({
            where:{
                ReportIDReport: IDReport
            }
        })
        if(reportInteragido === null){
            reportInteragido = await Report_Outdoor_Util_Instituicao.findOne({
                where:{
                    ReportIDReport: IDReport
                }
            })
            if(reportInteragido === null){
                {
                    reportInteragido = await Report_Outdoor_Outros_Util.findOne({
                    where:{
                        ReportIDReport: IDReport
                    }
                    })
                    tipoReport="OutroUtil"
                }
            }else{
                tipoReport="OutdoorUtilInst"
            }
        }else{
            tipoReport = "Indoor"
        }

        var PessoaEspecifica
        if(tipoReport === "OutroUtil"){
            PessoaEspecifica = await Outros_Util.findOne({
                where:{
                    ID_Outro_Util:reportInteragido.dataValues.OutrosUtilIDOutroUtil
                }
            })
        }else{
            PessoaEspecifica = await Utils_Instituicao.findOne({
                where:{
                    ID_Util:reportInteragido.dataValues.UtilsInstituicaoIDUtil
                }
            })
        }
        //tenho a pessoa, agora verificar se ira haver alteracoes à pontuacao
        //, se for dado um like e o numero de likes passa a para adiciona-se um ponto, caso contrario subtrai-se
        var numeroInteracoes
        if(Like == 1)
        {
            numeroInteracoes = await Tabela_LikesDislikes.findAll({
                attributes:['Like'],
                group:['PessoaIDPessoa','ReportIDReport'],
                where:{
                    Like:true
                }
            })
            if(numeroInteracoes.length%nLikesAdicionaOuSubtrai==0)
            {//update da pontuacao
                if(tipoReport==="OutroUtil"){
                    await PessoaEspecifica.update({
                        Pontos_Outro_Util:(PessoaEspecifica.dataValues.Pontos_Outro_Util+1)
                    })
                    pontuacao = PessoaEspecifica.dataValues.Pontos_Outro_Util
                }else{
                    await PessoaEspecifica.update({
                        Pontos:(PessoaEspecifica.dataValues.Pontos+1)
                    })
                    pontuacao = PessoaEspecifica.dataValues.Pontos
                }
            }
        }else{
            numeroInteracoes = await Tabela_LikesDislikes.findAll({
                attributes:['Dislike'],
                group:['PessoaIDPessoa','ReportIDReport'],
                where:{
                    Dislike:true
                }
            })
            if(numeroInteracoes.length%nLikesAdicionaOuSubtrai==0)
            {//update da pontuacao
                if(tipoReport==="OutroUtil"){
                    await PessoaEspecifica.update({
                        Pontos_Outro_Util:(PessoaEspecifica.dataValues.Pontos_Outro_Util-1)
                    })
                    pontuacao = PessoaEspecifica.dataValues.Pontos_Outro_Util
                }else{
                    await PessoaEspecifica.update({
                        Pontos:(PessoaEspecifica.dataValues.Pontos-1)
                    })
                    pontuacao = PessoaEspecifica.dataValues.Pontos
                }
            }
        }
    }catch(e){
        console.log(e);
        var msgErr = e.original;
        statusCode = 500
    }
    if(statusCode === 500)
        res.status(statusCode).send({status:statusCode, desc:"Erro a criar", err:msgErr})
    else res.status(statusCode).send({status:statusCode,LikeDislike:LikeDislikeNovo,PontuacaoNova:pontuacao })
} 
module.exports = controllers;