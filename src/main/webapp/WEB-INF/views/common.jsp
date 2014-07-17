<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>


<!-- Begin Confirm -->
<div class="reveal-modal small" id="confirm_modal"
	data-reveal>
	<h3 id="confirmModalLabel">Confirm</h3>
	<div class="row">
		<div id="confirm_message"></div>
		<div>&nbsp;</div>
	</div>
	<button id="confirm_yes_button" class="button">yes</button>
	<a class="close-reveal-modal">&#215;</a>
</div>
<!-- End Confirm -->