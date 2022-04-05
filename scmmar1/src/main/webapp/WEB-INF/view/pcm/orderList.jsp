<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${totalCnt eq 0}"> 
	<tr>
		<td colspan="6">데이터가 존재하지 않습니다.</td>
	</tr>
</c:if>


<c:if test="${totalCnt > 0 }">
	<c:set var="nRow" value="${pageSize*(currentPage-1)}" />
	<c:forEach items="${listPurchaseOrderModel}" var="list">
		<tr>
			<td>${list.orderid}</td>
			<td>${list.comp_nm}</td>
			<td>${list.sales_nm}</td>
			<td>${list.regdate}</td>
			<td>${list.confirmYN}</td>
			<!--<td>${list.depositYN}</td>-->
			
			<td>
			<a class="btnType3 color1" href="javascript:fPcmOrderOne('${list.orderid}');"><span>확인</span></a>
			</td>
			
				
		</tr>
		<c:set var="nRow" value="${nRow + 1}" />
	</c:forEach>
	</c:if>
	
	<!--totalCnt 네비 부분 여기 문제
	  -->
	<input type="hidden" id="totalCnt" name="totalCnt" value="${totalCnt}" />