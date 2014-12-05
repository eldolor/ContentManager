<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>

<script type="text/javascript">
	jQuery(function($) {
		try {
			log("function($)", "Entering");

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

			var lSticky = <c:out value="${param.sticky}" >true</c:out>;

			// run our function on load
			if (lSticky) {
				sticky_navigation();
			}

			// and run it again every time you scroll
			if (lSticky) {
				$(window).scroll(function() {
					sticky_navigation();
				});
			}

			$('#searchForm').unbind();
			$('#searchForm').submit(function() {
				var lSearchTerm = $('#search_term').val();
				if (lSearchTerm != null && lSearchTerm != '')
					window.location.href = '/search/' + lSearchTerm;
				//always
				return false;
			});

		} catch (err) {
			handleError("function($)", err);
		} finally {
			log("function($)", "Exiting");
		}
	});
</script>
<nav id="sticky_navigation" class="top-bar" data-topbar>
	<ul class="title-area">
		<!-- Title Area -->
		<li class="name">
			<h1>
				<a href="#"> <img src="/resources/images/cm/logo-72x72.png"
					alt="medium logo" />
				</a>
			</h1>
		</li>
		<li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
	</ul>
	<section class="top-bar-section">

		<!-- Right Nav Section -->
		<ul class="left">
			<li><a href="/demo">Home</a></li>
			<li><a href="/demo/all">Display All</a></li>
			<li><a href="/demo/tag/golden%20gate%20bridge">Content
					Tagged - &apos;Golden Gate Bridge&apos;</a></li>
			<li><a href="/demo/tag/chinatown">Content Tagged -
					&apos;Chinatown&apos;</a></li>
			<li><a href="/demo/all/images">All Images</a></li>
			<li><a href="/demo/all/videos">All Videos</a></li>
			<li><a href="/demo/any/image">Any Image</a></li>
			<li><a href="/demo/any/video">Any Video</a></li>

		</ul>

	</section>
</nav>