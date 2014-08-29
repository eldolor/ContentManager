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
		$(document).foundation();

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);
		// enable abide form validation
		$(document).foundation('abide', 'events');
		setupLeftNavBar();
		setupBreadcrumbs();

		$("#content_start_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#content_start_date'
		});
		$("#content_end_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#content_end_date'
		});
		// set application and content group name
		$('#application_name').html(
				'Application:&nbsp;' + mSelectedApplication.name);
		$('#contentgroup_name').html(
				'Content Group:&nbsp;' + mSelectedContentGroup.name);

		$("#jquery_jplayer_1").jPlayer({
			swfPath : "/resources/js/jquery",
			supplied : "webmv, ogv, m4v",
			size : {
				width : "640px",
				height : "360px",
				cssClass : "jp-video-360p"
			},
			errorAlerts : true,
			warningAlerts : true
		});
		$('#content_create')
				.on(
						'hidden',
						function() {
							$(this).removeData();
							$("#content_dropbox").empty();
							$("#content_dropbox")
									.html(
											"<span class=\"message\">Drop content here to upload</span>");
						});

		getContent(mSelectedApplication.id, mSelectedContentGroup.id);

		// set the available storage quota per plan
		setAvailableStorageQuota();
	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function setupLeftNavBar() {
	log("setupLeftNavBar", "Entering");
	try {
		$('#left_nav_bar')
				.empty()
				.html(
						'<li><a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Create Content</a></li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			$('#contents_list').hide();
			$('#content_create').show();
			newContent();
		});

	} catch (err) {
		handleError("setupLeftNavBar", err);
	} finally {
		log("setupLeftNavBar", "Exiting");
	}
}

