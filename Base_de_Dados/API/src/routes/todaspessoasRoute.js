const express = require('express');
const router = express.Router();
//importar os controllers
const todaspessoasController = 
require('../controllers/todaspessoasController')

router.post('/createAdmin',todaspessoasController.createAdmin);
router.post('/createUtil_Instituicao',todaspessoasController.createUtil_Instituicao);
router.post('/createOutros_Util',todaspessoasController.createOutros_Util);
router.get('/getTop3Pessoas/:numerotoppessoas',todaspessoasController.getTop3Pessoas)

module.exports = router;