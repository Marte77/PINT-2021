const localController = require('../controllers/localController');
const express = require('express');
const router = express.Router();

router.get('/listlocaisout/:idInstituicao',localController.getlocais_assocInstituicao);
router.get('/listar',localController.listarLocais);
router.get('/getLocalPorId/:id',localController.getLocalbyId)
router.post('/criar_novo_local_indoor',localController.criarLocalIndoor)
router.get('/apagar_local_indoor/:idLocal/:idLocalIndoor',localController.apagarLocalIndoor)
router.post('/criar_novo_local',localController.criarLocal)
router.get('/get_lista_locais_indoor_local/:idLocal',localController.getListaLocaisIndoor)
router.post('/percentagem_reports_locais',localController.getPercentagemDeReportsDeCadaLocal)

module.exports = router;