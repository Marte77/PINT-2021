const localController = require('../controllers/localController');
const express = require('express');
const router = express.Router();

router.get('/listar',localController.listarLocais);
router.get('/getLocalPorId/:id',localController.getLocalbyId)
//TODO: fazer rota que lista os locais e os seus reportes respetivos, reports outdoor outros util e util empresa

//todo: meter o status como aqui em baixo nas outras 
//router.get('/boas',(req,res)=>{res.status(201).send({status:301})})
module.exports = router;