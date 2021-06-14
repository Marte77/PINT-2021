const express = require('express');
const router = express.Router();
//importar os controllers
const likedislikeController = 
require('../controllers/likedislikeController')

router.post('/novo_likedislike',likedislikeController.criarLikeDislike);
module.exports = router;