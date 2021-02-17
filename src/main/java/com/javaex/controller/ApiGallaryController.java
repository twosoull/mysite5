package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GallaryService;
import com.javaex.vo.GallaryVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/api/gallary" , method= {RequestMethod.GET,RequestMethod.POST})
public class ApiGallaryController {
	
	@Autowired
	private GallaryService gallaryService;
	
	//path를 받아와 얻는방식
	@ResponseBody
	@RequestMapping(value="/modalGallary" , method= {RequestMethod.GET,RequestMethod.POST})
	public GallaryVo modalGallary(@RequestParam("path")String path) {
		System.out.println("[ApiGallaryController] : modalGallary()");
		
		System.out.println(path);
		
		return gallaryService.modalGallary(path);
		
	}
	
	//no를 받아와 얻는 방식
	@ResponseBody
	@RequestMapping(value="/modalGallary2" , method= {RequestMethod.GET,RequestMethod.POST})
	public GallaryVo modalGallary(@RequestParam("no")int no) {
		System.out.println("[ApiGallaryController] : modalGallary()");
		System.out.println(no);
		return gallaryService.modalGallary(no);
		
		
		
	}
	
	@ResponseBody
	@RequestMapping(value="/remove" , method= {RequestMethod.GET,RequestMethod.POST})
	public int remove(@RequestParam("no")int no) {
		System.out.println("[ApiGallaryController] : modalGallary()");
		System.out.println(no);
		return gallaryService.remove(no);
		
	}
	@ResponseBody
	@RequestMapping(value="/session" , method= {RequestMethod.GET,RequestMethod.POST})
	public int session(HttpSession session) {
		System.out.println("[ApiGallaryController] : session()");
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		System.out.println(userVo.getNo());
		if(userVo.getNo() == 0) {
			return 0;
		}else {
			return userVo.getNo();
		}
	}
}
