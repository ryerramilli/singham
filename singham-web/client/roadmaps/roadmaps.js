var currentList = new function() {
	
	function key() {
		return "raodmaps_listing";
	}
	
	this.refresh = function() {
		
		console.log("Contacting server for roadmap");
		
		Meteor.call('getRoadmapsList', function(error, result) {
			$.each(result, function(index, item) 
					{ 
						item["listName"] = "roadmaps"; 
					} );
			Session.set(key(), result);
		});	
	}
	
	this.get = function() {
		
		console.log("Loading data from session for: roadmaps");
		
		return Session.get(key());
	}
	
	console.log("Instantiated currentList");
}();

Template.roadmaps_listing.helpers({
	
	list : function() { 
		var data = currentList.get(); 
		return data;
	}

});
		
Tracker.autorun(function() {
		
	console.log("Refreshing list");
	currentList.refresh();
	
});