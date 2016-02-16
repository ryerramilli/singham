var backend = new Object();

backend['roadmaps'] = function(callback) {

    var data =['_Payments_', '_Events_'];
    callback(data);

};

backend['roadmap'] = function(callback) {

  var data = {
    'calendar' : [
      { "month" : "Oct '15"},
      { "month" : "Nov '15"},
      { "month" : "Dec '15"},
      { "month" : "Jan '16"},
      { "month" : "Feb '16"},
      { "month" : "Mar '16"},
      { "month" : "Apr '16"},
      { "month" : "May '16"},
      { "month" : "Jun '16"},
      { "month" : "Jul '16"},
      { "month" : "Aug '16"},
      { "month" : "Sep '16"},
      { "month" : "Oct '16"},
      { "month" : "Nov '16"},
      { "month" : "Dec '16"},
      { "month" : "Jan '17"},
      { "month" : "Feb '17"},
      { "month" : "Mar '17"},
      { "month" : "Apr '17"},
      { "month" : "May '17"},
      { "month" : "Jun '17"},
      { "month" : "Jul '17"},
      { "month" : "Aug '17"},
      { "month" : "Sep '17"},
      { "month" : "Oct '17"},
      { "month" : "Nov '17"},
      { "month" : "Dec '17"},
      { "month" : "Jan '18"},
      { "month" : "Feb '18"},
      { "month" : "Mar '18"}
    ],
    'swimlanes' : [
      {
        'internalId' : 54,
        'role' : 'Accountable',
        'opportunity' : {
          'name' : 'Hatuelook',
          'timeOffset' : 160,
          'timePeriod' : 400
        }
      } ,
      {
        'role' : 'Responsible',
        'opportunity' : {
          'internalId' : 96,
          'name' : 'Hatuelook',
          'timeOffset' : 240,
          'timePeriod' : 200,
          'xroadmaps' : [
            {
              'name' : 'Mergers'
            },
            {
              'name' : 'Film Fare Awards'
            },
          ]
        }
      }
    ]
  };

  data.calendar.forEach(function(cell, index) {
    cell["timeOffset"] = 80 * index;
    cell["tiling"] = index%2 == 0 ? "even" : "odd";
  });

  callback(data);

};

module.exports = backend;
