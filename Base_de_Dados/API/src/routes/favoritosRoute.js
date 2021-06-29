const express = require('express');
const router = express.Router();
const favoritosController = require('../controllers/favoritosController')

router.post('/novo_localfavorito',favoritosController.adicionarListaFavoritos);
router.get("/remover_local_da_lista/:IDPessoa/:IDLocal",favoritosController.removerLocalDaListaFavoritos);
router.get("/verificar_se_local_esta_favoritado/:IDPessoa/:IDLocal",favoritosController.verificarSeLocalEstaNaLista);
router.get("/get_lista_locais_favoritos/:IDPessoa",favoritosController.getListaComLocaisFavoritados);
module.exports=router