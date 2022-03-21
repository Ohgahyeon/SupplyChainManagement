<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src='${CTX_PATH}/js/sweetalert/sweetalert.min.js'></script>
<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>
    
<style>
*:disabled {
	background: #FFD9EC;
}
}
</style>

<script type="text/javascript">

// 페이징 설정
var pageSize = 5;
var pageBlockSize = 5;

var nowdate = new Date();

console.log("세션 아이디: " +'${loginId}');
console.log("세션 유저타입: " +'${userType}');
console.log("세션 유저이름: " +'${userNm}');
console.log("세션 시간: " + nowdate);

var s_loginID ='${loginId}';
var s_userType = '${userType}';
var s_userNm = '${userNm}'

/** OnLoad event */ 
$(function() {
	console.log("location: " + location.href);
	
	$("#search_startdate").datepicker();
	$("#search_enddate").datepicker();
	$("#start_enddate").datepicker();
	
	surveyList();
	survey_insert();
});

/* 모달창 취소 버튼 */
function fcancleModal() {
	gfCloseModal();
	
	surveyList();
}

/* 프로젝트 목록 조회 */
function surveyList(currentPage) {
		
	currentPage = currentPage || 1;

	var sname = $("#sname").val();
	var oname = $("#searchkey").val();
	
	var param = {

		s_loginID : s_loginID,
		currentPage : currentPage,
		pageSize : pageSize,
		sname : sname,
		oname : oname
	};

	var resultCallback = function(data) {
		listAppend(data, currentPage);
	};

	callAjax("/mng/surveyList.do", "post", "text", true, param,
			resultCallback);
}

/* 리스트 붙이기 */
function listAppend(data, currentPage) {
	
	$('#surveyList').empty();
	$("#surveyList").append(data);

	var listCount = $("#listCount").val();

	// 페이지 네비게이션 생성
	var paginationHtml = getPaginationHtml(currentPage, listCount, pageSize, pageBlockSize, "surveyList");
	$("#pagingnavi").empty().append( paginationHtml );
}

/* 모달창 데이터 주입 */
function surveyFormInit(data){	
	
	$("#appraisal_no").val(data.surveyInfo.appraisal_no);
	$("#class_enddate").val(data.surveyInfo.class_enddate);
		
	$("#appraisal_content").val(data.surveyInfo.appraisal_content);
		
	if(data.surveyInfo.appraisal_score == 0 || data.surveyInfo.appraisal_score == ''){
		
		$('input[name="appraisal_score"]:radio[value="3"]').prop('checked', true);	
	
	} else {
	
		$('input[name="appraisal_score"]:radio[value="'+ data.surveyInfo.appraisal_score + '"]').prop('checked', true);	
	
	}
 	
	var enddate = new Date($("#class_enddate").val()); 	
 	console.log("+ enddate : " +enddate);
 	
 	var limit = new Date(enddate.setDate(enddate.getDate() + 7));
 	console.log("+ limit : " +limit);
 	
 	var now = new Date(); 	
 	console.log("+ now : " +now);

 	function getYmd11() {
 	    //yyyy-mm-dd 포맷 날짜 생성
 	    var d = limit;
 	    return d.getFullYear() + "년 " + ((d.getMonth() + 1) > 9 ? (d.getMonth() + 1).toString() : "0" + (d.getMonth() + 1)) + "월 " + (d.getDate() > 9 ? d.getDate().toString() : "0" + d.getDate().toString())+ "일";
 	}
 	
 	/* 버튼 컨트롤 */
	if(data.surveyInfo.appraisal_score = null || data.surveyInfo.appraisal_score == '' || data.surveyInfo.appraisal_score == '0' || data.surveyInfo.appraisal_score == 0 || data.surveyInfo.appraisal_score == undefined){

		$("#FBinsbtn").show();
		$("#FBreinsbtn").hide();
		$("#FBdelbtn").hide();
		
	} else {
		
		$("#FBinsbtn").hide();
		$("#FBreinsbtn").show();
		$("#FBdelbtn").hide();
	}
 	
 	swal("설문조사 기간은 " + getYmd11() + "까지 입니다.");
	 	
	if(now > limit){
		
		swal("설문조사 기간이 종료되었습니다.");

		$('#surveyModal *').attr("disabled", true);
		
		$("#FBinsbtn").hide();
		$("#FBreinsbtn").hide();
			
	} else {
		
		$('#surveyModal *').attr("disabled", false);

	}  			
}

