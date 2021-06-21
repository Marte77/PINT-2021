const express = require('express');
const router = express.Router();
const reportesController = require('../controllers/reportsController')

router.post('/novo_report_outdoor_outros',reportesController.criarReportOutdoorOutrosUtil);
router.post('/novo_report_outdoor_utilsInstituicao',reportesController.criarReportOutdoorUtilInstituicao);
router.post('/novo_report_indoor',reportesController.criarReportIndoor);
router.put('/get_lista_reports_local/:idlocal',reportesController.getListaReportsOutdoorLocal);
router.put('/get_densidade_media_local/:idlocal',reportesController.getDensidadeMediaLocal)
module.exports=router