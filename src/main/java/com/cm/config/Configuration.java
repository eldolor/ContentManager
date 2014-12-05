package com.cm.config;

public class Configuration {
	private static final String STRIPE_PRIVATE_API_KEY_TEST = "sk_test_4aEiOFaIp1sl35p1Gqjco3Is";
	private static final String STRIPE_PUBLIC_API_KEY_TEST = "pk_test_4aEi34FWLvjmVHc14fQoUQPZ";

	private static final String STRIPE_PUBLIC_API_KEY_PROD = "pk_live_4aEivQL4zUHiFxj5UiYDC2OT";
	private static final String STRIPE_PRIVATE_API_KEY_PROD = "sk_live_4aEirz6nwUZNsh7DggmeHREZ";
	
	//DEV
//	private static final String DOMAIN = "skok-dev.appspot.com";
//	public static final String STRIPE_PRIVATE_API_KEY = STRIPE_PRIVATE_API_KEY_TEST;
//	public static final String STRIPE_PUBLIC_API_KEY = STRIPE_PUBLIC_API_KEY_TEST;
	
	//PROD
	private static final String DOMAIN = "skok.co";
	public static final String STRIPE_PRIVATE_API_KEY = STRIPE_PRIVATE_API_KEY_PROD;
	public static final String STRIPE_PUBLIC_API_KEY = STRIPE_PUBLIC_API_KEY_PROD;
	
	public static final String CURRENT_SDK_VERSION = "1.3";
	
	public static final String GOOGLE_API_KEY = "AIzaSyDmUXoFreTugYfSL5B2QvM8mUDwhCte7BM";
	public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
	public static final String MESSAGE_TYPE_CONTENT_LIST = "MESSAGE_TYPE_CONTENT_LIST";
	public static final String MESSAGE_TYPE_SEND_TO_SYNC = "MESSAGE_TYPE_SEND_TO_SYNC";
	public static final String MESSAGE_TYPE_DISPLAY_NOTIFICATION = "MESSAGE_TYPE_DISPLAY_NOTIFICATION";
	

	public static final String SITE_NAME = "Skok";
	//Dev
	public static final String FORGOT_PASSWORD_URL = "https://"+ DOMAIN +"/forgotpassword";
	public static final String FROM_EMAIL_ADDRESS = "anshu@skok.co";
	public static final String FROM_NAME = "Skok";
	
	public static final String GCS_STORAGE_BUCKET = DOMAIN + "/media";
	
	public static final String GOOGLE_API_PROJECT_NUMBER = "468566067831";

	public static final String CONTENT_STATS_QUEUE_NAME = "contentstatsqueue";
	public static final String CONTENT_QUEUE_NAME = "contentqueue";
	public static final String GCM_QUEUE_NAME = "gcmqueue";
	public static final String EMAIL_QUEUE_NAME = "emailqueue";
	
	public static final int GCM_MAX_ATTEMPTS = 10;
	public static final int GCM_MESSAGE_SIZE_LIMIT_BYTES = 4096;
	
	public static final String TRACKING_ID_PREFIX = "SKOK_";
	
	

}