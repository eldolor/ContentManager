function setupContentDropBox(url) {
	var lUrl = url;
	console.log("setupContentDropBox url: " + lUrl);

	var dropbox = $('#content_dropbox'), message = $('.message', dropbox);

	var options = {
		// The name of the $_FILES entry:
		paramname : 'file',
		maxfiles : 1,
		maxfilesize : 10,
		// The domain name of the URL must match the domain name of the web page
		// for the XHR request to work
		url : lUrl,

		uploadFinished : function(i, file, response) {
			$.data(file).addClass('done');
			// response is the JSON object that post_file.php returns
			$('#content_uri').val(response.uri);
		},

		error : function(err, file) {
			switch (err) {
			case 'BrowserNotSupported':
				showMessage('Your browser does not support HTML5 file uploads!');
				break;
			case 'TooManyFiles':
				alert('Too many files! Please select 1 at most! (configurable)');
				break;
			case 'FileTooLarge':
				alert(file.name
						+ ' is too large! Please upload files up to 10mb (configurable).');
				break;
			default:
				alert(err);
				break;
			}
		},

		// Called before each upload is started
		beforeEach : function(file) {

			if ((file.type.match(/^video\//)) || (file.type.match(/^image\//))) {
				return true;
			} else {
				alert('Only videos and images are allowed!');

				// Returning false will cause the
				// file to be rejected
				return false;
			}
		},

		uploadStarted : function(i, file, len) {
			createImage(file);
		},

		progressUpdated : function(i, file, progress) {
			$.data(file).find('.progress').width(progress);
		}

	};

	// reset
	dropbox.unbind();
	dropbox.filedrop(options);

	var template = '<div class="preview">' + '<span class="imageHolder">'
			+ '<img />' + '<span class="uploaded"></span>' + '</span>'
			+ '<div class="progressHolder">' + '<div class="progress"></div>'
			+ '</div>' + '</div>';

	function createImage(file) {

		var preview = $(template), image = $('img', preview);

		var reader = new FileReader();

		image.width = 100;
		image.height = 100;

		reader.onload = function(e) {

			if (file.type.match(/^video\//)) {
				image.attr('src', '/resources/images/video_icon.png');
			} else {
				// e.target.result holds the DataURL which
				// can be used as a source of the image:

				// image.attr('src', e.target.result);
				image.attr('src', '/resources/images/image_icon.png');
			}

		};

		// Reading the file as a DataURL. When finished,
		// this will trigger the onload function above:
		reader.readAsDataURL(file);

		message.hide();
		preview.appendTo(dropbox);

		// Associating a preview container
		// with the file, using jQuery's $.data():

		$.data(file, preview);
	}

	function showMessage(msg) {
		message.html(msg);
	}

}