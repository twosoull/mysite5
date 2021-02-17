<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->
		<!-- //nav -->

		<c:import url="/WEB-INF/views/include/galleryAside.jsp"></c:import>
		<!-- //aside -->


		<div id="content">

			<div id="content-head">
				<h3>갤러리</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>갤러리</li>
						<li class="last">갤러리</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->


			<div id="gallery">
				<div id="list">

					<c:if test="${!empty authUser }">
						<button id="btnImgUpload">이미지올리기</button>
						<div class="clear"></div>
					</c:if>


					<ul id="viewArea">

						<!-- 이미지반복영역 -->
						<c:forEach items="${gallaryList}" var="vo">
							<li id="li-${vo.no}" data-no="${vo.no}">
								<div class="view">
									<img id="image" class="imgItem" src="${pageContext.request.contextPath}/upload/${vo.saveName}">
									<div class="imgWriter">
										작성자: <strong>${vo.name}</strong> 
									</div>
								</div>
							</li>
						</c:forEach>
						<!-- 이미지반복영역 -->


					</ul>
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->



	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
					
					</h4>
				</div>

				<form method="post" action="${pageContext.request.contextPath }/gallary/add" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label class="form-text">글작성</label> <input id="addModalContent" type="text" name="content" value="">
						</div>
						<div class="form-group">
							<label class="form-text">이미지선택</label> <input id="file" type="file" name="file" value="">
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn" id="btnUpload">등록</button>
					</div>
					<input type="text" name="user_no" value="${authUser.no}">
				</form>


			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
					이미지보기
					</h4>
				</div>
				<div class="modal-body">

					<div id = "formImage"class="formgroup">
						
						<!-- ajax로 처리 : 이미지출력 위치-->
					</div>

					<div class="formgroup">
						<p id="viewModelContent"></p>
					</div>
					
				</div>
				<div id="nohidden">
				
				</div>
				<form method="" action="">
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>			
						<button type="button" class="btn btn-danger" id="btnDel">삭제</button>		
					</div>


				</form>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


</body>

<script type="text/javascript">
	

	//업로드 모달창
	$("#btnImgUpload").on("click", function() {
		$("#addModal").modal();

	});
	
	//이미지 보기 모달창
	$("#viewArea").on("click","li",function(){
		console.log("li클릭");
		//리스트 속성확인
		console.log($(this));
		var no = $(this).data("no")
		
		$.ajax({

			url : "${pageContext.request.contextPath}/api/gallary/modalGallary2", //컨트롤러의 url과 파라미터
			type : "post", // 겟 포스트
			//contentType : "application/json",
			data : {no : no},

			dataType : "json",
			success : function(gallaryVo) { //성공시
				console.log(gallaryVo);
				
				$("#btnDel").val(no);
				imgVal(gallaryVo);
				
				$("#viewModelContent").text(gallaryVo.content);
				
				
			},
			error : function(XHR, status, error) { //실패
				console.error(status + " : " + error);
			}
		});
		
		
		$("#viewModal").modal();
	});
	
	//삭제버튼 클릭
	$("#btnDel").on("click",function(){
		console.log("버튼 클릭");
		var no = $("#btnDel").val();
		
		$.ajax({

			url : "${pageContext.request.contextPath}/api/gallary/remove", //컨트롤러의 url과 파라미터
			type : "post", // 겟 포스트
			//contentType : "application/json",
			data : {no : no},

			dataType : "json",
			success : function(count) { //성공시
				console.log(count);
				
				$("#li-"+no).remove();
				
				
				$("#viewModal").modal("hide");
			},
			error : function(XHR, status, error) { //실패
				console.error(status + " : " + error);
			}
		});
	});
	
	/*
	//사진 모달창
	$("#viewArea").on("click","img",function(){
		console.log("이미지 클릭");
		
		console.log(no);
		var path = $(this).attr("src")
		//data-no가 계속 안먹어서 옳은 선택은 아닌것 같지만 path값으로 시도해보았다
		//하지만 삭제 버튼 부분에서 로그인유저와 비교할 if문 작성이 불가능 하다고 판단되는데..
		//어디선가 true false를 받아낸다면 좋으거같은데..흠..머리가 정지된것같다
		
		//마지막 삭제로직에서 ajax로 리스트에서 삭제가 되려면 반복문의 전체를 잡아야 된다고 판단됐다
		//li를 자식태그로 잡아보는 로직 
		
		$("#viewModal").modal();
		
		
		
		$.ajax({

			url : "${pageContext.request.contextPath}/api/gallary/modalGallary", //컨트롤러의 url과 파라미터
			type : "post", // 겟 포스트
			//contentType : "application/json",
			data : {path : path},

			dataType : "json",
			success : function(gallaryVo) { //성공시
				console.log(gallaryVo);
			
				imgVal(gallaryVo);
				
				$("#viewModelContent").text(gallaryVo.content);
				
				$("#btnDel").val(gallaryVo.no);
				
			},
			error : function(XHR, status, error) { //실패
				console.error(status + " : " + error);
			}
		});
		
	});
	*/
	
	
	function imgVal(gallaryVo){
		
		str='';
		str += '<img id="viewModelImg" src=${pageContext.request.contextPath}/upload/'+gallaryVo.saveName+'>'
		
		$("#formImage").html(str);
	}
	
	
	
	
</script>




</html>

