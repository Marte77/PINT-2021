const jwt = require('jsonwebtoken'); //módulo NPM
const config = require('./conf.js'); //ficheiro de configuração

let checkToken = (req, res, next) => {
let token = req.headers['x-access-token'] || req.headers['authorization'];
if (token.startsWith('Bearer ')) {
    token = token.slice(7, token.length); //remove a palavra ‘Bearer ’
    }


if (token) {
    var tokenverify  = jwt.sign({email:req.body.Email},config.jwtSecret,{expiresIn:'1h'})
    jwt.verify(tokenverify, config.jwtSecret, (err, decoded) => {
        if (err) {
            return res.json({
                success: false,
                message: 'O token não é válido.'
            });
        } else {
            req.decoded = decoded;
            next();
        }
    });
    } else {
        return res.json({
            success: false,
            message: 'Token indisponível.'
        });
    }
};
module.exports = { checkToken: checkToken}