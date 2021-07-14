const express = require('express');
const router = express.Router();
//importar os controllers
const utilizadoresController = require('../controllers/utilizadores_instituicao');
const middleware = require('../middleware');

router.get('/listutilizadores/:idInstituicao', utilizadoresController.get_utilizadores)
module.exports=router