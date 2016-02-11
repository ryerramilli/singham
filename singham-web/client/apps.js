var activeListing = new function() {
	
	var key = "active-listing";
	Session.setDefault(key, "apps");
	
	this.get = function() {
		
		var activeOne = Session.get(key);	
		return activeOne;
	};
	
	this.set = function(activeOne) {
		if(activeOne)
			Session.set(key, activeOne);	
	};
	
	console.log("Instantiated activeListing");
	
}();

var currentList = new function() {
	
	function key(listName) {
		return listName + "_listing";
	}
	
	this.refresh = function(listName) {
		
		console.log("Contacting server for " + listName);
		
		Meteor.call('fetchList', activeListing.get(), function(error, result) {
			$.each(result, function(index, item) 
					{ 
						item["listName"] = listName; 
					} );
			Session.set(key(listName), result);
		});	
	}
	
	this.get = function(listName) {
		
		console.log("Loading data from session for: " + listName);
		
		return Session.get(key(listName));
	}
	
	console.log("Instantiated currentList");
}();

var newObjectDialog = new function() {
	
	var selector = "#singham #dlg_add_new_object";
	
	this.open = function() {
		$(selector).css("display", "block");
	};
	
	this.close = function() {
		$(selector).css("display", "none");
	};
	
	console.log("Instantiated newObjectDialog");
}();

Template.object_nav_bar.helpers({
	
	list : function() {
		return [{"name": "persons"}, {"name" : "apps"}];
	}

});

Template.object_nav_bar.events({
			'click' : function(event) {
				activeListing.set($(event.target).attr('data-object-name'))
			}
});

Template.listing.helpers({
	
	listName : function() {
		return activeListing.get();
	},
	
	list : function() { 
		var data = currentList.get(activeListing.get()); 
		return data;
	}

});

Template.listing.events({
	'click #btn-open-add-object' : function(event) {
		
		var desiredObject = $(event.target).attr('data-object-name');
		
		newObjectDialog.open();
		
		// Show the form now
		
	}
});
		
Template.form_add_new_object.events( {
	
	'click #btn-submit-add' : function(event) {
		
		var name = $('#add_new_object_name').val();
		
		console.log('Contact server to add a new object name = ' + name);
		
		Meteor.call('addObject', "persons", name, function(error, result) {
			newObjectDialog.close();
		});
		
	}
	
});

Tracker.autorun(function() {
	
	var activeOne = activeListing.get();
	
	console.log("Refreshing list");
	
	currentList.refresh(activeOne);
	
});