package com.javaex.dao;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;
	
	//회원가입(회원정보 등록)
	public int insert(UserVo userVo) {
		System.out.println("[DAO] : insert");
		System.out.println(userVo);
		 
		return sqlSession.insert("user.insert",userVo); 
	}
	
	//로그인 --> 회원정보가져오기(login)
	public UserVo selectUser(UserVo userVo) {
		System.out.println("[DAO] : userSelect");
		System.out.println(userVo);
		
		return sqlSession.selectOne("user.selectUser",userVo);
	}
	//아이디체크
	public UserVo selectOne(String id) {
		System.out.println("[DAO] : selectOne");
		System.out.println(id);
		
		return sqlSession.selectOne("user.selectById",id);
	
	}
	
	//회원정보수정폼 -->회원정보 가져오기(modifyForm)
	public UserVo selectUser2(int no ) {
		System.out.println("[DAO] : selectUser2");
		
		//UserVo로 받기
		return sqlSession.selectOne("user.selectUser2",no);
	}
	
	public void update(UserVo userVo) {
		System.out.println("[DAO] : update");
		System.out.println(userVo);
		//count가 쓸곳이없다
		sqlSession.update("user.update",userVo);
		
	}
	
}
