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
							if (mDisplayAsGrid) {
								handleDisplayContentAsGrid_Callback(content);
							} else {
								handleDisplayContent_Callback(content);
							}
							// Google Analytics
							ga('send', {
								'hitType' : 'pageview',
								'page' : '/secured/content',
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

function handleDisplayContentAsGrid_Callback(pContent) {
	log("handleDisplayContentAsGrid_Callback", "Entering");
	try {
		var lInnerHtml = '';
		lInnerHtml += "<ul id='Grid'> ";
		for (var int = 0; int < pContent.length; int++) {
			var lContent = pContent[int];
			var lContentName = lContent.name;
			lContentName = lContentName.trunc(11, true);

			lInnerHtml += "<li class='mix large-4 medium-4 columns "
					+ lContent.type + "'>";
			lInnerHtml += "<div>";
			var lImgId = lContent.id + "_img";
			if (lContent.type == 'image' && (lContent.uri)) {
				displayThumbnailImage(lImgId, lContent.id, function(pImgId,
						pUrl) {
					$('#' + pImgId).attr('src', pUrl + "=s200-c");
				}, true);

				lInnerHtml += "<img  class='thumb' src='/resources/images/cm/page-loader.gif' alt='"
						+ lContent.name
						+ "' id='"
						+ lImgId
						+ "' onclick='viewContent(" + lContent.id + ")'>";
			} else if (lContent.type == 'image' && (!lContent.uri)) {
				lInnerHtml += "<img  class='thumb' src='/resources/images/cm/image_icon_2.png' alt='"
						+ lContent.name + "'>";
			} else if (lContent.type == 'video') {
				lInnerHtml += "<img class='thumb' src='/resources/images/cm/video_icon.jpg' alt='"
						+ lContent.name
						+ "' onclick='viewContent("
						+ lContent.id + ")'>";
				// lInnerHtml += "<video width='320' height='240' controls>";
				// lInnerHtml += " <source
				// src='https://00e9e64bac197ada5c32f2fd799f3f31a46040011bc915d504-apidata.googleusercontent.com/download/storage/v1_internal/b/skok-dev.appspot.com/o/media%2FL2FwcGhvc3RpbmdfcHJvZC9ibG9icy9BRW5CMlVxMlNCdzI3NzlNOHdHRXc1WktETUZlS0UxTHM5SUtZY1VtQzlnMTRxaUwtbk5JT3Jua180cW1UU0JxSHhqWXducXBybjVsWWRBRVU4ckdEU3hHUjl1bEZlQUhudy5pNzczOTY2eWFUM2pfbXhJ?qk=AD5uMEsYaw8KgTxfq81u9tSEnxt9Syi4RGOVNBEsxLh_g1KfRL-mIjuGK0g9QSTIAOyXv5VTED_B-jMlI6iTarANypTpLqgUqtterWs7xbhLLFcpkX8G9gnRkl3dAIF_08R8_kmILgJk_gVm6eMWflIr6ZDyPypBuKUIAZaXrWI8H4zGD1cEcR4ZZAcy4ShQfHWK6DJJU2z0pZkA5a_QfFz1iJXZXtuSF8jvVWUp0vZt_r-Jo2u-z_tpVeq5qYhVgiqFSMeh4oOVp_1nw9zasFfszauDKUmUsskj5ngC0IDMt8w5rKBGRQV9VUXd0f3d56Pif-FURdj6sffGmBSBrmms56Ukc4AMmfe52-8aO3ILWjtEcClmr5cLLIESlX9N-Pwq2g_aSxeay0May4ZcGFdU8nSe_yMjtLPBj0GJIZ8aLBAjTBaoBmOzl1X7OP4cS7mkSzgpvrDEBThHtAdUFHA3hkvrXag38J90UtAo3arSPb1ujoqAHGcaFgTffnorUfG5JeenJlQX_ta2Hvr8U9Etz7Sp0yHY4svUi-s957TwYcjf7zZq6CoGu9QfM85RMyffZRqAHb0KZK_L_kNTr0N9dzj3TwyjRxBc3sZn0dZO_pflzokiAGtlp-OE0iuCe_GM3OTRdAwkU_YNKfnZx3jrVsO_2tdGk0_EtccFyG0MYBO5yWv2SPvF-VRgqBEe-eummDwukeVkOKP32Ehb7B8_8apL371yOAi6z0sMTW3cdUs3bzlVYkoGWfo0nAxMlR-N957UN7RoU56SGyt6yMKjDBpxKufFCCsqmSmLAD6L1A1pgfkYBLOVbA0GhLQ0CdlkChYuzlk1GUYYU6C7sP_s8QFFQm_m4taYH1BqQsSOQIyhWG_wxhq0hZfh8FBCP0mHdpXqQXjwOlcFFsiFw4lWNkETBaOjJ4zxlEbSi802kLGZJZAle3PjUU_OxZBCQkC_u6o1aVeg'
				// type='video/mp4'>";
				// lInnerHtml += "Your browser does not support the video tag.";
				// lInnerHtml += "</video>"
			}
			lInnerHtml += "<div class='green detail text-center'>";
			lInnerHtml += lContentName;
			lInnerHtml += "<br>";
			if (int == 0) {
				lInnerHtml += "<span class='white'><a id='first_content' class='small' href='javascript:void(0)' onclick='editContent("
			} else {
				lInnerHtml += "<span class='white'><a class='small' href='javascript:void(0)' onclick='editContent("
			}
			lInnerHtml += lContent.id;
			lInnerHtml += ")'>&nbsp;edit</a></span>&nbsp;<span class='white'><a class='small' href='javascript:void(0)' onclick='deleteContent("
					+ lContent.id
					+ ")'>&nbsp;delete</a></span>"
					+ "<span class='white'><a class='small' href='javascript:void(0)' onclick='moveContent("
					+ lContent.id
					+ ", "
					+ lContent.applicationId
					+ ")'></i>&nbsp;move</a></span>";
			lInnerHtml += "</div>";
			lInnerHtml += "</div>";
			lInnerHtml += "</li>";

		}
		lInnerHtml += "</ul>";
		// log("handleDisplayContentAsGrid_Callback", lInnerHtml);
		$('#content_list').empty().html(lInnerHtml);
		// progress bar
		$('#content_progress_bar').css("width", "100%");
		$('#content_progress_bar').hide();
		$("#portfolio_page .container").addClass("fadeInUp animated");
		$('#Grid').mixitup(); // needed for our work section
		$(".btn").addClass("bounceInUp animated");

		$('#our_work').waypoint(function() {
			$("#our_work .visible").css('visibility', 'visible');
			$("#our_work .visible").addClass("fadeInUp animated");
		}, {
			offset : 335
		});

	} catch (err) {
		handleError("handleDisplayContentAsGrid_Callback", err);
	} finally {

		log("handleDisplayContentAsGrid_Callback", "Exiting");
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
									}).on(
									'valid',
									function() {
										updateContent();
										// Google Analytics
										ga('send', 'event', Category.CONTENT,
												Action.UPDATE);
										// End Google Analytics
									});

							$('#content_cancel_button').unbind();
							$('#content_cancel_button').click(
									function() {
										$('#content_create').hide();
										$('#content_list').show();
										// Google Analytics
										ga('send', 'event', Category.CONTENT,
												Action.CANCEL);
										// End Google Analytics
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

function displayThumbnailImage(pImgId, pContentId, pCallbackFunction, pIsAsync) {
	// load entry info via ajax
	log("displayThumbnailImage", "Entering");
	try {

		var url = "/contentserver/servingurl/" + pContentId;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					async : pIsAsync,
					contentType : "application/json",
					statusCode : {
						200 : function(response) {
							if (response) {
								var lResponseJson = JSON.parse(response);
								;
								if (!lResponseJson.servingUrl) {
									log('The content has no image or video attached');
									return;
								}
								log("servingUrl", lResponseJson.servingUrl);
								// call the callback
								if ($.isFunction(pCallbackFunction)) {
									pCallbackFunction.apply(null, [ pImgId,
											lResponseJson.servingUrl ]);
								} else {
									return lResponseJson.servingUrl;
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
		handleError("displayThumbnailImage", err);
	} finally {

		log("displayThumbnailImage", "Exiting");
	}
}

function displayContent(pContent) {
	// load entry info via ajax
	log("displayContent", "Entering");
	try {

		var url = "/contentserver/servingurl/" + pContent.id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(response) {
							var lUrl = null;

							if (response) {
								var lResponseJson = JSON.parse(response);
								;
								if (!lResponseJson.servingUrl) {
									displayMessage('The content has no image or video attached');
									return;
								}
								lUrl = lResponseJson.servingUrl;
							}
							log("servingUrl", lUrl);

							// Google Analytics
							ga('send', 'event', Category.CONTENT, Action.VIEW);
							// End Google Analytics

							if (pContent.type == 'image') {
								$("#image_widget").attr("src", lUrl);
								// close wait div

								$('#view_image_label').html(pContent.name);
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
							} else if (pContent.type == 'video') {
								// reset
								$("#jquery_jplayer_1").jPlayer("clearMedia");
								$("#jquery_jplayer_1")
										.jPlayer(
												"setMedia",
												{
													m4v : lUrl,
													ogv : lUrl,
													webmv : lUrl,
													poster : "/resources/images/cm/logo-512x512.png"
												});

								$('#view_video_title').html(pContent.name);
								$('#view_video_label').html(pContent.name);

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
		handleError("displayContent", err);
	} finally {

		log("displayContent", "Exiting");
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
						200 : function(pContent) {
							if ((pContent) && (pContent.uri)) {
								displayContent(pContent);
							} else {
								log("Either the content does not exist or the URI is null")
								displayMessage('The content has no image or video attached');
								return;
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
			// Google Analytics
			ga('send', 'event', Category.CONTENT, Action.CREATE);
			// End Google Analytics
		});
		$('#content_cancel_button').unbind();
		$('#content_cancel_button').click(function() {
			$('#content_create').hide();
			$('#content_list').show();
			// Google Analytics
			ga('send', 'event', Category.CONTENT, Action.CANCEL);
			// End Google Analytics
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
										// Google Analytics
										ga('send', 'event', Category.CONTENT,
												Action.DELETE);
										// End Google Analytics
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
						// Google Analytics
						ga('send', 'event', Category.CONTENT, Action.MOVE);
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

function restoreContent(pApplicationId, pContentGroupId) {
	log("restoreContent", "Entering");
	try {
		getDeletedContent(pApplicationId, pContentGroupId);

	} catch (err) {
		handleError("restoreContent", err);
	} finally {
		log("restoreContent", "Exiting");
	}

}

function getDeletedContent(pApplicationId, pContentGroupId) {
	log("getDeletedContent", "Entering");
	try {

		var jqxhr = $.ajax({
			url : "/secured/" + pApplicationId + '/' + pContentGroupId
					+ "/content/deleted",
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
		handleError("getDeletedContent", err);
		// close wait div
		;
	} finally {
		log("getDeletedContent", "Exiting");
	}
}

function buildList(pListItems) {
	log("buildList", "Entering");
	try {
		var lList = '<label>Please select a Content<select id="select_from_deleted">';
		for (var int = 0; int < pListItems.length; int++) {
			var lItem = pListItems[int];
			lList += '<option value="';
			lList += lItem.id;
			lList += '">';
			lList += lItem.name;
			lList += '</option>';
		}
		lList += '</select></label>';
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayRestoreConfirm(lList, function() {
			var lSelected = $('#select_from_deleted').val();

			if (lSelected) {
				var url = "/secured/content/restore/" + lSelected + "/"
						+ _timeUpdatedMs + "/" + _timeUpdatedTimeZoneOffsetMs;
				var jqxhr = $.ajax({
					url : url,
					type : "PUT",
					contentType : "application/json",
					statusCode : {
						200 : function() {
							// Google Analytics
							ga('send', 'event', Category.CONTENT,
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
/** *End Content***************************************** */
