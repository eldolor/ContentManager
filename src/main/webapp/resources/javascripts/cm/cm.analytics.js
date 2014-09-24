jQuery(function($) {
	try {
		log("function($)", "Entering");
		setup();
		// call this post setup
		// $(document).foundation('joyride', 'start');
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});

function setup() {
	try {
		log("setup", "Entering");
		// setupLeftNavBar();
		setupBreadcrumbs();
		// getLoggedInUser();

		$(document).foundation();

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);
		google.setOnLoadCallback(setupApplicationsGoogCharts);

	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function setupBreadcrumbs() {
	log("setupBreadcrumbs", "Entering");
	try {
		var lHtml = $('#breadcrumbs').html();
		$('#breadcrumbs')
				.html(
						lHtml
								+ "<a id=\"breadcrumb_analytics\" href=\"/analytics/applications\">Analytics</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}

function setupApplicationsGoogCharts() {
	log("setupApplicationsGoogCharts", "Entering");
	try {
		var url = "/contentstats/application/daily";
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(contentStats) {
							var lAccountUsageDetailsHtml = '';
//							lAccountUsageDetailsHtml += '<div class="row full-width">';
//							lAccountUsageDetailsHtml += '<h2 class="gray">Application Usage</h2>';
//							lAccountUsageDetailsHtml += '</div>';
							lAccountUsageDetailsHtml += '<p class="clearfix"></p>';
							for (var i = 0; i < contentStats.length; i++) {

								var lContentStatArray = contentStats[i];
								if (lContentStatArray.length > 0) {
									lAccountUsageDetailsHtml += '<div class="row full-width">';

									// BANDWIDTH
									lAccountUsageDetailsHtml += '<div id="tracking_id_'
											+ lContentStatArray[0].trackingId
											+ '"></div>';
									lAccountUsageDetailsHtml += '</div>';

								}
							}
							$('#analytics_applications_details').html(
									lAccountUsageDetailsHtml);
//							log("setupApplicationsGoogCharts",
//									lAccountUsageDetailsHtml);
							for (var i = 0; i < contentStats.length; i++) {

								var lContentStatArray = contentStats[i];
								// GOOG Charts
								var dataTable = new google.visualization.DataTable();
								dataTable.addColumn({
									type : 'date',
									id : 'Date'
								});
								dataTable.addColumn({
									type : 'number',
									id : 'Count'
								});
								for (var j = 0; j < lContentStatArray.length; j++) {
									var lDate = new Date(
											lContentStatArray[j].eventEndTimeMs);
									//displayMessage(lDate.toUTCString());
									var lNewDate = new Date(
											lDate.getUTCFullYear(), lDate
													.getUTCMonth(), lDate.getUTCDate());
									//displayMessage(lNewDate.toUTCString());
									dataTable.addRow([ lNewDate,
											lContentStatArray[j].count ]);
								}
								try {
									var chart = new google.visualization.Calendar(
											document
													.getElementById('tracking_id_'
															+ lContentStatArray[0].trackingId));
									// $("#tracking_id_"
									// +
									// lContentStatArray[0].trackingId));
									var options = {
										title : "Daily Impressions: Application '"
												+ lContentStatArray[0].name
												+ "'",
										height : 350,
										calendar: {
										      cellColor: {
										        stroke: '#1fad9d',
										        strokeOpacity: 0.5,
										        strokeWidth: 1,
										      }
										    }
									};

									chart.draw(dataTable, options);
									
									google.visualization.events.addListener(chart, 'select', function(){
										//displayMessage("item selected"	);
										var selection = chart.getSelection();

										var message = '';
										  for (var i = 0; i < selection.length; i++) {
										    var item = selection[i];
										    if (item.row != null && item.column != null) {
										      message += '{row:' + item.row + ',column:' + item.column + '}';
										    } else if (item.row != null) {
										      message += '{row:' + item.row + '}';
										    } else if (item.column != null) {
										      message += '{column:' + item.column + '}';
										    }
										  }
										  if (message == '') {
										    message = 'nothing';
										  }
										  displayMessage('You selected ' + message);
										
									});

									
								} catch (err) {
									handleError("setupApplicationsGoogCharts",
											err);
								}

							}

						},
						503 : function() {
							$('#content_errors').html(
									'Unable to get available storage quota');
							$('#content_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#content_errors').show();
					}
				});
	} catch (err) {
		handleError("setupApplicationsGoogCharts", err);
	} finally {
		log("setupApplicationsGoogCharts", "Exiting");
	}
}

/** *End***************************************** */
