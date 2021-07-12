const express = require('express');
const router = express.Router();
//importar os controllers
const instituicaoController = require('../controllers/instituicaoController') 
router.post('/createInstituicao',instituicaoController.createInstituicao);
router.get('/get_instituicoes/:idInstituicao',instituicaoController.getInstituicao);
router.post('/updateinstituicao/:idInstituicao',instituicaoController.updateinstituicao);
module.exports = router;