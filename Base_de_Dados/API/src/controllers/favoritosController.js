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

    try{var getall = await 
        Lista_Favoritos.findAll({where:{PessoaIDPessoa: IDPessoa}})
        idlista= getall.dataValues.ID_Lista}
    catch(e){console.log(e);//statusCode = 500
    try{var criarListaParaPessoa = await Lista_Favoritos.create({
        Descricao: Descricao,
        PessoaIDPessoa: IDPessoa
    }) 
    console.log(criarListaParaPessoa);
        idlista= criarListaParaPessoa.dataValues.ID_Lista}

    catch(e){console.log(e);statusCode = 500}
    }
//console.log(getall);
   try{var criarFavorito = await List_Instituicao.create(
        {
            InstituicaoIDInstituicao : IDInstituicao,
            ListaFavoritosIDLista: idlista
        }
    )}catch(e){console.log(e);statusCode = 500}
    if(statusCode === 500)
        res.send({status:statusCode, desc:"Erro a criar"})
    else res.send({status:statusCode, CriarFavorito: criarFavorito})
    
} 

module.exports = controllers;