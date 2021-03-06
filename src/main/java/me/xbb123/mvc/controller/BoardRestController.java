package me.xbb123.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.xbb123.configuration.exception.BaseException;
import me.xbb123.configuration.http.BaseResponseCode;
import me.xbb123.configuration.http.BaseRespose;
import me.xbb123.framework.data.domain.PageRequestParameter;
import me.xbb123.framework.web.bind.annotation.RequestConfig;
import me.xbb123.mvc.domain.Board;
import me.xbb123.mvc.domain.BoardType;
import me.xbb123.mvc.domain.MenuType;
import me.xbb123.mvc.domain.MySQLPageRequest;
import me.xbb123.mvc.parameter.BoardParameter;
import me.xbb123.mvc.parameter.BoardSearchParameter;
import me.xbb123.mvc.service.BoardService;

/**
 * 게시판 컨트롤러
 * 
 * @author xbb123
 *
 */
@Controller
@RequestMapping("/board")
@Api(tags = "게시판 API")
public class BoardRestController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoardService boardService;

	
	/**
	 * 목록 리턴.
	 * @param parameter
	 * @param pageRequest
	 * @return
	 */
	@GetMapping
	@ResponseBody
	@ApiOperation(value = "목록조회", notes = "게시물 목록 정보를 조회할 수 있습니다.")	
	public BaseRespose<List<Board>> getList(
			@ApiParam BoardSearchParameter parameter,
			@ApiParam MySQLPageRequest pageRequest) {
		logger.info("pageRequest : {}", pageRequest);
		PageRequestParameter<BoardSearchParameter> pageRequestParameter = new PageRequestParameter<BoardSearchParameter>(pageRequest, parameter);
		
		return new BaseRespose<List<Board>>(boardService.getList(pageRequestParameter));
	}
	
	/**
	 * 상세 정보 리턴.
	 * 
	 * @param boardSeq
	 * @return
	 */
	@GetMapping("/get/{boardSeq}")
	@ResponseBody
	@ApiOperation(value = "상세조회", notes = "게시물 번호에 해당하는 상세 정보를 조회할 수 있습니다.")
	@ApiImplicitParams({ @ApiImplicitParam(name = "boardSeq", value = "게시물 번호", example = "1") })
	public BaseRespose<Board> get(@PathVariable int boardSeq) {
		Board board = boardService.get(boardSeq);
		// null 처리
		if (board == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] { "계시물" });
		}
		return new BaseRespose<Board>(boardService.get(boardSeq));
	}
	
	/**
	 * 등록 화면
	 * @param parameter
	 * @param model
	 */
	@GetMapping("/form")
	@RequestConfig(loginCheck = false)
	public void form(BoardParameter parameter, Model model) {
		logger.info("parameter : {}", parameter);
		if(parameter.getBoardSeq() > 0) {
			Board board = boardService.get(parameter.getBoardSeq());
			model.addAttribute("board", board);
		}
		model.addAttribute("parameter", parameter);
	}
	
	/**
	 * 수정 화면
	 * @param parameter
	 * @param model
	 */
	@GetMapping("/edit/{boardSeq}")
	@RequestConfig(loginCheck = false)
	public String edit(@PathVariable(required = true) int boardSeq, BoardParameter parameter, Model model) {
		Board board = boardService.get(boardSeq);
		// null 처리
		if (board == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] { "게시물" });
		}
		model.addAttribute("board", board);
		model.addAttribute("parameter", parameter);
		return "/board/form";
	}
	

	/**
	 * 등록 / 수정 처리.
	 * 
	 * @param parameter
	 */
	@SuppressWarnings("deprecation")
		@PutMapping
	@RequestConfig(loginCheck = false)
	@ResponseBody
	@ApiOperation(value = "등록 / 수정 처리", notes = "신구 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
	@ApiImplicitParams({ @ApiImplicitParam(name = "boardSeq", value = "게시물 번호", example = "1"),
			@ApiImplicitParam(name = "boardType", value = "타입", example = "NOTICE"),
			@ApiImplicitParam(name = "title", value = "제목", example = "spring"),
			@ApiImplicitParam(name = "contents", value = "강좌", example = "spring 강좌") })
	public BaseRespose<Integer> save(BoardParameter parameter) {
		logger.info("parameter : {}", parameter);
		// 제목 필수 체크
		if (StringUtils.isEmpty(parameter.getTitle())) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] { "title", "제목" });
		}
		// 내용 필수 체크
		if (StringUtils.isEmpty(parameter.getContents())) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] { "contents", "내용" });
		}
		boardService.save(parameter);
		return new BaseRespose<Integer>(parameter.getBoardSeq());
	}

	/**
	 * 삭제 처리.
	 * 
	 * @param boardSeq
	 */
	@DeleteMapping("/{boardSeq}")
	@RequestConfig
	@ApiOperation(value = "삭제 처리", notes = "게시물 번호에 해당하는 정보를 삭제합니다.")
	@ApiImplicitParams({ @ApiImplicitParam(name = "boardSeq", value = "게시물 번호", example = "1"), })
	public BaseRespose<Boolean> delete(@PathVariable int boardSeq) {
		Board board = boardService.get(boardSeq);
		if (board == null) {
			return new BaseRespose<Boolean>(false);
		}
		boardService.delete(boardSeq);
		return new BaseRespose<Boolean>(true);
	}

	/**
	 * 대용량 등록 처리.
	 * 
	 * @return
	 */
	@PutMapping("/saveList1")
	@ApiOperation(value = "대용량 등록 처리1", notes = "대용량 등록 처리1")
	public BaseRespose<Boolean> saveList1() {
		int count = 0;
		// 테스트를 위한 랜덤 1000건의 데이터를 생성
		List<BoardParameter> list = new ArrayList<BoardParameter>();
		while (true) {
			count++;
			String title = RandomStringUtils.randomAlphabetic(10);
			String contents = RandomStringUtils.randomAlphabetic(10);
//			
			list.add(new BoardParameter(BoardType.INQUIRY, title, contents));
			if (count >= 10000) {
				break;
			}
		}
		long start = System.currentTimeMillis();
		boardService.saveList1(list);
		long end = System.currentTimeMillis();
		logger.info("실행시간 : {}", (end - start) / 1000.0);
		return new BaseRespose<Boolean>(true);
	}

	/**
	 * 대용량 등록 처리 2.
	 * 
	 * @return
	 */
	@PutMapping("/saveList2")
	@ApiOperation(value = "대용량 등록 처리2", notes = "대용량 등록 처리2")
	public BaseRespose<Boolean> saveList2() {
		int count = 0;
		// 테스트를 위한 랜덤 1000건의 데이터를 생성
		List<BoardParameter> list = new ArrayList<BoardParameter>();
		while (true) {
			count++;
			String title = RandomStringUtils.randomAlphabetic(10);
			String contents = RandomStringUtils.randomAlphabetic(10);
			list.add(new BoardParameter(null, title, contents));
			if (count >= 10000) {
				break;
			}
		}
		long start = System.currentTimeMillis();
		boardService.saveList2(list);
		long end = System.currentTimeMillis();
		logger.info("실행시간 : {}", (end - start) / 1000.0);
		return new BaseRespose<Boolean>(true);
	}
}
