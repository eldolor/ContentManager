jQuery(function($) {
	log("function($)", "Entering");
	try {

		$("#owl-demo").owlCarousel({
			navigation : true
		});

		$("#intro").addClass("fadeInUp animated");
		$("#learn_more").addClass("bounceInUp animated");
		$('#home a[href*=#]:not([href=#])').click(
				function() {
					if (location.pathname.replace(/^\//, '') == this.pathname
							.replace(/^\//, '')
							&& location.hostname == this.hostname) {
						var target = $(this.hash);
						target = target.length ? target : $('[name='
								+ this.hash.slice(1) + ']');
						if (target.length) {
							$('html,body').animate({
								scrollTop : target.offset().top
							}, 1000);
							return false;
						}
					}
				});
		// Slider.init();

		$(".rotate").textrotator();

		$('#services').waypoint(function() {
			$("#services .visible").css('visibility', 'visible');
			$("#services .visible").addClass("bounceInUp animated");
		}, {
			offset : 435
		});

		$('#message').waypoint(function() {
			$("#message .visible").css('visibility', 'visible');
			$("#message .visible").addClass("bounceInLeft animated");
		}, {
			offset : 535
		});

		$('#portfolio').waypoint(function() {
			$("#portfolio .visible").css('visibility', 'visible');
			$("#portfolio .visible").addClass("fadeInUp animated");
		}, {
			offset : 475
		});

		$('#testimonial').waypoint(function() {
			$("#testimonial .visible").css('visibility', 'visible');
			$("#testimonial .visible").addClass("bounceInDown animated");
		}, {
			offset : 655
		});

		$('#clients').waypoint(function() {
			$("#clients .visible").css('visibility', 'visible');
			$("#clients .visible").addClass("swing animated");
		}, {
			offset : 535
		});

		$(document).foundation();
		// Google Analytics
		ga('send', {
			'hitType' : 'pageview',
			'page' : '/',
			'title' : PageTitle.HOME
		});
		// End Google Analytics

	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});
