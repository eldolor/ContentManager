function getProduct() {
	log("getProduct", "Entering");
	try {
		$('#product_progress_bar').show();
		var jqxhr = $
				.ajax({
					url : "/" + pApplicationId + '/' + pProductGroupId
							+ "/product/",
					type : "GET",
					productType : "application/json",
					async : true,
					statusCode : {
						200 : function(product) {
							if (mDisplayAsGrid) {
								handleDisplayProductAsGrid_Callback(product);
							} else {
								handleDisplayProduct_Callback(product);
							}
						},
						503 : function() {
							$('#product_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#product_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					}
				});

	} catch (err) {
		handleError("getProduct", err);
		// close wait div
		;
	} finally {
		log("getProduct", "Exiting");
	}
}

function handleDisplayProduct_Callback(pProduct) {
	log("handleDisplayProduct_Callback", "Entering");
	try {
		var lInnerHtml = '';
		for (var int = 0; int < pProduct.length; int++) {
			var lProduct = pProduct[int];

			lInnerHtml += "<div class='blog_product'> ";
			lInnerHtml += " <h3 class='gray'>";
			lInnerHtml += lProduct.name;
			lInnerHtml += "</h3>";
			lInnerHtml += "<div class='blog_product_details float_left'>";
			var lId = '';
			if (int == 0) {
				lId += " id='first_product_id' ";
			}

			lInnerHtml += "<h7 class='gray' " + lId + ">Id: " + lProduct.id
					+ " </h7>";

			lInnerHtml += "<ul>";

			if (int == 0) {
				lInnerHtml += "<li class='light_gray'><a id='first_product' class='small green' href='javascript:void(0)' onclick='viewProduct(";
			} else {
				lInnerHtml += "<li class='light_gray'><a class='small green' href='javascript:void(0)' onclick='viewProduct(";
			}
			lInnerHtml += lProduct.id;
			lInnerHtml += ")'><i class='fi-page light_gray'></i>&nbsp;view</a></li> <li>|</li><li class='light_gray'><a class='small' href='javascript:void(0)' onclick='editProduct("
					+ lProduct.id
					+ ")'><i class='fi-page-edit light_gray'></i>&nbsp;edit</a></li><li>|</li> <li class='light_gray'><a class='small' href='javascript:void(0)' onclick='deleteProduct("
					+ lProduct.id
					+ ")'><i class='fi-page-delete light_gray'></i>&nbsp;delete</a></li>"
					+ "<li class='light_gray'><a class='small' href='javascript:void(0)' onclick='moveProduct("
					+ lProduct.id
					+ ", "
					+ lProduct.applicationId
					+ ")'><i class='fi-eject light_gray'></i>&nbsp;move</a></li>";
			lInnerHtml += "</ul>";
			lInnerHtml += "</div>";
			var lEpochDate = (lProduct.timeUpdatedMs == null) ? lProduct.timeCreatedMs
					: lProduct.timeUpdatedMs;
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
			lInnerHtml += "<div class='clearfix'></div><div class='blog_product_details float_left'><p class='light_gray'>"
					+ lProduct.description + "</p>";
			lInnerHtml += "</div>";
			lInnerHtml += "<div class='clearfix'></div><div class='separator'></div>";
			lInnerHtml += "</div>";

		}

		$('#product_list').empty().html(lInnerHtml);
		// progress bar
		$('#product_progress_bar').css("width", "100%");
		$('#product_progress_bar').hide();
	} catch (err) {
		handleError("handleDisplayProduct_Callback", err);
	} finally {

		log("handleDisplayProduct_Callback", "Exiting");
	}
}

function handleDisplayProductAsGrid_Callback(pProduct) {
	log("handleDisplayProductAsGrid_Callback", "Entering");
	try {
		var lInnerHtml = '';
		lInnerHtml += "<ul id='Grid'> ";
		for (var int = 0; int < pProduct.length; int++) {
			var lProduct = pProduct[int];
			var lProductName = lProduct.name;
			lProductName = lProductName.trunc(11, true);

			lInnerHtml += "<li class='mix large-4 medium-4 columns "
					+ lProduct.type + "'>";
			lInnerHtml += "<div>";
			var lImgId = lProduct.id + "_img";
			if (lProduct.type == 'image' && (lProduct.uri)) {
				displayThumbnailImage(lImgId, lProduct.id, function(pImgId,
						pUrl) {
					$('#' + pImgId).attr('src', pUrl + "=s200-c");
				}, true);

				lInnerHtml += "<img  class='thumb' src='/resources/images/cm/page-loader.gif' alt='"
						+ lProduct.name
						+ "' id='"
						+ lImgId
						+ "' onclick='viewProduct(" + lProduct.id + ")'>";
			} else if (lProduct.type == 'image' && (!lProduct.uri)) {
				lInnerHtml += "<img  class='thumb' src='/resources/images/cm/image_icon_2.png' alt='"
						+ lProduct.name + "'>";
			} else if (lProduct.type == 'video') {
				lInnerHtml += "<img class='thumb' src='/resources/images/cm/video_icon.jpg' alt='"
						+ lProduct.name
						+ "' onclick='viewProduct("
						+ lProduct.id + ")'>";
				// lInnerHtml += "<video width='320' height='240' controls>";
				// lInnerHtml += " <source
				// src='https://00e9e64bac197ada5c32f2fd799f3f31a46040011bc915d504-apidata.googleuserproduct.com/download/storage/v1_internal/b/skok-dev.appspot.com/o/media%2FL2FwcGhvc3RpbmdfcHJvZC9ibG9icy9BRW5CMlVxMlNCdzI3NzlNOHdHRXc1WktETUZlS0UxTHM5SUtZY1VtQzlnMTRxaUwtbk5JT3Jua180cW1UU0JxSHhqWXducXBybjVsWWRBRVU4ckdEU3hHUjl1bEZlQUhudy5pNzczOTY2eWFUM2pfbXhJ?qk=AD5uMEsYaw8KgTxfq81u9tSEnxt9Syi4RGOVNBEsxLh_g1KfRL-mIjuGK0g9QSTIAOyXv5VTED_B-jMlI6iTarANypTpLqgUqtterWs7xbhLLFcpkX8G9gnRkl3dAIF_08R8_kmILgJk_gVm6eMWflIr6ZDyPypBuKUIAZaXrWI8H4zGD1cEcR4ZZAcy4ShQfHWK6DJJU2z0pZkA5a_QfFz1iJXZXtuSF8jvVWUp0vZt_r-Jo2u-z_tpVeq5qYhVgiqFSMeh4oOVp_1nw9zasFfszauDKUmUsskj5ngC0IDMt8w5rKBGRQV9VUXd0f3d56Pif-FURdj6sffGmBSBrmms56Ukc4AMmfe52-8aO3ILWjtEcClmr5cLLIESlX9N-Pwq2g_aSxeay0May4ZcGFdU8nSe_yMjtLPBj0GJIZ8aLBAjTBaoBmOzl1X7OP4cS7mkSzgpvrDEBThHtAdUFHA3hkvrXag38J90UtAo3arSPb1ujoqAHGcaFgTffnorUfG5JeenJlQX_ta2Hvr8U9Etz7Sp0yHY4svUi-s957TwYcjf7zZq6CoGu9QfM85RMyffZRqAHb0KZK_L_kNTr0N9dzj3TwyjRxBc3sZn0dZO_pflzokiAGtlp-OE0iuCe_GM3OTRdAwkU_YNKfnZx3jrVsO_2tdGk0_EtccFyG0MYBO5yWv2SPvF-VRgqBEe-eummDwukeVkOKP32Ehb7B8_8apL371yOAi6z0sMTW3cdUs3bzlVYkoGWfo0nAxMlR-N957UN7RoU56SGyt6yMKjDBpxKufFCCsqmSmLAD6L1A1pgfkYBLOVbA0GhLQ0CdlkChYuzlk1GUYYU6C7sP_s8QFFQm_m4taYH1BqQsSOQIyhWG_wxhq0hZfh8FBCP0mHdpXqQXjwOlcFFsiFw4lWNkETBaOjJ4zxlEbSi802kLGZJZAle3PjUU_OxZBCQkC_u6o1aVeg'
				// type='video/mp4'>";
				// lInnerHtml += "Your browser does not support the video tag.";
				// lInnerHtml += "</video>"
			}
			lInnerHtml += "<div class='green detail text-center'>";
			lInnerHtml += lProductName;
			lInnerHtml += "<br>";
			if (int == 0) {
				lInnerHtml += "<span class='white'><a id='first_product' class='small' href='javascript:void(0)' onclick='editProduct("
			} else {
				lInnerHtml += "<span class='white'><a class='small' href='javascript:void(0)' onclick='editProduct("
			}
			lInnerHtml += lProduct.id;
			lInnerHtml += ")'><i class='fi-page-edit light_gray'></i>&nbsp;edit</a></span>&nbsp;<span class='white'><a class='small' href='javascript:void(0)' onclick='deleteProduct("
					+ lProduct.id
					+ ")'><i class='fi-page-delete light_gray'></i>&nbsp;delete</a></span>"
					+ "<span class='white'><a class='small' href='javascript:void(0)' onclick='moveProduct("
					+ lProduct.id
					+ ", "
					+ lProduct.applicationId
					+ ")'><i class='fi-eject light_gray'></i>&nbsp;move</a></span>";
			lInnerHtml += "</div>";
			lInnerHtml += "</div>";
			lInnerHtml += "</li>";

		}
		lInnerHtml += "</ul>";
		// log("handleDisplayProductAsGrid_Callback", lInnerHtml);
		$('#product_list').empty().html(lInnerHtml);
		// progress bar
		$('#product_progress_bar').css("width", "100%");
		$('#product_progress_bar').hide();
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
		handleError("handleDisplayProductAsGrid_Callback", err);
	} finally {

		log("handleDisplayProductAsGrid_Callback", "Exiting");
	}
}

function editProduct(id) {
	log("editProduct", "Entering");
	try {
		$('#progress_bar_top, #progress_bar_bottom').show();
		$('.button').addClass('disabled');
		// reset the form products
		$('#productForm').trigger("reset");

		$('#cm_errors_container').hide();

		$('#product_cancel_button').unbind();
		$('#product_cancel_button').click(function() {
			$('#product_create').hide();
			$('#product_list').show();
		});
		$('#product_list').hide();
		$('#product_create').show();
		// load entry info via ajax
		var url = "/product/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					productType : "application/json",
					async : true,
					statusCode : {
						200 : function(product) {

							$('#product_id').val(product.id);
							$('#product_brand').val(product.brand);
							$('#product_type').val(product.type);
							$('#product_name').val(product.name);
							$('#product_description').val(product.description);
							$('#product_ingredients').val(product.ingredients);
							$('#product_allergens').val(product.allergens);
							$('#product_available_sizes').val(
									product.availableSizes);

							// // add more
							$('#product_uri').val(product.uri);
							$('#product_media_type').val(product.mediaType);
							if (product.mediaType == 'image') {
								$('#product_media_type_image').click();
								// $('#product_type_image').attr('checked',
								// 'checked');
								// $('#product_type_video').attr('checked',
								// 'checked');
							} else if (product.type == 'video') {
								$('#product_media_type_video').click();
								// $('#product_type_video').attr('checked',
								// 'checked');
								// $('#product_type_image').attr('checked',
								// 'checked');
							}

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
								setupProductDropBox(
										dropBoxUrl,
										(lStorageQuota.storageLimitInBytes - lStorageQuota.storageUsedInBytes),
										getDisplayUpgradeMessage(lStorageQuota));
							// $("#product_dropbox").hide();
							// reset
							// $('#upload_product').unbind();
							// $('#upload_product').bind('click', function() {
							// $("#product_dropbox").slideToggle();
							// });

							$('#product_save_button').html('update');

							// not using valid.fndtn.abide & invalid.fndtn.abide
							// as it
							// causes the form to be submitted twice. Instead
							// use the
							// deprecated valid & invalid
							$('#productForm').on(
									'invalid',
									function() {
										var invalid_fields = $(this).find(
												'[data-invalid]');
										log(invalid_fields);
									}).on(
									'valid',
									function() {
										updateProduct();
										// Google Analytics
										ga('send', 'event', Category.CONTENT,
												Action.UPDATE);
										// End Google Analytics
									});

							$('#product_cancel_button').unbind();
							$('#product_cancel_button').click(
									function() {
										$('#product_create').hide();
										$('#product_list').show();
										// Google Analytics
										ga('send', 'event', Category.CONTENT,
												Action.CANCEL);
										// End Google Analytics
									});

							$('#product_errors').empty();
						},
						503 : function() {
							$('#product_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#product_errors')
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
		handleError("editProduct", err);
	} finally {
		log("editProduct", "Exiting");
	}
}

function selectedProduct(id) {
	log("selectedProduct", "Entering");
	try {
		// load entry info via sync call
		var url = "/product/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					productType : "application/json",
					async : false,
					statusCode : {
						200 : function(product) {
							mSelectedProduct = product;
						},
						503 : function() {
							$('#product_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#product_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					}
				});
	} catch (err) {
		handleError("selectedProduct", err);
	} finally {
		log("selectedProduct", "Entering");
	}
}

function displayThumbnailImage(pImgId, pProductId, pCallbackFunction, pIsAsync) {
	// load entry info via ajax
	log("displayThumbnailImage", "Entering");
	try {

		var url = "/contentserver/servingurl/" + pProductId;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					async : pIsAsync,
					productType : "application/json",
					statusCode : {
						200 : function(response) {
							if (response) {
								var lResponseJson = JSON.parse(response);
								;
								if (!lResponseJson.servingUrl) {
									log('The product has no image or video attached');
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
							$('#product_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#product_errors')
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

function displayProduct(pProduct) {
	// load entry info via ajax
	log("displayProduct", "Entering");
	try {

		var url = "/contentserver/servingurl/" + pProduct.id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					productType : "application/json",
					statusCode : {
						200 : function(response) {
							var lUrl = null;

							if (response) {
								var lResponseJson = JSON.parse(response);
								;
								if (!lResponseJson.servingUrl) {
									displayMessage('The product has no image or video attached');
									return;
								}
								lUrl = lResponseJson.servingUrl;
							}
							log("servingUrl", lUrl);

							if (pProduct.mediaType == 'image') {
								$("#image_widget").attr("src", lUrl);
								// close wait div

								$('#view_image_label').html(pProduct.name);
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
							} else if (pProduct.mediaType == 'video') {
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

								$('#view_video_title').html(pProduct.name);
								$('#view_video_label').html(pProduct.name);

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
							$('#product_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#product_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					}
				});
	} catch (err) {
		handleError("displayProduct", err);
	} finally {

		log("displayProduct", "Exiting");
	}
}
function viewProduct(pProductId) {
	// load entry info via ajax
	log("viewProduct", "Entering");
	try {

		var url = "/product/" + pProductId;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					productType : "application/json",
					statusCode : {
						200 : function(pProduct) {
							if ((pProduct) && (pProduct.uri)) {
								displayProduct(pProduct);
							} else {
								log("Either the product does not exist or the URI is null")
								displayMessage('The product has no image or video attached');
								return;
							}
						},
						503 : function() {
							$('#product_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#product_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					}
				});
	} catch (err) {
		handleError("viewProduct", err);
	} finally {

		log("viewProduct", "Exiting");
	}

}
function newProduct() {
	log("newProduct", "Entering");
	try {
		// reset the form products
		$('#productForm').trigger("reset");
		$('#product_id').val('');
		$('#product_brand').val('');
		$('#product_type').val('');
		$('#product_name').val('');
		$('#product_description').val('');
		$('#product_ingredients').val('');
		$('#product_allergens').val('');
		$('#product_available_sizes').val('');

		// // add more
		$('#product_uri').val('');
		$('#product_media_type').val(product.mediaType);

		var dropBoxUrl = getDropboxUrl();
		var lStorageQuota = null;
		for (var int = 0; int < mQuota.storageQuota.length; int++) {
			lStorageQuota = mQuota.storageQuota[int];
		}
		setupProductDropBox(
				dropBoxUrl,
				(lStorageQuota.storageLimitInBytes - lStorageQuota.storageUsedInBytes),
				getDisplayUpgradeMessage(lStorageQuota));

		$('#product_errors').empty();

		$('#product_save_button').html('create');
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#productForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			createProduct();
		});
		$('#product_cancel_button').unbind();
		$('#product_cancel_button').click(function() {
			$('#product_create').hide();
			$('#product_list').show();
		});

		$('#product_list').hide();
		$('#product_create').show();

	} catch (err) {
		handleError("newProduct", err);
	} finally {
		log("newProduct", "Exiting");
	}
}

function createProduct() {
	log("createProduct", "Entering");
	try {
		$('#progress_bar_top, #progress_bar_bottom').show();
		$('.button').addClass('disabled');
		if ($('#product_media_type_image').is(':checked')) {
			$('#product_media_type').val('image');
		} else if ($('#product_media_type_video').is(':checked')) {
			$('#product_media_type').val('video');
		}

		var lDate = new Date();
		var lTimeCreated = lDate.getTime();

		$('#product_id').val(product.id);
		$('#product_brand').val(product.brand);
		$('#product_type').val(product.type);
		$('#product_name').val(product.name);
		$('#product_description').val(product.description);
		$('#product_ingredients').val(product.ingredients);
		$('#product_allergens').val(product.allergens);
		$('#product_available_sizes').val(product.availableSizes);

		// // add more
		$('#product_uri').val(product.uri);
		$('#product_media_type').val(product.mediaType);

		var productObj = {
			id : $('#product_id').val(),
			brand : $('#product_brand').val(),
			type : $('#product_type').val(),
			name : $('#product_name').val(),
			description : $('#product_description').val(),
			ingredients : $('#product_ingredients').val(),
			allergens : $('#product_allergens').val(),
			availableSizes : $('#product_available_sizes').val(),
			mediaType : $('#product_media_type').val(),
			uri : $('#product_uri').val(),
			timeCreatedMs : lTimeCreated,
			timeCreatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : lTimeCreated,
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)

		};
		var productObjString = JSON.stringify(productObj, null, 2);
		// alert(productObjString);
		// create via sync call
		var jqxhr = $
				.ajax({
					url : "/product",
					type : "POST",
					data : productObjString,
					processData : false,
					dataType : "json",
					productType : "application/json",
					async : true,
					statusCode : {
						201 : function() {
							$('#product_create').hide();
							location.reload();
							// getProduct(mSelectedApplication.id,
							// mSelectedProductGroup.id);
							// $('#product_list').show();
						},
						400 : function(text) {
							try {
								$('#product_errors').html(
										getErrorMessages(text));
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("createProduct", err);
							}
						},
						503 : function() {
							$('#product_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#product_errors')
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
		handleError("createProduct", err);
	} finally {
		log("createProduct", "Exiting");
	}
}

function updateProduct() {
	log("updateProduct", "Entering");
	try {
		$('#progress_bar_top, #progress_bar_bottom').show();
		$('.button').addClass('disabled');
		if ($('#product_media_type_image').is(':checked')) {
			$('#product_media_type').val('image');
		} else if ($('#product_media_type_video').is(':checked')) {
			$('#product_media_type').val('video');
		}

		var lDate = new Date();
		var lTimeCreated = lDate.getTime();

		$('#product_id').val(product.id);
		$('#product_brand').val(product.brand);
		$('#product_type').val(product.type);
		$('#product_name').val(product.name);
		$('#product_description').val(product.description);
		$('#product_ingredients').val(product.ingredients);
		$('#product_allergens').val(product.allergens);
		$('#product_available_sizes').val(product.availableSizes);

		// // add more
		$('#product_uri').val(product.uri);
		$('#product_media_type').val(product.mediaType);

		var productObj = {
			id : $('#product_id').val(),
			brand : $('#product_brand').val(),
			type : $('#product_type').val(),
			name : $('#product_name').val(),
			description : $('#product_description').val(),
			ingredients : $('#product_ingredients').val(),
			allergens : $('#product_allergens').val(),
			availableSizes : $('#product_available_sizes').val(),
			mediaType : $('#product_media_type').val(),
			uri : $('#product_uri').val(),
			timeUpdatedMs : lTimeCreated,
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)

		};
		var productObjString = JSON.stringify(productObj, null, 2);
		// alert(productObjString);
		// create via sync call
		var jqxhr = $
				.ajax({
					url : "/product",
					type : "PUT",
					data : productObjString,
					processData : false,
					dataType : "json",
					productType : "application/json",
					async : true,
					statusCode : {
						201 : function() {
							$('#product_create').hide();
							location.reload();
							// getProduct(mSelectedApplication.id,
							// mSelectedProductGroup.id);
							// $('#product_list').show();
						},
						400 : function(text) {
							try {
								$('#product_errors').html(
										getErrorMessages(text));
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("createProduct", err);
							}
						},
						503 : function() {
							$('#product_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#product_errors')
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
		handleError("updateProduct", err);
	} finally {
		log("updateProduct", "Exiting");
	}
}

function deleteProduct(id) {
	log("deleteProduct", "Entering");
	try {
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayConfirm(
				"Are you sure you want to delete this Product?",
				function() {
					wait("confirm_wait");
					var url = "/product/" + id + "/" + _timeUpdatedMs + "/"
							+ _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $
							.ajax({
								url : url,
								type : "DELETE",
								productType : "application/json",
								statusCode : {
									200 : function() {
										// Google Analytics
										ga('send', 'event', Category.CONTENT,
												Action.DELETE);
										// End Google Analytics
										$('#product_create').hide();
										location.reload();
										// getProduct(mSelectedApplication.id,
										// mSelectedProductGroup.id);
									},
									503 : function() {
										$('#product_errors')
												.html(
														'Unable to process the request. Please try again later');
										$('#cm_errors_container').show();
									}
								},
								error : function(xhr, textStatus, errorThrown) {
									log(errorThrown);
									$('#product_errors')
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
		handleError("deleteProduct", err);
	} finally {
		log("deleteProduct", "Exiting");
	}
}
function moveProduct(pId, pApplicationId) {
	log("moveProduct", "Entering");
	try {
		getProductGroups(pId, pApplicationId);

	} catch (err) {
		handleError("moveProduct", err);
	} finally {
		log("moveProduct", "Exiting");
	}

}

function buildProductList(pId, pApplicationId, pProductGroups) {
	log("buildProductList", "Entering");
	try {
		var lList = '<label>Please select a Product Group<select id="select_product_groups">';
		for (var int = 0; int < pProductGroups.length; int++) {
			var lProductGroup = pProductGroups[int];
			lList += '<option value="';
			lList += lProductGroup.id;
			lList += '">';
			lList += lProductGroup.name;
			lList += '</option>';
		}
		lList += '</select></label>';
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayMoveProductConfirm(lList, function() {
			var lSelectedProductGroup = $('#select_product_groups').val();

			var url = "/product/move/" + pId + "/" + lSelectedProductGroup
					+ "/" + pApplicationId + "/" + _timeUpdatedMs + "/"
					+ _timeUpdatedTimeZoneOffsetMs;
			var jqxhr = $.ajax({
				url : url,
				type : "PUT",
				productType : "application/json",
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
		handleError("buildProductList", err);
		// close wait div
		;
	} finally {
		log("buildProductList", "Exiting");
	}

}
function displayMoveProductConfirm(pList, pCallback) {
	log("displayMoveProductConfirm", "Entering");
	try {
		$("#product_group_list").html(pList);
		// if the user clicks "yes"
		$('#move_confirm_button').bind('click', function() {
			// call the callback
			if ($.isFunction(pCallback)) {
				pCallback.apply();
			}
			$('#move_product_modal').foundation('reveal', 'close');
		});
		$('#move_product_modal').foundation('reveal', 'open');
	} catch (err) {
		handleError("displayMoveProductConfirm", err);
		// close wait div
		;
	} finally {
		log("displayMoveProductConfirm", "Exiting");
	}

}

function restoreProduct(pApplicationId, pProductGroupId) {
	log("restoreProduct", "Entering");
	try {
		getDeletedProduct(pApplicationId, pProductGroupId);

	} catch (err) {
		handleError("restoreProduct", err);
	} finally {
		log("restoreProduct", "Exiting");
	}

}

function getDeletedProduct(pApplicationId, pProductGroupId) {
	log("getDeletedProduct", "Entering");
	try {

		var jqxhr = $.ajax({
			url : "/" + pApplicationId + '/' + pProductGroupId
					+ "/product/deleted",
			type : "GET",
			productType : "application/json",
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
		handleError("getDeletedProduct", err);
		// close wait div
		;
	} finally {
		log("getDeletedProduct", "Exiting");
	}
}

function buildList(pListItems) {
	log("buildList", "Entering");
	try {
		var lList = '<label>Please select a Product<select id="select_from_deleted">';
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
				var url = "/product/restore/" + lSelected + "/"
						+ _timeUpdatedMs + "/" + _timeUpdatedTimeZoneOffsetMs;
				var jqxhr = $.ajax({
					url : url,
					type : "PUT",
					productType : "application/json",
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
		$('#restore_confirm_button').unbind();
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
/** *End Product***************************************** */
