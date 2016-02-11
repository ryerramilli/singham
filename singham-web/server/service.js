Meteor.methods({

	fetchList : function(listName) {
		
		this.unblock();
		
		console.log("fetching list: " + listName);

		var result = HTTP.get('http://localhost:8081/' + listName);

		//console.log(result);
		
		return result.data;
	},
	
	addObject : function(listName, objectName) {
		
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