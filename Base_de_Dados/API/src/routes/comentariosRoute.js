const express = require('express');
const router = express.Router();
const comentariosController = require('../controllers/comentariosController')

router.post('/novo_comentario',comentariosController.criarComentario);

module.exports=router