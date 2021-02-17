package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestVo> selectGuestList(Map<String, Object> map) {
		System.out.println("Dao selectGuest()");
		
		return sqlSession.selectList("guest.selectGuestList",map);
	}
	
	public void insert(GuestVo guestVo) {
		System.out.println("Dao insert()");
		System.out.println(guestVo);
		
		sqlSession.insert("guest.insert",guestVo);
	}
	
	public int delete(GuestVo guestVo) {
		System.out.println("Dao delete()");
		System.out.println(guestVo);
		
		return sqlSession.delete("guest.delete",guestVo);
		
	}
	
	/*글저장 */
	public void insertSelectKey(GuestVo guestVo) {
		System.out.println("[guestbookDao] insertSelectKey()");
		System.out.println("xml 실행전 :" + guestVo);
		sqlSession.insert("guest.insertSelectKey",guestVo);
		System.out.println("xml 실행 후 :" + guestVo);
		
		
	}
	
	public GuestVo selectOne(int no) {
		System.out.println("[Dao] selectOne()");
		
		return sqlSession.selectOne("guest.selectOne",no);
		
		
	}
	
}
