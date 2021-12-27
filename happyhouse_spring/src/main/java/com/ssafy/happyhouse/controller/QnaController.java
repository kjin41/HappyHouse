package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.QnaDto;
import com.ssafy.happyhouse.model.QnaParameterDto;
import com.ssafy.happyhouse.model.service.QnaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/qna")
@Api("QnA 컨트롤러 API V1")
public class QnaController {

	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private QnaService qnaService;

	@ApiOperation(value = "QnA 글작성", notes = "새로운 QnA 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeArticle(@RequestBody @ApiParam(value = "QnA 정보.", required = true) QnaDto qnaDto) throws Exception {
		logger.info("writeArticle - 호출");
		if (qnaService.writeArticle(qnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "QnA 글목록", notes = "모든 QnA의 정보를 반환한다.", response = List.class)
	@GetMapping("/{userId}")
	public ResponseEntity<List<QnaDto>> listArticle(@PathVariable("userId") @ApiParam(value = "얻어올 글의 글번호.", required = true) String id) throws Exception {
		logger.info("listArticle - 호출");
		System.out.println(id);
		
		return new ResponseEntity<List<QnaDto>>(qnaService.listArticle(id), HttpStatus.OK);
	}
	
//	@ApiOperation(value = "QnA 글보기", notes = "글번호에 해당하는 QnA의 정보를 반환한다.", response = QnaDto.class)
//	@GetMapping("/view/{no}")
//	public ResponseEntity<QnaDto> getArticle(@PathVariable("no") @ApiParam(value = "얻어올 글의 글번호.", required = true) int no) throws Exception {
//		logger.info("getArticle - 호출 : " + no);
////		qnaService.updateHit(no);
//		return new ResponseEntity<QnaDto>(qnaService.getArticle(no), HttpStatus.OK);
//	}
	
	@ApiOperation(value = "QnA 글수정", notes = "새로운 QnA 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) QnaDto qnaDto) throws Exception {
		logger.info("modifyArticle - 호출");
		
		if (qnaService.modifyArticle(qnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "QnA 글삭제", notes = "글번호에 해당하는 QnA의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{no}")
	public ResponseEntity<String> deleteArticle(@PathVariable("no") @ApiParam(value = "살제할 글의 글번호.", required = true) int no) throws Exception {
		logger.info("deleteArticle - 호출");
		if (qnaService.deleteArticle(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
