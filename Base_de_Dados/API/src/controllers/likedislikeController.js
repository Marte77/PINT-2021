
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

//aqui so serve para indicar que esta variavel existe, mas o valor dela esta a ser obtido sempre que se chama a funcao que a usa
//pois se alguem alterar o valor no ficheiro já nao tem de reiniciar o servidor
//var {nLikesAdicionaOuSubtrai} = require('./numero_Like.json'); //a cada 2 likes ou dislikes subtrai ou adiciona 1 ponto


controllers.criarLikeDislike = async (req,res) => { //post
    let statusCode = 200;
    let isUtilInst = true;
    const { Like,Dislike,IDPessoa,IDReport}= req.body
    var {nLikesAdicionaOuSubtrai} = require('./numero_Like.json'); //a cada 2 likes ou dislikes subtrai ou adiciona 1 ponto

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
            let repo =await Report.findOne({
                where:{
                    ID_Report:IDReport
                }
            })
            await repo.update({
                N_Likes:(repo.dataValues.N_Likes+1)
            })
            

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
            let repo =await Report.findOne({
                where:{
                    ID_Report:IDReport
                }
            })
            
            await repo.update({
                N_Dislikes:(repo.dataValues.N_Dislikes+1)
            })
            

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

controllers.removerLikeDislike = async (req,res) =>{//post
    const {IDPessoa, IDReport} = req.body
    var {nLikesAdicionaOuSubtrai} = require('./numero_Like.json'); //a cada 2 likes ou dislikes subtrai ou adiciona 1 ponto
    try {
        let existeInteracao = await Tabela_LikesDislikes.findOne({
            where:{
                PessoaIDPessoa:IDPessoa,
                ReportIDReport:IDReport
            }
        })
        if(existeInteracao === null)
            throw new Error('Interacao nao existe')

        //se chegou aqui existe pessoa e report
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
        //tenho o report que ficou com menos um dislike ou like, agora vou ver se altera a pontuacao da pessoa
        var foidadolikeoudislike = await Tabela_LikesDislikes.findOne({
            where:{
                PessoaIDPessoa:IDPessoa,
                ReportIDReport:IDReport
            }
        })
        //todo nao esta a remover o ponto do utilizador
        
        if(foidadolikeoudislike.dataValues.Like)
        {
            let repo = await Report.findOne({
                where:{
                    ID_Report:IDReport
                }
            })
            
            await repo.update({
                N_Likes:repo.dataValues.N_Likes-1
            })
            if(repo.dataValues.N_Likes%nLikesAdicionaOuSubtrai==0){//vai descer 1 ponto
                if(tipoReport=="OutroUtil"){
                    let pessoa = await Outros_Util.findOne({
                        where:{
                            ID_Outro_Util: reportInteragido.dataValues.OutrosUtilIDOutroUtil
                        }
                    })
                    await pessoa.update({
                        Pontos_Outro_Util: pessoa.dataValues.Pontos_Outro_Util-1
                    })
                }else{
                    let pessoa = await Utils_Instituicao.findOne({
                        where:{
                            ID_Util: reportInteragido.dataValues.UtilsInstituicaoIDUtil
                        }
                    })
                    await pessoa.update({
                        Pontos: pessoa.dataValues.Pontos-1
                    })
                }
            }
        }else{
            let repo = await Report.findOne({
                where:{
                    ID_Report:IDReport
                }
            })
            await repo.update({
                N_Dislikes:repo.dataValues.N_Dislikes-1
            })
            if(repo.dataValues.N_Dislikes%nLikesAdicionaOuSubtrai==0){//vai descer 1 ponto
                if(tipoReport=="OutroUtil"){
                    let pessoa = await Outros_Util.findOne({
                        where:{
                            ID_Outro_Util: reportInteragido.dataValues.OutrosUtilIDOutroUtil
                        }
                    })
                    await pessoa.update({
                        Pontos_Outro_Util: pessoa.dataValues.Pontos_Outro_Util+1
                    })
                }else{
                    let pessoa = await Utils_Instituicao.findOne({
                        where:{
                            ID_Util: reportInteragido.dataValues.UtilsInstituicaoIDUtil+1
                        }
                    })
                    await pessoa.update({
                        Pontos: pessoa.dataValues.Pontos+1
                    })
                }
            }
        }
        
        
        
        //vou remover a interacao
        var interacaoRemovida = await Tabela_LikesDislikes.destroy({
            where:{
                PessoaIDPessoa: IDPessoa,
                ReportIDReport:IDReport
            }
        })
    } catch (e) {
        console.log(e)
        let msgerr = e 
        res.status(500).send({desc:"Erro a apagar a interacao, é possível que nao tenha interagido com esse report",err:msgerr.toString()})
        return
    }
    
    res.status(200).send({Interacao: interacaoRemovida})

}

controllers.verificarSeInteragiu = async (req,res) =>{//post
    const {IDPessoa, IDReport} = req.body
    try {
        var existeInteracao = await Tabela_LikesDislikes.findOne({
            where:{
                PessoaIDPessoa:IDPessoa,
                ReportIDReport:IDReport
            }})
        
    } catch (e) {
        console.log(e);
        res.status(500).send({desc:"Ocorreu um erro",err:e.original, errString:e.toString()})
        return;
    }
    if(existeInteracao === null){
        res.send({existe:false});
    }else{
        let islike = existeInteracao.dataValues.Like
        res.send({existe:true, isLike:islike})
    }
}

module.exports = controllers;