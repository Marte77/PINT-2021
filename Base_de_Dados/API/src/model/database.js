var Sequelize = require('sequelize');
const sequelize = new Sequelize(
'dc46lifec8n9j9',
'pjellcdlhqirbb',
'd80db698597148f935fa7622540e2bb54db21434bd6106712fd3ec767ea0cc03', 
{
host: 'ec2-54-154-101-45.eu-west-1.compute.amazonaws.com',
port: '5432',
dialect: 'postgres',
dialectOptions:{
        ssl:{          
            rejectUnauthorized: false        
        }
    },
   // logging:false
}
);
sequelize.sync()
module.exports = sequelize;