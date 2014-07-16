/** Begin Feed****************************************** */

function getFeeds(feedCampaignId) {
	log("getFeeds(feedCampaignId)", "Entering")
	try {
		log("campaign id: " + feedCampaignId);
		// manage state
		selectedCampaign(feedCampaignId);

		// open wait div
		openWait();
		var _feeds = "";
		var url = "/secured/fm/feeds/" + feedCampaignId;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(feeds) {
					// close wait div
					handleDisplayFeeds_Callback(feedCampaignId, feeds);
				}
			}
		});
		return _feeds;
	} catch (err) {
		handleError("getFeeds(feedCampaignId)", err);
		// close wait div
		;
	}
	log("getFeeds(feedCampaignId)", "Exiting");
}

function getFeedsByType(feedCampaignId, type) {
	log("getFeedsByType(feedCampaignId, type)", "Entering")
	try {
		log("campaign id: " + feedCampaignId);
		log("type: " + type);
		// open wait div
		openWait();
		var _feeds = "";
		var url = "/secured/fm/feeds/" + feedCampaignId + "/" + type;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(feeds) {
					// close wait div
					;
					_feeds = feeds;
				}
			}
		});
		return _feeds;
	} catch (err) {
		handleError("getFeedsByType(feedCampaignId, type)", err);
		// close wait div
		;
	}
	log("getFeedsByType(feedCampaignId, type)", "Exiting");
}

function handleDisplayFeeds_Callback(feedCampaignId, feeds) {
	log("handleDisplayFeeds_Callback", "Entering")
	try {
		var innerHtml = '';
		if (feeds != null) {
			for ( var int = 0; int < feeds.length; int++) {
				var feed = feeds[int];
				if (feed != null) {
					var _innerHtml = "<div class=\"entry open\"> "
							+ "<div class=\"liner\">"
							+ "<div class=\"header\">"
							+ "<div class=\"expando\"></div>"
					if (feed.type == 'survey') {
						_innerHtml += "<h2>Survey</h2>";
					}
					_innerHtml += "<div class=\"meta\">"
							+ "<span class=\"last\">"
							+ getDisplayDate(feed.date) + "</span>" + "</div>"
							+ "</div>" + "<div class=\"body\">"
							+ "<div class=\"content\">"
							+ "<div class=\"liner\">"
					if (feed.type == 'survey') {
						_innerHtml += "<div class=\"synopsis\">"
								+ feed.question + "</div>"
					}
					_innerHtml += "<div class=\"actions\">" + "<ul>";

					_innerHtml += "<li><a href=\"javascript:void(0)\""
							+ "onclick=\"editFeed('"
							+ feed.id
							+ "')\">edit feed</a>"
							+ "</li>"
							+ "<li>"
							+ "<a href=\"javascript:void(0)\" onclick=\"deleteFeed('"
							+ feed.feedCampaignId + "','" + feed.id
							+ "')\">delete this feed </a>" + "</li>"
							+ "<li class=\"last\">";
					if (feed.type == 'survey') {
						_innerHtml += "<a href=\"javascript:void(0)\" onclick=\"displayFeedFeedStats('"
								+ feed.id
								+ "','"
								+ feed.question
								+ "')\">statistics</a>"
					}
					_innerHtml += "</li>" + "</ul>" + "</div>" + "</div>"
							+ "</div>" + "</div>" + "</div>" + "</div>";
					innerHtml += _innerHtml;
				}
			}
		}
		var breadCrumbsHtml = "<div class=\"actions\"><ul><li><a href=\"javascript:void(0)\" onclick=\"getCampaigns()\">campaigns</a></li>"
				+ "<li><span class=\"separator\"> > </span></li>"
				+ "<li><a href=\"javascript:void(0)\" onclick=\"getFeedGroups('"
				+ mSelectedFeedCampaign.id
				+ "')\">"
				+ mSelectedFeedCampaign.name
				+ "</a></li>"
				+ "<li><span class=\"separator\"> > </span></li>"
				+ "<li><a href=\"javascript:void(0);\" onclick=\"displaySelectFeedType('"
				+ mSelectedFeedCampaign.id
				+ "')\"> <img alt=\"\" src=\"../resources/images/plus-box-16x16.png\" height=\"16\" width=\"16\" />&nbsp;create new feed</a></li></ul></div>";

		$('#breadcrumbs').html(breadCrumbsHtml);

		$('#entries').html(innerHtml);
		// $('#titleBoxHeader').html('Feeds');

		$('a[href="#TODO"]').click(function() {
			alert('Nothing to see here.');
		});

		$('#entries .entry .expando').click(
				function() {
					$(this).closest('.entry').toggleClass('open').find('.body')
							.slideToggle().end();
				});
	} catch (err) {
		handleError("handleDisplayFeeds_Callback", err);
	} finally {
		// close wait div
		;
	}
	log("handleDisplayFeeds_Callback", "Exiting")
}
function displayFeedFeedStats(id, name) {
	log("displayFeedFeedStats", "Entering");
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/fm/feedfeedstats/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(feedStats) {
					$('#stats_header').html(name);
					$('#stats_clicks').html('Clicks: ' + feedStats.clicks);
					$('#stats_impressions').html(
							'Impressions: ' + feedStats.impressions);

					// close wait div
					;
					openPopup("stats_div");
				}
			}
		});
	} catch (err) {
		handleError("displayFeedFeedStats", err);
		// close wait div
		;
	}
	log("displayFeedFeedStats", "Exiting");
}

