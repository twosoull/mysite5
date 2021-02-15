package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//글전체 가져오기(리스트만 출력할때)
	public List<BoardVo> selectBoardList() {
		System.out.println("BoardDao : selectBoardList()");
		List<BoardVo> list = sqlSession.selectList("board.selectList");
		System.out.println(list);
				
	return list;
	}
	
	//글전체 가져오기 (키워드)
	public List<BoardVo> selectList2(String keyword){
		System.out.println("BoardDao: selectList2");
		System.out.println(keyword);
		
		return sqlSession.selectList("board.selectList2",keyword);
		
	}
	public List<BoardVo> selectList3(String keyword,int startRnum,int endRnum){
		System.out.println("BoardDao: selectList3");
		//vo로 묶거나 map 으로 묶어줘야 하는데 한번쓰고 말것이기 때문에 map으로 묶어주자
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		System.out.println(map);
		
		return sqlSession.selectList("board.selectList3",map);
	}
	
	public int selectTotalCnt(String keyword) {
		System.out.println("[BoardDao.selectTotalCnt()");
		
		return sqlSession.selectOne("board.selectTotalCnt",keyword);
		
	}
	
	
	//페이지숫자를 얻기위한 글갯수를 가져온다
	
	public int boardAmount() {
		System.out.println("BoardDao : boardAmount()");
		return sqlSession.selectOne("pageAmount");
	}
	
	public BoardVo selectBoard(int no) {
		System.out.println("BoardDao : selectBoard()");
		System.out.println(no);
		
		return sqlSession.selectOne("board.selectBoard",no);
	
	}
	
	public void upHit(int no) {
		System.out.println("BoardDao : upHit()");
		
		sqlSession.update("board.updateHit",no);
	}
	public void insertBoard(BoardVo boardVo) {
		System.out.println("BoardDao : insertBoard()");
		/*
		for(int i = 1 ; i<=1234; i++) {
			boardVo.setTitle(i + "번째 글 제목입니다.");
			boardVo.setContent(i + "번째 글 내용입니다.");
			
			sqlSession.insert("board.insert",boardVo);
		}
		*/
		sqlSession.insert("board.insert",boardVo);
	
	}
	
	///수정폼 원래 정석적인 방법 sql문에서 필요한 정보만 빼오는 경우
	/*
	public BoardVo selectBoard2(int no) {
		System.out.println("BoardDao : selectBoard()");
		System.out.println(no);
		
		return sqlSession.selectOne("board.selectBoard2",no);
		
		
	}
	*/
	
	public void updateBoard(BoardVo boardVo) {
		System.out.println("BoardDao : updateBoard");
		
		sqlSession.update("board.update",boardVo);
		
	}
	
	public void deleteBoard(int no) {
		System.out.println("BoardDao : deleteBoard");
		System.out.println(no);
		
		sqlSession.delete("board.delete",no);
		
	}
}
