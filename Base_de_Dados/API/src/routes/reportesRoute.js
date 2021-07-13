const express = require('express');
const router = express.Router();
const reportesController = require('../controllers/reportsController')

router.post('/novo_report_outdoor_outros',reportesController.criarReportOutdoorOutrosUtil);
router.post('/novo_report_outdoor_utilsInstituicao',reportesController.criarReportOutdoorUtilInstituicao);
router.post('/novo_report_indoor',reportesController.criarReportIndoor);
router.put('/get_lista_reports_local/:idlocal',reportesController.getListaReportsOutdoorLocal);//retorna os reports outdoor
router.put('/get_densidade_media_local/:idlocal',reportesController.getDensidadeMediaLocal)
router.get('/get_numero_reports_pessoa/:id',reportesController.getNumeroReportsFeitos)
router.put('/get_lista_reports_indoor_local/:idlocal',reportesController.getListaReportsIndoor)
router.get('/get_report_mais_relevante/:tipoTempo/:tempo',reportesController.getReportMaisRelevante)
router.get('/get_pessoa_from_report/:IDReport',reportesController.getPessoaFromIDReport)
router.get('/get_nreports_ultimos_dias/:nDias/:IDLocal',reportesController.getNReportsUltimosXDias)
module.exports=router