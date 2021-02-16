package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GallaryVo;

@Repository
public class GallaryDao {
	@Autowired
	private SqlSession sqlSession;
	//LIST 
	public List<GallaryVo> getList() {
		System.out.println("[GallaryDao] : getList()");
		
		return sqlSession.selectList("gallary.selectGallaryList");
	
	}
	
	//DB에 정보입력
	public void insertGallary(GallaryVo gallaryVo) {
		System.out.println("[GallaryDao] : insertGallary");
		System.out.println(gallaryVo.toString());
		
		sqlSession.insert("gallary.insertGallary",gallaryVo);
		
	}

	public GallaryVo getSelectOne(String saveName) {
		System.out.println("[GallaryDao] : getSelectOne");
		
		return sqlSession.selectOne("gallary.selectOne",saveName);
		
		
	}

	


}
