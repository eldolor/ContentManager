<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>

<script type="text/javascript">
	jQuery(function($) {
		try {
			log("function($)", "Entering");
			$('#search_button').unbind();
			$('#search_button').click(function() {
				var lSearchTerm = $('#search_term').val();
				if (lSearchTerm != null && lSearchTerm != '')
					window.location.href = '/search/' + lSearchTerm;
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
				<a href="#"> <img src="/resources/images/box/Cube.png" alt="big logo" />
				</a>
			</h1>
		</li>
		<li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
	</ul>
	<section class="top-bar-section">
		<!-- Right Nav Section -->
		<ul class="left">
			<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
				<li class="has-form">
					<div class="row collapse">
						<div class="large-8 small-9 columns">
							<input type="text" id="search_term" name="search_term"
								placeholder="...">
						</div>
						<div class="large-4 small-3 columns">
							<a href="javascript:void(0);" id="search_button"
								class="button expand">Search</a>
						</div>
					</div>
				</li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
				<li class="divider"></li>
				<li class="active"><a id="myAccount" href="/account">Account
						Settings</a></li>
				<li class="divider"></li>
				<li><a href="<c:url value="/j_spring_security_logout"/>">Sign
						out </a></li>
			</sec:authorize>
			<sec:authorize ifNotGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
				<li><a href="<c:url value="/login"/>">Sign In </a></li>
			</sec:authorize>
		</ul>
		<ul class="right">
			<li><a href="index.html">Home</a></li>
			<!-- 			<li><a href="about.html">About</a></li>
			<li><a href="blog.html">Blog</a></li>
 -->
			<!-- 			<li class="has-dropdown"><a href="#">Pages</a>
				<ul class="dropdown">
					<li><a href="404.html">404 Error</a></li>
					<li><a href="portfolio.html">Portfolio</a></li>
					<li><a href="blogdetail.html">Blog Detail</a></li>
					<li><a href="pricing.html">Pricing</a></li>
				</ul></li> -->
			<li><a href="contact.html">Contact</a></li>
		</ul>
	</section>
</nav>