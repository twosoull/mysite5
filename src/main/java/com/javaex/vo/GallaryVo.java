package com.javaex.vo;

public class GallaryVo {
	private int no;  //primary key
	private int userNo; //users와의 forign key
	private String content; //글의 코멘트
	private String filePath; //file이 저장될 경로 
	private String orgName; //파일의 오리지널 이름
	private String saveName; //파일이 저장될때의 이름
	private long fileSize; //file의 사이즈
	//users name
	private String name; 
	

	public GallaryVo() {
		
	}
	
	public GallaryVo(String filePath, String orgName, String saveName, long fileSize) {
		super();
		this.filePath = filePath;
		this.orgName = orgName;
		this.saveName = saveName;
		this.fileSize = fileSize;
	}

	public GallaryVo(String content, String filePath, String orgName, String saveName, long fileSize) {
		super();
		this.content = content;
		this.filePath = filePath;
		this.orgName = orgName;
		this.saveName = saveName;
		this.fileSize = fileSize;
	}

	public GallaryVo(int no, int userNo, String content, String filePath, String orgName, String saveName, long fileSize) {
		
		this.no = no;
		this.userNo = userNo;
		this.content = content;
		this.filePath = filePath;
		this.orgName = orgName;
		this.saveName = saveName;
		this.fileSize = fileSize;
	}
	
	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "GallaryVo [no=" + no + ", user_no=" + userNo + ", content=" + content + ", filePath=" + filePath + ", orgName=" + orgName + ", saveName="
				+ saveName + ", fileSize=" + fileSize + "]";
	}
	
	
}
