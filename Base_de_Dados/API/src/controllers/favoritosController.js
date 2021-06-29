var sequelize = require('../model/database');
const controllers = {}
const List_LocalFavorito = require('../model/List_LocalFavorito')
const Lista_Favoritos = require('../model/Lista_Favoritos')


const Instituicao = require('../model/Instituicao');
const Local = require('../model/Local');

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
            //LocalIDLocal : LocalIDLocal,
            //ListaFavoritoIDLista: idlista
            ID_Local:LocalIDLocal,
            ID_Lista:idlista
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


controllers.removerLocalDaListaFavoritos = async(req,res) =>{
    const IDLocal = req.params.IDLocal
    const IDPessoa = req.params.IDPessoa

    try {//so considera que existe 1 lista para cada utilizador apesar de dar para terem mais do que uma
        var listapessoa = await Lista_Favoritos.findOne({
            where:{
                PessoaIDPessoa:IDPessoa
            }
        })
        var localnalista = await List_LocalFavorito.findOne({
            where:{
                //LocalIDLocal: IDLocal,
                //ListaFavoritoIDLista: listapessoa.dataValues.ID_Lista
                ID_Local:IDLocal,
                ID_Lista:listapessoa.dataValues.ID_Lista
            }
        })
        if(localnalista === null)
            throw new Error('Local nao esta na lista')
        var localdestruido = await localnalista.destroy()
    } catch (e) {
        console.log(e.toString())
        if(e.toString() === 'Error: Local nao esta na lista')
            {res.send({sucesso:false,desc:'local nao esta na lista'})}
        else res.status(500).send({desc:'Erro a apagar', err:e.original})
    }
    res.send({sucesso:true,desc:'local removido da lista com sucesso'})
    // nao e necessario fazer nenhum return atras 
    //pq se ele fizer algum res.send os resstantes ja nao serao executados
}

controllers.verificarSeLocalEstaNaLista= async(req,res) =>{
    const IDLocal = req.params.IDLocal
    const IDPessoa = req.params.IDPessoa
    var existeLocal = false
    try {//so considera que existe 1 lista para cada utilizador apesar de dar para terem mais do que uma
        var listafavoritos = await Lista_Favoritos.findOne({
            where:{
                PessoaIDPessoa:IDPessoa
            }
        })
        if(listafavoritos !== null){
            var localnalista = await List_LocalFavorito.findOne({
                where:{
                    //LocalIDLocal:IDLocal,
                    //ListaFavoritoIDLista: listafavoritos.dataValues.ID_Lista
                    ID_Local:IDLocal,
                    ID_Lista:listapessoa.dataValues.ID_Lista    
                }
            })
            if(localnalista !== null)
                existeLocal = true
        }
    }catch(e){
        console.log(e)
        res.status(500).send({desc:"Erro a pesquisar", err:e.original})
    }
    res.send({existe:existeLocal})
}

controllers.getListaComLocaisFavoritados = async(req,res)=>{
    const {IDPessoa} = req.params
    try{
        var listaPessoa = await Lista_Favoritos.findOne({
            where:{
                PessoaIDPessoa:IDPessoa
            }
        })
        if(listaPessoa === null)
            throw new Error('Lista nao existe')
        var listaLocais = await List_LocalFavorito.findAll({
            where:{
                ListaFavoritoIDLista: listaPessoa.dataValues.ID_Lista
            }            
        })        
        console.log(Object.getOwnPropertyNames(List_LocalFavorito))
        console.log(List_LocalFavorito.associations)
        console.log(Local.associations)
        console.log(Local.primaryKeys)
        console.log(Lista_Favoritos.primaryKeys)
    }catch(e){
        console.log(e)
        if(e.toString() === 'Error: Lista nao existe')
            res.send({desc:"Lista nao existe", sucesso: false})
        else res.status(500).send({desc:"erro a pesquisar", err:e.original})
    }
    res.send({sucesso:true, lista:listaLocais})
}

module.exports = controllers;