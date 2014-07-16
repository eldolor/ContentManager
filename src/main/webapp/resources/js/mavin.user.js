
function createAccount() {
	try {
		wait('create_account_wait');
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var obj = {
			userName : $('#username').val(),
			password : $('#password').val(),
			password2 : $('#password2').val(),
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(obj, null, 2);
		var jqxhr = $.ajax({
			url : "/admin/account",
			type : "POST",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					;
					window.location.href = "/secured";
				},
				400 : function(text) {
					try {
						$('#create_account_errors')
								.html(getErrorMessages(text));
					} catch (err) {
						handleError("createAccount", err);
					}
				}
			}
		});

		jqxhr.always(function(msg) {
			clearWait('create_account_wait');
		});

		return false;
	} catch (err) {
		handleError("createAccount", err);
	}
}
