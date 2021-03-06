package kr.happyjob.study.notice.controller;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.happyjob.study.common.comnUtils.FileModel;
import kr.happyjob.study.notice.exception.NoticeNotExistException;
import kr.happyjob.study.notice.model.NoticeModel;
import kr.happyjob.study.notice.service.ScmNoticeService;


@Controller
@RequestMapping("/scm/")
public class ScmNoticeController {

	@Autowired
	ScmNoticeService noticeService;
	
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());
	// Get class name for logger
	private final String className = this.getClass().toString();
	
	@Value("${fileUpload.virtualRootPath}")
	private String virtualRootPath;
	@Value("${fileUpload.rootPath}")
	private String rootPath;
	@Value("${fileUpload.noticePath}")
	private String noticePath;	
	
	
	@RequestMapping("noticeMgr.do")
	public String initNotice(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		logger.info("+ Start " + className + ".initNotice");
		logger.info("   - paramMap : " + paramMap);
		
		// ??????????????? ???????????? ?????? ?????????.
		// model.addAttribute("writer", session.getAttribute("loginId"));
		// ?????? ?????? ???????????? ????????? ?????? ?????????.
		// System.out.println("writer : " + session.getAttribute("loginId"));
		
		logger.info("+ End " + className + ".initNotice");
		
		return "notice/notice";
	}
	
	
	/* ???????????? ????????? ????????? */
	@RequestMapping("noticeList.do")
	public String noticeList(Model model, @RequestParam Map<String,Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
			
			// extraction params
			int currentPage = Integer.parseInt((String)paramMap.get("currentPage")); // ??????????????? 
			int pageSize = Integer.parseInt((String)paramMap.get("pageSize"));
			int pageIndex = (currentPage -1)*pageSize;
			
			// put parameters into Map
			paramMap.put("pageIndex", pageIndex);
			paramMap.put("pageSize", pageSize);
			
		
			// get notice list
			List<NoticeModel> noticeList = noticeService.noticeList(paramMap);
			
			
			// get notices total 
			int totalCnt = noticeService.noticeTotalCnt(paramMap);
			
			
			// Add attribute
			model.addAttribute("totalCnt", totalCnt);
			model.addAttribute("noticeList", noticeList);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("currentPage",currentPage);
			
		return "notice/noticeList";	
	}
	
	/* ???????????? ?????? ?????? ????????? */
	@RequestMapping("detailNoticeList.do")
	@ResponseBody
	public Map<String,Object> detailList(Model model, @RequestParam Map<String,Object> paramMap, HttpServletRequest request){
		
		  
		String result="";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			// get notice info by ntc_no
			NoticeModel detailNotice = noticeService.detailNotice(paramMap);
			
			if(detailNotice != null) {
				
				result = "SUCCESS";  // ????????? ????????????. 
				
			}else {
				result = "?????????????????? ?????? ?????????????????????";  // null?????? ???????????????.
			}
			
			resultMap.put("type", request.getSession().getAttribute("userType"));
			resultMap.put("info", detailNotice); 
			resultMap.put("resultMsg", result); // success ?????? ?????? 
		
		}catch(Exception e){
			
		}
	
		return resultMap;
	}
	
	
	
	// ???????????? ??????
	@RequestMapping("noticeSave.do")
	@PostMapping
	@ResponseBody
	public int insertNotice(String action, NoticeModel data,List<MultipartFile> files, HttpServletRequest request) throws Exception {
		
		int insertResult=0; 
		
		try{
			
			if("I".equalsIgnoreCase(action)) {
				
				// ?????? service
				insertResult=noticeService.insertNotice(data,files,request);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return insertResult;
	}
	
	// ???????????? ??????
	@RequestMapping("noticeUpdate.do")
	@PutMapping
	@ResponseBody
	public int updateNotice(String action, @RequestParam Map<String, Object> params, List<MultipartFile> files, List<String> delTargets, HttpServletRequest req){
		
		
		logger.info("+ initiate updateNotice...");
		int updateResult=0;
		
		try{
			if("U".equalsIgnoreCase(action)){
				
				

				// ?????? ??????????????? ????????? ????????????
				NoticeModel latestNoticeInfo=noticeService.detailNotice(params); 
				latestNoticeInfo.setFilesInfo(noticeService.selectFilesByNoticeId(params));
				// ??????????????? ???????????? ??????
				List<FileModel> deleteFileInfo =noticeService.selectFilesByFileNo(delTargets);
				// ????????? ???????????? ????????? ????????? ??????
				// ????????????

				
				updateResult=noticeService.updateNotice(latestNoticeInfo, params, files, delTargets, req);
			}
		}catch(Exception e){
			if(!(e instanceof NoticeNotExistException)){
				// ?????? ?????? ??????
				// NewFileUtil fUtil=new NewFileUtil(req, rootPath, virtualRootPath, noticePath);
			}
			
		}
		
		return updateResult;
	}
	
	// ???????????? ??????
	@RequestMapping("noticeDelete/{idx}")
	@DeleteMapping
	@ResponseBody
	public int deleteNotice(@PathVariable String idx){
		
		int result=0;
		
		try{
			result=noticeService.deleteNotice(idx);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	@RequestMapping("fileDown.do")
	public void downloadBbsAtmtFil(@RequestParam Map<String, Object> paramMap, HttpServletResponse response) throws Exception {
	
		logger.info("+ Start " + className + ".downloadBbsAtmtFil");
		logger.info("   - paramMap : " + paramMap);
		
		// ???????????? ??????
		FileModel file = noticeService.selectOneFile(paramMap.get("file_no").toString());
		
		
		byte fileByte[] = FileUtils.readFileToByteArray(new File(file.getFile_local_path()));
		
		response.setContentType("application/octet-stream");
	    response.setContentLength(fileByte.length);
	    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getFile_ofname(),"UTF-8")+"\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");
	    response.getOutputStream().write(fileByte);
	     
	    response.getOutputStream().flush();
	    response.getOutputStream().close();

		logger.info("+ End " + className + ".fileDown");
	}
	
	
	
	
	

}
