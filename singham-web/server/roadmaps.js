Meteor.methods({

	getRoadmapsList : function() {
		
		this.unblock();
		
		console.log("fetching list: roadmaps");

		var result = HTTP.get('http://localhost:8081/roadmaps');
		
		return result.data;
	},
	
	addRoadmap : function(listName, objectName) {
		
		this.unblock();
		
		console.log("** got it **");
		console.log("** " + listName + " **");
		console.log(objectName);
		console.log("------------------------------");
		
		var result = HTTP.post('http://localhost:8081/' + 'persons', {'data': {'name' : objectName}});
		
		console.log(result);
		
		return true;
	}
	
});