package com.ssafy.happyhouse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.CoronaDto;
import com.ssafy.happyhouse.model.NewsDto;
import com.ssafy.happyhouse.model.WeatherDto;

@RestController
@RequestMapping("/news")
public class NewsController {
   
   @GetMapping("/list")
   private ResponseEntity<List<NewsDto>> list(@RequestParam Map<String, String> map) throws Exception{
      String cityNo = map.get("cityNo");
      String dvsnNo = map.get("dvsnNo");
      cityNo = (cityNo == null) ? "" : cityNo;
      dvsnNo = (dvsnNo == null) ? "" : dvsnNo;
      cityNo = (cityNo.equals("00000000")) ? "" : cityNo;
      dvsnNo = (dvsnNo.equals("00000")) ? "" : dvsnNo;
      String url= "https://land.naver.com/news/region.naver?city_no=" + cityNo + "&dvsn_no=" + dvsnNo;
      System.out.println(url);
      Document doc = Jsoup.connect(url).get();
      
      Elements element = doc.select("ul.headline_list");
      
      List<NewsDto> newsList = new ArrayList<NewsDto>();
      int i = 0 ;
      for(Element el : element.select("li")) {
    	 if(i == 5) break;
         System.out.println(el.select("a").select("img").attr("abs:src")); // 이미지 url
         System.out.println(el.select("a").attr("abs:href")); //news url
         System.out.println(el.select("dt").select("a").text()); // 뉴스 제목
         System.out.println(el.select("dd").text()); // 내용
         System.out.println(el.select("span.writing").text()); // 작성언론
         System.out.println(el.select("span.date").text()); // 작성일자
         
         NewsDto newsDto = new NewsDto();
         if(el.select("a").select("img") != null) {
            newsDto.setImgUrl(el.select("a").select("img").attr("abs:src")); // 이미지 url
         }
         newsDto.setUrl(el.select("a").attr("abs:href")); //  (뉴스 url)
         newsDto.setTitle(el.select("dt").select("a").text()); // 뉴스 제목
         newsDto.setContent(el.select("dd").text()); // 내용
         newsDto.setWriting(el.select("span.writing").text()); // 작성언론
         newsDto.setDate(el.select("span.date").text()); // 작성일자
         
         newsList.add(newsDto);
         i++;
      }
      
      return new ResponseEntity<List<NewsDto>>(newsList , HttpStatus.OK);
   }
   
   @GetMapping("/temp")
   private ResponseEntity<WeatherDto> tempList() throws Exception{
	   
	   String url = "https://www.weather.go.kr/w/weather/forecast/short-term.do";
	   Document doc = Jsoup.connect(url).get();
	   Elements element = doc.select("table.table-col tbody tr");
	   
	   WeatherDto weatherDto = new WeatherDto();
	   
	   int index = 0 ;
	   for(Element el : element.select("td")) {
		   if(index == 0) weatherDto.setLow(el.text());
		   if(index == 5) {
			   weatherDto.setHigh(el.text());
			   break;
		   }
		   index++;
	   }
	   
	   return new ResponseEntity<WeatherDto>(weatherDto , HttpStatus.OK);
   
   }
   @GetMapping("/corona")
   private ResponseEntity<List<CoronaDto>> coronaList(@RequestParam Map<String, String> map) throws Exception{
	   String url = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=13&ncvContSeq=&contSeq=&board_id=&gubun=";
	   Document doc = Jsoup.connect(url).get();
	   Elements element = doc.select("div.data_table table tbody");
	   
	   List<CoronaDto> cList = new ArrayList<CoronaDto>();
	   
	   for(Element el: element.select("tr")) {
		   CoronaDto coronaDto = new CoronaDto();
		   System.out.println(el.text());
		   String[] ary = new String[9];
		   
		   ary = el.text().split(" ");
		   for(int i = 0 ; i < 9 ; i++) {
			   if(ary[i].equals("-")) ary[i] = "0";
		   }
		   coronaDto.setRegion(ary[0].trim()); 
		   coronaDto.setTdTotal(Integer.parseInt(ary[1].trim().replaceAll(",","")));
		   coronaDto.setTdIn(Integer.parseInt(ary[2].trim().replaceAll(",","")));
		   coronaDto.setTdOut(Integer.parseInt(ary[3].trim().replaceAll(",","")));
		   coronaDto.setTotal(Integer.parseInt(ary[4].trim().replaceAll(",","")));
		   coronaDto.setCure(Integer.parseInt(ary[6].trim().replaceAll(",","")));
		   coronaDto.setDead(Integer.parseInt(ary[7].trim().replaceAll(",","")));
		   coronaDto.setRate(Integer.parseInt(ary[8].trim().replaceAll(",","")));
		   cList.add(coronaDto);
	   }
	   return new ResponseEntity<List<CoronaDto>>(cList, HttpStatus.OK);
   }
}