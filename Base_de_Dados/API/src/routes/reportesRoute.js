const express = require('express');
const router = express.Router();
const reportesController = require('../controllers/reportsController')

router.post('/novo_report_outdoor_outros',reportesController.criarReportOutdoorOutrosUtil);
router.post('/novo_report_outdoor_utilsInstituicao',reportesController.criarReportOutdoorUtilInstituicao);
router.post('/novo_report_indoor',reportesController.criarReportIndoor);

module.exports=router