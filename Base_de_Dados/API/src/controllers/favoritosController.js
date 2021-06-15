var sequelize = require('../model/database');
const controllers = {}
const List_Instituicao = require('../model/List_Instituicao')
const Lista_Favoritos = require('../model/Lista_Favoritos')

const Local = require('../model/Local')
const Instituicao = require('../model/Instituicao')

controllers.adicionarListaFavoritos = async (req,res) => { //post
    const { Descricao, IDPessoa, IDInstituicao}= req.body
    let n_LikesDislikes = 0
    let statusCode = 200;
    let dataAgr = new Date()
    dataAgr = dataAgr.toISOString()
    var idlista;
    var mensagemErro;
    try{
        var getall = await 
        Lista_Favoritos.findOne({where:{PessoaIDPessoa: IDPessoa}})
        console.log("getall");
        console.log(getall);
        
        idlista= getall.dataValues.ID_Lista
    }
    catch(e){
        try{
            var criarListaParaPessoa = await Lista_Favoritos.create({
                Descricao: Descricao,
                PessoaIDPessoa: IDPessoa
            }) 
            
            idlista= criarListaParaPessoa.dataValues.ID_Lista
        }
        catch(err){
            console.log(err);
            statusCode = 500
            mensagemErro ="ERRO criacao lista favorito" 
        }
    }
        
    try{
        var criarFavorito = await List_Instituicao.create({
            InstituicaoIDInstituicao : IDInstituicao,
            ListaFavoritoIDLista: idlista
        })
    }catch(e){
        console.log(e);
        statusCode = 500;
        mensagemErro = "instituicao ja existe na lista"
    }
    if(statusCode === 500)
        res.send({status:statusCode, desc:mensagemErro})
    else res.send({status:statusCode, CriarFavorito: criarFavorito})
    
} 

//todo: remover lista favoritos

module.exports = controllers;