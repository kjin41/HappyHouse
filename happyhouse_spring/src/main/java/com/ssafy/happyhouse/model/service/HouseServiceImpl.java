package com.ssafy.happyhouse.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ssafy.happyhouse.model.AptInfoDto;
import com.ssafy.happyhouse.model.BikeDto;
import com.ssafy.happyhouse.model.BusDto;
import com.ssafy.happyhouse.model.ClinicDto;
import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.HouseDealInfoDto;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.JjimDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.StarbucksDto;
import com.ssafy.happyhouse.model.TrainDto;
import com.ssafy.happyhouse.model.mapper.HouseMapper;

@Service
public class HouseServiceImpl implements HouseService {
	
	@Autowired
	private SqlSession sqlSession;	

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getDongInGugun(gugun);
	}
 
	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getAptInDong(dong);
	}

	@Override
	public List<HouseDealDto> getHouseDealInApt(int aptCode) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getHouseDealInApt(aptCode);
	}

	@Override
	public List<JjimDto> listJjim(String userId) {
		return sqlSession.getMapper(HouseMapper.class).listJjim(userId);
	}
	
	@Override
	public List<JjimDto> insertJjim(Map<String, String> map) throws Exception {
		int tmpnum = sqlSession.getMapper(HouseMapper.class).insertJjim(map);
		
		if(tmpnum == 0) return null;
		else {
			return sqlSession.getMapper(HouseMapper.class).listJjim(map.get("userId"));
		}
	}

	@Transactional
	@Override
	public List<JjimDto> deleteJjim(JjimDto jjimDto) throws Exception {
		int tmpnum = sqlSession.getMapper(HouseMapper.class).deleteJjim(jjimDto);
		if(tmpnum == 0) return null;
		else {
			return sqlSession.getMapper(HouseMapper.class).listJjim(jjimDto.getUserId());
		}
	}
	
	private static String getTagValue(String tag, Element eElement) {
	      if(eElement.getElementsByTagName(tag).item(0) == null) return "";
	      NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	      Node nValue = (Node) nlList.item(0);
	      if(nValue == null) return "";
	      return nValue.getNodeValue().trim();
	   }
	   
	   private boolean aptCheck(List<AptInfoDto> jsonary, String apt) {
	      for(int i = 0 ; i < jsonary.size(); i++) {
	         if(apt.trim().equals(jsonary.get(i).get아파트().trim())) return false;
	      }
	      return true;
	   }
	   
	   public List<AptInfoDto> getHouseList(Map<String, String> map) {
	      try {
	      String url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
	      String serviceKey = "IV0jNt%2B5hKSeTK8Vvpef%2BGjvrUrdPfaL3271SHrXiPAt1InsJpYlPzfO6LJAjEibd502cOiZWb%2FBhfQ%2BdtyIKQ%3D%3D";
	      String pageNo = map.get("pageNo");
	      String numOfRows = map.get("numOfRows");
	      String LAWD_CD = map.get("LAWD_CD");
	      String DEAL_YMD = map.get("DEAL_YMD");
	      
	      Document documentInfo = DocumentBuilderFactory
	            .newInstance()
	            .newDocumentBuilder()
	            .parse(url + "?serviceKey=" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows + "&LAWD_CD=" + LAWD_CD + "&DEAL_YMD=" +DEAL_YMD);
	      documentInfo.getDocumentElement().normalize();
	      
	      NodeList nList = documentInfo.getElementsByTagName("item");
	      List<AptInfoDto> jsonArrayList = new ArrayList<AptInfoDto>();

	      for(int temp = 0 ; temp <nList.getLength(); temp++) {
	         Node nNode = nList.item(temp);
	         if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	            Element eElement = (Element) nNode;
	            if(!jsonArrayList.isEmpty() && !aptCheck( jsonArrayList, getTagValue("아파트", eElement))) continue;
	            
	            AptInfoDto jobj = new AptInfoDto();
	            jobj.set거래금액(getTagValue("거래금액", eElement));
	            jobj.set건축년도(getTagValue("건축년도", eElement));
	            jobj.set년(getTagValue("년", eElement));
	            jobj.set도로명(getTagValue("도로명", eElement));
	            jobj.set도로명건물본번호코드(getTagValue("도로명건물본번호코드", eElement));
	            jobj.set도로명건물부번호코드(getTagValue("도로명건물부번호코드", eElement));
	            jobj.set도로명시군구코드(getTagValue("도로명시군구코드", eElement));
	            jobj.set도로명일련번호코드(getTagValue("도로명일련번호코드", eElement));
	            jobj.set도로명지상지하코드(getTagValue("도로명지상지하코드", eElement));
	            jobj.set도로명코드(getTagValue("도로명코드", eElement));
	            jobj.set법정동(getTagValue("법정동", eElement));
	            jobj.set법정동본번코드(getTagValue("법정동본번코드", eElement));
	            jobj.set법정동부번코드(getTagValue("법정동부번코드", eElement));
	            jobj.set법정동시군구코드(getTagValue("법정동시군구코드", eElement));
	            jobj.set법정동읍면동코드(getTagValue("법정동읍면동코드", eElement));
	            jobj.set아파트(getTagValue("아파트", eElement));
	            jobj.set월(getTagValue("월", eElement));
	            jobj.set일(getTagValue("일", eElement));
	            jobj.set일련번호(getTagValue("일련번호", eElement));
	            jobj.set전용면적(getTagValue("전용면적", eElement));
	            jobj.set지번(getTagValue("지번", eElement));
	            jobj.set지역코드(getTagValue("지역코드", eElement));
	            jobj.set층(getTagValue("층", eElement));               
	            jsonArrayList.add(jobj);
	         }
	      }
	      return jsonArrayList;
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }      
	      return null;
	   }
	   
	   public JSONArray getHouseDeal(Map<String, String> map) {
	      try {
	         String url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
	         String serviceKey = "IV0jNt%2B5hKSeTK8Vvpef%2BGjvrUrdPfaL3271SHrXiPAt1InsJpYlPzfO6LJAjEibd502cOiZWb%2FBhfQ%2BdtyIKQ%3D%3D";
	         String pageNo = map.get("pageNo");
	         String numOfRows = map.get("numOfRows");
	         System.out.println(numOfRows);
	         String DEAL_YMD = "2021";
	         String LAWD_CD = map.get("LAWD_CD");
	         String houseName = map.get("houseName");
	         JSONArray jsonArrayList = new JSONArray();

	         // 1월부터 ~
	         for(int i = 5 ; i <= 10 ; i++) {
	            DEAL_YMD = "2021";
	            if(i >= 10) DEAL_YMD += Integer.toString(i);
	            else DEAL_YMD += "0" + Integer.toString(i);
	            Document documentInfo = DocumentBuilderFactory
	                  .newInstance()
	                  .newDocumentBuilder()
	                  .parse(url + "?serviceKey=" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows + "&LAWD_CD=" + LAWD_CD + "&DEAL_YMD=" +DEAL_YMD);
	            documentInfo.getDocumentElement().normalize();
	            
	            NodeList nList = documentInfo.getElementsByTagName("item");
	            for(int temp = 0 ; temp <nList.getLength(); temp++) {
	               Node nNode = nList.item(temp);
	               if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	                  Element eElement = (Element) nNode;
	                  if(!houseName.equals(getTagValue("아파트", eElement))) continue;
	                  JSONObject jobj = new JSONObject();
	                  jobj.put("거래금액",  getTagValue("거래금액", eElement));
	                  jobj.put("건축년도",  getTagValue("건축년도", eElement));
	                  jobj.put("년",  getTagValue("년", eElement));
	                  jobj.put("도로명",  getTagValue("도로명", eElement));
	                  jobj.put("도로명건물본번호코드",  getTagValue("도로명건물본번호코드", eElement));
	                  jobj.put("도로명건물부번호코드",  getTagValue("도로명건물부번호코드", eElement));
	                  jobj.put("도로명시군구코드",  getTagValue("도로명시군구코드", eElement));
	                  jobj.put("도로명일련번호코드",  getTagValue("도로명일련번호코드", eElement));
	                  jobj.put("도로명지상지하코드",  getTagValue("도로명지상지하코드", eElement));
	                  jobj.put("도로명코드",  getTagValue("도로명코드", eElement));
	                  jobj.put("법정동",  getTagValue("법정동", eElement));
	                  jobj.put("법정동본번코드",  getTagValue("법정동본번코드", eElement));
	                  jobj.put("법정동부번코드",  getTagValue("법정동부번코드", eElement));
	                  jobj.put("법정동시군구코드",  getTagValue("법정동시군구코드", eElement));
	                  jobj.put("법정동읍면동코드",  getTagValue("법정동읍면동코드", eElement));
	                  jobj.put("아파트",  getTagValue("아파트", eElement));
	                  jobj.put("월",  getTagValue("월", eElement));
	                  jobj.put("일",  getTagValue("일", eElement));
	                  jobj.put("일련번호",  getTagValue("일련번호", eElement));
	                  jobj.put("전용면적",  getTagValue("전용면적", eElement));
	                  jobj.put("지번",  getTagValue("지번", eElement));
	                  jobj.put("지역코드",  getTagValue("지역코드", eElement));
	                  jobj.put("층",  getTagValue("층", eElement));      
	                  
	                  jsonArrayList.put(jobj);
	               }
	            }
	         }
	         System.out.println(jsonArrayList.length());
	         return jsonArrayList;
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }      
	      return null;
	   }

	@Override
	public List<HouseDealInfoDto> getDealList(Map<String, Double> map) throws Exception {
		
		return sqlSession.getMapper(HouseMapper.class).getDealList(map);
	}

	@Override
	public List<TrainDto> getTrainList(Map<String, String> map) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getTrainList(map);
	}

	@Override
	public List<ClinicDto> getClinicList(Map<String, String> map) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getClinicList(map);
	}

	@Override
	public List<BusDto> getBusList(Map<String, String> map) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getBusList(map);
	}

	@Override
	public List<StarbucksDto> getStarbucksList(Map<String, String> map) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getStarbucksList(map);
	}

	@Override
	public List<BikeDto> getBikeList(Map<String, String> map) throws Exception {
		return sqlSession.getMapper(HouseMapper.class).getBikeList(map);
	}

}
