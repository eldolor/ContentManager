<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>


<div id="product_create" style="display: none">
	<div class="large-12 columns">
		<h3 class="gray">Product Setup</h3>
		<form role="form" id="productForm" name="productForm"
			data-abide="ajax">
			<input type="hidden" id="product_id" name="product_id" /> <input
				type="hidden" id="product_uri" name="product_uri" /><input
				type="hidden" id="product_media_type" name="product_media_type" />

			<div id="progress_bar_top" style="display: none">
				<div class="progress radius">
					<span class="meter" style="width: 40%; background-color: #5cb85c;">creating/updating...</span>
				</div>
			</div>
			<div>
				<label>Brand <small>required</small><input type="text"
					class="form-control" id="product_brand" name="product_brand"
					required="required" placeholder="brand of the product" />
				</label>
			</div>
			<div>
				<label>Product Type <small>required</small><input type="text"
					class="form-control" id="product_type" name="product_type"
					required="required" placeholder="type of the product" />
				</label>
			</div>
			<div>
				<label>Name <small>required</small><input type="text"
					class="form-control" id="product_name" name="product_name"
					required="required" placeholder="name of the product" />
				</label>
			</div>
			<div class="clearfix"></div>
			<div>
				<label>Description <textarea rows="5" class="form-control"
						id="product_description" name="product_description"
						placeholder="short description of the product"></textarea>
				</label>
			</div>
			<div class="clearfix"></div>
			<div>
				<label>Ingredients <textarea rows="5" class="form-control"
						id="product_ingredients" name="product_ingredients"
						placeholder="product ingredients"></textarea>
				</label>
			</div>
			<div class="clearfix"></div>
			<div>
				<label>Allergens <textarea rows="5" class="form-control"
						id="product_allergens" name="product_allergens"
						placeholder="product allergens"></textarea>
				</label>
			</div>
			<div>
				<label>Available Sizes <small>required</small><input type="text"
					class="form-control" id="product_available_sizes" name="product_available_sizes"
					required="required" placeholder="available sizes of the product" />
				</label>
			</div>
			<div>
				<fieldset>
					<legend>Media Type</legend>
					<ul class="inline-list">
						<!-- Using radio buttons each switch turns off the other -->
						<li><fieldset>
								<legend>
									Image<span data-tooltip class="has-tip"
										title="Skok auto-sizes your images, to match the
										screen dimensions of the mobile device, before being
										downloaded. This greatly reduces the storage and memory
										footprint on mobile devices, allows for faster downloads, and
										saves bandwidth utilization.">&nbsp;<i
										class="fi-info light_gray"></i></span>
								</legend>
								<div class="switch radius">
									<input id="product_media_type_image" type="radio" checked="checked"
										class="form-control" name="product_media_type_group"> <label
										for="product_media_type_image">Image</label>
								</div>
							</fieldset></li>

						<li>
							<fieldset>
								<legend>Video</legend>
								<div class="switch radius">
									<input id="product_media_type_video" type="radio"
										class="form-control" name="product_media_type_group"> <label
										for="product_media_type_video">Video</label>
								</div>
							</fieldset>
						</li>
					</ul>
				</fieldset>
			</div>			<div>
				<!-- 				<a id="upload_product" href="javascript:void(0);">Click here to
					upload product</a>
 -->
				<div id="product_dropbox">
					<span class="message">Drag-n-Drop product here to upload</span>
				</div>
			</div>
			<div id="cm_errors_container" style="display: none">
				<ul id="vision">
					<li><div>
							<i class="fi-alert"></i>
						</div> <span id="product_errors"></span>
						<p class="clearfix"></p></li>
				</ul>
			</div>
			<div>
				<button id="product_save_button" class="button radius btn-default">create</button>
				<a href="javascript:void(0);" id="product_cancel_button">cancel</a>
			</div>
			<div id="progress_bar_bottom" style="display: none">
				<div class="progress radius">
					<span class="meter" style="width: 40%; background-color: #5cb85c;">creating/updating...</span>
				</div>
			</div>
		</form>
	</div>
</div>