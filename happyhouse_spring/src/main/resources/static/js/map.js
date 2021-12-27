var root = "";

// 마커를 담을 배열입니다
var markers = [];

//var mapContainer = document.getElementById("map"); // 지도를 표시할 div	
//var mapOption = {
//	    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
//	    level: 3, // 지도의 확대 레벨
//	};

var overlayOn = false, // 지도 위에 로드뷰 오버레이가 추가된 상태를 가지고 있을 변수
container = document.getElementById('container'), // 지도와 로드뷰를 감싸고 있는 div 입니다
mapWrapper = document.getElementById('mapWrapper'), // 지도를 감싸고 있는 div 입니다
mapContainer = document.getElementById('map'), // 지도를 표시할 div 입니다 
rvContainer = document.getElementById('roadview'); //로드뷰를 표시할 div 입니다
var mapCenter = new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
mapOption = {
    center: mapCenter, // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
};
// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
// 커스텀 오버레이를 생성합니다
var customOverlay;
//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();
var clickedOverlay = null;
var bounds = new kakao.maps.LatLngBounds();

var rv = new kakao.maps.Roadview(rvContainer); 

//좌표로부터 로드뷰 파노라마 ID를 가져올 로드뷰 클라이언트 객체를 생성합니다 
var rvClient = new kakao.maps.RoadviewClient(); 

var mapTypeControl = new kakao.maps.MapTypeControl();

//지도에 컨트롤을 추가해야 지도위에 표시됩니다
//kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

//지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);


//검색 결과 목록과 마커를 표출하는 함수입니다
function displayMarkers(places) {
	
	var listEl = document.getElementById('placesList'), 
    menuEl = document.getElementById('menu_wrap'),
	fragment = document.createDocumentFragment(),
    bounds = new kakao.maps.LatLngBounds();
    var listStr = "";
    
    removeAllChildNods(listEl);
    
	// 지도에 표시되고 있는 마커를 제거합니다
	removeMarker();
	places.forEach(function(place, index) {
		geocoder.addressSearch(place.address, function(result, status) {
		    // 정상적으로 검색이 완료됐으면 
		     if (status === kakao.maps.services.Status.OK) {
		        var placePosition = new kakao.maps.LatLng(result[0].y, result[0].x);
		        var marker = addMarker(placePosition, index);
		        var itemEl = getListItem(index, place); // 검색 결과 항목 Element를 생성합니다
		        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		        // LatLngBounds 객체에 좌표를 추가합니다
		        
		        bounds.extend(placePosition);		       
		        
		        // 마커와 검색결과 항목에 mouseover 했을때
		        // 해당 장소에 인포윈도우에 장소명을 표시합니다
		        // mouseout 했을 때는 인포윈도우를 닫습니다
		        (function (marker, title, code, place) {
		        	kakao.maps.event.addListener(marker, "click", function () {
		        		displayInfowindow(marker, title, place);
		        	    if (clickedOverlay) {
		        	        clickedOverlay.setMap(null);
		        	    }
		        	    customOverlay.setMap(map);
		        	    clickedOverlay = customOverlay;
		        		console.log(title + " " + code);
		        	});
//		        	kakao.maps.event.addListener(marker, "click", function () {
//		        		console.log(customOverlay);
//		        		
//		        		customOverlay.setMap(null);
//		        	});
//		        	
//		        	kakao.maps.event.addListener(marker, "click", function () {
//		        		displayInfowindow(marker, title, place);
//		        		console.log(title + " " + code);
//		        	});
//		        	
//		        	kakao.maps.event.addListener(map, "click", function () {
//		        		console.log(customOverlay);
//		        		customOverlay.setMap(null);
//		        	});
//		        	
		        	itemEl.onmouseenter = function () {
		        		displayInfowindow(marker, title, place);
		        	};	
		        	
		        	itemEl.onmouseleave = function () {
		        		customOverlay.setMap(null);
		        	};
		        })(marker, place.aptName, place.aptCode, place);
		        
		    	setTimeout(function() {
		    		fragment.appendChild(itemEl);
		   		}, 50);
		    } 
		});    
	});
	
	setTimeout(function() {
		listEl.appendChild(fragment);
		}, 500);
    menuEl.scrollTop = 0;
	// 마커를 생성하고 지도에 표시합니다 
	// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
//    panTo();
    setBounds(bounds);
}

