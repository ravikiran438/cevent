ceventApp.
  filter('fromNow', function() {
    return function(mongodbId) {
    	var timestamp = mongodbId.toString().substring(0,8);
    	var date = new Date( parseInt( timestamp, 16 ) * 1000 );
    	return moment(date.toDateString()).fromNow();
    };
  });