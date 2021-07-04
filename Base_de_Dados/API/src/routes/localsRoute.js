const localController = require('../controllers/localController');
const express = require('express');
const router = express.Router();

router.get('/listar',localController.listarLocais);
router.get('/getLocalPorId/:id',localController.getLocalbyId)
router.post('/criar_novo_local_indoor',localController.criarLocalIndoor)
router.get('/apagar_local_indoor/:idLocal/:idLocalIndoor',localController.apagarLocalIndoor)
router.post('/criar_novo_local',localController.criarLocal)
router.get('/get_lista_locais_indoor_local/:idLocal',localController.getListaLocaisIndoor)


module.exports = router;