function editFeed(id) {
	log("editFeed", "Entering")
	// save state
	selectedFeed(id);

	if (mSelectedFeed.type == 'survey') {
		editSurvey(id);
	} else if (mSelectedFeed.type == 'coupon') {
		editCoupon(id);
	}
	log("editFeed", "Exiting")
}
function selectedFeed(id) {
	log("selectedFeed", "Entering")
	try {
		// load entry info via sync call
		var url = "/secured/fm/feed/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(feed) {
					mSelectedFeed = feed;
				}
			}
		});
	} catch (err) {
		handleError("selectedFeed", err);
	}
	log("selectedFeed", "Exiting")
}

function displaySelectFeedType(feedCampaignId) {
	log("displaySelectFeedType", "Entering")
	$('#feed_type_campaign_id').val(feedCampaignId);
	// reset
	$("input:radio[name=feed_type_preload]").unbind();
	$("input:radio[name=feed_type_preload]").click(function() {
		var value = $(this).val();
		$('#feed_type_selected').val(value);
	});
	// reset
	$('#feed_type_continue_button').unbind();
	$('#feed_type_continue_button').one('click', function() {
		try {
			// feedGroupId = $('#feed_type_feedgroup_id').val();
			var feedTypeSelected = $('#feed_type_selected').val();
			// alert(feedTypeSelected);
			if (feedTypeSelected == 'survey') {
				;
				newSurvey(feedCampaignId);
			} else if (feedTypeSelected == 'coupon') {
				;
				newCoupon(feedCampaignId);
			}
			$('input:radio[name=feed_type_preload]').unbind();
		} catch (err) {
			handleError("displaySelectFeedType", err);
		}
	});
	openPopup('feed_type_div');
	log("displaySelectFeedType", "Exiting")
}

function deleteFeed(feedCampaignId, id) {
	log("deleteFeed", "Entering")
	try {
		displayConfirm("Are you sure you want to delete this Feed?",
				function() {
					wait("confirm_wait");
					var url = "/secured/fm/feed/" + id;
					var jqxhr = $.ajax({
						url : url,
						type : "DELETE",
						contentType : "application/json",
						async : false,
						statusCode : {
							200 : function() {
								getFeeds(feedCampaignId);
							}
						}
					});
					jqxhr.always(function(msg) {
						clearWait("confirm_wait");
					});
				});
	} catch (err) {
		handleError("deleteFeed", err);
	}
	log("deleteFeed", "Exiting")
}

function viewImage(id) {
	log("viewImage", "Entering")
	try {
		// open wait div
		openWait();

		// load entry info via ajax
		var url = "/secured/fm/feed/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(feed) {
					var _url = "/dropbox/" + feed.uri;
					log("viewImage", _url);
					$("#feed_image_widget").attr("src", _url);
					// close wait div
					;
					openPopup("view_feed_image_div");
				}
			}
		});
	} catch (err) {
		handleError("viewImage", err);
		// close wait div
		;
	}
	log("viewImage", "Exiting")

}

/** End Feed****************************************** */
