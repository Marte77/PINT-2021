var sequelize = require('../model/database');
const controllers = {}
const List_LocalFavorito = require('../model/List_LocalFavorito')
const Lista_Favoritos = require('../model/Lista_Favoritos')

const Local = require('../model/Local')
const Instituicao = require('../model/Instituicao')

controllers.adicionarListaFavoritos = async (req,res) => { //post
    const { Descricao, IDPessoa, LocalIDLocal}= req.body
    let n_LikesDislikes = 0
    let statusCode = 200;
    let dataAgr = new Date();
    dataAgr = dataAgr.toISOString()
    var idlista;
    var mensagemErro;
    try{
        var getall = await Lista_Favoritos.findOne({where:{PessoaIDPessoa: IDPessoa}})
        idlista= getall.dataValues.ID_Lista
        //se este falhar, quer dizer que o utilizador nao tem lista de favoritos criada
        //esta lista vai ser criada dentro do catch
    }
    catch(e){
        try{
            var criarListaParaPessoa = await Lista_Favoritos.create({
                Descricao: Descricao,
                PessoaIDPessoa: IDPessoa
            }) 
            
            idlista= criarListaParaPessoa.dataValues.ID_Lista
        }
        catch(error){
            console.log(error);
            statusCode = 500
            mensagemErro ="ERRO criacao lista favorito" 
            var msgErr = error.original; 
            res.status(statusCode).send({status:statusCode, desc:mensagemErro, err:msgErr})
            
        }
    }
    if(statusCode===500)//terminar funcao caso tenha dado erro em cima
        return;
    
    try{
        var criarFavorito = await List_LocalFavorito.create({
            LocalIDLocal : LocalIDLocal,
            ListaFavoritoIDLista: idlista
        })
    }catch(e){
        console.log(e);
        statusCode = 500;
        msgErr = e.original;
        mensagemErro = "local ja existe na lista"
    }
    if(statusCode === 500)
        res.status(statusCode).send({status:statusCode, desc:mensagemErro, err:msgErr})
    else res.status(statusCode).send({status:statusCode, CriarFavorito: criarFavorito})
    
} 

//todo: remover local da lista favoritos

module.exports = controllers;