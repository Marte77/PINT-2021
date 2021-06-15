const express = require('express');
const router = express.Router();
const favoritosController = require('../controllers/favoritosController')

router.post('/novo_localfavorito',favoritosController.adicionarListaFavoritos);

module.exports=router