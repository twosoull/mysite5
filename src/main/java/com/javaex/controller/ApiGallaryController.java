package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GallaryService;
import com.javaex.vo.GallaryVo;

@Controller
@RequestMapping(value="/api/gallary" , method= {RequestMethod.GET,RequestMethod.POST})
public class ApiGallaryController {
	
	@Autowired
	private GallaryService gallaryService;
	
	@ResponseBody
	@RequestMapping(value="modalGallary" , method= {RequestMethod.GET,RequestMethod.POST})
	public GallaryVo modalGallary(@RequestParam("path")String path) {
		System.out.println(path);
		
		return gallaryService.modalGallary(path);
		
	}

}
