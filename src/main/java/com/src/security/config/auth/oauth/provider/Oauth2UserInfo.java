package com.src.security.config.auth.oauth.provider;

public interface Oauth2UserInfo {

	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
	
}
