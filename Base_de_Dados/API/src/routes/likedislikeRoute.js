const express = require('express');
const router = express.Router();
//importar os controllers
const likedislikeController = 
require('../controllers/likedislikeController')

router.post('/novo_likedislike',likedislikeController.criarLikeDislike);
router.post('/remover_likedislike',likedislikeController.removerLikeDislike);
router.post('/verificar_se_existe_interacao',likedislikeController.verificarSeInteragiu);

module.exports = router;