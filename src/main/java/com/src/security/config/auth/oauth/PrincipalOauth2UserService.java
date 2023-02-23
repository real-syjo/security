package com.src.security.config.auth.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.src.security.config.auth.PrincipalDetails;
import com.src.security.config.auth.oauth.provider.FacebookUserInfo;
import com.src.security.config.auth.oauth.provider.GoogleUserInfo;
import com.src.security.config.auth.oauth.provider.NaverUserInfo;
import com.src.security.config.auth.oauth.provider.Oauth2UserInfo;
import com.src.security.model.User;
import com.src.security.repository.UserRepository;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oauth2User  = super.loadUser(userRequest);
		Oauth2UserInfo oAuth2UserInfo = null;

		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		}
		
		//회원가입 
		String providerId = oAuth2UserInfo.getProviderId();//google
		String provider = oAuth2UserInfo.getProvider();
		String username = oAuth2UserInfo.getEmail();
		String password= bCryptPasswordEncoder.encode("겟인데어");
		String role = "USER_ROLE";
		
		User userEntity = userRepository.findByUsername(username);
//		User userEntity = userRepository.findByProviderId(providerId);
		
		if(userEntity == null) {
			System.out.println("구글로그인이 최초입니다.");
			userEntity = User.builder()
					.username(username)
					.password(password)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}else {
			System.out.println("이미 가입되어있어욧");
		}
		
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}
	
	
}
