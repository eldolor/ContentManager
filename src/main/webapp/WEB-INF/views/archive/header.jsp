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

<div class="row">
	<div class="large-12 columns">
		<div class="contain-to-grid">
			<nav class="top-bar radius" data-topbar>
				<ul class="title-area">

					<li class="name">
						<h1>
							<a href="#">Content Manager</a>
						</h1>
					</li>
					<li class="toggle-topbar menu-icon"><a href="#"></a></li>
				</ul>
				<section class="top-bar-section">
					<ul class="left">
						<sec:authorize
							ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
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
					</ul>
					<ul class="right">
						<sec:authorize
							ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
							<li class="active"><a id="myAccount" href="/account">Account
									Settings</a></li>
							<li class="divider"></li>
							<li><a href="<c:url value="/j_spring_security_logout"/>">Sign
									out </a></li>
						</sec:authorize>
						<sec:authorize
							ifNotGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
							<li><a href="<c:url value="/login"/>">Sign In </a></li>
						</sec:authorize>
					</ul>
				</section>
			</nav>
		</div>
	</div>
</div>