function setupImageAdDropBox(url, bannerSize) {
	var lBannerSize = bannerSize;
	var n = lBannerSize.split("x");
	log("setupImageAdDropBox", " width " + n[0]
			+ " height " + n[1]);
	var lWidth = n[0];
	var lHeight = n[1];
	var lUrl = url;

	var dropbox = $('#image_ad_dropbox'), message = $('.message', dropbox);

	var options = {
		// The name of the $_FILES entry:
		paramname : 'file',
		maxfiles : 1,
		maxfilesize : 2,
		width : lWidth,
		height : lHeight,
		// The domain name of the URL must match the domain name of the web page
		// for the XHR request to work
		url : lUrl,

		uploadFinished : function(i, file, response) {
			$.data(file).addClass('done');
			// response is the JSON object that post_file.php returns
			$('#image_ad_uri').val(response.uri);
			//set the banner size for the image that was uploaded
			$('#image_ad_banner_size').val(lBannerSize);
		},

		error : function(err, file) {
			switch (err) {
			case 'BrowserNotSupported':
				showMessage('Your browser does not support HTML5 file uploads!');
				break;
			case 'TooManyFiles':
				alert('Too many files! Please select 5 at most! (configurable)');
				break;
			case 'FileTooLarge':
				alert(file.name
						+ ' is too large! Please upload files up to 2mb (configurable).');
				break;
			case 'InvalidDimensions':
				alert(file.name
						+ ' The image dimensions do not match the banner image size for the ad');
				log("setupImageAdDropBox",
						"The image dimensions does not match the specified dimensions");
				break;
			default:
				alert(err);
				break;
			}
		},

		// Called before each upload is started
		beforeEach : function(file) {
			if (!file.type.match(/^image\//)) {
				alert('Only images are allowed!');

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

			// e.target.result holds the DataURL which
			// can be used as a source of the image:

			image.attr('src', e.target.result);
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