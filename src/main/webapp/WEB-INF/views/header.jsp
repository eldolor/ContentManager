<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>



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
						<li><a id="header_link_1" href="#" style="display: none">Link
								1</a></li>
						<li><a id="header_link_2" href="#" style="display: none">Link
								2</a></li>
						<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN">
							<li><a id="accounts" href="./am">Accounts</a></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN">
							<li><a id="users" href="./um">Users</a></li>
						</sec:authorize>
					</ul>
					<ul class="right">
						<sec:authorize
							ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
							<li><a id="myAccount" href="/account">Account
									Settings</a></li>
						</sec:authorize>
						<sec:authorize
							ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">
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