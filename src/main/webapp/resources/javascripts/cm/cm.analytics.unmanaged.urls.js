jQuery(function($) {
	try {
		log("function($)", "Entering");
		setup();
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});

function setup() {
	try {
		log("setup", "Entering");

		$(document).foundation();

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);
		google.setOnLoadCallback(displayAnalytics);
		// Google Analytics
		ga('send', {
			'hitType' : 'pageview',
			'page' : '/analytics/contentgroups',
			'title' : PageTitle.USAGE_REPORTS_UNMANAGED_CONTENTS_BY_URL
		});
		// End Google Analytics

	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function displayAnalytics() {
	log("displayAnalytics", "Entering");
	try {
		$('#progress_bar').show();

		var url = "/contentstats/unmanaged/" + mApplicationId + "/urls/daily";
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(contentStats) {
							var lAccountUsageDetailsHtml = '';
							lAccountUsageDetailsHtml += '<p class="clearfix"></p>';

							if (contentStats == null
									|| contentStats.length == 0) {
								lAccountUsageDetailsHtml = '<div class="row"><div class="large-12 columns" >No content usage data available</div></div>';
							}
							var lContentStatArray = contentStats[0];
							var lGroupedContentStats = groupBy(
									lContentStatArray, function(item) {
										return [ item.urlHash ];
									});

							for (var i = 0; i < lGroupedContentStats.length; i++) {
								var lArray = lGroupedContentStats[i];
								if (lArray.length > 0) {
									lAccountUsageDetailsHtml += '<div class="row">';
									lAccountUsageDetailsHtml += '<div class="large-10 columns float_left" >';

									// BANDWIDTH
									lAccountUsageDetailsHtml += '<div id="id_'
											+ lArray[i].urlHash + '"></div>';
									lAccountUsageDetailsHtml += '</div>';
									lAccountUsageDetailsHtml += '<div  class="large-2 columns float_right"><div id="category"><h3>&nbsp;</h3></div>';
									// end large 10
									lAccountUsageDetailsHtml += '</div>';
									// end row
									lAccountUsageDetailsHtml += '</div>';
									lAccountUsageDetailsHtml += '<p class="clearfix"></p>';

								}
							}
							$('#analytics_details').html(
									lAccountUsageDetailsHtml);
							// log("displayAnalytics",
							// lAccountUsageDetailsHtml);
							for (var i = 0; i < lGroupedContentStats.length; i++) {

								var lContentStatArray = lGroupedContentStats[i];
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
									// displayMessage(lDate.toUTCString());
									var lNewDate = new Date(lDate
											.getUTCFullYear(), lDate
											.getUTCMonth(), lDate.getUTCDate());
									// displayMessage(lNewDate.toUTCString());
									dataTable.addRow([ lNewDate,
											lContentStatArray[j].count ]);
								}
								try {
									var chart = new google.visualization.Calendar(
											document
													.getElementById('id_'
															+ lContentStatArray[0].urlHash));
									var options = {
										title : "URL: "
												+ lContentStatArray[0].url,
										calendar : {
											cellColor : {
												stroke : '#1fad9d',
												strokeOpacity : 0.5,
												strokeWidth : 1,
											}
										}
									};

									chart.draw(dataTable, options);

								} catch (err) {
									handleError("displayAnalytics", err);
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
					},
					complete : function(xhr, textStatus) {
						$('#progress_bar').css("width", "100%");
						$('#progress_bar').hide();
						log(xhr.status);
					}
				});
	} catch (err) {
		handleError("displayAnalytics", err);
	} finally {
		log("displayAnalytics", "Exiting");
	}
}

/** *End***************************************** */
