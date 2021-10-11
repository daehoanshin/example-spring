package me.xbb123.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import me.xbb123.configuration.session.HttpSessionMember;
import me.xbb123.configuration.session.HttpSessionNiceauth;
import me.xbb123.example.session.SessionNiceauth;

@RequiredArgsConstructor
@Controller
public class MemberController {
	private final HttpSessionNiceauth httpSessionNiceauth;
	private final HttpSessionMember httpSessionMember;
	
	@PostMapping("/niceauth/response")
	@ResponseBody
	public boolean niceauthResponse(HttpServletRequest request) {
		// 실명인증 성공해서 응답이온경우 예시
		// 디비 조회해서 실제회원 인경우라고 가정하고 예시.
		SessionNiceauth niceauth = new SessionNiceauth();
		// 인증받은 식별코드..
		niceauth.setAuthId("3242213123213");
		niceauth.setPhoneNumber("01012341234");
		niceauth.setName("홍길동");
		// 로그인 시 세선에 정보 저장
		httpSessionNiceauth.setAttribute(niceauth);
		return true;
	}
	
	@PostMapping("/member/signup/save")
	@ResponseBody
	public boolean signupSave(@RequestParam String memberId) {
		// 회원가입 예시
		SessionNiceauth niceauth = httpSessionNiceauth.getAttribute();
//		 본인인증이 안된경우 예외처리
		if(niceauth == null) {
			throw new RuntimeException("회원가입시 본인인증은 필수 입니다.");
		}
		
		// DB에 저장될 정보 set 본인인증 정보까지
//		MemberSaveParameter member = new MemberSaveParameter();
//		member.setAuthId(niceauth.getAuthid());
		return true;
	}
}