//마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
	var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
	imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
	imgOptions = {
		spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
		spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
		offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
	},
	markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
	marker = new kakao.maps.Marker({
		position: position, // 마커의 위치
		image: markerImage,
	});

	marker.setMap(map); // 지도 위에 마커를 표출합니다
	markers.push(marker); // 배열에 생성된 마커를 추가합니다

	return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];
}

//검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, place) {
	var el = document.createElement("li");
	var itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
		'<div class="info">' +
		'<input type="hidden" value="' + place.dongCode + '">' +
		'    <h5>' + place.aptName + '</h5>' +
		'<span>' + place.sidoName + ' ' + place.gugunName + ' ' + place.dongName + ' ' + place.jibun + '</span>' +
		'</div>';
	el.innerHTML = itemStr;
	el.className = "item";

	return el;
}

//검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
//인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title, place) {
	var content = `
	<div class="wrap">
		<div class="info">'
		<div class="overlaybox">
			<div class="boxtitle">${title}
			<img src="img/happyhouse/close.png" class="close" onclick="closeOverlay()" title="닫기"></img>
			</div>
			<div class="first"><img src="${root}/img/apt.png" style="width:247px; height:136px;" alt=""></div>
			<ul style="padding-left:0px;">	
				<li class="up">
					<span class="title">건축년도</span>
					<span class="count">${place.buildYear}</span>
				</li>
				<li>
					<span class="title">주소</span>
					<span class="count">${place.sidoName} ${place.gugunName} ${place.dongName} ${place.jibun}</span>
				</li>
				<li>
					<span class="title">최신거래금액</span>
					<span class="count">${place.recentPrice}</span>
				</li>
				<li>
					<span class="last" id="recenthistor" data-toggle="modal" data-target="#myModal">아파트정보 update</span>
				</li>
			</ul>
		</div>
		</div>
	</div>
	`;
	var position = new kakao.maps.LatLng(marker.getPosition().getLat()+0.00033, marker.getPosition().getLng()-0.00003);
	customOverlay = new kakao.maps.CustomOverlay({
		position: position,
		content: content,
		xAnchor: 0.40,
		yAnchor: 1.0,
	});
	customOverlay.setMap(map);
}

function closeOverlay() {
    customOverlay.setMap(null);     
}	

function panTo() {
    // 이동할 위도 경도 위치를 생성합니다 
    var moveLatLon = new kakao.maps.LatLng(33.450580, 126.574942);
    
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);            
}     

function setBounds(result) {
    // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
    // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다 
	setTimeout(function() {
		 map.setBounds(result);
		}, 1000);
}

//검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
	while (el.hasChildNodes()) {
		el.removeChild(el.lastChild);
	}
}

function relayout() {    
    
    // 지도를 표시하는 div 크기를 변경한 이후 지도가 정상적으로 표출되지 않을 수도 있습니다
    // 크기를 변경한 이후에는 반드시  map.relayout 함수를 호출해야 합니다 
    // window의 resize 이벤트에 의한 크기변경은 map.relayout 함수가 자동으로 호출됩니다
	map.relayout();
}



// 로드뷰!!!

kakao.maps.event.addListener(rv, 'position_changed', function() {

    // 현재 로드뷰의 위치 좌표를 얻어옵니다 
    var rvPosition = rv.getPosition();

    // 지도의 중심을 현재 로드뷰의 위치로 설정합니다
    map.setCenter(rvPosition);

    // 지도 위에 로드뷰 도로 오버레이가 추가된 상태이면
    if(overlayOn) {
        // 마커의 위치를 현재 로드뷰의 위치로 설정합니다
        marker.setPosition(rvPosition);
    }
});

// 마커 이미지를 생성합니다
var markImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png',
    new kakao.maps.Size(26, 46),
    {
        // 스프라이트 이미지를 사용합니다.
        // 스프라이트 이미지 전체의 크기를 지정하고
        spriteSize: new kakao.maps.Size(1666, 168),
        // 사용하고 싶은 영역의 좌상단 좌표를 입력합니다.
        // background-position으로 지정하는 값이며 부호는 반대입니다.
        spriteOrigin: new kakao.maps.Point(705, 114),
        offset: new kakao.maps.Point(13, 46)
    }
);

// 드래그가 가능한 마커를 생성합니다
var marker = new kakao.maps.Marker({
    image : markImage,
    position: mapCenter,
    draggable: true
});

