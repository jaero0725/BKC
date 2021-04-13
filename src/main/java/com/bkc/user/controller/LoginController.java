package com.bkc.user.controller;

import java.security.Principal;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bkc.user.service.LoginService;
import com.bkc.user.service.UserService;
import com.bkc.user.vo.UserVO;


@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	//@Autowired
	//private LoginService loginService;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private UserService userService;
	
	// 로그인 페이지로 보내기 
	@RequestMapping(value= "/login", method= {RequestMethod.GET, RequestMethod.POST})
	public String goLoginPage() {
		return "delivery/login";
	}
	
	// login 처리
	@RequestMapping(value= "/login.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String doLogin(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, @ModelAttribute UserVO vo) {

		if (error != null) {
			model.addAttribute("errorMsg", "유효하지 않은 회원 입니다.");
		}
		if (logout != null) {
			model.addAttribute("logoutMsg", "로그아웃 되었습니다.");
		}
		
		//오류 없을 경우
		if(error==null && logout == null) {
			
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("로그인 회원 정보  : " + principal.toString());
		logger.info("로그인 실행 : " + model.toString()+"\n " + vo.toString());
	
		// loginService에서 로그인시 암호화
		System.out.println("들어온 vo 정보 : "  + vo.toString());
		UserVO passwdCheck = userService.passwdCheck(vo);
		
		System.out.println("passwdCheck 은 !! : " + passwdCheck.getPassword()  + "\n vo : "  + vo.getPassword());
		boolean pwdMatch = passwordEncoder.matches(vo.getPassword(), passwdCheck.getPassword());
		
		if(pwdMatch) {
			System.out.println("match 완료");
			return "delivery/login";	
		} else {
			System.out.println("match 실패");
			model.addAttribute("errorMsg", "유효하지 않은 회원 입니다.");
			return "delivery/login";
		}
	}
	
	// 회원가입 페이지로 이동
	@RequestMapping(value = "/join", method= {RequestMethod.GET, RequestMethod.POST})
	public String join() {
		System.out.println("회원가입 페이지 이동");
		return "delivery/join";
	}

	// 회원가입 디테일 페이지로 이동
	@RequestMapping(value = "/joindetail", method= {RequestMethod.GET, RequestMethod.POST})
	public String joinDetail(Model model) {
		System.out.println("회원가입 디테일 페이지 이동");
		model.addAttribute("user", new UserVO());
		return "delivery/joindetail";
	}
}
