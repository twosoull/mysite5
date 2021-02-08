package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	// 회원가입폼
	@RequestMapping(value = "/joinForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {
		System.out.println("joinForm");

		return "user/joinForm";
	}

	// 회원가입
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("join");
		System.out.println("controller join UserVo : " + userVo);

		userService.join(userVo);
		
		return "user/joinOk";
	}
	//id 중복체크
	@ResponseBody
	@RequestMapping(value ="/idcheck" ,  method= {RequestMethod.GET , RequestMethod.POST})
	public String idCheck(@RequestParam("id") String id,@RequestParam("password") String password) { //modelAtribute
		System.out.println("idcheck");
		System.out.println("chkid " + id);
		//패스워드는 테스트용 코드 추가
		System.out.println("chkPass:"+password);
		String result = userService.idCheck(id);
		System.out.println(result);
		return result; // @ResponseBody --> response의 body 영역에 data만 보낸다(return값)
	}
	
	
	// 로그인폼
	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {
		System.out.println("loginForm");

		return "user/loginForm";
	}

	// 로그인
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("/user/login");
		System.out.println(userVo);
		
		UserVo authUser = userService.login(userVo);
		System.out.println("login : "+authUser);
		// 실패했을때
		if (authUser == null) {
			
			return "redirect:/user/loginForm?result=fail";
		} else {
			// 성공했을때
			session.setAttribute("authUser", authUser);

			return "redirect:/";
		}
	}
	//로그아웃
	@RequestMapping(value = "/logout", method = {RequestMethod.GET,RequestMethod.POST})
	public String logout(HttpSession session) {
		System.out.println("logout");
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/modifyForm", method = {RequestMethod.GET,RequestMethod.POST})
	public String modifyForm(Model model ,HttpSession session) {
		System.out.println("controller : modifyForm");
		//세션에서 no값 가져오기 
		int no = ((UserVo)session.getAttribute("authUser")).getNo();
		
		//세션값이 없으면 --> 로그인폼
		
		//회원정보 가져오기
		UserVo userVo = userService.modifyForm(no);
		
		model.addAttribute("userVo",userVo);
		
		return "user/modifyForm";
	}
	@RequestMapping(value = "/modify", method = {RequestMethod.GET,RequestMethod.POST})
	public String modify(@ModelAttribute UserVo userVo,HttpSession session) {
		System.out.println("countroller : modify");
		System.out.println(userVo);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		int no = authUser.getNo();
		
		userVo.setNo(no);
		
		userService.modify(userVo);
		
		//같은주소이므로 변수지정만해서 이름만 바꿔준다
		authUser.setName(userVo.getName());
		
		return "redirect:/";
	}
}
