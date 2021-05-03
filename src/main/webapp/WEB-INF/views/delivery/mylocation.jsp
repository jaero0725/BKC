<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">

<!--font-->
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap" rel="stylesheet">

<!--fabicon-->
<link rel="shortcut icon" type="image/x-icon"
	href="https://bkcbuc.s3.ap-northeast-2.amazonaws.com/bkc_img/bkclogo/favicon.png" />
<!-- location.css -->
<link rel="stylesheet" href="${contextPath}/resources/css/include/delivery-gnb2.css">
<link rel="stylesheet" href="../resources/css/delivery/mylocation.css?v=<%=System.currentTimeMillis()%>" />
<!-- jquery -->
<script src="http://code.jquery.com/jquery-3.6.0.js"></script>
<!-- JS -->
<script src="../resources/js/delivery/mylocation.js"></script>
<title>배달지설정</title>
</head>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
</script>
<body>
	<input type='hidden' name="userid" id="userid" value='<c:out value="${user.getUserid()}"/>'>

	<div class="subWrap02">
		<!-- 딜리버리 페이지 헤더 이클립스에선 따로 빼놓을 것 -->
		<div class="headerWrap">
			<div class="head_menuWrap">
				<div class="web_container">
					<h1 class="WEB logo">
						<span>딜리버리</span>
					</h1>
					<div class="WEB utilWrap">
						<a href="${contextPath}/"> <span>브랜드홈</span>
						</a> <a href="${contextPath}/login"> <span>로그아웃</span>
						</a> <a href="${contextPath}/delivery/mybkc.do"> <span>MYBKC</span>
						</a> <a href="${contextPath}/notice.do"> <span>고객센터</span>
						</a>
					</div>
					<div class="WEB on_cont user">
						<p>
							<span>${user.name }</span> 님 안녕하세요
						</p>
						<a href="${contextPath}/delivery/mybkc.do"> <strong>MYBKC
								바로가기</strong>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="WEB on_cont head_personalWrap">
			<div class="web_container">
				<div class="personal_logon">
					<a href="#">
						<div class="personal_order">
							<dl>
								<dt>
									<strong>딜리버리 주문내역</strong>
								</dt>
								<dd>주문내역이 없습니다.(DB)</dd>
							</dl>
						</div>
					</a> <a href="${contextPath}/delivery/cart.do">
						<div class="personal_cart">
							<dl>
								<dt>
									<strong>카트</strong> <em class="count" style="display: none;">
										<span>0</span>
									</em>
								</dt>
								<dd>카트에 담은 상품이 없습니다.(DB)</dd>
							</dl>
						</div>
					</a>
				</div>
			</div>
		</div>
		<!-- contents 부분 -->
		<div class="contentsWrap">
			<div class="locationWrap">
				<div class="web_container">
					<div class="page_navi">
						<a href="#"> <span>딜리버리</span>
						</a> <a href="#" class="gotomenu"> <span>배달지검색</span>
						</a>
					</div>
					<div class="location">
						
						<span class="addr"> 
							<span>${location.addr} ${location.addr_detail}</span>
						</span> 
						<span class="shop"> 
							<span>DB</span>
						</span> 
						<span class="btn"> 
							<a href="" class="addrchange"> <span>변경</span></a>
						</span>
					</div>
				</div>
				>
			</div>
			<div class="contentsBox02 bg_w">
				<div class="web_container02">
					<div class="subtitWrap">
						<h2 class="page_tit">배달지 검색</h2>
					</div>
					<div class="searchWrap02">
						<div class="r_srch01">
							<div class="inpbox">
								<input class="findaddr" type="text" maxlength="50"
									placeholder="'주소찾기'를 선택해주세요.">
							</div>
							<button type="button" class="btn_search01 btn01_m"
								onclick="javascript: sample6_execDaumPostcode()"><span>주소 찾기</span>
							</button>
						</div>
					</div>
					<form role="form" action="${pageContext.request.contextPath }/delivery/insertOneDB.do" method="post" enctype="multipart/form-data">
					<input  type='hidden' name ="userid" id ="userid" value='<c:out value="${user.getUserid()}"/>'>
					<div class="inputWrap">
						<div class="inpbox2" >
							<input class="addr" id="sample6_address" name="addr" type="text" placehoder="'주소'">
						</div>
						<div class="inpbox2" >
							<input class="detailaddr" id="sample6_extraAddress" name="addr_detail" type="text" placehoder="'상세 주소'">
						</div>
						<div class="inpbox2">
							<input class="detailaddr" id="sample6_detailAddress" name="addr_detail" type="text"  placehoder="'상세 주소'">
						</div>					
						<div class="inpbox2">
							<input class="detailaddr" id="sample6_postcode" name="zipcode" type="text" placehoder="'우편 번호'">
						</div>
						<button type="submit" class="btn_search01 btnput">이 주소로 배달지 설정</button>
					</div>
					</form>
				</div>
			</div>
			<div class="contentsBox02 bg_b">
				<div class="web_container02">
					<div class="tabfixWrap">
						<div class="tab01">
							<ul class="item2">
								<li class="on">
									<button type="button">
										<span>최근 배달지</span>
									</button>
								</li>
								<li>
									<button type="button">MY배달지</button>
								</li>
							</ul>
						</div>
						<div class="tab_cont">
							<h3 class="hide">최근 배달지</h3>
							<ul class="delivery_list">
								<li><c:forEach var="newlist" items="${newlist}">
										<li>
											<div class="cont">
												<div class="addr">
													<em class="type"> <span> </span><br></em> 
													<p>${newlist.addr} ${newlist.addr_detail}</p>
												</div>
												<button type="button" class="btn_del02" onclick="location.href='${pageContext.request.contextPath}/delivery/deleteLocaDB.do?addr_seq=${newlist.addr_seq}'">삭제</button>
											</div>
											
										</li>
									</c:forEach> <a href="#" type="button" class="btn_detail"> <span>배달지로
											설정</span>
								</a></li>
							</ul>
							<div class="container01"></div>
						</div>
						<div class="tab_cont" style="display: none;">
							<h3 class="hide">MY배달지</h3>
							<ul class="delivery_list">
								<li><c:forEach var="localist" items="${localist}">
										<li>
											<div class="cont">
												<div class="addr">
													<em class="type"> <span>${localist.addr_name}</span><br>
													</em> 
													<span>${localist.addr}</span> <span>${localist.addr_detail}</span>
												</div>
											</div>
											<button type="button" class="btn_del02" onclick="location.href='${pageContext.request.contextPath}/delivery/deleteLocaDB.do?addr_seq=${localist.addr_seq}'">삭제</button>
										</li>
									</c:forEach> 
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<a href="#app" class="btn_top" style="opacity: 1; display: inline;">Top</a>
</body>

</html>