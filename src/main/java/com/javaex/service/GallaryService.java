package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GallaryDao;
import com.javaex.vo.GallaryVo;

@Service
public class GallaryService {
	@Autowired
	private GallaryDao gallaryDao;

	public Map<String, Object> list(int crtPage, String keyword) {
		System.out.println("[GallaryService]: list()");

		// 한페이지 당갯수
		int gallaryCnt = 12;
		
		//crtPage 가 음수일 경우
		crtPage = (crtPage > 0)? crtPage : (crtPage = 1);
		
		// rNum end
		int endRnum = crtPage * gallaryCnt;
		System.out.println("endRnum = " + endRnum);
		// rNum start
		int startRnum = (crtPage - 1) * gallaryCnt + 1;
		System.out.println("startRnum =" + startRnum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("endRnum", endRnum);
		map.put("startRnum", startRnum);
		map.put("keyword", keyword);
		
		//리스트
		List<GallaryVo> gallaryList = gallaryDao.getList(map);
		//////////////////////////
		//
		int pageCnt = 6;

		int endPageNum = (int) (Math.ceil(crtPage / (double) pageCnt) * pageCnt);
		int startPageNum = endPageNum - (pageCnt - 1);
		
		//이전
		boolean prev;
		if(startPageNum != 1) {
			prev = true;
		}else {
			prev = false; 
		}
		//다음
		int totalGallary = gallaryDao.getTotalGallary(); 
		
		
		boolean next;
		if(endPageNum <totalGallary/gallaryCnt) { // totalGallary / 
			next = true;
		}else {
			next = false;
			endPageNum = totalGallary/gallaryCnt;
			if(totalGallary/gallaryCnt >0) {
				endPageNum += 1 ;
			}
		}
		
	
		
		System.out.println("endPageNum = " + endPageNum);
		System.out.println("StartPageNum = " + startPageNum);
		
		Map<String, Object> pMap = new HashMap<String, Object>();

		pMap.put("startPageNum", startPageNum);
		pMap.put("endPageNum", endPageNum);
		pMap.put("gallaryList", gallaryList);
		pMap.put("prev", prev);
		pMap.put("next", next);
		pMap.put("totalGallary", totalGallary);
		return pMap;

	}

	// 저장할 정보 입력
	public String add(GallaryVo gallaryVo, MultipartFile file) {
		System.out.println("[GallaryService]: add()");

		// 오리지날네임
		String orgName = file.getOriginalFilename();
		System.out.println("orgName = " + orgName);

		///////// 저장파일
		// 확장자
		// vo에 넣지않는다
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exName = " + exName);
		// 저장파일명
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		System.out.println("saveName = " + saveName);
		////////

		// 파일경로
		String filePath = "C:\\javaStudy\\uploadGallaryFile";
		System.out.println("filePath =" + filePath);

		long fileSize = file.getSize();

		gallaryVo.setFilePath(filePath);
		gallaryVo.setFileSize(fileSize);
		gallaryVo.setSaveName(saveName);
		gallaryVo.setOrgName(orgName);

		gallaryDao.insertGallary(gallaryVo);

		String pathAndName = filePath + "\\" + saveName;

		return pathAndName;

	}

	// 서버(하드디스크)정보 저장
	public void upload(String pathAndName, MultipartFile file) {

		try {
			byte[] filedata = file.getBytes();
			OutputStream out = new FileOutputStream(pathAndName);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			bos.write(filedata);
			bos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// path로 vo얻기
	public GallaryVo modalGallary(String path) {
		System.out.println("[GallaryService]: modalGallary()");

		String saveName = path.substring(1 + path.lastIndexOf("/"));
		System.out.println(saveName);

		return gallaryDao.getSelectOne(saveName);

	}

	// no를 vo얻기
	public GallaryVo modalGallary(int no) {
		System.out.println("[GallaryService]: modalGallary()");

		return gallaryDao.getSelectOne(no);
	}

	public int remove(int no) {

		System.out.println("[GallaryService]: remove()");
		return gallaryDao.delete(no);

	}

}
