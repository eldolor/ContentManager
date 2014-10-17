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
						$('#cm_errors_container').show();
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
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					}
				});
	} catch (err) {
		handleError("selectedContentGroup", err);
	} finally {
		log("selectedContentGroup", "Entering");
	}
}

function setAvailableStorageQuota(id, isAsync) {
	try {
		log("getAvailableStorageQuota", "Entering");

		var url = "/secured/quota/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : isAsync,
					statusCode : {
						200 : function(quota) {
							mQuota = quota;
						},
						503 : function() {
							$('#content_errors').html(
									'Unable to get available storage quota');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					}
				});
	} catch (err) {
		handleError("getAvailableStorageQuota", err);

	} finally {
		log("getAvailableStorageQuota", "Exiting");
	}
}
function setAllAvailableStorageQuota(isAsync) {
	try {
		log("getAvailableStorageQuota", "Entering");

		var url = "/secured/quota/";
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : isAsync,
					statusCode : {
						200 : function(quota) {
							mQuota = quota;
						},
						503 : function() {
							$('#content_errors').html(
									'Unable to get available storage quota');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
		var lInnerHtml = '';
		for (var int = 0; int < pContent.length; int++) {
			var lContent = pContent[int];

			lInnerHtml += "<div class='blog_content'> ";
			lInnerHtml += " <h3 class='gray'>";
			lInnerHtml += lContent.name;
			lInnerHtml += "</h3>";
			lInnerHtml += "<div class='blog_content_details float_left'>";
			if (int == 0) {
				lInnerHtml += "<ul> <li id='first_content_id' class='green'>Content Id: "
						+ lContent.id
						+ " </li><li>|</li><li class='light_gray'>";
				lInnerHtml += "<a id='first_content' class='small green' href='javascript:void(0)' onclick='viewContent(";
			} else {
				lInnerHtml += "<ul> <li class='green'>Content Id: "
						+ lContent.id
						+ " </li><li>|</li><li class='light_gray'>";
				lInnerHtml += "<a class='small green' href='javascript:void(0)' onclick='viewContent(";
			}
			lInnerHtml += lContent.id;
			lInnerHtml += ")'><i class='fi-page light_gray'></i>&nbsp;view</a></li> <li>|</li><li class='light_gray'><a class='small' href='javascript:void(0)' onclick='editContent("
					+ lContent.id
					+ ")'><i class='fi-page-edit light_gray'></i>&nbsp;edit</a></li><li>|</li> <li class='light_gray'><a class='small' href='javascript:void(0)' onclick='deleteContent("
					+ lContent.id
					+ ")'><i class='fi-page-delete light_gray'></i>&nbsp;delete</a></li>"
					+ "<li class='light_gray'><a class='small' href='javascript:void(0)' onclick='moveContent("
					+ lContent.id
					+ ", "
					+ lContent.applicationId
					+ ")'><i class='fi-eject light_gray'></i>&nbsp;move</a></li>";
			lInnerHtml += "</ul>";
			lInnerHtml += "</div>";
			var lEpochDate = (lContent.timeUpdatedMs == null) ? lContent.timeCreatedMs
					: lContent.timeUpdatedMs;
			var lDisplayDate = getFormattedDisplayDate(lEpochDate, "ll");
			var lSplit = lDisplayDate.split(",");
			var lYear = lSplit[1];
			var lSplitM = lSplit[0].split(" ");
			var lMonth = lSplitM[0];
			var lDate = lSplitM[1];

			lInnerHtml += "<div class='blog_date green text_center'> <span>";
			lInnerHtml += lDate;
			lInnerHtml += "</span>";
			lInnerHtml += "<div>";
			lInnerHtml += lMonth + "</div>";
			lInnerHtml += "</div>";

			lInnerHtml += "<div class='blog_comments text_center green'>";
			lInnerHtml += lYear;
			lInnerHtml += "</div>";
			lInnerHtml += "<div class='blog_content_details float_left'><p class='light_gray'>"
					+ lContent.description + "</p>";
			lInnerHtml += "</div>";
			lInnerHtml += "<div class='clearfix'></div><div class='separator'></div>";
			lInnerHtml += "</div>";

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
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
						$('#cm_errors_container').show();
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

		$('#cm_errors_container').hide();

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

							// tags
							if (content.tags != null) {
								for (var int = 0; int < content.tags.length; int++) {
									$('#content_tags')
											.addTag(content.tags[int]);
								}
							}
							// end tags

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
							$('#content_type_image').unbind();
							$('#content_type_image').bind('click', function() {
								$('#content_tags').addTag('image');
								$('#content_tags').removeTag('video');
							});
							$('#content_type_video').unbind();
							$('#content_type_video').bind('click', function() {
								$('#content_tags').addTag('video');
								$('#content_tags').removeTag('image');
							});

							var dropBoxUrl = getDropboxUrl();
							var lStorageQuota = null;
							/**
							 * Begin: to support 'edit' functionality from
							 * Search
							 */
							if (mQuota == null) {
								setAllAvailableStorageQuota(false);
							}
							/**
							 * End: to support 'edit' functionality from Search
							 */

							for (var int = 0; int < mQuota.storageQuota.length; int++) {
								lStorageQuota = mQuota.storageQuota[int];
							}
							if (lStorageQuota != null)
								setupContentDropBox(
										dropBoxUrl,
										(lStorageQuota.storageLimitInBytes - lStorageQuota.storageUsedInBytes),
										getDisplayUpgradeMessage(lStorageQuota));
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
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
function getDisplayUpgradeMessage(pStorageQuota) {
	var lAvailableStorageQuotaMessage = convertBytes((pStorageQuota.storageLimitInBytes - pStorageQuota.storageUsedInBytes));

	var lPlanStorageQuotaMessage = convertBytes(pStorageQuota.storageLimitInBytes);

	var lDisplayUpgradeMessage = ' The selected file is too large! Your plan allows for '
			+ lPlanStorageQuotaMessage
			+ ' of total storage per application. You only have  '
			+ lAvailableStorageQuotaMessage
			+ " of storage available for this application. Please upgrade your Plan to add more storage";
	log(lDisplayUpgradeMessage);
	return lDisplayUpgradeMessage;
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
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
								if (content.uri != null) {
									$("#image_widget").attr("src", _url);
									// close wait div

									$('#view_image_label').html(content.name);
									$('#view_image_done_button').unbind();
									$('#view_image_done_button').one(
											'click',
											function() {
												try {
													$('#view_image_container')
															.foundation(
																	'reveal',
																	'close');
												} catch (err) {
													handleError("viewVideo",
															err);
												}
											});

									$('#view_image_container').foundation(
											'reveal', 'open');
								} else {
									displayMessage('The content has no image or video attached');
								}
							} else if (content.type == 'video') {
								var _url = "/contentserver/dropbox/"
										+ content.uri;
								log("viewVideo", _url);
								if (content.uri != null) {
									// reset
									$("#jquery_jplayer_1")
											.jPlayer("clearMedia");
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
													$("#jquery_jplayer_1")
															.jPlayer("stop");
													$('#jp_container_1')
															.foundation(
																	'reveal',
																	'close');
												} catch (err) {
													handleError("viewVideo",
															err);
												}
											});
									$('#jp_container_1').foundation('reveal',
											'open');
								} else {
									displayMessage('The content has no image or video attached');
								}
							}

						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
		$('#cm_errors_container').hide();

		$('#content_name').val('');
		$('#content_description').val('');
		// reset tags
		var toSplit = $('#content_tags').val().split(",");
		for (var i = 0; i < toSplit.length; i++) {
			$('#content_tags').removeTag(toSplit[i]);
		}
		// default for image
		$('#content_tags').addTag('image');

		$('#content_type_image').unbind();
		$('#content_type_image').bind('click', function() {
			$('#content_tags').addTag('image');
			$('#content_tags').removeTag('video');
		});
		$('#content_type_video').unbind();
		$('#content_type_video').bind('click', function() {
			$('#content_tags').addTag('video');
			$('#content_tags').removeTag('image');
		});

		// end tags

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
		var lStorageQuota = null;
		for (var int = 0; int < mQuota.storageQuota.length; int++) {
			lStorageQuota = mQuota.storageQuota[int];
		}
		setupContentDropBox(
				dropBoxUrl,
				(lStorageQuota.storageLimitInBytes - lStorageQuota.storageUsedInBytes),
				getDisplayUpgradeMessage(lStorageQuota));

		// setupContentDropBox(dropBoxUrl,
		// (mQuota.storageLimitInBytes - mQuota.storageUsedInBytes),
		// getDisplayUpgradeMessage(mQuota));

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
		var jsonArrayTags = [];
		var toSplit = $('#content_tags').val().split(",");
		for (var i = 0; i < toSplit.length; i++) {
			jsonArrayTags.push(toSplit[i]);
		}

		var contentObj = {
			id : $('#content_id').val(),
			applicationId : $('#application_id').val(),
			contentGroupId : $('#contentgroup_id').val(),
			name : $('#content_name').val(),
			description : $('#content_description').val(),
			tags : jsonArrayTags,
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
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("createContent", err);
							}
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
	var jsonArrayTags = [];
	var toSplit = $('#content_tags').val().split(",");
	for (var i = 0; i < toSplit.length; i++) {
		jsonArrayTags.push(toSplit[i]);
	}
	// alert(JSON.stringify(jsonArrayTags, null, 2));
	var lDate = new Date();
	try {
		var contentObj = {
			id : $('#content_id').val(),
			applicationId : $('#application_id').val(),
			contentGroupId : $('#contentgroup_id').val(),
			name : $('#content_name').val(),
			description : $('#content_description').val(),
			tags : jsonArrayTags,
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
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("updateContent", err);
							}
						},
						503 : function() {
							$('#content_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
										$('#cm_errors_container').show();
									}
								},
								error : function(xhr, textStatus, errorThrown) {
									log(errorThrown);
									$('#content_errors')
											.html(
													'Unable to process the request. Please try again later');
									$('#cm_errors_container').show();
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
function moveContent(pId, pApplicationId) {
	log("moveContent", "Entering");
	try {
		getContentGroups(pId, pApplicationId);

	} catch (err) {
		handleError("moveContent", err);
	} finally {
		log("moveContent", "Exiting");
	}

}

function getContentGroups(pId, pApplicationId) {
	log("getContentGroups", "Entering");
	try {

		var jqxhr = $.ajax({
			url : "/secured/" + pApplicationId + "/contentgroups",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(contentGroups) {
					buildContentList(pId, pApplicationId, contentGroups);
				},
				503 : function() {
				}
			},
			error : function(xhr, textStatus, errorThrown) {
				log(errorThrown);
			}
		});

	} catch (err) {
		handleError("getContentGroups", err);
		// close wait div
		;
	} finally {
		log("getContentGroups", "Exiting");
	}
}

function buildContentList(pId, pApplicationId, pContentGroups) {
	log("buildContentList", "Entering");
	try {
		var lList = '<label>Please select a Content Group<select id="select_content_groups">';
		for (var int = 0; int < pContentGroups.length; int++) {
			var lContentGroup = pContentGroups[int];
			lList += '<option value="';
			lList += lContentGroup.id;
			lList += '">';
			lList += lContentGroup.name;
			lList += '</option>';
		}
		lList += '</select></label>';
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayMoveContentConfirm(lList, function() {
			var lSelectedContentGroup = $('#select_content_groups').val();

			var url = "/secured/content/move/" + pId + "/"
					+ lSelectedContentGroup + "/" + pApplicationId + "/"
					+ _timeUpdatedMs + "/" + _timeUpdatedTimeZoneOffsetMs;
			var jqxhr = $.ajax({
				url : url,
				type : "PUT",
				contentType : "application/json",
				statusCode : {
					200 : function() {
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
		});

	} catch (err) {
		handleError("buildContentList", err);
		// close wait div
		;
	} finally {
		log("buildContentList", "Exiting");
	}

}
function displayMoveContentConfirm(pList, pCallback) {
	log("displayMoveContentConfirm", "Entering");
	try {
		$("#content_group_list").html(pList);
		// if the user clicks "yes"
		$('#move_confirm_button').bind('click', function() {
			// call the callback
			if ($.isFunction(pCallback)) {
				pCallback.apply();
			}
			$('#move_content_modal').foundation('reveal', 'close');
		});
		$('#move_content_modal').foundation('reveal', 'open');
	} catch (err) {
		handleError("displayMoveContentConfirm", err);
		// close wait div
		;
	} finally {
		log("displayMoveContentConfirm", "Exiting");
	}

}
/** *End Content***************************************** */
