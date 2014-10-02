	jQuery(function($) {
		try {
			log("function($)", "Entering");
			$(document).foundation();

			var doc = document.documentElement;
			doc.setAttribute('data-useragent', navigator.userAgent);
		} catch (err) {
			handleError("function($)", err);
		} finally {
			log("function($)", "Exiting");
		}
	});
	function getClientKeys() {
		log("getClientKeys", "Entering");
		try {
			$('#content_progress_bar').show();
			var jqxhr = $
					.ajax({
						url : "/secured/applications",
						type : "GET",
						contentType : "application/json",
						async : true,
						statusCode : {
							200 : function(applications) {
								handleDisplayClientKeys_Callback(applications);
								// Google Analytics
								ga('send', {
									'hitType' : 'pageview',
									'page' : '/secured/applications',
									'title' : PageTitle.APPLICATIONS
								});
								// End Google Analytics
							},
							503 : function() {
								$('#application_errors')
										.html(
												'Unable to process the request. Please try again later');
								$('#cm_errors_container').show();
							}
						},
						error : function(xhr, textStatus, errorThrown) {
							log(errorThrown);
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					});

		} catch (err) {
			handleError("getClientKeys", err);
			// close wait div
			;
		} finally {
			log("getClientKeys", "Exiting");
		}
	}

	function handleDisplayClientKeys_Callback(pApplications) {
		log("handleDisplayClientKeys_Callback", "Entering");
		try {
			// displayMessage(JSON.stringify(pApplications, null, 2));
			var lInnerHtml = '';
			for (var int = 0; int < pApplications.length; int++) {
				var lApplication = pApplications[int];
				lInnerHtml += "<div class=\"blog_content\"> ";
				lInnerHtml += " <h3 class=\"gray\">";
				lInnerHtml += lApplication.name;
				lInnerHtml += "</h3>";
				lInnerHtml += "<div class=\"blog_content_details float_left\">";
				lInnerHtml += "<ul> <li class=\"green\">Application Id: "
						+ lApplication.trackingId
						+ " </li><li>|</li><li class=\"light_gray\"><a class=\"small green\" href=\"javascript:void(0)\" onclick=\"displayContentGroups("
						+ lApplication.id
						+ ")\"><i class=\"fi-list-number light_gray\"></i>&nbsp;content groups</a></li> <li>|</li><li class=\"light_gray\"><a class=\"small\" href=\"javascript:void(0)\" onclick=\"editApplication("
						+ lApplication.id
						+ ")\"><i class=\"fi-page-edit light_gray\"></i>&nbsp;edit</a></li><li>|</li> <li class=\"light_gray\"><a class=\"small\" href=\"javascript:void(0)\" onclick=\"deleteApplication("
						+ lApplication.id
						+ ")\"><i class=\"fi-page-delete light_gray\"></i>&nbsp;delete</a></li>";
				lInnerHtml += "</ul>";
				lInnerHtml += "</div>";
				var lEpochDate = (lApplication.timeUpdatedMs == null) ? lApplication.timeCreatedMs
						: lApplication.timeUpdatedMs;
				var lDisplayDate = getFormattedDisplayDate(lEpochDate, "ll");
				var lSplit = lDisplayDate.split(",");
				var lYear = lSplit[1];
				var lSplitM = lSplit[0].split(" ");
				var lMonth = lSplitM[0];
				var lDate = lSplitM[1];

				lInnerHtml += "<div class=\"blog_date green text_center\"> <span>";
				lInnerHtml += lDate;
				lInnerHtml += "</span>";
				lInnerHtml += "<div>";
				lInnerHtml += lMonth + "</div>";
				lInnerHtml += "</div>";

				lInnerHtml += "<div class=\"blog_comments text_center green\">";
				lInnerHtml += lYear;
				lInnerHtml += "</div>";
				lInnerHtml += "<div class=\"blog_content_details float_left\"><p class=\"light_gray\">"
						+ lApplication.description + "</p>";
				if (lApplication.changesStaged) {
					lInnerHtml += "<a href=\"javascript:void(0);\" onclick=\"pushChangestoHandsets('"
							+ lApplication.id
							+ "')\" class=\"button tiny radius btn-default\">Push Changes to Handsets</a>";
				}
				lInnerHtml += "</div>";
				lInnerHtml += "<div class=\"clearfix\"></div><div class=\"separator\"></div>";
				lInnerHtml += "</div>";

			}

			$('#applications_list').empty().html(lInnerHtml);
			// progress bar
			$('#content_progress_bar').css("width", "100%");
			$('#content_progress_bar').hide();
		} catch (err) {
			handleError("handleDisplayClientKeys_Callback", err);
		} finally {

			log("handleDisplayClientKeys_Callback", "Exiting");
		}
	}