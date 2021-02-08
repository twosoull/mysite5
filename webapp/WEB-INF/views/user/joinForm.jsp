<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>

<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">


</head>

<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->

		<!-- //nav -->

		<div id="aside">
			<h2>회원</h2>
			<ul>
				<li>회원정보</li>
				<li>로그인</li>
				<li>회원가입</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>회원가입</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>회원</li>
						<li class="last">회원가입</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="user">
				<div id="joinForm">
					<form action="${pageContext.request.contextPath}/user/joinOk" method="">


						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> <input type="text" id="input-uid" name="id" value="" placeholder="아이디를 입력하세요">
							<button type="button" id="btnCheck">중복체크</button>
						</div>

						<p id="msg">
							<!-- 아이디 사용할 예제 -->
						</p>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label> <input type="text" id="input-pass" name="password" value="" placeholder="비밀번호를 입력하세요">
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label> <input type="text" id="input-name" name="name" value="" placeholder="이름을 입력하세요">
						</div>

						<!-- //나이 -->
						<div class="form-group">
							<span class="form-text">성별</span> <label for="rdo-male">남</label> <input type="radio" id="rdo-male" name="gender" value="male"> <label for="rdo-female">여</label> <input type="radio" id="rdo-female" name="gender" value="female">

						</div>

						<!-- 약관동의 -->
						<div class="form-group">
							<span class="form-text">약관동의</span> <input type="checkbox" id="chk-agree" value="" name=""> <label for="chk-agree">서비스 약관에 동의합니다.</label>
						</div>


						<div class="button-area">
							<button type="submit" id="btn-submit">회원가입</button>
						</div>

					</form>
				</div>
				<!-- //joinForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

<script type="text/javascript">
	$("#btnCheck").on("click", function() {

		var uid = $("#input-uid").val();
		console.log(uid);

		console.log(password);
		console.log("${pageContext.request.contextPath }/user/idcheck");
		//ajax 데이터만 받을래
		$.ajax({

			//보낼때
			url : "${pageContext.request.contextPath }/user/idcheck",
			type : "post",
			//contentType : "application/json",
			data : {
				id : uid,
				password : password
			},

			//받을때
			dataType : "text",
			success : function(result) { //보낼값이 이곳에 담긴다
				/*성공시 처리해야될 코드 작성*/

				if (result == 'can') {
					$("#msg").html("사용할 수 있는 아이디 입니다.");

				} else {
					$("#msg").html("사용할 수 없는 아이디 입니다.");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	//폼을 submit 할때 --> submit되기 전
	$("#joinForm").on("submit", function() {
		
		//패스워드체크wnsql
		var password = $("#input-pass").val();
		
		//약관동의 체크준비
		var check = $("#chk-agree").is(":checked"); //true or false
		console.log(check);
		
		//아이디 작성 여부 준비
		var uid = $("#input-uid").val();
		
		//이름 작성여부준비
		var name = $("#input-name").val();
		
		//성별체크 여부 준비
		var gender = $("[name='gender']:checked").val();
		
		//아이디 작성여부
		if(uid == ""){
			alert("id를 입력해주세요");
			return false;
		}
			//패스워드 체크 8글자 이상 통과
		if(password.length<8){
			//패스워드 체크 나머지 alert(패스워드는 8글자이상입니다)
			alert("패스워드는 8글자 이상입니다.");
			return false;			
		}
		
		//이름 작성여부
		if(name == ""){
			$("#noName").text("이름을 기입해주세요");
			return false;
		}else{
			$("#noName").text("");
		}
		
		//성별 작성여부
		if(gender == null){
			alert("성별을 체크해주세요");
			return false;
		}
		if (!check) {
			//동의하기 checkbox가 체크되어 있으면 --> submit
			alert("약관에 동의해주세요");
		
			return false;
		}
		
		return true;
	});
</script>

</html>