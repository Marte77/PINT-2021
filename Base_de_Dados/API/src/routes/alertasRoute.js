const express = require('express');
const router = express.Router();
//importar os controllers
const alertasController = 
require('../controllers/alertasController')
//router.get('/list',filmeController.list);
//router.get('/get/:id',filmeController.get);
router.post('/createTipoAlerta',alertasController.createTipoAlerta);
router.post('/createAlerta',alertasController.createAlerta);
router.get('/listalertas/:idInstituicao',alertasController.getlistaalertas_byinstituicao);
router.get('/totalalertaslocais/:IDInstituicao',alertasController.getTotalAlertasPorLocalDeInstituicao);

//router.post('/update/:id', filmeController.update);
//router.post('/delete', filmeController.delete);
//router.get('/testdata',filmeController.testdata );
module.exports = router;