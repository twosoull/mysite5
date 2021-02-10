<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>
</head>

<body>
	<div id="wrap">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->

		<!-- //nav -->

		<div id="aside">
			<h2>방명록${pageContext.request.contextPath}</h2>
			<ul>
				<li>일반방명록</li>
				<li>ajax방명록</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>일반방명록</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>방명록</li>
						<li class="last">일반방명록</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="guestbook">
				<!-- <form action="" method=""> -->
				<table id="guestAdd">
					<colgroup>
						<col style="width: 70px;">
						<col>
						<col style="width: 70px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th><label class="form-text" for="input-uname">이름</label>
							</td>
							<td><input id="input-uname" type="text" name="name"></td>
							<th><label class="form-text" for="input-pass">패스워드</label>
							</td>
							<td><input id="input-pass" type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
						</tr>
						<tr class="button-area">
							<td colspan="4"><button id="btnSubmit" type="submit">등록</button></td>
						</tr>
					</tbody>

				</table>
				<!-- //guestWrite -->
				<input type="hidden" name="action" value="add">

				<!-- 	</form>	 -->
				<div id="guestbookListArea">
					<!-- 방명록 글 리스트..출력영역 -->

				</div>

				<!-- //guestRead -->

			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

	<!-- 모달창영역 -->
	<div class="modal fade" id="delModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">방명록 삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호</label>
					<input id="modalPassword" type="password" name="" value="">
					
					<!-- no는 hidden 처리 -->
					<input id= "modalNo" type="text" name="no" value="">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button id= "modalBtnDel"type="button" class="btn btn-primary">삭제</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
<script type="text/javascript">
	//DOM이 생성되면 
	$("document").ready(function() {
		console.log("ready");

		//리스트출력	
		fetchList();

	});
	//모달창 삭제버튼 클릭할때 --> 삭제프로세서
	$("#modalBtnDel").on("click",function(){
		console.log("모달창 삭제버튼클릭");
		//모달창 비밀번호 , no 수집
		var no = $("#modalNo").val();
		
		//줄여서 함수로 만들어주기 20개라고 가장
		var guestbookVo = {
			password: $("#modalPassword").val(),
			no : $("#modalNo").val()
		};
		console.log(guestbookVo);
		
		
		//모달창 no password 보내고 count(삭제여부) 받기
		$.ajax({

			url : "${pageContext.request.contextPath }/api/guestbook/remove", //컨트롤러의 url과 파라미터
			type : "post", // 겟 포스트
			//contentType : "application/json",
			data : guestbookVo,

			dataType : "json",
			success : function(count) { //성공시
				console.log("count:"+count);
				
				if(count == 1){
				//no 테이블(글) 화면에서 안보이도록 처리
				//count==1 --> 삭제작업
				$("#t-"+ no).remove();
				
				
				//모달창닫기
				$("#delModal").modal("hide");
				}else{
				//count == 0
				
				
				alert("비밀번호가 틀렸습니다");
				$("#modalPassword").val("");
				//모달창 닫기
				//$("#delModal").modal("hide");
					
				}
				
			},
			error : function(XHR, status, error) { //실패
				console.error(status + " : " + error);
			}
		});

		
	});
	
	//삭제버튼 클릭
	$("#guestbookListArea").on("click", "a", function() {

		event.preventDefault();
		console.log("모달 창 호출");
		//비밀번호 필드 비워놓기
		$("#modalPassword").val("");
		
		
		var no = $(this).data("no");
		
		//삭제버튼에 적어준 data-no 속성의 값을 불러와 모달창에
		//id="modalNo" 인 곳에 넣어준다
		//삭제버튼 속성 --> 모달창으로 내용이동
		$("#modalNo").val(no);
		//이순서가 가능한 것은 모달창은 html 가장밑에 숨어있어서 이미 있기때문에
		//값을 바꿔준후에 띄우는 것일 뿐이다
		//모달창 띄우기
		$("#delModal").modal();
		
		
	});

	$("#btnSubmit").on("click", function() {
		console.log("btnSubmit");
		//방명록 데이터 수집
		var guestbookVo = {
			name : $("[name='name']").val(),
			password : $("[name='pass']").val(),
			content : $("[name='content']").val()	
		}
	
		console.log(guestbookVo);
		
		//ajax 방식으로 데이터 요청
		$.ajax({

			url : "${pageContext.request.contextPath }/api/guestbook/write2", //컨트롤러의 url과 파라미터
			type : "post", // 겟 포스트
			contentType : "application/json",
			data : JSON.stringify(guestbookVo),

			dataType : "json",
			success : function(guestVo) { //성공시
				console.log(guestVo.name);
				render(guestVo, "up"); //게스트북 정보출력

				//입력폼 비우기

				$("[name='name']").val("");
				$("[name='pass']").val("");
				$("[name='content']").val("");
			},
			error : function(XHR, status, error) { //실패
				console.error(status + " : " + error);
			}
		});

	});

	// 방명록 글정보 + html 조합하여 화면에 출력
	function render(guestVo, updown) {
		var str = "";
		str += '<table id ="t-'+guestVo.no+'"class="guestRead">';
		str += '<colgroup>';
		str += '	<col style="width: 10%;">';
		str += '	<col style="width: 40%;">';
		str += '	<col style="width: 40%;">';
		str += '	<col style="width: 10%;">';
		str += '</colgroup>';
		str += '<tr>';
		str += '	<td>' + guestVo.no + '</td>';
		str += '	<td>' + guestVo.name + '</td>';
		str += '	<td>' + guestVo.regDate + '</td>';
		str += '	<td><a href="" data-no="'+guestVo.no+'">[삭제]</a></td>';
		str += '</tr>';
		str += '<tr>';
		str += '	<td colspan=4 class="text-left">' + guestVo.content + '</td>';
		str += '</tr>';
		str += '</table>';

		if (updown == "down") {
			$("#guestbookListArea").append(str);

		} else if (updown == "up") {
			$("#guestbookListArea").prepend(str);
		} else {
			console.log("테이블 순서 미지정")
		}
	}

	//전체 리스트 출력

	function fetchList() {

		$.ajax({

			url : "${pageContext.request.contextPath }/api/guestbook/list", //컨트롤러의 url과 파라미터
			type : "post", // 겟 포스트
			//contentType : "application/json",
			//data : {name: name, password:password, content:content},

			dataType : "json",
			success : function(guestbookList) { //성공시
				console.log(guestbookList);

				for (var i = 0; i < guestbookList.length; i++) {
					render(guestbookList[i], "down");
				}
			},
			error : function(XHR, status, error) { //실패
				console.error(status + " : " + error);
			}
		});

	}
</script>


</html>