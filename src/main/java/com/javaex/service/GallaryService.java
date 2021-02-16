package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
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
	
	public  List<GallaryVo>  list() {
		System.out.println("[GallaryService]: list()");
		
		return gallaryDao.getList();
		
	}
	//저장할 정보 입력
	public String add(GallaryVo gallaryVo, MultipartFile file) {
		System.out.println("[GallaryService]: add()");
		
		//오리지날네임
		String orgName = file.getOriginalFilename();
		System.out.println("orgName = " + orgName);
		
		/////////저장파일
		//확장자
		//vo에 넣지않는다
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exName = "+exName);
		//저장파일명
		String saveName = System.currentTimeMillis()+UUID.randomUUID().toString()+exName;
		System.out.println("saveName = " + saveName);
		////////
		
		//파일경로
		String filePath = "C:\\javaStudy\\uploadGallaryFile";
		System.out.println("filePath ="+filePath);
		
		long fileSize = file.getSize();
		
		gallaryVo.setFilePath(filePath);
		gallaryVo.setFileSize(fileSize);
		gallaryVo.setSaveName(saveName);
		gallaryVo.setOrgName(orgName);
		
		gallaryDao.insertGallary(gallaryVo);
		
		
		String pathAndName  = filePath+"\\"+saveName;
		
		return pathAndName ;
		
	}
	
	//서버(하드디스크)정보 저장
	public void upload(String pathAndName,MultipartFile file) {
		
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
	public GallaryVo modalGallary(String path) {
		System.out.println("[GallaryService]: modalGallary()");
		
		String saveName = path.substring(1+path.lastIndexOf("/"));
		System.out.println(saveName);
		
		return gallaryDao.getSelectOne(saveName);
		
		
	}
	
	
}
