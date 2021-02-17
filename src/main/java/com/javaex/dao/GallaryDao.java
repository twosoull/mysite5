package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GallaryVo;

@Repository
public class GallaryDao {
	@Autowired
	private SqlSession sqlSession;
	//LIST 
	public List<GallaryVo> getList(Map<String, Object> map) {
		System.out.println("[GallaryDao] : getList()");
		
		return sqlSession.selectList("gallary.selectGallaryList",map);
	
	}
	
	//DB에 정보입력
	public void insertGallary(GallaryVo gallaryVo) {
		System.out.println("[GallaryDao] : insertGallary");
		System.out.println(gallaryVo.toString());
		
		sqlSession.insert("gallary.insertGallary",gallaryVo);
		
	}
	
	//saveName으로 값얻기
	public GallaryVo getSelectOne(String saveName) {
		System.out.println("[GallaryDao] : getSelectOne");
		
		return sqlSession.selectOne("gallary.selectOne",saveName);
		
		
	}

	//no로 얻기
	public GallaryVo getSelectOne(int no) {
		System.out.println("[GallaryDao] : getSelectOne");
		return sqlSession.selectOne("gallary.selectOne2",no);
	}

	public int delete(int no) {
		System.out.println("[GallaryDao] : delete");
		return sqlSession.delete("gallary.delete",no);
	}

	public int getTotalGallary() {
		System.out.println("[GallaryDao] : getTotalGallary");
		return sqlSession.selectOne("gallary.getTotalGallary");
		
	}

	

	


}
