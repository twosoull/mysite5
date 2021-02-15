package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.PageAmount;
import com.javaex.vo.UserVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	
	
	
	public List<BoardVo> list() {
		System.out.println("Service : list");
		
		return boardDao.selectBoardList();
	}
	//검색
	public List<BoardVo> list2(String keyword) {
		System.out.println("Service : list2");
		System.out.println(keyword);
		
		List<BoardVo> boardList = boardDao.selectList2(keyword);
		return boardList;
	}
	
	//검색+페이징
	public Map<String,Object> list3(String keyword,int crtPage){
		System.out.println("Service : list3");
		
		//crtPage --> 시작번호 끝번호  1--> 1,10  2--> 11,20 3-->21,30
		
		//페이지당 글객수
		int listCnt = 20;
		crtPage =(crtPage > 0) ? crtPage : (crtPage = 1);
		
		//현재페이지
		//crtPage;
		
		//시작글 번호 starRnum
		//)(crtPage -1 )* listCnt) +1
		
		//1 1   0*10 -- 0 + 1  1
		//2 11  1*10 -- >10 + 1  11
		//3 21  2*10 --> 20 + 1  21
		
		
		//1 1~15  1-1   0*15 -- 0 + 1  1
		//2 16~30 2-1   1*15 -- 15+1   16
		//3 31~50 3-1   2*15 -- 30+1   31
		int startRnum = (crtPage-1)*listCnt+1;
		
		//끝글 번호 endRnum
		//페이지 * listCnt
		//1*10
		int endRnum = (startRnum + listCnt) -1; 
		
		System.out.println("service: keyword" + keyword);
		System.out.println("service: startRnum" + startRnum);
		System.out.println("service: endRnum" + endRnum);
		
		List<BoardVo> boardList = boardDao.selectList3(keyword,startRnum,endRnum);
		
		///////////////////////////////////////
		// 페이징 계산 
		///////////////////////////////////////
		
		//페이지당 버튼 갯수
	
		int pageBtnCount = 5;
		//전체 글 갯수
		
		int totalCount = boardDao.selectTotalCnt(keyword);
		
		//1--> 1~5
		//2--> 1~5
		//3--> 1~5
		//4--> 1~5
		//5--> 1~5
		//6--> 6~10
		//7--> 7~10
		
		
		//마지막 버튼 번호
		int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtnCount) * pageBtnCount; 
		// 1/5.0 --> 0.2 --> 1.0 -->1 * 5 -->5
		// 2/5.0 --> 0.4 --> 1.0 -->1 * 5 -->5
		// 5/5.0 --> 1.0 --> 1.0 -->1 * 5 -->5
		
		// 6/5.0 --> 1.2 --> 2.0 -->2 * 5 -->10
		// 10/5.0 --> 2.0 --> 2.0-->2 * 5 -->10
		
		// 11/5.0 --> 2.2 --> 3.0 -->3* 5 -->15
		
		//시작버튼 번호
		int startPageBtnNo = endPageBtnNo - (pageBtnCount - 1);
		
		//다음버튼 boolean
		
		boolean next;
		//int listCnt = 10;
		//totalCount 글개수
		if(endPageBtnNo * listCnt < totalCount) { // 5*10 <1234
			next = true;
		}else {									  // 125*10 <1234
			next = false;
			endPageBtnNo = (int) Math.ceil(totalCount/(double)listCnt);
			
			//마지막 페이지는 124 p가 되어야 하는데  위에 공식이 없을 경우 125로 잡혀버린다
			//5개씩 잡기로 했기 때문인데
			//그래서 마지막만  == 올림(글총갯수/(double)한페이지당 글 ) 공식을 새우면
			//(1234/10) == 1234/10 == 123.4 가되 는데 올림하면 124 되서 마지막이 124가 된다
			//
		}
		
		//이전버튼 boolean
		boolean prev ;
		
		if(startPageBtnNo !=1) {
			prev = true;
		}else {
			prev = false;
		}
		
		//prev startPageBtnNo  endPageBtnNo next 
		//한번만 사용할거기 때문에 map으로 보낸다
		// ++ boardList도 보내야한다
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("prev",prev);
		pMap.put("startPageBtnNo",startPageBtnNo);
		pMap.put("endPageBtnNo",endPageBtnNo);
		pMap.put("next",next);
		pMap.put("boardList",boardList);
		
		return pMap;
	}
	
	
	
	
	//page의 양을 센다
	public PageAmount pageAmount(int pageNum) {
		System.out.println("Service : pageAmount");
		PageAmount pageAmount = new PageAmount();
		
		int boardAmount = boardDao.boardAmount();
		
		pageAmount.setBoardAmount(boardAmount); //처음 페이지 양구하기
		pageAmount.setPrev(pageNum-1);	//이전페이지
		pageAmount.setNext(pageNum+1);	//이후페이지
		pageAmount.setPageNow(pageNum);  //현재페이지
		System.out.println("page : "+pageAmount.getPage());
		
		return pageAmount;
	}

	public BoardVo read(int no) {
		System.out.println("Service : read");
		System.out.println(no);
		boardDao.upHit(no);
		return boardDao.selectBoard(no);
	}
	//writeForm.jsp에서 session no값을 name = userNo로 파라미터로 넘겨
	//객체로 받은방식
	public void write(BoardVo boardVo) {
		System.out.println("Service : write");

		boardDao.insertBoard(boardVo);
		
		
	}
	
	//service에서 세션값을 얻어 vo에 담는 방식
	//만약 프로그램을 다른곳에 갈아끼울경우 객체타입이 다를 수 있다고 생각이들어
	//jsp에서 hidden으로 세션값을 주는게 좀더 나은 방식이라 생각이 들기도합니다
	public void write(BoardVo boardVo,HttpSession session) {
		System.out.println("Service : write + session");
		
		//jsp에서 hidden으로 넣는 것보다 여기서 값을 가져오는게 안전하다고
		//판단해서 만들어 봤는데 맞을지 모르겠습니다
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		System.out.println(boardVo);
		
		boardDao.insertBoard(boardVo);
	
	}
	public BoardVo modifyForm(int no) {
		System.out.println("Service : modifyForm");
		
		BoardVo boardVo = boardDao.selectBoard(no);
		//필요없는 정보인 userNo를 기본값 0으로 바꿔준다
		//선생님 이렇게 해도될까요??
		boardVo.setUserNo(0);
		
		return boardVo;
	}
	
	public void modify(BoardVo boardVo) {
		System.out.println("Service : modify");
		
		boardDao.updateBoard(boardVo);
		
	}
	
	public void remove(int no) {
		System.out.println("Service : remove");
		
		boardDao.deleteBoard(no);
		
	}
}
