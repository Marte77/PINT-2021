var pessoa = require('../model/Pessoas/Pessoas');
var report = require('../model/Reports/Report');
var likedislike = require('../model/Reports/Tabela_LikesDislikes');
var sequelize = require('../model/database');
const controllers = {}

controllers.criarLikeDislike = async (req,res) => { //post
  let statusCode = 200;
    const { Like,Dislike,IDPessoa,IDReport}= req.body
    try{var LikeDislikeNovo = await likedislike.create(
        {
            Like: Like,
            Dislike : Dislike,
            PessoaIDPessoa: IDPessoa,
            ReportIDReport: IDReport
        }
    )}catch(e){console.log(e);statusCode = 500}
    if(statusCode === 500)
        res.send({status:statusCode, desc:"Erro a criar"})
    else res.send({status:statusCode,LikeDislike:LikeDislikeNovo })
} 
module.exports = controllers;