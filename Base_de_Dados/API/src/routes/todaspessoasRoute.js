const express = require('express');
const router = express.Router();
//importar os controllers
const todaspessoasController = 
require('../controllers/todaspessoasController');
const middleware = require('../middleware');


router.post('/Login', middleware.checkToken, todaspessoasController.login);

router.post('/createAdmin',todaspessoasController.createAdmin);
router.post('/createUtil_Instituicao',todaspessoasController.createUtil_Instituicao);
router.post('/createOutros_Util',todaspessoasController.createOutros_Util);
router.get('/getTopXPessoas/:numerotoppessoas',todaspessoasController.getTop3Pessoas)
router.get('/getInfoPessoa/:idpessoa',todaspessoasController.getInfoPessoa);
//router.post('/login',todaspessoasController.login)
router.get('/ver_se_util_esta_verificado/:id',todaspessoasController.isUtilizadorInstVerificado);
router.get('/getUtilsEspera',todaspessoasController.List_Utils_Espera); // para o home frontend
module.exports = router;