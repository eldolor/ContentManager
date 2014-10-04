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
			$('#searchForm').unbind();
			$('#searchForm').submit(function() {
				var lSearchTerm = $('#search_term').val();
				if (lSearchTerm != null && lSearchTerm != '')
					window.location.href = '/search/' + lSearchTerm;
				//always
				return false;
			});
			$('#sign_up_button').unbind();
			$('#sign_up_button').click(function() {
				window.location.href = '/signup';
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
				<a href="#"> <img src="/resources/images/box/Cube.png"
					alt="big logo" />
				</a>
			</h1>
		</li>
		<li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
	</ul>
	<section class="top-bar-section">
		<!-- Left Nav Section -->
		<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
			<div class="error_search left">
				<div class="row">
					<form role="form" id="searchForm">
						<div class="form-group">
							<input id="search_term" name="search_term" type="text"
								class="form-control input-lg" placeholder="search" />
						</div>
					</form>
				</div>
			</div>
		</sec:authorize>
		<sec:authorize ifNotGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
			<div class="left">
				<button id="sign_up_button" class="button radius btn-default">Sign
					Up</button>
			</div>
		</sec:authorize>

		<!-- Right Nav Section -->
		<ul class="right">
			<li><a href="/">Home</a></li>
			<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
				<li><a href="/analytics/applications">Usage Reports</a></li>
				<li class="has-dropdown"><a href="#">Account Settings</a>
					<ul class="dropdown">
						<li><a href="/account/clientkeys">Client Keys</a></li>
						<li><a href="/account/billing">Billing</a></li>
						<li><a href="/account/plans">Plans & Pricing</a></li>
						<li><a href="/account/usage">Account Usage</a></li>
						<li><a href="/account/changepassword">Change Password</a></li>
					</ul></li>
			</sec:authorize>
			<li><a href="/docs">Documents</a></li>
			<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
				<li><a href="<c:url value="/j_spring_security_logout"/>">Sign
						out </a></li>
			</sec:authorize>
			<sec:authorize ifNotGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
				<li><a href="<c:url value="/login"/>">Sign In </a></li>
			</sec:authorize>
		</ul>
	</section>
</nav>