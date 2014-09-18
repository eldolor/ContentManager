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

		var sticky_navigation_offset_top = $('#sticky_navigation').offset().top;
		// our function that decides weather the navigation bar should have
		// "fixed" css position or not.
		var sticky_navigation = function() {
			var scroll_top = $(window).scrollTop(); // our current vertical
													// position from the top

			// if we've scrolled more than the navigation, change its position
			// to fixed to stick to top, otherwise change it back to relative
			if (scroll_top > sticky_navigation_offset_top) {
				$('#sticky_navigation').css({
					'position' : 'fixed',
					'top' : 0,
					'left' : 0,
					'width' : '100%',
					'z-index' : 9999999999
				});
			} else {
				$('#sticky_navigation').css({
					'position' : 'relative'
				});
			}
		};

		// run our function on load
		sticky_navigation();

		// and run it again every time you scroll
		$(window).scroll(function() {
			sticky_navigation();
		});

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
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});
