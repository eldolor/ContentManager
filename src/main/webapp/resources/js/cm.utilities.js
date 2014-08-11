/** Begin Utilities ******************************** */
function setErrors(pErrors) {
	log("setErrors", "Entering");
	try {

		mErrors = pErrors;
	} catch (err) {
		handleError("setErrors", err);
	} finally {
		log("setErrors", "Entering");
	}
}

function getDropboxUrl() {
	log("getDropboxUrl", "Entering")
	var url;
	try {

		var jqxhr = $.ajax({
			url : "/secured/dropbox/url",
			type : "GET",
			processData : false,
			async : false,
			statusCode : {
				200 : function(response) {
					url = response.url;
				}
			}
		});
	} catch (err) {
		handleError("getDropboxUrl", err);
	}
	log("getDropboxUrl", "Exiting")
	return url;
}
function getErrorMessages(text) {
	log("getErrorMessages", "Entering")
	try {
		log("getErrorMessages", text);
		var response = JSON.parse(JSON.stringify(text));
		var errors = eval(response.responseText);
		var message = '';
		for (var int = 0; int < errors.length; int++) {
			message += errors[int].description;
			message += ', ';
		}
		return message;

	} catch (err) {
		handleError("getErrorMessages", err);
	}
	log("getErrorMessages", "Exiting")
}

function getErrorMessage(pErrorCode, pErrors) {
	log("getErrorMessage", "Entering")
	try {
		log("getErrorMessage", pErrorCode);
		var message = '';
		for (var int = 0; int < pErrors.length; int++) {
			if (pErrors[int].code == pErrorCode) {
				message = pErrors[int].description;
			}
		}
		return message;

	} catch (err) {
		handleError("getErrorMessage", err);
	}
	log("getErrorMessage", "Exiting")
}
// TODO:
function isRoleAdmin() {
	log("isRoleAdmin", "Entering")
	var isRoleAdmin = false;
	try {
		// load entry info via ajax
		var url = "secured/isroleadmin/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(campaigns) {
					isRoleAdmin = true;
				}
			}
		});
	} catch (err) {
		handleError("isRoleAdmin", err);
	}
	log("isRoleAdmin", "Exiting")
	return isRoleAdmin;
}

function isRoleSuperAdmin() {
	log("isRoleSuperAdmin", "Entering")
	var isRoleSuperAdmin = false;
	try {
		// load entry info via ajax
		var url = "secured/isRoleSuperAdmin/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(campaigns) {
					isRoleSuperAdmin = true;
				}
			}
		});
	} catch (err) {
		handleError("isRoleSuperAdmin", err);
	}
	log("isRoleSuperAdmin", "Exiting")
	return isRoleSuperAdmin;
}

function getCurrentDisplayDate() {
	log("getCurrentDisplayDate", "Entering")
	try {
		return moment().format('\L');
	} catch (err) {
		handleError("getCurrentDisplayDate", err);
	}
	log("getCurrentDisplayDate", "Exiting")
}

function getDisplayDate(transferDate) {
	log("getDisplayDate", "Entering")
	try {
		if ((transferDate != null) && (transferDate != "")) {
			var _date = moment(transferDate);
			if (_date != null) {
				return _date.format('\L');
			} else {
				console.log("Unable to parse " + transferDate);
				return transferDate;
			}
		}
	} catch (err) {
		handleError("getDisplayDate", err);
	}
	log("getDisplayDate", "Exiting")
}
function getDisplayDateAndTime(transferDateAndTime) {
	log("getDisplayDateAndTime", "Entering")
	try {
		if ((transferDateAndTime != null) && (transferDateAndTime != "")) {
			var _date = moment(transferDateAndTime);
			if (_date != null) {
				return _date.format('MM/D/YYYY h:mm a Z');
			} else {
				console.log("Unable to parse " + transferDateAndTime);
				return transferDateAndTime;
			}
		}
	} catch (err) {
		handleError("getDisplayDateAndTime", err);
	}
	log("getDisplayDateAndTime", "Exiting")
}

function getTransferDate(displayDate) {
	log("getTransferDate", "Entering");
	try {
		if ((displayDate != null) && (displayDate != "")) {
			log("getTransferDate: Display date: ", displayDate);
			var _date = moment(displayDate).toDate();
			// Returns ISO 8601 string
			var _dateIso = _date.toISOString();
			log("getTransferDate ISO Date: ", _dateIso);
			return _dateIso;
		}
	} catch (err) {
		handleError("getTransferDate", err);
	}
	log("getTransferDate", "Exiting")
}