// 마커에 dragend 이벤트를 등록합니다
kakao.maps.event.addListener(marker, 'dragend', function(mouseEvent) {

    // 현재 마커가 놓인 자리의 좌표입니다 
    var position = marker.getPosition();

    // 마커가 놓인 위치를 기준으로 로드뷰를 설정합니다
    toggleRoadview(position);
});

//지도에 클릭 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent){
    
    // 지도 위에 로드뷰 도로 오버레이가 추가된 상태가 아니면 클릭이벤트를 무시합니다 
    if(!overlayOn) {
        return;
    }

    // 클릭한 위치의 좌표입니다 
    var position = mouseEvent.latLng;

    // 마커를 클릭한 위치로 옮깁니다
    marker.setPosition(position);

    // 클락한 위치를 기준으로 로드뷰를 설정합니다
    toggleRoadview(position);
});

// 전달받은 좌표(position)에 가까운 로드뷰의 파노라마 ID를 추출하여
// 로드뷰를 설정하는 함수입니다
function toggleRoadview(position){
    rvClient.getNearestPanoId(position, 50, function(panoId) {
        // 파노라마 ID가 null 이면 로드뷰를 숨깁니다
        if (panoId === null) {
            toggleMapWrapper(true, position);
        } else {
         toggleMapWrapper(false, position);

            // panoId로 로드뷰를 설정합니다
            rv.setPanoId(panoId, position);
        }
    });
}

// 지도를 감싸고 있는 div의 크기를 조정하는 함수입니다
function toggleMapWrapper(active, position) {
    if (active) {

        // 지도를 감싸고 있는 div의 너비가 100%가 되도록 class를 변경합니다 
        container.className = '';

        // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
        map.relayout();

        // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
        map.setCenter(position);
    } else {

        // 지도만 보여지고 있는 상태이면 지도의 너비가 50%가 되도록 class를 변경하여
        // 로드뷰가 함께 표시되게 합니다
        if (container.className.indexOf('view_roadview') === -1) {
            container.className = 'view_roadview';

            // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
            map.relayout();

            // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
            map.setCenter(position);
        }
    }
}

// 지도 위의 로드뷰 도로 오버레이를 추가,제거하는 함수입니다
function toggleOverlay(active) {
    if (active) {
        overlayOn = true;

        // 지도 위에 로드뷰 도로 오버레이를 추가합니다
        map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

        // 지도 위에 마커를 표시합니다
        marker.setMap(map);

        // 마커의 위치를 지도 중심으로 설정합니다 
        marker.setPosition(map.getCenter());

        // 로드뷰의 위치를 지도 중심으로 설정합니다
        toggleRoadview(map.getCenter());
    } else {
        overlayOn = false;

        // 지도 위의 로드뷰 도로 오버레이를 제거합니다
        map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

        // 지도 위의 마커를 제거합니다
        marker.setMap(null);
    }
}

// 지도 위의 로드뷰 버튼을 눌렀을 때 호출되는 함수입니다
function setRoadviewRoad() {
    var control = document.getElementById('roadviewControl');

    // 버튼이 눌린 상태가 아니면
    if (control.className.indexOf('active') === -1) {
        control.className = 'active';

        // 로드뷰 도로 오버레이가 보이게 합니다
        toggleOverlay(true);
    } else {
        control.className = '';

        // 로드뷰 도로 오버레이를 제거합니다
        toggleOverlay(false);
    }
}

// 로드뷰에서 X버튼을 눌렀을 때 로드뷰를 지도 뒤로 숨기는 함수입니다
function closeRoadview() {
    var position = marker.getPosition();
    toggleMapWrapper(true, position);
}

// 아파트 검색 메뉴 부분!!!