function setupBreadcrumbs() {
	log("setupBreadcrumbs", "Entering");
	try {
		var lHtml = $('#breadcrumbs').html();
		$('#breadcrumbs')
				.html(
						lHtml
								+ "<a id=\"breadcrumb_applications\" href=\"/applications\">Applications</a>"
								+ "<a id=\"breadcrumb_content_groups\" href=\"/"
								+ mSelectedApplication.id
								+ "/contentgroups\">Content Groups</a>"
								+ "<a id=\"breadcrumb_content_groups\" href=\"/"
								+ mSelectedApplication.id + "/"
								+ mSelectedContentGroup.id
								+ "/content\">Content</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
function setSelectedApplication(id) {
	log("setSelectedApplication", "Entering");
	try {
		// load entry info via sync call
		var url = "/secured/application/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : false,
					statusCode : {
						200 : function(application) {
							mSelectedApplication = application;
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
		handleError("setSelectedApplication", err);
	} finally {
		log("setSelectedApplication", "Entering");
	}
}
function setSelectedContentGroup(id) {
	log("selectedContentGroup", "Entering");
	try {
		// load entry info via sync call
		var url = "/secured/contentgroup/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : false,
					statusCode : {
						200 : function(contentgroup) {
							mSelectedContentGroup = contentgroup;
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
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
		handleError("selectedContentGroup", err);
	} finally {
		log("selectedContentGroup", "Entering");
	}
}

function setAvailableStorageQuota() {
	try {
		log("getAvailableStorageQuota", "Entering");

		var url = "/secured/quota";
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(quota) {
							mQuota = quota;
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
		handleError("getAvailableStorageQuota", err);

	} finally {
		log("getAvailableStorageQuota", "Exiting");
	}
}
function getContent(pApplicationId, pContentGroupId) {
	log("getContent", "Entering");
	try {
		$('#content_progress_bar').show();
		var jqxhr = $
				.ajax({
					url : "/secured/" + pApplicationId + '/' + pContentGroupId
							+ "/content/",
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(content) {
							handleDisplayContent_Callback(content);
							// Google Analytics
							ga('send', {
								'hitType' : 'pageview',
								'page' : '/secured/' + pApplicationId + '/'
										+ pContentGroupId + '/content/',
								'title' : PageTitle.CONTENTS
							});
							// End Google Analytics
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
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
		handleError("getContent", err);
		// close wait div
		;
	} finally {
		log("getContent", "Exiting");
	}
}

function handleDisplayContent_Callback(pContent) {
	log("handleDisplayContent_Callback", "Entering");
	try {
		var lInnerHtml = "<div class=\"row\"> <div class=\"large-6 columns\">";
		for (var int = 0; int < pContent.length; int++) {
			var lContent = pContent[int];
			lInnerHtml += "<p><span data-tooltip class=\"has-tip\" title=\"Click here to view the content in this Content\"><a href=\"javascript:void(0)\" onclick=\"viewContent("
					+ lContent.id + ")\"><strong>";
			lInnerHtml += lContent.name;
			lInnerHtml += "</strong></a></span></p>";

			lInnerHtml += "<ul class=\"inline-list\"> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"viewContent("
					+ lContent.id
					+ ")\">view</a></li> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"editContent("
					+ lContent.id
					+ ")\">edit</a></li> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"deleteContent("
					+ lContent.id + ")\">delete</a></li></ul>";
			lInnerHtml += "<span id=\"content_id\" class=\"secondary radius label\">Content Id: "
					+ lContent.id + "</span>";
			lInnerHtml += "</div></div><hr>";
		}

		$('#content_list').empty().html(lInnerHtml);
		// progress bar
		$('#content_progress_bar').css("width", "100%");
		$('#content_progress_bar').hide();
	} catch (err) {
		handleError("handleDisplayContent_Callback", err);
	} finally {

		log("handleDisplayContent_Callback", "Exiting");
	}
}

function updateContentEnabled(pContentId, pContentEnabled, pElementName) {
	openWait();
	log("updateContentEnabled", "Entering");
	var lElementName = "#" + pElementName;

	try {
		var lDate = new Date();
		var lContentObj = {
			id : pContentId,
			enabled : pContentEnabled,
			timeUpdatedMs : lDate.getTime(),
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var lContentObjString = JSON.stringify(lContentObj, null, 2);
		var jqxhr = $
				.ajax({
					url : "/secured/content/enabled",
					type : "PUT",
					data : lContentObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : false,
					statusCode : {
						201 : function() {
							window.location.href = '/content';
						},
						400 : function(text) {
							try {
								$('#content_errors').html(
										getErrorMessages(text));
							} catch (err) {
								handleError("updateContentEnabled", err);
							}
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
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
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

		return false;
	} catch (err) {
		handleError("updateContentEnabled", err);
	} finally {
		log("updateContentEnabled", "Exiting");
	}
}
function displayContentStats(id, name) {
	log("displayContentStats", "Entering");
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/contentadstats/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(adStats) {
							$("#contentAccordian" + id).popover(
									{
										selector : '#contentAccordian' + id,
										content : 'Clicks: ' + adStats.clicks
												+ ', Impressions: '
												+ adStats.impressions
									});
							$("#contentAccordian" + id).popover('toggle');
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
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("displayContentStats", err);
		;
	} finally {
		log("displayContentStats", "Exiting");
	}
}

function editContent(id) {
	log("editContent", "Entering");
	try {
		$('#progress_bar_top, #progress_bar_bottom').show();
		$('.button').addClass('disabled');
		// reset the form contents
		$('#contentForm').trigger("reset");

		$('#content_errors').hide();

		$('#content_cancel_button').unbind();
		$('#content_cancel_button').click(function() {
			$('#content_create').hide();
			$('#content_list').show();
		});
		$('#content_list').hide();
		$('#content_create').show();
		// load entry info via ajax
		var url = "/secured/content/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(content) {

							$('#content_id').val(content.id);
							// set the application id
							$('#application_id').val(content.applicationId);
							// set the content group id
							$('#contentgroup_id').val(content.contentGroupId);

							$('#content_name').val(content.name);
							$('#content_description').val(content.description);
							$('#content_start_date').val(
									getDisplayDate(content.startDateIso8601));
							$('#content_end_date').val(
									getDisplayDate(content.endDateIso8601));
							// // add more
							$('#content_uri').val(content.uri);
							$('#content_type').val(content.type);
							if (content.type == 'image') {
								$('#content_type_image').click();
								// $('#content_type_image').attr('checked',
								// 'checked');
								// $('#content_type_video').attr('checked',
								// 'checked');
							} else if (content.type == 'video') {
								$('#content_type_video').click();
								// $('#content_type_video').attr('checked',
								// 'checked');
								// $('#content_type_image').attr('checked',
								// 'checked');
							}

							var dropBoxUrl = getDropboxUrl();
							// calculate
							var lAvailableStorageQuotaInMB = Math
									.round(((mQuota.storageLimitInBytes - mQuota.storageUsedInBytes) / 1024) / 1024);
							lAvailableStorageQuotaInMB = (lAvailableStorageQuotaInMB < 1) ? 1
									: lAvailableStorageQuotaInMB;
							var lPlanStorageQuotaInMB = Math
									.round(((mQuota.storageLimitInBytes) / 1024) / 1024);
							lPlanStorageQuotaInMB = (lPlanStorageQuotaInMB < 1) ? 1
									: lPlanStorageQuotaInMB;
							log("getAvailableStorageQuota",
									"Available storage in MB is "
											+ lAvailableStorageQuotaInMB);
							setupContentDropBox(dropBoxUrl,
									lAvailableStorageQuotaInMB,
									lPlanStorageQuotaInMB);
							$("#content_dropbox").hide();
							// reset
							$('#upload_content').unbind();
							$('#upload_content').bind('click', function() {
								$("#content_dropbox").slideToggle();
							});

							log("editContent", "Content enabled: "
									+ content.enabled);
							if (content.enabled == true) {
								$('#content_enabled')
										.attr('checked', 'checked');
							} else {
								$('#content_enabled').removeAttr('checked');
							}

							$('#content_save_button').html('update');

							// not using valid.fndtn.abide & invalid.fndtn.abide
							// as it
							// causes the form to be submitted twice. Instead
							// use the
							// deprecated valid & invalid
							$('#contentForm').on(
									'invalid',
									function() {
										var invalid_fields = $(this).find(
												'[data-invalid]');
										log(invalid_fields);
									}).on('valid', function() {
								updateContent();
							});

							$('#content_cancel_button').unbind();
							$('#content_cancel_button').click(function() {
								$('#content_create').hide();
								$('#content_list').show();
							});

							$('#content_errors').empty();
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
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
						$('#progress_bar_top, #progress_bar_bottom').css(
								"width", "100%");
						$('#progress_bar_top, #progress_bar_bottom').hide();
						$('.button').removeClass('disabled');
						log(xhr.status);
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editContent", err);
	} finally {
		log("editContent", "Exiting");
	}
}

function selectedContent(id) {
	log("selectedContent", "Entering");
	try {
		// load entry info via sync call
		var url = "/secured/content/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : false,
					statusCode : {
						200 : function(content) {
							mSelectedContent = content;
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
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
		handleError("selectedContent", err);
	} finally {
		log("selectedContent", "Entering");
	}
}
function viewContent(pContentId) {
	// load entry info via ajax
	log("viewContent", "Entering");
	try {

		var url = "/secured/content/" + pContentId;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(content) {

							if (content.type == 'image') {
								var _url = "/contentserver/dropbox/"
										+ content.uri;
								log("viewImage", _url);
								$("#image_widget").attr("src", _url);
								// close wait div

								$('#view_image_label').html(content.name);
								$('#view_image_done_button').unbind();
								$('#view_image_done_button').one(
										'click',
										function() {
											try {
												$('#view_image_container')
														.foundation('reveal',
																'close');
											} catch (err) {
												handleError("viewVideo", err);
											}
										});

								$('#view_image_container').foundation('reveal',
										'open');
							} else if (content.type == 'video') {
								var _url = "/contentserver/dropbox/"
										+ content.uri;
								log("viewVideo", _url);
								// reset
								$("#jquery_jplayer_1").jPlayer("clearMedia");
								$("#jquery_jplayer_1")
										.jPlayer(
												"setMedia",
												{
													m4v : _url,
													ogv : _url,
													webmv : _url,
													poster : "/resources/images/mavin_logo_orange_on_white_480x271.jpg"
												});

								$('#view_video_title').html(content.name);
								$('#view_video_label').html(content.name);

								$('#view_video_done_button').unbind();
								$('#view_video_done_button').one(
										'click',
										function() {
											try {
												$("#jquery_jplayer_1").jPlayer(
														"stop");
												$('#jp_container_1')
														.foundation('reveal',
																'close');
											} catch (err) {
												handleError("viewVideo", err);
											}
										});
								$('#jp_container_1').foundation('reveal',
										'open');
							}

						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
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
		handleError("viewContent", err);
	} finally {

		log("viewContent", "Exiting");
	}

}
function newContent() {
	log("newContent", "Entering");
	try {
		// reset the form contents
		$('#contentForm').trigger("reset");

		// set the application id
		$('#application_id').val(mSelectedApplication.id);
		// set the content group id
		$('#contentgroup_id').val(mSelectedContentGroup.id);
		$('#content_id').val('');
		$('#content_errors').hide();

		$('#content_name').val('');
		$('#content_description').val('');

		// $('#content_start_date').val(getCurrentDisplayDate());
		// $('#content_end_date').val('');
		// default to the dates set for the content group
		$('#content_start_date').val(
				getDisplayDate(mSelectedContentGroup.startDateIso8601));
		$('#content_end_date').val(
				getDisplayDate(mSelectedContentGroup.endDateIso8601));

		$('#content_userid').val('');

		// set default
		$('#content_enabled').attr('checked', 'checked');

		var dropBoxUrl = getDropboxUrl();
		// calculate
		var lAvailableStorageQuotaInMB = Math
				.round(((mQuota.storageLimitInBytes - mQuota.storageUsedInBytes) / 1024) / 1024);
		lAvailableStorageQuotaInMB = (lAvailableStorageQuotaInMB < 1) ? 1
				: lAvailableStorageQuotaInMB;
		var lPlanStorageQuotaInMB = Math
				.round(((mQuota.storageLimitInBytes) / 1024) / 1024);
		lPlanStorageQuotaInMB = (lPlanStorageQuotaInMB < 1) ? 1
				: lPlanStorageQuotaInMB;
		log("getAvailableStorageQuota", "Available storage in MB is "
				+ lAvailableStorageQuotaInMB);
		setupContentDropBox(dropBoxUrl, lAvailableStorageQuotaInMB,
				lPlanStorageQuotaInMB);
		// $('#view_ad_video').hide();
		// $('#view_video').hide();

		$("#content_dropbox").hide();
		// reset
		$('#upload_content').unbind();
		$('#upload_content').bind('click', function() {
			$("#content_dropbox").slideToggle();
		});

		$('#content_errors').empty();

		$('#content_save_button').html('create');
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#contentForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			createContent();
		});
		$('#content_cancel_button').unbind();
		$('#content_cancel_button').click(function() {
			$('#content_create').hide();
			$('#content_list').show();
		});

		$('#content_list').hide();
		$('#content_create').show();

	} catch (err) {
		handleError("newContent", err);
	} finally {
		log("newContent", "Exiting");
	}
}

function createContent() {
	log("createContent", "Entering");
	try {
		$('#progress_bar_top, #progress_bar_bottom').show();
		$('.button').addClass('disabled');
		var lEnabled;
		if ($('#content_enabled').is(':checked')) {
			lEnabled = true;
		} else {
			lEnabled = false;
		}
		if ($('#content_type_image').is(':checked')) {
			$('#content_type').val('image');
		} else if ($('#content_type_video').is(':checked')) {
			$('#content_type').val('video');
		}

		var lDate = new Date();
		var lTimeCreated = lDate.getTime();

		var contentObj = {
			id : $('#content_id').val(),
			applicationId : $('#application_id').val(),
			contentGroupId : $('#contentgroup_id').val(),
			name : $('#content_name').val(),
			description : $('#content_description').val(),
			startDateIso8601 : getTransferDate($('#content_start_date').val()),
			endDateIso8601 : getTransferDate($('#content_end_date').val()),
			enabled : lEnabled,
			type : $('#content_type').val(),
			uri : $('#content_uri').val(),
			timeCreatedMs : lTimeCreated,
			timeCreatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : lTimeCreated,
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)

		};
		var contentObjString = JSON.stringify(contentObj, null, 2);
		// alert(contentObjString);
		// create via sync call
		var jqxhr = $
				.ajax({
					url : "/secured/content",
					type : "POST",
					data : contentObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						201 : function() {
							$('#content_create').hide();
							location.reload();
							// getContent(mSelectedApplication.id,
							// mSelectedContentGroup.id);
							// $('#content_list').show();
						},
						400 : function(text) {
							try {
								$('#content_errors').html(
										getErrorMessages(text));
								$('#content_errors').show();
							} catch (err) {
								handleError("createContent", err);
							}
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
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
						$('#progress_bar_top, #progress_bar_bottom').css(
								"width", "100%");
						$('.button').removeClass('disabled');
						log(xhr.status);
					}
				});

		return false;
	} catch (err) {
		handleError("createContent", err);
	} finally {
		log("createContent", "Exiting");
	}
}

function updateContent() {
	log("updateContent", "Entering");
	$('#progress_bar_top, #progress_bar_bottom').show();
	$('.button').addClass('disabled');
	var lEnabled;
	if ($('#content_enabled').is(':checked')) {
		lEnabled = true;
	} else {
		lEnabled = false;
	}

	if ($('#content_type_image').is(':checked')) {
		$('#content_type').val('image');
	} else if ($('#content_type_video').is(':checked')) {
		$('#content_type').val('video');
	}

	var lDate = new Date();
	try {
		var contentObj = {
			id : $('#content_id').val(),
			applicationId : $('#application_id').val(),
			contentGroupId : $('#contentgroup_id').val(),
			name : $('#content_name').val(),
			description : $('#content_description').val(),
			startDateIso8601 : getTransferDate($('#content_start_date').val()),
			endDateIso8601 : getTransferDate($('#content_end_date').val()),
			enabled : lEnabled,
			type : $('#content_type').val(),
			uri : $('#content_uri').val(),
			timeUpdatedMs : lDate.getTime(),
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var contentObjString = JSON.stringify(contentObj, null, 2);
		var jqxhr = $
				.ajax({
					url : "/secured/content",
					type : "PUT",
					data : contentObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function() {
							$('#content_create').hide();
							location.reload();
							// getContent(mSelectedApplication.id,
							// mSelectedContentGroup.id);
							// $('#content_list').show();
						},
						400 : function(text) {
							try {
								$('#content_errors').html(
										getErrorMessages(text));
								$('#content_errors').show();
							} catch (err) {
								handleError("updateContent", err);
							}
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
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
						$('#progress_bar_top, #progress_bar_bottom').css(
								"width", "100%");
						$('.button').removeClass('disabled');
						log(xhr.status);
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

		return false;
	} catch (err) {
		handleError("updateContent", err);
	} finally {
		log("updateContent", "Exiting");
	}
}

function deleteContent(id) {
	log("deleteContent", "Entering");
	try {
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayConfirm(
				"Are you sure you want to delete this Content?",
				function() {
					wait("confirm_wait");
					var url = "/secured/content/" + id + "/" + _timeUpdatedMs
							+ "/" + _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $
							.ajax({
								url : url,
								type : "DELETE",
								contentType : "application/json",
								statusCode : {
									200 : function() {
										$('#content_create').hide();
										location.reload();
										// getContent(mSelectedApplication.id,
										// mSelectedContentGroup.id);
									},
									503 : function() {
										$('#content_errors')
												.html(
														'Unable to process the request. Please try again later');
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
					jqxhr.always(function(msg) {
						clearWait("confirm_wait");
					});
				});
	} catch (err) {
		handleError("deleteContent", err);
	} finally {
		log("deleteContent", "Exiting");
	}

}

/** *End Content***************************************** */
