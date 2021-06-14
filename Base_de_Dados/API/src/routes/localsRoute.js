const localController = require('../controllers/localController');
const express = require('express');
const router = express.Router();

router.get('/listar',localController.listarLocais);
router.get('/getLocalPorId/:id',localController.getLocalbyId)
//TODO: fazer rota que lista os locais e os seus reportes respetivos, reports outdoor outros util e util empresa

module.exports = router;