//검색결과 목록 하단에 페이지번호를 표시하는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('store_pagination'),
        fragment = document.createDocumentFragment(),
        i; 

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild (paginationEl.lastChild);
    }

    for (i=1; i<=pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i===pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function(i) {
                return function() {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 상권 부분!!!

//검색 결과 목록과 마커를 표출하는 함수입니다
function displayStoreMarkers(places) {
	
	var listEl = document.getElementById('store_placesList'), 
    menuEl = document.getElementById('store_wrap'),
	fragment = document.createDocumentFragment(),
    bounds = new kakao.maps.LatLngBounds();
    var listStr = "";
    
    removeAllChildNods(listEl);
    
	// 지도에 표시되고 있는 마커를 제거합니다
	removeMarker();
	places.forEach(function(place, index) {
		        var placePosition = new kakao.maps.LatLng(place.lat, place.lng);
		        var marker = addStoreMarker(placePosition, index);
		        var itemEl = getListStoreItem(index, place); // 검색 결과 항목 Element를 생성합니다
		        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		        // LatLngBounds 객체에 좌표를 추가합니다
		        
		        bounds.extend(placePosition);		       
		        
		        // 마커와 검색결과 항목에 mouseover 했을때
		        // 해당 장소에 인포윈도우에 장소명을 표시합니다
		        // mouseout 했을 때는 인포윈도우를 닫습니다
		        (function (marker, title, code, place) {
		        	kakao.maps.event.addListener(marker, "click", function () {
		        		displayStoreInfowindow(marker, title, place);
		        	    if (clickedOverlay) {
		        	        clickedOverlay.setMap(null);
		        	    }
		        	    customOverlay.setMap(map);
		        	    clickedOverlay = customOverlay;
		        		console.log(title + " " + code);
		        	});
//		        	kakao.maps.event.addListener(marker, "click", function () {
//		        		console.log(customOverlay);
//		        		
//		        		customOverlay.setMap(null);
//		        	});
//		        	
//		        	kakao.maps.event.addListener(marker, "click", function () {
//		        		displayInfowindow(marker, title, place);
//		        		console.log(title + " " + code);
//		        	});
//		        	
//		        	kakao.maps.event.addListener(map, "click", function () {
//		        		console.log(customOverlay);
//		        		customOverlay.setMap(null);
//		        	});
//		        	
		        	itemEl.onmouseenter = function () {
		        		displayStoreInfowindow(marker, title, place);
		        	};	
		        	
		        	itemEl.onmouseleave = function () {
		        		customOverlay.setMap(null);
		        	};
		        })(marker, place.name, place.no, place);
		        
		    	setTimeout(function() {
		    		fragment.appendChild(itemEl);
		   		}, 50);  
	});
	
	setTimeout(function() {
		listEl.appendChild(fragment);
		}, 500);
    menuEl.scrollTop = 0;
	// 마커를 생성하고 지도에 표시합니다 
	// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
//    panTo();
    setBounds(bounds);
    
    displayPagination(pagination)
}

function getListStoreItem(index, place) {
	var el = document.createElement("li");
	var itemStr = '<div class="info">' +
		'    <h5>' + '<img src="img/happyhouse/store.png">' + '  '+ place.name + '</h5>' +
		'<span>' + place.sidoName + ' ' + place.gugunName + ' ' + place.dongName + '</span>' +
		'</div>';
	el.innerHTML = itemStr;
	el.className = "store";

	return el;
}

function displayStoreInfowindow(marker, title, place) {
	var content = `
	<div class="wrap">
		<div class="info">'
		<div class="overlaybox">
			<div class="boxtitle">${title}
			<img src="img/happyhouse/close.png" class="close" onclick="closeOverlay()" title="닫기"></img>
			</div>
			<div class="first"><img src="${root}/img/storepic.png" style="width:247px; height:136px;" alt=""></div>
			<ul style="padding-left:0px;">	
				<li class="up">
					<span class="title">업종</span>
					<span class="count">${place.type}</span>
				</li>
				<li>
					<span class="title">주소</span>
					<span class="count">${place.sidoName} ${place.gugunName} ${place.dongName} </span>
				</li>
				<li>
					<span class="last" id="recenthistor" data-toggle="modal" data-target="#myModal">상권정보 update</span>
				</li>
			</ul>
		</div>
		</div>
	</div>
	`;
	var position = new kakao.maps.LatLng(marker.getPosition().getLat()+0.00033, marker.getPosition().getLng()-0.00003);
	customOverlay = new kakao.maps.CustomOverlay({
		position: position,
		content: content,
		xAnchor: 0.40,
		yAnchor: 0.85,
	});
	customOverlay.setMap(map);
}

//마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addStoreMarker(position, idx, title) {
	var imageSrc = "img/happyhouse/store.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
	imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
	imgOptions = {
		spriteSize: new kakao.maps.Size(36, 37), // 스프라이트 이미지의 크기
		spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
		offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
	},
	markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
	marker = new kakao.maps.Marker({
		position: position, // 마커의 위치
	});

	marker.setMap(map); // 지도 위에 마커를 표출합니다
	markers.push(marker); // 배열에 생성된 마커를 추가합니다

	return marker;
}

// 환경 점검 !!!
function displayEnvMarkers(places) {
	
	var listEl = document.getElementById('env_placesList'), 
    menuEl = document.getElementById('env_wrap'),
	fragment = document.createDocumentFragment(),
    bounds = new kakao.maps.LatLngBounds();
    var listStr = "";
    
    removeAllChildNods(listEl);
    
	// 지도에 표시되고 있는 마커를 제거합니다
	removeMarker();
	places.forEach(function(place, index) {
		geocoder.addressSearch(place.roadAddress, function(result, status) {
		    // 정상적으로 검색이 완료됐으면 
		     if (status === kakao.maps.services.Status.OK) {
		        var placePosition = new kakao.maps.LatLng(result[0].y, result[0].x);
		        var marker = addMarker(placePosition, index);
		        var itemEl = getListEnvItem(index, place); // 검색 결과 항목 Element를 생성합니다
		        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		        // LatLngBounds 객체에 좌표를 추가합니다
		        
		        bounds.extend(placePosition);		       
		        
		        // 마커와 검색결과 항목에 mouseover 했을때
		        // 해당 장소에 인포윈도우에 장소명을 표시합니다
		        // mouseout 했을 때는 인포윈도우를 닫습니다
		        (function (marker, title, code, place) {
		        	kakao.maps.event.addListener(marker, "click", function () {
		        		displayEnvInfowindow(marker, title, place);
		        	    if (clickedOverlay) {
		        	        clickedOverlay.setMap(null);
		        	    }
		        	    customOverlay.setMap(map);
		        	    clickedOverlay = customOverlay;
		        		console.log(title + " " + code);
		        	});
//		        	kakao.maps.event.addListener(marker, "click", function () {
//		        		console.log(customOverlay);
//		        		
//		        		customOverlay.setMap(null);
//		        	});
//		        	
//		        	kakao.maps.event.addListener(marker, "click", function () {
//		        		displayInfowindow(marker, title, place);
//		        		console.log(title + " " + code);
//		        	});
//		        	
//		        	kakao.maps.event.addListener(map, "click", function () {
//		        		console.log(customOverlay);
//		        		customOverlay.setMap(null);
//		        	});
//		        	
		        	itemEl.onmouseenter = function () {
		        		displayEnvInfowindow(marker, title, place);
		        	};	
		        	
		        	itemEl.onmouseleave = function () {
		        		customOverlay.setMap(null);
		        	};
		        })(marker, place.facName, place.jobName, place);
		        
		    	setTimeout(function() {
		    		fragment.appendChild(itemEl);
		   		}, 50);
		    } else {
		    	console.log("쩝...");
		    }
		});    
	});
	
	setTimeout(function() {
		listEl.appendChild(fragment);
		}, 500);
    menuEl.scrollTop = 0;
	// 마커를 생성하고 지도에 표시합니다 
	// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
//    panTo();
    setBounds(bounds);
}



//검색결과 항목을 Element로 반환하는 함수입니다
function getListEnvItem(index, place) {
	var el = document.createElement("li");
	var itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
		'<div class="info">' +
		'    <h5>' + place.facName + '</h5>' +
		'<span>' + place.roadAddress + '</span>' +
		'</div>';
	el.innerHTML = itemStr;
	el.className = "item";

	return el;
}

//검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
//인포윈도우에 장소명을 표시합니다
function displayEnvInfowindow(marker, title, place) {
	var content = `
	<div class="wrap">
		<div class="info">'
		<div class="overlaybox">
			<div class="boxtitle">${title}
			<img src="img/happyhouse/close.png" class="close" onclick="closeOverlay()" title="닫기"></img>
			</div>
			<div class="first"><img src="${root}/img/factory.png" style="width:247px; height:136px;" alt=""></div>
			<ul style="padding-left:0px;">	
				<li class="up">
					<span class="title">관리 대상</span>
					<span class="count">${place.jobName}</span>
				</li>
				<li>
					<span class="title">주소</span>
					<span class="count">${place.roadAddress}</span>
				</li>
				<li>
					<span class="title">점검사항</span>
					<span class="count">${place.checkDesc}</span>
				</li>
				<li>
					<span class="title">점검날짜</span>
					<span class="count">${place.mapCheckDate}</span>
				</li>
			</ul>
		</div>
		</div>
	</div>
	`;
	var position = new kakao.maps.LatLng(marker.getPosition().getLat()+0.00033, marker.getPosition().getLng()-0.00003);
	customOverlay = new kakao.maps.CustomOverlay({
		position: position,
		content: content,
		xAnchor: 0.40,
		yAnchor: 1.0,
	});
	customOverlay.setMap(map);
}
