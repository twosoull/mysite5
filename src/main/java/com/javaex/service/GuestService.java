package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Service
public class GuestService {
	
	@Autowired
	private GuestDao guestDao;
	
	public List<GuestVo> list(int crtPage, String keyword) {
		System.out.println("service list()");
		
		int guestCnt = 5;
		
		int endRnum = crtPage * guestCnt;
		System.out.println("endRnum =" +endRnum);
		int startRnum = (crtPage - 1)*guestCnt+1;
		System.out.println("startRnum =" +startRnum );
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		map.put("keyword", keyword);
		
		return guestDao.selectGuestList(map);
	}
	
	public void add(GuestVo guestVo) {
		System.out.println("service add()");
		
		guestDao.insert(guestVo);
		
	}
	
	public int delete(GuestVo guestVo) {
		System.out.println("service delete()");
		System.out.println(guestVo);
		
		return guestDao.delete(guestVo);
	}
	
	// ajax 글저장 및 저장 vo 받기
	public GuestVo writeResultVo(GuestVo guestVo) {
		
		System.out.println("[service] dao:xml 시키기전 = " + guestVo);
		//글저장
		guestDao.insertSelectKey(guestVo);
		System.out.println("[service] dao:xml 시키기gn = " + guestVo);
		int no = guestVo.getNo();
		
		//글 1개 가져오기
		return  guestDao.selectOne(no);
		
		
	}
}
