Meteor.methods({

	getRoadmap : function(roadmapName) {
		
		this.unblock();
		
		console.log("fetching list: roadmap opportunities");

		var url = 'http://localhost:8081/roadmaps/' + roadmapName + "/opportunities";
		console.log("Contacting: " + url);
		
		var result = HTTP.get(url);
		console.log(result);
		
		var swimlanes = [];
		
		result.data.forEach( function(item, index) {
			
			item.opportunity["timeOffset"] =  80 * 16;
			
			var factor = (new Date(2018, 03, 31).getTime() - new Date(2015, 10, 1).getTime()) /30
			
			if(item.opportunity.startDate) {
				
				item.opportunity["timeOffset"] =  80 * Math.floor(
						(item.opportunity.startDate - new Date(2015, 10, 1).getTime()) / factor);
				
			}
			
			var endOffset = 80 * 30;
			if(item.opportunity.endDate) {
				
				endOffset =  80 * Math.ceil(
						(item.opportunity.endDate - new Date(2015, 10, 1).getTime()) / factor);
				
			}
			
			item.opportunity["timePeriod"] = endOffset - item.opportunity["timeOffset"];
			
			swimlanes.push({"name" : "lane " + index, "entry" : item});
			
		});
		
		var calendar = [
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
								        { "month" : "Mar '18"},
								        ];
		
		calendar.forEach( function(item, index) {
			item["timeOffset"] =  80 * index;
			item["tiling"] = index%2 == 0 ? "even" : "odd";
		});
		
		var toReturn =  { 
				"calendar" : calendar,
		        "swimlanes" : swimlanes
		}
		
		console.log(toReturn);
		
		return toReturn;
		
	},
	
	getRoadmapsCarryingOpportunity : function(opportunityName) {
		
		this.unblock();
		
		console.log("fetching list: roadmap carrying opportunities");

		var url = 'http://localhost:8081/opportunities/' + opportunityName + "/roadmaps";
		console.log("Contacting: " + url);
		
		var result = HTTP.get(url);
		console.log(result);
				
		return result.data;
		
	}
	
});