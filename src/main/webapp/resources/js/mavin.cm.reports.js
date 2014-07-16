function getDailyMetricsReport(clicked) {
	log("getDailyMetricsReport", "Entering");
	
	try {
		// open wait div
		openWait();

		// manage state

		var url = "secured/reports/dailymetrics";
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(users) {
					handleGetDailyMetricsRpt_Callback(users);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("getDailyMetricsReport", err);
		// close wait div
		//
	}
	log("getDailyMetricsReport", "Exiting")
}

function handleGetDailyMetricsRpt_Callback(users) {
	log("handleGetDailyMetricsRpt_Callback", "Entering");
	
	try {
		var innerHtml = "<div id=\"getDailyMetrics\"> ";
		innerHtml += "<script type='text/javascript'>" +
		"$(document).ready( " +
			"function () " +
				"{$('#dailyMetricsTable').dataTable({'sScrollY': '300px', 'sScrollX': '150%','sScrollXInner': '1200px', 'bScrollCollapse':true, 'bPaginate':true } );" +
		"} )\;" +
		"</script>";
		if (users != null) {

			var columns1 = ['Date', 'Current Users', 'Daily Installs', 'Daily Unisntalls', 'Impressions', 'Clicks', 'CTR', 'Impressions/User', 'Clicks/User', 'Campaign Name', 'New Ads/Day'];
			
			var columns = getColumnHeaders(users);
			innerHtml += "<div id=\"dailyMetricsDiv\"> ";
			
			innerHtml += "<table id=\"dailyMetricsTable\">"; 
			innerHtml += '<thead><tr>';
			for (var c = 0; c < columns1.length; c++) {
				innerHtml += '<th>' + columns1[c] + '</th>';
			}
			innerHtml += '</tr></thead>';
			innerHtml += '<tbody>';
			
			for (var i = 0; i < users.length; i++) {
				innerHtml += '<tr>';
		        for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
		            var cellValue = users[i][columns[colIndex]];

		            if (cellValue == null) { cellValue = ""; }
		            innerHtml += "<td>" + cellValue + "</td>";

		        }
		        innerHtml += '</tr>';
			}
			innerHtml += '</tbody>';
			innerHtml += "</table></div></div>";
		}
		$('#breadcrumbs').remove();
		$('#accordion').remove();
		$('#entries').html(innerHtml);
		
	} catch (err) {
		handleError("handleGetDailyMetricsRpt_Callback", err);
	} finally {
		// close wait div
		//
	}
	log("handleGetDailyMetricsRpt_Callback", "Exiting")
}

function getRegisteredUsersReport(clicked) {
	log("getRegisteredUsersReport", "Entering");
	
	try {
		// open wait div
		openWait();

		// manage state

		var url = "secured/reports";
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(users) {
					handleViewRegisteredUsers_Callback(users);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("getRegisteredUsersReport", err);
		// close wait div
		//
	}
	log("getRegisteredUsersReport", "Exiting")
}

function handleViewRegisteredUsers_Callback(users) {
	log("handleViewRegisteredUsers_Callback", "Entering");
	
	try {
		var innerHtml = "<div id=\"viewRegisteredUsers\"> ";
		innerHtml += "<script type='text/javascript'>" +
		"$(document).ready( " +
			"function () " +
				"{$('#excelDataTable').dataTable({'sScrollY': '300px', 'sScrollX': '100%','sScrollXInner': '1200px', 'bScrollCollapse':true, 'bPaginate':true } );" +
		"} )\;" +
		"</script>";
		if (users != null) {

			var columns1 = ['Device Id', 'Account Id', 'Phone Number', 'First Touch', 'Last Touch', 'Impressions'];
			
			var columns = getColumnHeaders(users);
			innerHtml += "<div id=\"regUsersTable\"> ";
			
			innerHtml += "<table id=\"excelDataTable\">"; 
			innerHtml += '<thead><tr>';
			for (var c = 0; c < columns1.length; c++) {
				innerHtml += '<th>' + columns1[c] + '</th>';
			}
			innerHtml += '</tr></thead>';
			innerHtml += '<tbody>';
			
			for (var i = 0; i < users.length; i++) {
				innerHtml += '<tr>';
		        for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
		            var cellValue = users[i][columns[colIndex]];

		            if (cellValue == null) { cellValue = "null"; }
		            innerHtml += "<td>" + cellValue + "</td>";

		        }
		        innerHtml += '</tr>';
			}
			innerHtml += '</tbody>';
			innerHtml += "</table></div></div>";
		}
		$('#breadcrumbs').remove();
		$('#accordion').remove();
		$('#entries').html(innerHtml);
		
	} catch (err) {
		handleError("handleViewRegisteredUsers_Callback", err);
	} finally {
		// close wait div
		//
	}
	log("handleViewRegisteredUsers_Callback", "Exiting")
}

function getColumnHeaders(ads) {
    var columnSet = [];

    for (var i = 0 ; i < ads.length ; i++) {
        var rowHash = ads[i];
        for (var key in rowHash) {
            if ($.inArray(key, columnSet) == -1){
                columnSet.push(key);
            }
        }
    }
    return columnSet;
}
