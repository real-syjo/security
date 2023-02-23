package com.src.security.controller;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.src.security.config.auth.PrincipalDetails;
import com.src.security.model.User;
import com.src.security.repository.UserRepository;

@Controller
public class IndexController {

	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({ "", "/" })
	public @ResponseBody String index() {
		return "랄라";
	}
	
//	@GetMapping("/test/login")
//	public @ResponseBody String testLogin(Authentication authentication) {
//		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//		return "세션정보 확인";
//	}
//	
//	@GetMapping("/test/oauth/login")
//	public @ResponseBody String testOauthLogin(Authentication authentication,
//												@AuthenticationPrincipal OAuth2User oauth) {
//		OAuth2User oauth2User = (PrincipalDetails) authentication.getPrincipal();
//		System.out.println("authentication::"+ oauth2User.getAttributes());
//		System.out.println("oauth2User::"+oauth.getAttributes());
//		return "Oauth세션정보 확인";
//	}

	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("Principal : " + principal);
		System.out.println("OAuth2 : "+principal.getUser().getProvider());
		// iterator 순차 출력 해보기
		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
		while (iter.hasNext()) {
			GrantedAuthority auth = iter.next();
			System.out.println(auth.getAuthority());
		}

		return "유저 페이지입니다.";
	}

	@GetMapping("/admin")
	public String admin() {
		return "어드민 페이지입니다.";
	}
	
	//@PostAuthorize("hasRole('ROLE_MANAGER')")
	//@PreAuthorize("hasRole('ROLE_MANAGER')")
//	@Secured("ROLE_MANAGER")
//	@GetMapping("/manager")
//	public @ResponseBody String manager() {
//		return "매니저 페이지입니다.";
//	}
	
	@GetMapping("/login")
	public String login() {
		return "loginForm";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println("회원가입 진행 : " + user);
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "redirect:/loginForm";
	}
	
//	@Secured("ROLE_ADMIN")
//	@GetMapping("/info")
//	public @ResponseBody String info() {
//		return "갠정보";
//	}
//	
//	@Secured("ROLE_ADMIN")
//	@GetMapping("/data")
//	public @ResponseBody String info() {
//		return "갠정보";
//	}


//	@PostMapping("/joinProc")
//	public String joinProc(User user) {
//		System.out.println("회원가입 진행 : " + user);
//		String rawPassword = user.getPassword();
//		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//		user.setPassword(encPassword);
//		user.setRole("ROLE_USER");
//		userRepository.save(user);
//		return "redirect:/";
//	}
}
