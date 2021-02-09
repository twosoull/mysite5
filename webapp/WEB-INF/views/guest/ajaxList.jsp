<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>

<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->

		<!-- //nav -->

		<div id="aside">
			<h2>방명록</h2>
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
								<th><label class="form-text" for="input-uname">이름</label></td>
								<td><input id="input-uname" type="text" name="name"></td>
								<th><label class="form-text" for="input-pass">패스워드</label></td>
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
			<div id = "guestbookListArea">
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

</body>
<script type="text/javascript">

//DOM이 생성되면 
$("document").ready(function(){
	console.log("ready");
	
	$.ajax({
		
		url : "${pageContext.request.contextPath }/api/guestbook/list",  //컨트롤러의 url과 파라미터
		type : "post",	// 겟 포스트
		//contentType : "application/json",
		//data : {name: name, password:password, content:content},

		dataType : "json",
		success : function(guestbookList){  //성공시
				console.log(guestbookList);
			for(var i= 0; i<guestbookList.length; i++){
				render(guestbookList[i]);
			}
		},
		error : function(XHR, status, error) { //실패
			console.error(status + " : " + error);
		}
	});
	
});


	$("#btnSubmit").on("click",function(){
		console.log("btnSubmit");
		//방명록 데이터 수집
		var name = $("[name='name']").val();
		var password = $("[name='pass']").val();
		var content  = $("[name='content']").val();
		//ajax 방식으로 데이터 요청
		
		console.log(name);
		console.log(password);
		console.log(content);
		
		$.ajax({
			
			url : "${pageContext.request.contextPath }/api/guestbook/write",  //컨트롤러의 url과 파라미터
			type : "post",	// 겟 포스트
			//contentType : "application/json",
			data : {name: name, password:password, content:content},

			dataType : "json",
			success : function(guestVo){  //성공시
					console.log(guestVo.name);
					render(guestVo); //게스트북 정보출력
			},
			error : function(XHR, status, error) { //실패
				console.error(status + " : " + error);
			}
		});

	});

	
	
	// 방명록 글정보 + html 조합하여 화면에 출력
	function render(guestVo){
		var str = "";
		str += '<table class="guestRead">';
		str += '<colgroup>';
		str += '	<col style="width: 10%;">';
		str += '	<col style="width: 40%;">';
		str += '	<col style="width: 40%;">';
		str += '	<col style="width: 10%;">';
		str += '</colgroup>';
		str += '<tr>';
		str += '	<td>'+guestVo.no+'</td>';
		str += '	<td>'+guestVo.name+'</td>';
		str += '	<td>'+guestVo.regDate+'</td>';
		str += '	<td><a href="">[삭제]</a></td>';
		str += '</tr>';
		str += '<tr>';
		str += '	<td colspan=4 class="text-left">'+guestVo.content+'</td>';
		str += '</tr>';
		str += '</table>';
	
		$("#guestbookListArea").prepend(str);
		
	}
	
</script>


</html>