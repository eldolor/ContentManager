
/*********************Account*******************/
function getAccounts() {
	log("getAccounts", "Entering")
	try {
		// open wait div
		openWait();

		var jqxhr = $.ajax({
			url : "am/accounts",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(accounts) {
					handleDisplayAccounts_Callback(accounts);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("getAccounts", err);
		// close wait div
		;
	}
	log("getAccounts", "Exiting")
}

function handleDisplayAccounts_Callback(accounts) {
	log("handleDisplayAccounts_Callback", "Entering")
	try {
		var innerHtml = "<div class=\"accordion\" id=\"accountAccordian\"> ";
		for (var int = 0; int < accounts.length; int++) {
			var account = accounts[int];
			var enabled = account.enabled;
			var _innerHtml = "<div class=\"accordion-group\" id=\"accountAccordianGroup"
					+ account.id
					+ "\">"
					+ "<div class=\"accordion-heading\">"
					+ "<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#accountAccordianGroup"
					+ account.id + "\" href=\"#" + account.id + "\"><div >";
			if (!enabled) {
				_innerHtml += "<span class=\"label label-important\">[Disabled] </span>";
			}
			_innerHtml += "<strong>&nbsp;" + account.name
					+ "</strong></div></div>" + "<div id=\"" + account.id
					+ "\" class=\"accordion-body collapse in\">"
					+ "<div class=\"accordion-inner\">" + account.description
					+ "<p class=\"text-center\">&nbsp;</p>"
					+ "<ul class=\"inline\">"
					+ "<li><a href=\"javascript:void(0)\""
					+ "onclick=\"editAccount(" + account.id
					+ ")\"><i class=\"icon-edit\"></i> edit</a>" + "</li>"
					+ "</ul>"

					+ "</div>" + "</div>" + "</div>";

			innerHtml += _innerHtml + "<p class=\"text-center\">&nbsp;</p>";
		}
		innerHtml += "</div>";

		$('#entries').empty().html(innerHtml);
		// $('#titleBoxHeader').html('Accounts');
		var breadCrumbsHtml = "<div ><ul class=\"breadcrumb breadcrumb-fixed-top\"><li><a href=\"javascript:void(0)\" onclick=\"getAccounts()\"><strong>accounts</strong></a></li>"
				+ "<li><span class=\"divider\"> / </span></li>"
				+ "<li><a href=\"javascript:void(0);\" onclick=\"newAccount()\"> <img alt=\"\" src=\"resources/images/plus-box-16x16.png\" height=\"16\" width=\"16\" />&nbsp;create new account</a></li></ul></div>";

		$('#breadcrumbs').html(breadCrumbsHtml);

		$('a[href="#TODO"]').click(function() {
			alert('Nothing to see here, boss.');
		});

		$('#entries .entry .expando').click(
				function() {
					$(this).closest('.entry').toggleClass('open').find('.body')
							.slideToggle().end();
				});

	} catch (err) {
		handleError("handleDisplayAccounts_Callback", err);
	} finally {
	}
	log("handleDisplayAccounts_Callback", "Exiting")
}

function newAccount() {
	log("newAccount", "Entering")
	try {
		$('#account_save_button').html('create account');
		// unbind click listener to reset
		$('#account_save_button').unbind();
		$('#account_save_button').bind('click', createAccount);
		$('#account_cancel_button').one('click', function() {
			$('#account_save_button').unbind();
			;
		});
		$('#account_id').val('');
		$('#account_name').val('');
		$('#account_description').val('');

		$('#account_errors').empty();
		openPopup("account_div");
	} catch (err) {
		handleError("newAccount", err);
	}
	log("newAccount", "Exiting")
}
function editAccount(id) {
	log("editAccount", "Entering")
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "am/account/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(account) {
					$('#account_id').val(account.id);
					if (account.hasOwnProperty('name'))
						$('#account_name').val(account.name);
					if (account.hasOwnProperty('description'))
						$('#account_description').val(account.description);

					if (account.hasOwnProperty('enabled')) {
						log("editAccount", "Account enabled: " + account.enabled);
						if (account.enabled == true) {
							$('#account_enabled').attr('checked', 'checked');
							$('#account_status').html('Enabled');
						} else {
							$('#account_enabled').removeAttr('checked');
							$('#account_status').html('Disabled');
						}
					}
					$('#account_enabled').bind('click', function() {
						if ($('#account_enabled').is(':checked')) {
							$('#account_status').html('Enabled');
						} else {
							$('#account_status').html('Disabled');
						}
					});

					$('#account_save_button').html('update account');

					// unbind click listener to reset
					$('#account_save_button').unbind();
					$('#account_save_button').bind('click', updateAccount);

					$('#account_cancel_button').one('click', function() {
						$('#account_save_button').unbind();
						;
					});

					$('#account_errors').empty();
					// close wait div
					;
					openPopup("account_div");
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editAccount", err);
	}
	log("editAccount", "Exiting")
}
function displayAccountName(pId, pElementName) {
	log("getAccount", "Entering")
	var lElementName = "#" + pElementName;
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "am/account/" + pId;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(account) {
					$('#account_id').val(account.id);
					if (account.hasOwnProperty('name'))
						$(lElementName).val(account.name);

				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("getAccount", err);
	}
	log("getAccount", "Exiting")
}


function createAccount() {
	try {
		wait('account_wait');
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var _enabled;
		if ($('#account_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var obj = {
			name : $('#account_name').val(),
			description : $('#account_description').val(),
			enabled : _enabled,
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(obj, null, 2);
		var jqxhr = $.ajax({
			url : "/am/account",
			type : "POST",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					getAccounts();
					$('#account_div').modal('hide');
				},
				400 : function(text) {
					try {
						$('#create_account_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("createAccount", err);
					}
				}
			}
		});

		jqxhr.always(function(msg) {
			clearWait('account_wait');
		});

		return false;
	} catch (err) {
		handleError("createAccount", err);
	}
}

function updateAccount() {
	try {
		wait('account_wait');
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var _enabled;
		if ($('#account_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var obj = {
			id : $('#account_id').val(),
			name : $('#account_name').val(),
			description : $('#account_description').val(),
			enabled : _enabled,
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(obj, null, 2);
		//alert(objString);
		var jqxhr = $.ajax({
			url : "/am/account",
			type : "PUT",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#account_div').modal('hide');
					getAccounts();
				},
				400 : function(text) {
					try {
						$('#account_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("createAccount", err);
					}
				}
			}
		});

		jqxhr.always(function(msg) {
			clearWait('account_wait');
		});

		return false;
	} catch (err) {
		handleError("updateAccount", err);
	}
}
