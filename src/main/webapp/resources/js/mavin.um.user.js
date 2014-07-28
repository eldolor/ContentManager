function createUser() {
	try {
		wait('user_wait');
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var _enabled;
		if ($('#user_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var obj = {
			firstName : $('#user_first_name').val(),
			lastName : $('#user_last_name').val(),
			email : $('#user_email').val(),
			userName : $('#user_user_name').val(),
			password : $('#user_password').val(),
			password2 : $('#user_password_2').val(),
			role : $('#user_role').val(),
			accountId : $('#user_account_selection').val(),
			enabled : _enabled,
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(obj, null, 2);
		var jqxhr = $.ajax({
			url : "/um/user",
			type : "POST",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					getUsers();
					$('#user_div').modal('hide');
				},
				400 : function(text) {
					try {
						$('#user_errors').html('<p>'+ getErrorMessages(text)+'</p>');
					} catch (err) {
						handleError("createUser", err);
					}
				}
			}
		});

		jqxhr.always(function(msg) {
			clearWait('user_wait');
		});

		return false;
	} catch (err) {
		handleError("createUser", err);
	}
}

function updateUser() {
	try {
		wait('user_wait');
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var _enabled;
		if ($('#user_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var obj = {
			id : $('#user_id').val(),
			firstName : $('#user_first_name').val(),
			lastName : $('#user_last_name').val(),
			email : $('#user_email').val(),
			userName : $('#user_user_name').val(),
			password : $('#user_password').val(),
			password2 : $('#user_password_2').val(),
			role : $('#user_role').val(),
			accountId : $('#user_account_selection').val(),
			enabled : _enabled,
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(obj, null, 2);
		//alert(objString);
		var jqxhr = $.ajax({
			url : "/um/user",
			type : "PUT",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#user_div').modal('hide');
					getUsers();
				},
				400 : function(text) {
					try {
						$('#user_errors').html('<p>'+ getErrorMessages(text)+'</p>');
					} catch (err) {
						handleError("updateUser", err);
					}
				}
			}
		});

		jqxhr.always(function(msg) {
			clearWait('user_wait');
		});

		return false;
	} catch (err) {
		handleError("updateUser", err);
	}
}

function updateLoggedInUser() {
	try {
		wait('user_wait');
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var _enabled;
		if ($('#user_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var obj = {
			id : $('#user_id').val(),
			userName : $('#user_user_name').val(),
			firstName : $('#user_first_name').val(),
			lastName : $('#user_last_name').val(),
			email : $('#user_email').val(),
			password : $('#user_password').val(),
			password2 : $('#user_password_2').val(),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(obj, null, 2);
		//alert(objString);
		var jqxhr = $.ajax({
			url : "/secured/loggedinuser",
			type : "PUT",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#user_div').modal('hide');
				},
				400 : function(text) {
					try {
						$('#user_errors').html('<p>'+ getErrorMessages(text)+'</p>');
					} catch (err) {
						handleError("updateLoggedInUser", err);
					}
				}
			}
		});

		jqxhr.always(function(msg) {
			clearWait('user_wait');
		});

		return false;
	} catch (err) {
		handleError("updateLoggedInUser", err);
	}
}


