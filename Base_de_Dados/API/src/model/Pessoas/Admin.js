var Sequelize = require('sequelize');
var sequelize = require('./database');
var Admin = sequelize.define('Admin', {
ID_Admin: {
type: Sequelize.INTEGER,
primaryKey: true,
autoIncrement: true,
}
},
{
timestamps: false,
});
module.exports = Admin