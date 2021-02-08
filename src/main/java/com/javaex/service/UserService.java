package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	// 회원가입
	public int join(UserVo userVo) {
		System.out.println("userService join()");
		return userDao.insert(userVo);
	}
	
	//로그인
	public UserVo login(UserVo userVo) {
		System.out.println("userService login()");
		return userDao.selectUser(userVo);
		
	}
	
	//idcheck
	public String idCheck(String id) {
		System.out.println("userService idCheck()");
		UserVo vo = userDao.selectOne(id);
		System.out.println("service" + vo);
		
		String result = "";
		
		if(vo == null) {
			//사용할수있는 id
			result = "can";
		}else {
			//사용할수없는 id
			result = "cant";
		}
		
		
		return result;

	}
	public UserVo modifyForm(int no) {
		System.out.println("userService modifyForm()");
		return userDao.selectUser2(no);
		
		
	}
	
	public void modify(UserVo userVo) {
		System.out.println("userService modify()");
		
		userDao.update(userVo);
	}
}
