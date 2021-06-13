const localController = require('../controllers/localController');
const express = require('express');
const router = express.Router();

router.get('/listar',localController.listarLocais);



module.exports = router;