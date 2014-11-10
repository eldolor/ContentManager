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
var mStripeKey = 'pk_test_4aEi34FWLvjmVHc14fQoUQPZ'
// var mStripeKey = 'pk_live_4aEivQL4zUHiFxj5UiYDC2OT'

/** ***************************** */
$.ajaxSetup({
	// Disable caching of AJAX responses
	cache : false
});
