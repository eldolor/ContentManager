//Global Variables
var mDebugEnabled = 'Y';
var mSelectedApplication;
var mSelectedContentGroup;
var mSelectedContent;
var mErrors;
var mQuota;

var mLoggedInUser;
var mIsNewCard;
var mStripeCustomer;

/** ***************************** */
$.ajaxSetup({
	// Disable caching of AJAX responses
	cache : false
});
