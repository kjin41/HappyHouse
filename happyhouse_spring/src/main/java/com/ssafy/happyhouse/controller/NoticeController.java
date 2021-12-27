package com.ssafy.happyhouse.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.happyhouse.model.FileInfoDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.NoticeParameterDto;
import com.ssafy.happyhouse.model.service.NoticeService;
import com.ssafy.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/notice")
@Api("공지사항 컨트롤러")
public class NoticeController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS="success";
	private static final String FAIL="fail";

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private NoticeService noticeService;

//	@RequestBody @ApiParam(value="공지사항 정보", required=true) NoticeDto noticeDto, 
	@ApiOperation(value="공지사항 글작성", notes="새로운 공지사항 작성. DB 입력 성공 시 success")
	@PostMapping
	public ResponseEntity<String> register(@RequestParam("userId") String userId, @RequestParam("title") String title, @RequestParam("content") String content,  
			@RequestParam(value="upfile", required = false) MultipartFile[] files) throws Exception {
//	public ResponseEntity<String> register(@RequestParam Map<Object, MultipartFile[]> param) throws Exception {
//	public ResponseEntity<String> register(@RequestBody @ApiParam(value="공지사항 정보", required=true) NoticeDto noticeDto) throws Exception {
		logger.info("register notice 호출");
//		System.out.println(title);
		NoticeDto noticeDto=new NoticeDto();
		noticeDto.setTitle(title);
		noticeDto.setUserId(userId);
		noticeDto.setContent(content);
//		System.out.println(param);
//		FileUpload 관련 설정.
//		MultipartFile[] files=param.get("upfile");
		if (files!=null && !files[0].isEmpty()) {
			String realPath = servletContext.getRealPath("/upload");
//			String realPath = servletContext.getRealPath("/resources/img");
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = realPath + File.separator + today;
//			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<FileInfoDto> fileInfoDtos = new ArrayList<FileInfoDto>();
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfoDtos.add(fileInfoDto);
			}
			System.out.println(fileInfoDtos);
			noticeDto.setFileInfoDtos(fileInfoDtos);
		}

		noticeService.registerNotice(noticeDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value="공지사항 글목록", notes="모든 게시글 정보 반환. DB 입력 성공 시 success", response=List.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> list() throws Exception {
		logger.info("notice list 호출");
		Map<String, Object> map = new HashMap<>();
		
		map.put("noticeList", noticeService.listNotice());
//		map.put("noticeParameterDto", noticeService.makePageNavigation(noticeParameterDto));
			
		return new ResponseEntity<>(map,HttpStatus.OK);
//		PageNavigation pageNavigation = noticeService.makePageNavigation(noticeParameterDto);
//		return new ResponseEntity<List<NoticeDto>>(noticeService.listNotice(noticeParameterDto), HttpStatus.OK);
	}
	
	@GetMapping("/{no}")
	public ResponseEntity<NoticeDto> detail(@PathVariable("no") int no) throws Exception {
		logger.info("detail 호출");
		noticeService.updateHit(no);
		return new ResponseEntity<NoticeDto>(noticeService.getNotice(no), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> modify(@RequestBody NoticeDto noticeDto) throws Exception {
		logger.info("modify 호출");
		noticeService.updateNotice(noticeDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@DeleteMapping("/{no}")
	//@PathVariable("no") int no, @PathVariable("path") String path, @RequestParam Map<Integer, String>map, @RequestParam(value="path", required=false String path
	public ResponseEntity<String> delete(@PathVariable("no") int no) throws Exception {
		logger.info("delete 호출");
//		noticeService.deleteNotice(no, null);
		String path=servletContext.getRealPath("/upload");
		noticeService.deleteNotice(no, path);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PostMapping("/download")
//	@ResponseBody	// rest 로
//	public ResponseEntity<Resource> download(@RequestParam Map<String, String> param, HttpServletRequest request){
	public ResponseEntity<Resource> download(@RequestBody FileInfoDto fileInfoDto, HttpServletRequest request){
		logger.info("download 호출");
//		System.out.println(param);
//		String filePath = servletContext.getRealPath("/upload") + File.separator + param.get("sfolder") + File.separator + param.get("sfile");
		String filePath = servletContext.getRealPath("/upload") + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile();
		File target = new File(filePath);
		System.out.println(target);
		HttpHeaders header = new HttpHeaders();
		Resource rs = null;
		if(target.exists()) {
			try {
				String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
				System.out.println("mimeType : "+mimeType);
				if(mimeType == null) {
					mimeType = "application/download; charset=UTF-8";
				}
				rs = new UrlResource(target.toURI());
				String userAgent = request.getHeader("User-Agent");
		        boolean isIE = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1;
				String fileName = null;
//				String originalFile = (String) param.get("ofile");
				String originalFile = fileInfoDto.getOriginFile();
		        // IE는 다르게 처리
		        if (isIE) {
		        	fileName = URLEncoder.encode(originalFile, "UTF-8").replaceAll("\\+", "%20");
		        } else {
		            fileName = new String(originalFile.getBytes("UTF-8"), "ISO-8859-1");
		        }
//		        fileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
				header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName +"\"");
				header.setCacheControl("no-cache");
				header.setContentType(MediaType.parseMediaType(mimeType));
				System.out.println("header: "+header);
				System.out.println("rs: "+rs);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.ok()
	            .headers(header)
	            .body(rs);
//		return new ResponseEntity<Resource>(rs, header, HttpStatus.OK);
	}
}
