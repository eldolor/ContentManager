jQuery(function($) {
	try {
		log("function($)", "Entering");
		$(document).foundation();

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);
		setupContextNavBar();
		getClientKeys();
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});

function setupContextNavBar() {
	log("setupContextNavBar", "Entering");
	try {
		$('#create_client_key').unbind();
		$('#create_client_key').click(function() {
			createClientKey()
		});
		$('#restore_clientkeys').unbind();
		$('#restore_clientkeys').click(function() {
			restoreClientKeys();
		});

	} catch (err) {
		handleError("setupContextNavBar", err);
	} finally {
		log("setupContextNavBar", "Exiting");
	}
}

function getClientKeys() {
	log("getClientKeys", "Entering");
	try {
		$('#content_progress_bar').show();
		var jqxhr = $.ajax({
			url : "/secured/clientkeys",
			type : "GET",
			contentType : "application/json",
			async : true,
			statusCode : {
				200 : function(clientKeys) {
					// displayMessage(JSON.stringify(clientKeys, null, 2));
					handleDisplayClientKeys_Callback(clientKeys);
					// Google Analytics
					ga('send', {
						'hitType' : 'pageview',
						'page' : '/account/clientkeys',
						'title' : PageTitle.CLIENT_KEYS
					});
					// End Google Analytics
				},
				503 : function() {
					// $('#application_errors')
					// .html(
					// 'Unable to process the request. Please try again later');
					// $('#cm_errors_container').show();
				}
			},
			error : function(xhr, textStatus, errorThrown) {
				log(errorThrown);
				// $('#application_errors')
				// .html(
				// 'Unable to process the request. Please try again later');
				// $('#cm_errors_container').show();
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

function handleDisplayClientKeys_Callback(pClientKeys) {
	log("handleDisplayClientKeys_Callback", "Entering");
	try {
		// displayMessage(JSON.stringify(pClientKeys, null, 2));
		var lInnerHtml = '';
		for (var int = 0; int < pClientKeys.length; int++) {
			var lClientKey = pClientKeys[int];
			lInnerHtml += "<div class=\"blog_content\"> ";
			lInnerHtml += " <h3 class=\"gray\">";
			lInnerHtml += lClientKey.key;
			lInnerHtml += "</h3>";
			lInnerHtml += "<div class=\"blog_content_details float_left\">";
			lInnerHtml += "<ul><li class=\"light_gray\"><a class=\"small\" href=\"javascript:void(0)\" onclick=\"deleteClientKey("
					+ lClientKey.id
					+ ")\"><i class=\"fi-page-delete light_gray\"></i>&nbsp;delete</a></li>";
			lInnerHtml += "</ul>";
			lInnerHtml += "</div>";
			var lEpochDate = (lClientKey.timeUpdatedMs == null) ? lClientKey.timeCreatedMs
					: lClientKey.timeUpdatedMs;
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
			lInnerHtml += "<div class=\"clearfix\"></div><div class=\"separator\"></div>";
			lInnerHtml += "</div>";

		}

		$('#client_keys_list').empty().html(lInnerHtml);
		// progress bar
		$('#content_progress_bar').css("width", "100%");
		$('#content_progress_bar').hide();
	} catch (err) {
		handleError("handleDisplayClientKeys_Callback", err);
	} finally {

		log("handleDisplayClientKeys_Callback", "Exiting");
	}
}

function deleteClientKey(id) {
	log("deleteClientKey", "Entering");
	try {
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayConfirm("Are you sure you want to delete this Client Key?",
				function() {
					// Google Analytics
					// ga('send', 'event', Category.APPLICATION, Action.DELETE);
					// End Google Analytics

					var url = "/secured/clientkey/" + id + "/" + _timeUpdatedMs
							+ "/" + _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $.ajax({
						url : url,
						type : "DELETE",
						contentType : "application/json",
						statusCode : {
							200 : function() {
								// Google Analytics
								ga('send', 'event', Category.CLIENT_KEYS,
										Action.DELETE);
								// End Google Analytics
								location.reload();
							},
							503 : function() {
								// $('#application_errors')
								// .html(
								// 'Unable to process the request. Please try
								// again later');
								// $('#cm_errors_container').show();
							}
						},
						error : function(xhr, textStatus, errorThrown) {
							log(errorThrown);
							// $('#application_errors')
							// .html(
							// 'Unable to process the request. Please try again
							// later');
							// $('#cm_errors_container').show();
						}
					});
				});
	} catch (err) {
		handleError("deleteClientKey", err);
	} finally {
		log("deleteClientKey", "Exiting");
	}

}

function createClientKey() {
	log("createClientKey", "Entering");
	try {
		$('#content_progress_bar').show();
		var jqxhr = $.ajax({
			url : "/secured/clientkey",
			type : "POST",
			contentType : "application/json",
			async : true,
			statusCode : {
				200 : function(clientKeys) {
					// Google Analytics
					ga('send', 'event', Category.CLIENT_KEYS, Action.CREATE);
					// End Google Analytics
					location.reload();
				},
				503 : function() {
					// $('#application_errors')
					// .html(
					// 'Unable to process the request. Please try again later');
					// $('#cm_errors_container').show();
				}
			},
			error : function(xhr, textStatus, errorThrown) {
				log(errorThrown);
				// $('#application_errors')
				// .html(
				// 'Unable to process the request. Please try again later');
				// $('#cm_errors_container').show();
			}
		});

	} catch (err) {
		handleError("createClientKey", err);
		// close wait div
		;
	} finally {
		log("createClientKey", "Exiting");
	}
}

function restoreClientKeys() {
	log("restoreClientKeys", "Entering");
	try {
		getDeletedClientKeys();

	} catch (err) {
		handleError("restoreClientKeys", err);
	} finally {
		log("restoreClientKeys", "Exiting");
	}

}

function getDeletedClientKeys() {
	log("getDeletedClientKeys", "Entering");
	try {

		var jqxhr = $.ajax({
			url : "/secured/clientkeys/deleted",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(lListItems) {
					buildList(lListItems);
				},
				503 : function() {
				}
			},
			error : function(xhr, textStatus, errorThrown) {
				log(errorThrown);
			}
		});

	} catch (err) {
		handleError("getDeletedClientKeys", err);
		// close wait div
		;
	} finally {
		log("getDeletedClientKeys", "Exiting");
	}
}
function buildList(pListItems) {
	log("buildList", "Entering");
	try {
		var lList = '<label>Please select a Client Key<select id="select_from_deleted">';
		if (pListItems) {
			for (var int = 0; int < pListItems.length; int++) {
				var lItem = pListItems[int];
				lList += '<option value="';
				lList += lItem.id;
				lList += '">';
				lList += lItem.key;
				lList += '</option>';
			}
		}
		lList += '</select></label>';
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayRestoreConfirm(lList, function() {
			var lSelected = $('#select_from_deleted').val();

			if (lSelected) {
				var url = "/secured/clientkey/restore/" + lSelected + "/"
						+ _timeUpdatedMs + "/" + _timeUpdatedTimeZoneOffsetMs;
				var jqxhr = $.ajax({
					url : url,
					type : "PUT",
					contentType : "application/json",
					statusCode : {
						200 : function() {
							// Google Analytics
							ga('send', 'event', Category.CLIENT_KEYS,
									Action.RESTORE);
							// End Google Analytics
							location.reload();
						},
						503 : function() {
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
					}
				});
				jqxhr.always(function(msg) {
				});
			}
		});

	} catch (err) {
		handleError("buildList", err);
		// close wait div
		;
	} finally {
		log("buildList", "Exiting");
	}

}
function displayRestoreConfirm(pList, pCallback) {
	log("displayRestoreConfirm", "Entering");
	try {
		$("#select_from_deleted_list").html(pList);
		// if the user clicks "yes"
		$('#restore_confirm_button').bind('click', function() {
			// call the callback
			if ($.isFunction(pCallback)) {
				pCallback.apply();
			}
			$('#restore_modal').foundation('reveal', 'close');
		});
		$('#restore_modal').foundation('reveal', 'open');
	} catch (err) {
		handleError("displayRestoreConfirm", err);
		// close wait div
		;
	} finally {
		log("displayRestoreConfirm", "Exiting");
	}

}
