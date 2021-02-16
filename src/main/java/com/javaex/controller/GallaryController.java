package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GallaryService;
import com.javaex.vo.GallaryVo;

@Controller
@RequestMapping(value="/gallary" , method = {RequestMethod.GET,RequestMethod.POST})
public class GallaryController {
	
	@Autowired
	private GallaryService gallaryService;
	
	@RequestMapping(value="/list" , method = {RequestMethod.GET,RequestMethod.POST})
	public String list(Model model) {
		System.out.println("GallaryController : list()");
		
		List<GallaryVo> gallaryList = gallaryService.list();
		model.addAttribute("gallaryList",gallaryList);
		
		return "/gallary/list";
	}
	@RequestMapping(value="/add" , method = {RequestMethod.GET,RequestMethod.POST})
	public String add(
					  @RequestParam("file")MultipartFile file,
					  @ModelAttribute GallaryVo gallaryVo) {
		System.out.println("GallaryController : add()");
		System.out.println(gallaryVo.getContent());
		System.out.println(gallaryVo.getUserNo());
		System.out.println(file.getOriginalFilename());
		
		//db 저장
		String pathAndName = gallaryService.add(gallaryVo,file);
		
		//서버(하드디스크) 저장
		gallaryService.upload(pathAndName, file);
		
		return "redirect:/gallary/list";
	}
}
