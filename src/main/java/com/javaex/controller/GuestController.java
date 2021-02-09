package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value = "/guest", method = { RequestMethod.GET, RequestMethod.POST })
public class GuestController {

	@Autowired
	private GuestService guestService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("controller list()");

		model.addAttribute("guestList", guestService.list());

		return "guest/addList";
	}

	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public String add(@ModelAttribute GuestVo guestVo) {
		System.out.println("controller add");

		guestService.add(guestVo);
		return "redirect:/guest/list";
	}

	@RequestMapping(value = "/deleteForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm(@RequestParam("no") int no) {
		System.out.println("controller deleteForm");
		System.out.println("no =" +no);
		
		return "guest/deleteForm";
	}
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@ModelAttribute GuestVo guestVo) {
		System.out.println("controller delete");
		System.out.println(guestVo);
		
		int count = guestService.delete(guestVo);
		
		if(count == 1 ) {
			//성공
			return "redirect:/guest/list";
		}else {
			//실패
			return "redirect:/guest/deleteForm?no="+guestVo.getNo()+"&result=fail";
		}
	}
	//ajaxList
	@RequestMapping(value="/ajaxList",method = {RequestMethod.GET , RequestMethod.POST})
	public String ajaxList() {
		System.out.println("controller ajaxList");
		return "guest/ajaxList";
	}

}
