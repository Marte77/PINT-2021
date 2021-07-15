const express = require('express');
const router = express.Router();
const comentariosController = require('../controllers/comentariosController')

router.post('/novo_comentario',comentariosController.criarComentario);
router.post('/update_comentario',comentariosController.updateComentario);
router.get('/get_comentario/:IDLocal/:IDPessoa',comentariosController.getComentario)
router.delete('/delete_comentario/:IDLocal/:IDPessoa',comentariosController.apagarComentario)
router.post('/deletecomentario/:ID',comentariosController.delete_Comentario)
router.get('/opinioes/:idInstituicao', comentariosController.get_comentarios);
module.exports=router