/* 상세 모달 */
function surveyModal_popup(appraisal_no) {

	survey_do(appraisal_no);
	
	gfModalPop("#surveyModal");

}


/* 단건 조회 */
function survey_do(appraisal_no) {
		
	var param = {
		appraisal_no : appraisal_no
	}
	
	
	var survey_doCallback = function(data) {

		surveyInfo(data);
	}

	callAjax("/mng/survey_do.do", "post", "json", true, param,
			survey_doCallback);
}

/* 단건 조회 콜백 */
function surveyInfo(data) {

	console.log("surveyInfo : " + JSON.stringify(data));
	
	
	if (data.result == "SUCCESS") {
		surveyFormInit(data);
			// 조회 후 모달의 hidden 값으로 no를 넣어줌.
		$('#appraisal_no').val(data.surveyInfo.appraisal_no);
	} else {
		alert("조회 실패")
	}
}

/* 설문조사 수정 */
function surveyUpdate(){
	
	var param = $("#RegisterSurveyForm").serialize();
	
	console.log("++ param : " + param);
			
	var surveyUpdateResult = function(data) {
		surveyUpdateCallback(data);
	}
	
	callAjax("/mng/surveyUpdate.do", "post", "json", true, param,
			surveyUpdateResult);
}

function surveyUpdateCallback(data) {

	console.log("surveyUpdateCallback : " + JSON.stringify(data));
	
	
	if (data.result == "SUCCESS") {
		swal("완료")
		fcancleModal()
	} else {
		alert("수정 실패")
	}
}	


// 종료예정 강의 select
function survey_insert(){
	
	var param = {
			s_loginID : s_loginID
	}
	
	var survey_insertResult = function(data){
		
		survey_insertCallback(data);
		
	}
	
	callAjax("/mng/survey_insert.do", "post", "json", true, param,
			survey_insertResult);
	

	
}

function survey_insertCallback(data){
	
	console.log("survey_insertCallback : " + JSON.stringify(data));
	
	
	if (data.result == "SUCCESS") {
		swal("새로운 설문조사 항목이 생성되었습니다");
		surveyList();

		console.log("survey_insertCallback : " + JSON.stringify(data.survey_endclass));

	} else if(data.result == "ALREADY"){

		console.log("추가 설문조사 없음");
		
	} else {
		alert("생성 실패");
	}	
}

</script>

<!-- 모달팝업 -->
<div id="surveyModal" class="layerPosition layerPop layerType2" style="width: 800px; padding: 15px">
    <form id="RegisterSurveyForm" action="" method="post">
        <input type="hidden" name="action" id="action" value="">
        <input type="hidden" id="c_loginID" name="c_loginID" value="${sessionScope.loginId}" /> 
        <input type="hidden" id="appraisal_no" name="appraisal_no" value="" />  
        <input type="hidden" id="class_enddate" name="class_enddate" value="" />
             
   
		<br>
        <h1 style="font-size:120%">강의 평가 상세</h1>
		<br>
		
		<table style="background-color: #F3F3F3; width: 600px;" id="score_radio">
        	<tr>
        		<th style="padding-top: 1em; width:10%;">점수</th>
        		<td style="padding-top: 1em;">
        			<input type="radio" id="appraisal_score" name="appraisal_score" value="1">1점
        			<input type="radio" id="appraisal_score" name="appraisal_score" value="2">2점
        			<input type="radio" id="appraisal_score" name="appraisal_score" value="3">3점
        			<input type="radio" id="appraisal_score" name="appraisal_score" value="4">4점
        			<input type="radio" id="appraisal_score" name="appraisal_score" value="5">5점
        		</td>
        	</tr>
        </table>
        
        <table style="background-color: #F3F3F3; width: 600px;" id="tb_content">
        	<tr>
        		<th style="padding-top: 1em; width:10%;">평가내용</th>
        		<td style="padding-top: 1em;">
        			<textarea rows="20" cols="20" name="appraisal_content" id="appraisal_content"></textarea>
        		</td>
        	</tr>
        </table>
        
        <div class="btn_areaC mt30">
			<a href="javascript:surveyUpdate();" class="btnType blue" id="FBinsbtn"> <span>등록</span></a>
			<a href="javascript:surveyUpdate();" class="btnType blue" id="FBreinsbtn"> <span>재등록</span></a>
			<a href="javascript:fcancleModal()" class="btnType gray" id="btnCloseLsmCod"><span>닫기</span></a>
		</div>
    </form>
</div>