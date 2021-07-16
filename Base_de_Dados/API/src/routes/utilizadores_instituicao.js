const express = require('express');
const router = express.Router();
//importar os controllers
const utilizadoresController = require('../controllers/utilizadores_instituicao');
const middleware = require('../middleware');

router.get('/listutilizadores/:idInstituicao', utilizadoresController.get_utilizadores)
router.post('/deleteutil',utilizadoresController.deleteutil)
router.get('/countUtilVerify/:idInstituicao', utilizadoresController.count_utilizadores_verify)
router.get('/countUtilNOVerify/:idInstituicao', utilizadoresController.count_utilizadores_NOverify)
router.get('/listutilizadoresNoVerify/:idInstituicao',utilizadoresController.getUtilNoverify)

module.exports=router