function getTransferDateAndTime(displayDateAndTime) {
	log("getTransferDateAndTime", "Entering");
	try {
		if ((displayDateAndTime != null) && (displayDateAndTime != "")) {
			log("getTransferDateAndTime: Display date: ", displayDateAndTime);
			var _moment = moment(displayDateAndTime, 'MM/D/YYYY h:mm a Z');
			var _date = _moment.toDate();
			// Returns ISO 8601 string
			var _dateIso = _date.toISOString();
			log("getTransferDateAndTime ISO Date: ", _dateIso);
			return _dateIso;
		}
	} catch (err) {
		handleError("getTransferDateAndTime", err);
	}
	log("getTransferDateAndTime", "Exiting")
}

function getTimeZone(dateAndTime) {
	// default to Zulu time
	var _timeZone = "Z";
	if (dateAndTime.indexOf('+') != '-1') {
		_timeZone = '+' + dateAndTime.split('+')[1];
	} else if (dateAndTime.indexOf('-') != '-1') {
		_timeZone = '-' + dateAndTime.split('-')[1];
	}
	log("timezone: " + _timeZone);
	return _timeZone;
}

function handleError(functionName, err) {
	alert(functionName);
	try {
		txt = functionName + "()\n\n";
		txt += "There was an error on this page.\n\n";
		txt += "Error description: " + err.message + "\n\n";
		console.log(txt);
		if (mDebugEnabled = "Y") {
			$('#main_errors').html(txt);
		} else {
			$('#main_errors')
					.html(
							'Unable to continue processing the request. Please try again later...');
		}
	} catch (err) {
		txt = functionName + "()\n\n";
		txt += "There was an error on this page.\n\n";
		txt += "Error description: " + err.message + "\n\n";
		console.log(txt);
	}
}

function log(pFunctionName, message) {
	if (mDebugEnabled = "Y") {
		console.log(pFunctionName + '():: ' + message);
	}
}

function wait(waitElementId) {
	try {
		waitElement = document.getElementById(waitElementId);
		if (waitElement != null)
			waitElement.style.display = 'inline';
	} catch (err) {
		handleError("wait", err);
	}
}

function clearWait(waitElementId) {
	waitElement = document.getElementById(waitElementId);
	if (waitElement != null)
		waitElement.style.display = 'none';
}

function openPopupWithDimensions(divId, width, height) {
	try {
		$("#" + divId).modal({
			minWidth : width,
			maxWidth : width,
			minHeight : height,
			opacity : 70,
			overlayCss : {
				backgroundColor : "#000",
				borderColor : "#000"
			},
			onOpen : function(dialog) {
				dialog.overlay.fadeIn('fast', function() {
					dialog.data.hide();
					dialog.container.fadeIn('fast', function() {
						dialog.data.fadeIn('slow');
					});
				});
			}
		});
	} catch (err) {
		handleError("openPopupWithDimensions", err);
	}

}

function openWait() {
	try {
		$('body').css('cursor', 'wait');
		/*
		 * $("#wait_div").modal({ minWidth : 100, maxWidth : 100, minHeight :
		 * 100, onOpen : function(dialog) { dialog.overlay.fadeIn('fast',
		 * function() { dialog.data.hide(); dialog.container.fadeIn('fast',
		 * function() { dialog.data.fadeIn('fast'); }); }); } });
		 */} catch (err) {
		handleError("openWait", err);
	}

}
function closeWait() {
	try {
		$('body').css('cursor', 'auto');

	} catch (err) {
		handleError("openWait", err);
	}

}

function openPopup(divId) {
	$("#" + divId).modal('show');
	// openPopupWithDimensions(divId, 600);
}

function displayConfirm(message, callback) {
	$("#confirm_message").html(message);
	// if the user clicks "yes"
	$('#confirm_yes_button').bind('click', function() {
		// call the callback
		if ($.isFunction(callback)) {
			callback.apply();
		}
		$('#confirm_modal').foundation('reveal', 'close');
	});

	$('#confirm_modal').foundation('reveal', 'open');

}



var imageCount = 9; // max images displayed in the image gallery
function shiftGallery(index, entry_id, frames, max) {

	indexField = document.getElementById(index);

	if (frames < 0 && indexField.value == 0) {
		return;
	}
	if (frames > 0 && (Number(indexField.value) + imageCount) == max) {
		return;
	}

	// Add the frame count from the index
	indexField.value = Number(indexField.value) + frames;

	for (x = 0; x < max; x++) {
		currentPhoto = document.getElementById("photo_" + entry_id + "_" + x);
		if (x < indexField.value) {
			currentPhoto.style.display = 'none';
		} else if (x > indexField.value + imageCount) {
			currentPhoto.style.display = 'none';
		} else {
			currentPhoto.style.display = '';
		}

	}

}

function validateEmail(pEmail) {
	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	if (filter.test(pEmail)) {
		return true;
	} else {
		return false;
	}
}
/** End Utilities ******************************** */
