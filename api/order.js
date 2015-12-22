var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var OrderSchema   = new Schema({
    id: Number,
    type: String,
    products: Array,
    status: String
});

module.exports = mongoose.model('Order', OrderSchema);