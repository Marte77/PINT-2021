const sequelize = require('./model/database')

async function meterHerancas(){
    try{
    await sequelize.sync()
    sequelize.query(`alter table public."Report_Outdoor_Outros_Utils" inherit public."Reports";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
    }catch(err){console.log(err)}

        

    try{
    await sequelize.sync()
    sequelize.query(`alter table public."Report_Indoors" inherit public."Reports";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
    }catch(err){console.log(err)}

        

    try{
    await sequelize.sync()
    sequelize.query(`alter table public."Report_Outdoor_Util_Instituicaos" inherit public."Reports";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
    }catch(err){console.log(err)}

        

    try{
    await sequelize.sync()
    sequelize.query(`alter table public."Admins" inherit public."Pessoas";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);}); 
    }catch(err){console.log(err)}

        

    try{
    await sequelize.sync()
    sequelize.query(`alter table public."Outros_Utils" inherit public."Pessoas";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});  
    }catch(err){console.log(err)}

        

    try{
    await sequelize.sync()
    sequelize.query(`alter table public."Utils_Instituicaos" inherit public."Pessoas";`)
        .then(function(res){console.log("deu gucci");console.log(res);})
        .catch(function(err){console.log("deu mal bem not good"); console.log(err);});   }
  
        
catch(err){console.log(err)}
}
meterHerancas()