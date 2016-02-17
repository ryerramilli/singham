var express = require('express');
var backend = require('../backend/singham.js');

var router = express.Router();

router.get('/roadmaps', function(req, res, next) {

  backend.roadmaps(function(list) {
    res.render('roadmaps', { title: 'List of roadmaps - from backend',
      roadmaps:  list});
  });

});

router.get('/roadmaps/:roadmap', function(req, res, next) {

  backend.roadmap(function(details) {

    res.render('roadmap', { title: req.params.roadmap,
      details : details
    });

  });

});

router.get('/opportunites/some_opportunity/capability_heat_map',function(req, res, next) {
  res.send('Sure, I will send you the capability heat map, when I figure out how');
});

module.exports = router;
