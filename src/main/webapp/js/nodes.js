
var selectedNode = null;
var selectedDependency = null;
var isFirstNode = true;
var refreshIntervalId = null;

var zoomLevel = 1;

$(function(){ 

	var cy = cytoscape({
	  container: $('#cy')[0],
	  zoom: 1,
	  pan: { x: 0, y: 0 },
	  
	  layout: {
		    name: 'breadthfirst',
		    fit: true,
		    directed: true,
		    padding: 50  
	  },
	  
	  style: cytoscape.stylesheet()
	    .selector('node')
	      .css({
	    	'border-color': '#0266C8',
	    	'border-width' : '1px',
	        'shape': 'data(faveShape)',
	        'width': '120px',
	        'font-family' : 'Consolas',
	        'font-size' : '10px',
	        'content': 'data(id)',
	        'text-valign': 'center',
	        'background-color': 'data(faveColor)',
	        'color': 'data(textColor)'
	      })
	    .selector(':selected')
	      .css({
	        'border-width': 1,
	        'border-color': 'black',
	        'background-color' : '#4D7A93',
	        'color': 'white'
	      })
	    .selector('edge')
	      .css({
	        'opacity': 0.666,
	        'width': 1,
	        'target-arrow-shape': 'triangle',
	        'source-arrow-shape': 'circle',
	        'line-color': 'data(faveColor)',
	        'source-arrow-color': '#00933B',
	        'target-arrow-color': '#F90101'
	      }),
	  
		  ready: function(){
		    window.cy = this;
		  }
	});


	cy.on('tap', function(){
		selectedNode = null;
		selectedDependency = null;
		$("#actDependencyDetailBox").css("display", "none");
		$("#actDetailBox").css("display", "none");
		$("#dspTag").text( "" );
		$("#dspType").text( "" );
		$("#dspActivation").text( "" );
		$("#dspInput").text( "" );
		$("#dspInputJoin").text( "" );
		$("#dspOutput").text( "" );
		$("#dspDescription").text( "" );
		$('#relInput').prop('disabled', false);
		$("#criteriaContentPanel").css("display","none");
		if ( cy.elements('*').size() > 0 ) {
			$("#insertBox").css("height", "25px");
		} 
		
	});
	
	cy.on('tap', 'edge', function(){
		selectedNode = null;
		selectedDependency = this;
		$("#actDetailBox").css("display", "none");
		$("#actDependencyDetailBox").css("display", "block");
		
		var sourceTag = this.data('source');
		var targetTag = this.data('target');
		var sourceNode = cy.elements("node[id='"+sourceTag+"']");
		var targetNode = cy.elements("node[id='"+targetTag+"']");
		$("#criteriaContentPanel").css("display","none");
		$("#dspDepSource").text( sourceNode.data('id') );
		$("#dspDepTarget").text( targetNode.data('id') );
	});
	
	cy.on('tap', 'node', function(){
		selectedNode = this;
		selectedDependency = null;
		$("#actDetailBox").css("display", "table");
		$("#actDependencyDetailBox").css("display", "none");
		$("#criteriaContentPanel").css("display","none");
		$("#dspTag").text( this.data('id') );
		$("#dspType").text( this.data('name') );
		$("#dspActivation").text( this.data('activation') );
		$("#dspDescription").text( this.data('description') );
		$("#dspInput").text( this.data('input') );
		
		var inputJoin = this.data('inputJoin');
		$("#dspInputJoin").text( inputJoin );
		$("#dspOutput").text( this.data('output') );
	
		$('#relInput').val( this.data('outputId') );
		$('#relInput').prop('disabled', 'disabled');
		
		if ( inputJoin != '' ) {
			$("#joinLine").css("display", "cell");
			$("#unoLine").css("display", "none");			
		} else {
			$("#joinLine").css("display", "none");
			$("#unoLine").css("display", "cell");			
		}
	
		$("#insertBox").css("display", "block");
	
		var selects = "<option value='-1'>-- Select a target --</option>";
		var tagRef = this.data('id');
		$.each( cy.filter('node'), function(){
			if ( this.data('id') != tagRef ) {
				var type = this.data('name');
				if( (type == "REDUCE") || (type == "SELECT" ) ) {
				selects = selects + "<option>"+ this.data('id')+"</option>";
				}
			}
		});
		
		$("#selectConnect").html( selects );
		
	});

	cy.panningEnabled( true );	
	cy.boxSelectionEnabled(false);
	cy.zoomingEnabled( true );
	cy.userZoomingEnabled( false );

}); 