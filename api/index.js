// index.js

// BASE SETUP
// =============================================================================

// call the packages we need
var express    = require('express');        // call express
var app        = express();                 // define our app using express
var bodyParser = require('body-parser');
var _ = require('lodash');
// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;        // set our port

var urlRoot = process.env.URLROOT || 'http://localhost:' + port;
var mongoose   = require('mongoose');
mongoose.connect('mongodb://archon:archon@ds035485.mongolab.com:35485/archon-nfc-hack'); // connect to our database

var Order = require('./order');

// ROUTES FOR OUR API
// =============================================================================
var router = express.Router();              // get an instance of the express Router

var statuses = ['created', 'picked', 'packed', 'shipped', 'received'];

function formatOrder(order){
  var formattedOrder = _.pick(order, ['id', 'type', 'products', 'status']);
  formattedOrder.url = urlRoot + '/o/' + formattedOrder.id;
  return formattedOrder;
}

function getNextStatus(status){
  var i = _.indexOf(statuses, status);
  if (i == statuses.length - 1) i = statuses.length - 2;
  i++;
  return statuses[i];
}


// test route to make sure everything is working (accessed at GET http://localhost:8080/api)
router.get('/', function(req, res) {
    res.json({ orders: '/o' });
});

// more routes for our API will happen here

router.route('/o')

  // create a Order (accessed at POST http://localhost:8080/o)
  .post(function(req, res) {
      var order = new Order();      // create a new instance of the Order model
      order.id = Math.floor(Math.random()*999);
      order.type = 'order';
      order.products = req.body.products;
      order.status = getNextStatus('');

      // save the Order and check for errors
      order.save(function(err) {
          if (err)
              res.send(err);

          res.json(formatOrder(order));
      });

  })

  // get all the orders (accessed at GET http://localhost:8080/o)
  .get(function(req, res) {
      Order.find(function(err, orders) {
          if (err)
              res.send(err);

          res.json(_.map(orders, function(order){ return formatOrder(order)}));
      });
  });

router.route('/o/:order_id')
  .get(function(req, res) {
      Order.find({id: req.params.order_id}, function(err, orders) {
          if (err)
              res.send(err);
          res.json(formatOrder(orders[0]));
      });
  })
  .put(function(req, res) {
      Order.find({id: req.params.order_id}, function(err, orders) {
          if (err)
              res.send(err);
          var order = orders[0];

          order.status = getNextStatus(order.status);

          // save the Order and check for errors
          order.save(function(err) {
              if (err)
                  res.send(err);

              res.json(formatOrder(order));
          });
      });
  })

  .delete(function(req, res) {
      Order.find({id: req.params.order_id}, function(err, orders) {
          if (err)
              res.send(err);
          var order = orders[0]
          order.status = statuses[0]
          // save the Order and check for errors
          order.save(function(err) {
              if (err)
                  res.send(err);

              res.json(formatOrder(order));
          });
      });
  })

// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/', router);

// START THE SERVER
// =============================================================================
app.listen(port);