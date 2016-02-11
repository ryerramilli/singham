var currentList = new function() {
	
	var sessionKey = "roadmap_details";
	
	Session.setDefault(sessionKey, { "calendar" : [], "swimlanes" : []});
	
	this.refresh = function() {
		
		console.log("Contacting server for roadmap details");
		
		if(Router.current()) {
			Meteor.call('getRoadmap', Router.current().params._roadmap_name, function(error, result) {
				Session.set(sessionKey, result);
			});	
		}
		else {
			console.log("Router not yet set up");
		}
		
	}
	
	this.get = function() {
		
		console.log("Loading data from session for roadmap details");
		
		return Session.get(sessionKey);
	}
	
	console.log("Instantiated currentList");
	
}();

var _swimlane = new function() {
	this.elem_id = function() {
		return "opp_" +  Template.currentData().entry.opportunity.internalId;
	}
}();

var opportunity_fly_out = new function() {
	
	this.detail_elem_id = function() {
		return "opp_details_" + Template.currentData().entry.opportunity.internalId;
	}
	
}();

Template.roadmap.onCreated(function() {console.log("==> roamdap.onCreated <==");});
Template.roadmap.onRendered(function() {console.log("==> roamdap.onRendered <==");});
Template.roadmap.onDestroyed(function() {console.log("==> roamdap.onDestroyed <==");});

Template.roadmap.helpers({
	
	title : function() {
		return Router.current().params._roadmap_name;
	},
	
	calendar : function() {
		return currentList.get().calendar;
	},
	
	swimlanes : function() { 
		
		console.log("binding details to template");
		return currentList.get().swimlanes;
		
	}

});

Template.swimlane.helpers({
	
	opp_elem_id : function() {
		return _swimlane.elem_id();
	}
	
});

Template.swimlane.onCreated(function() {console.log("==> swimlane.onCreated <==");});
Template.swimlane.onRendered(function() {console.log("==> swimlane.onRendered <==");})
Template.swimlane.onDestroyed(function() {console.log("==> swimlane.onDestroyed <==");});;

Template.swimlane.events({
	'mouseenter .swimmer span' : function(event) {
		
		console.log("Event triggered");
		console.log( _swimlane.elem_id());
		$("#" + _swimlane.elem_id() + " .opp_fly_out").show( "fold", 1000 );
		//$(".opp_fly_out").show( "fold", 1000 );
	}
});

Template.opportunity_fly_out.helpers( {
	
	fly_out_elem_id : function() {
		Session.get("x-roadmaps-" + Template.currentData().entry.opportunity.name);
		return opportunity_fly_out.detail_elem_id();
	},

	capability_heatmap_url : function() {
		Session.get("x-roadmaps-" + Template.currentData().entry.opportunity.name);
		return "/opportunities/" + Template.currentData().entry.opportunity.name + "/capability_heat_map";
	}
	
});

Template.opportunity_fly_out.onCreated(function() {console.log("==> opportunity_fly_out.onCreated <== ");});

Template.opportunity_fly_out.onRendered(function() {	
	
	console.log("==> opportunity_fly_out.onRendered <==");
	
	var oppName = Template.currentData().entry.opportunity.name;
	
	var sessionKey = "x-roadmaps-" + oppName;
	Meteor.call('getRoadmapsCarryingOpportunity', oppName, function(error, result) {
		
		console.log("Got x-roadmaps for opportunity : " + oppName);
		
		var roadmapToSuppress;
		var roadmaps = result;
		
		$.each(roadmaps, function(index, item) 
				{ 
					item["_roadmap_name"] = item.name; 
					item["other_roadmap"] = item.name != Router.current().params._roadmap_name;
				} );
		
		console.log("Updating data in " + sessionKey);
		
		Session.set(sessionKey, roadmaps);
	});
		
});

Template.opportunity_fly_out.events({
	
	'click .opp_fly_out .close, mouseleave  .opp_fly_out' : function(event) {
		
		 $("#" + opportunity_fly_out.detail_elem_id() ).hide( "explode", 1000 );
	},

	'click .x-roamdaps button' : function(event) {
	
		$("#" + opportunity_fly_out.detail_elem_id() ).hide( "explode", 1000 );
		
		var destinationRoadmap = $(event.target).attr("data-x-roadmap");
		Router.go('roadmap_details', {_roadmap_name: destinationRoadmap});
	}
	
});

Template.opportunity_fly_out.onDestroyed(function() {
	console.log("**> opportunity_fly_out.onDestroyed <**");
	$("#" + opportunity_fly_out.detail_elem_id() ).hide( "explode", 1000 );
	});

Template.cross_referenced_roadmaps.onCreated(function() {console.log("==> cross_referenced_roadmaps.onCreated <==");});
Template.cross_referenced_roadmaps.onRendered(function() {console.log("==> cross_referenced_roadmaps.onRendered <==");});
Template.cross_referenced_roadmaps.onDestroyed(function() {console.log("==> cross_referenced_roadmaps.onDestroyed <==");});

Template.cross_referenced_roadmaps.helpers({
	
		xroadmaps : function() {
			
			var sessionKey = "x-roadmaps-" + Template.currentData().entry.opportunity.name;
			console.log("Binding data from session key: " + sessionKey);
			
			var roadmaps = Session.get(sessionKey);
			
			return roadmaps;
			
		}
	}
);
		
Tracker.autorun(function() {
		
	console.log("Refreshing list");
	
	currentList.refresh();
	
});

