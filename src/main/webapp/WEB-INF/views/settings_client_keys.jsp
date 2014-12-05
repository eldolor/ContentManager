
<!doctype html>
<!--[if IE 9]><html class="lt-ie10" lang="en" > <![endif]-->
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>



<head>
<jsp:include page="meta_tags.jsp"></jsp:include>

<title>Skok</title>
<jsp:include page="resources.jsp" flush="true"></jsp:include>

<!-- Begin Custom -->
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.client.keys.js"></script>
<!-- End Custom -->
</head>
<body>



	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section id="blog">
		<div class="row full-width">

			<h2 class="text-center gray">Client Keys in Use</h2>
			<!-- 			<div class="line">
				<img src="/resources/images/cm/line.png" alt="line" />
			</div>
 -->
			<br />
			<div class="row">
				<div class="large-7 columns">
					<jsp:include page="progress_bar.jsp"></jsp:include>
					<div id="client_keys_list"></div>
				</div>
				<div class="large-4 columns col-md-offset-1">
					<div id="category">
						<h3>Options</h3>
						<ul>
							<li><i class="fi-arrow-right"></i><a id="create_client_key"
								href="javascript:void(0);">&nbsp;Create New Client Key</a></li>
							<li><i class="fi-arrow-right"></i><a id="restore_clientkeys"
								href="javascript:void(0);">&nbsp;Restore Deleted Client Keys</a></li>
						</ul>
					</div>
					<br> <br />
					<dl class="tabs" data-tab>
						<dd class="active">
							<a href="#panel2-1">Client Key</a>
						</dd>
					</dl>
					<div class="tabs-content">
						<div class="content active" id="panel2-1">
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Creation</span> <span class="date">We
										highly recommend that you create a new Client Key for each
										Application.</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Deletion</span> <span class="date">Be
										careful when deleting Client Keys. Deleting a Client Key will
										block the corresponding application from downloading content.
										If needed, you can always restore a key that was previously
										deleted.</span>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>

					</div>

				</div>
			</div>
		</div>
	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

	<div class="reveal-modal small" id="restore_modal" data-reveal>
		<h3 class="gray" id="modalLabel">Restore Client Key</h3>
		<div class="row">
			<div id="select_from_deleted_list"></div>
		</div>
		<button id="restore_confirm_button" class="button radius btn-default">restore</button>
		<a class="close-reveal-modal">&#215;</a>
	</div>

</body>
</html>


