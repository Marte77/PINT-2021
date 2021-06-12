const express = require('express');
const router = express.Router();
//importar os controllers
const todaspessoasController = 
require('../controllers/todaspessoasController')
//router.get('/list',filmeController.list);
//router.get('/get/:id',filmeController.get);
router.post('/createAdmin',todaspessoasController.createAdmin);
router.post('/createUtil_Instituicao',todaspessoasController.createUtil_Instituicao);
router.post('/createOutros_Util',todaspessoasController.createOutros_Util);
//router.post('/update/:id', filmeController.update);
//router.post('/delete', filmeController.delete);
//router.get('/testdata',filmeController.testdata );
module.exports = router;