/** *Begin User*********************************** */
function getUsers() {
	log("getUsers", "Entering")
	try {
		// open wait div
		openWait();

		var jqxhr = $.ajax({
			url : "um/users",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(users) {
					handleDisplayUsers_Callback(users);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("getUsers", err);
		// close wait div
		;
	}
	log("getUsers", "Exiting")
}

function handleDisplayUsers_Callback(users) {
	log("handleDisplayUsers_Callback", "Entering")
	try {
		var innerHtml = "<div class=\"accordion\" id=\"userAccordian\"> ";
		for (var int = 0; int < users.length; int++) {
			var user = users[int];
			var enabled = user.enabled;
			var _innerHtml = "<div class=\"accordion-group\" id=\"userAccordianGroup"
					+ user.id
					+ "\">"
					+ "<div class=\"accordion-heading\">"
					+ "<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#userAccordianGroup"
					+ user.id + "\" href=\"#" + user.id + "\"><div >";
			if (!enabled) {
				_innerHtml += "<span class=\"label label-important\">[Disabled] </span>";
			}
			_innerHtml += "<strong>&nbsp;" + user.userName + "</strong><small>&nbsp;"
					+ user.role + "</small></div></div>" + "<div id=\""
					+ user.id + "\" class=\"accordion-body collapse in\">"
					+ "<div class=\"accordion-inner\">" + user.firstName
					+ "&nbsp" + user.lastName
					+ "<p class=\"text-center\">&nbsp;</p>"
					+ "<ul class=\"inline\">"
					+ "<li><a href=\"javascript:void(0)\""
					+ "onclick=\"editUser(" + user.id
					+ ")\"><i class=\"icon-edit\"></i> edit</a>" + "</li>"
					+ "</ul>"

					+ "</div>" + "</div>" + "</div>";

			innerHtml += _innerHtml + "<p class=\"text-center\">&nbsp;</p>";
		}
		innerHtml += "</div>";

		$('#entries').empty().html(innerHtml);
		// $('#titleBoxHeader').html('Users');
		var breadCrumbsHtml = "<div ><ul class=\"breadcrumb breadcrumb-fixed-top\"><li><a href=\"javascript:void(0)\" onclick=\"getUsers()\"><strong>users</strong></a></li>"
				+ "<li><span class=\"divider\"> / </span></li>"
				+ "<li><a href=\"javascript:void(0);\" onclick=\"newUser()\"> <img alt=\"\" src=\"resources/images/plus-box-16x16.png\" height=\"16\" width=\"16\" />&nbsp;create new user</a></li></ul></div>";

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
		handleError("handleDisplayUsers_Callback", err);
	} finally {
	}
	log("handleDisplayUsers_Callback", "Exiting")
}

function toggleRole(role) {

	if (role == 'admin') {
		$('#user_role').val('admin');
	} else if (role == 'user') {
		$('#user_role').val('user');
	}
}

function newUser() {
	log("newUser", "Entering")
	try {
		$('#user_save_button').html('create user');
		// unbind click listener to reset
		$('#user_save_button').unbind();
		$('#user_save_button').bind('click', createUser);
		$('#user_cancel_button').one('click', function() {
			$('#user_save_button').unbind();
			;
		});
		$('#user_id').val('');
		$('#user_first_name').val('');
		$('#user_last_name').val('');
		$('#user_email').val('');
		$('#user_user_name').val('');
		$('#user_password').val('');
		$('#user_password_2').val('');
		// set default
		$('#user_role').val('user');
		$('input[name="user_role_selection"]').each(function() {
			if ('user' == $(this).val()) {
				$(this).attr('checked', 'checked');
				return;
			}
		});
		getAccountsForDisplay(0);
		$('#user_errors').empty();
		openPopup("user_div");
	} catch (err) {
		handleError("newUser", err);
	}
	log("newUser", "Exiting")
}

function editUser(id) {
	log("editUser", "Entering")
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "um/user/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(user) {
					$('#user_id').val(user.id);
					if (user.hasOwnProperty('firstName'))
						$('#user_first_name').val(user.firstName);
					if (user.hasOwnProperty('lastName'))
						$('#user_last_name').val(user.lastName);
					if (user.hasOwnProperty('email'))
						$('#user_email').val(user.email);
					$('#user_user_name').val(user.userName);
					// add more
					$('#user_password').val('');
					$('#user_password_2').val('');
					if (user.hasOwnProperty('role')) {
						$('input[name="user_role_selection"]').each(function() {
							if (user.role == $(this).val()) {
								$(this).attr('checked', 'checked');
								return;
							}
						});
						$('#user_role').val(user.role);
					}

					if (user.hasOwnProperty('enabled')) {
						log("editUser", "User enabled: " + user.enabled);
						if (user.enabled == true) {
							$('#user_enabled').attr('checked', 'checked');
							$('#user_status').html('Enabled');
						} else {
							$('#user_enabled').removeAttr('checked');
							$('#user_status').html('Disabled');
						}
					}
					$('#user_enabled').bind('click', function() {
						if ($('#user_enabled').is(':checked')) {
							$('#user_status').html('Enabled');
						} else {
							$('#user_status').html('Disabled');
						}
					});

					$('#user_save_button').html('update user');

					// unbind click listener to reset
					$('#user_save_button').unbind();
					$('#user_save_button').bind('click', updateUser);

					$('#user_cancel_button').one('click', function() {
						$('#user_save_button').unbind();
						;
					});
					getAccountsForDisplay(user.accountId);

					$('#user_errors').empty();
					// close wait div
					;
					openPopup("user_div");
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editUser", err);
	}
	log("editUser", "Exiting")
}


function editLoggedInUser() {
	log("editLoggedInUser", "Entering")
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/loggedinuser";
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(user) {
					$('#user_id').val(user.id);
					if (user.hasOwnProperty('firstName'))
						$('#user_first_name').val(user.firstName);
					if (user.hasOwnProperty('lastName'))
						$('#user_last_name').val(user.lastName);
					if (user.hasOwnProperty('email'))
						$('#user_email').val(user.email);
					$('#user_user_name').val(user.userName);
					// add more
					$('#user_password').val('');
					$('#user_password_2').val('');

					$('#user_save_button').html('update user');

					// unbind click listener to reset
					$('#user_save_button').unbind();
					$('#user_save_button').bind('click', updateLoggedInUser);

					$('#user_cancel_button').one('click', function() {
						$('#user_save_button').unbind();
						;
					});
					$('#user_errors').empty();
					// close wait div
					;
					openPopup("user_div");
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editLoggedInUser", err);
	}
	log("editLoggedInUser", "Exiting")
}

function getAccountsForDisplay(pUserAccountId) {
	log("getAccountsForDisplay", "Entering");

	try {
		var jqxhr = $
				.ajax({
					url : "am/accounts",
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(accounts) {
							var _innerHtml = "<div class=\"popupFieldInput\"><select id=\"user_account_selection\"><option value=\"\">--select account--</option>";
							for (var int = 0; int < accounts.length; int++) {
								var account = accounts[int];
								_innerHtml += "<option value=\"" + account.id
										+ "\">" + account.name + "</option>";
							}
							_innerHtml += "</select></div>";
							// alert(_innerHtml);
							$('#user_account').empty().html(_innerHtml);
							$("#user_account_selection").val(pUserAccountId);
						}
					}
				});

	} catch (err) {
		handleError("getAccountsForDisplay", err);
	}
	log("getAccountsForDisplay", "Exiting");
}
