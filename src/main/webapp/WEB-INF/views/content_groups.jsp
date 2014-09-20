
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

<title>Content Manager</title>
<jsp:include page="resources.jsp" flush="true"></jsp:include>

<!-- Begin Custom -->
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.content.group.setup.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.content.group.js"></script>
<!-- End Custom -->
<script type="text/javascript">
	setSelectedApplication(${applicationId});
</script>


</head>
<body>

	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section id="blog">
		<div class="row full-width">
			<h2 class="text-center gray">Content Groups</h2>

			<p class="text-center page_sub_heading">Content groups help
				organize your contents</p>
			<div class="line">
				<img src="/resources/images/box/line.png" alt="line" />
			</div>
			<br />
			<div class="row">
				<div class="large-7 columns">
					<jsp:include page="breadcrumbs.jsp"></jsp:include>
					<div class="clearfix"></div>
					<jsp:include page="progress_bar.jsp"></jsp:include>
					<div id="content_groups_list"></div>
					<jsp:include page="create_content_group.jsp"></jsp:include>

				</div>
				<div class="large-4 columns col-md-offset-1">
					<div id="category">
						<h3>Options</h3>
						<ul>
							<li><i class="fi-arrow-right"></i><a
								id="create_contentgroup" href="javascript:void(0);">&nbsp;Create
									New Content Group</a></li>
						</ul>
					</div>
					<br> <br />
					<dl class="tabs" data-tab>
						<dd class="active">
							<a href="#panel2-1">Content Group</a>
						</dd>
					</dl>
					<div class="tabs-content">
						<div class="content active" id="panel2-1">
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Start Date</span> <span class="date">A
										future start date will allow you to make the content within the content group
										effective, as of that date</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">End Date</span> <span class="date">Specify
										an End Date only if you want the content within the content group, to expire after a
										certain date, or else leave it empty.</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Enabled</span> <span class="date">Disabling
										a content group will prevent the rich media content within
										that content group, from being downloaded</span>
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
	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>
</body>
</html>