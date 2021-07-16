const { Router } = require('express');
const express = require('express');
const router = express.Router();
//importar os controllers
const alertasController = 
require('../controllers/alertasController');
const controllers = require('../controllers/instituicaoController');
//router.get('/list',filmeController.list);
//router.get('/get/:id',filmeController.get);
router.post('/createTipoAlerta',alertasController.createTipoAlerta);
router.post('/createAlerta',alertasController.createAlerta);
router.get('/listalertas/:idInstituicao',alertasController.getlistaalertas_byinstituicao);
router.get('/totalalertaslocais/:IDInstituicao',alertasController.getTotalAlertasPorLocalDeInstituicao);
router.get('/gettipoalerta',alertasController.gettipoalertas);
router.post('/createalertaweb',alertasController.createAlerta_web);
router.post('/deletalerta',alertasController.deletalerta);
router.get('/get_ultimo_alerta_desinfecao/:idinstituicao',alertasController.getUltimoAlertaDesinfecaoInstituicao)
//router.post('/update/:id', filmeController.update);
//router.post('/delete', filmeController.delete);
//router.get('/testdata',filmeController.testdata );
module.exports = router;