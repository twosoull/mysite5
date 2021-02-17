package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value = "/api/guestbook")
public class ApiGuestbookController {
	
	@Autowired
	private GuestService guestService;
	
	@ResponseBody
	@RequestMapping(value="/list")
	public List<GuestVo> list() {
		System.out.println("[ApiGuestbookController] /list");
		
		//guestService.list(key);
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/write")
	public GuestVo write(@ModelAttribute GuestVo guestVo ) {
		System.out.println("[ApiGuestbookController] /write");
		System.out.println(guestVo);
		//입력된 vo전달하고 저장 vo를 받아야함
		return guestService.writeResultVo(guestVo);
	}
	
	//json형식일때에
	@ResponseBody
	@RequestMapping(value = "/write2")
	public GuestVo write2(@RequestBody GuestVo guestVo ) {
		System.out.println("[ApiGuestbookController] /write");
		System.out.println(guestVo);
		//입력된 vo전달하고 저장 vo를 받아야함
		return guestService.writeResultVo(guestVo);
	}
	
	//글삭제
	@ResponseBody
	@RequestMapping(value = "/remove")
	public int remove(@ModelAttribute GuestVo guestVo) {
		System.out.println("[apiGuestbookController] / remove");
		System.out.println(guestVo);
		
		int count = guestService.delete(guestVo);
		
		return count;
	}
}
