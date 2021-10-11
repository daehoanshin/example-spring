package me.xbb123.mvc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.xbb123.configuration.exception.BaseException;
import me.xbb123.configuration.http.BaseResponseCode;
import me.xbb123.configuration.http.BaseRespose;
import me.xbb123.framework.data.domain.PageRequestParameter;
import me.xbb123.framework.web.bind.annotation.RequestConfig;
import me.xbb123.mvc.domain.Board;
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
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoardService boardService;

	/**
	 * 목록 리턴.
	 * 
	 * @param menuType
	 * @param parameter
	 * @param pageRequest
	 * @param model
	 * @return
	 */
	@GetMapping("/{menuType}")
	public String list(@PathVariable MenuType menuType, BoardSearchParameter parameter, MySQLPageRequest pageRequest,
			Model model) {
		logger.info("menuType : {}", menuType);
		logger.info("pageRequest : {}", pageRequest);

		PageRequestParameter<BoardSearchParameter> pageRequestParameter = new PageRequestParameter<BoardSearchParameter>(
				pageRequest, parameter);
		List<Board> boardList = boardService.getList(pageRequestParameter);
		model.addAttribute("boardList", boardList);
		model.addAttribute("menuType", menuType);
		return "/board/list";
	}

	/**
	 * 등록 화면
	 * 
	 * @param parameter
	 * @param model
	 */
	@GetMapping("/{menuType}/form")
	@RequestConfig(loginCheck = false)
	public String form(@PathVariable MenuType menuType, BoardParameter parameter, Model model) {
		logger.info("parameter : {}", parameter);
		if (parameter.getBoardSeq() > 0) {
			Board board = boardService.get(parameter.getBoardSeq());
			model.addAttribute("board", board);
		}
		model.addAttribute("parameter", parameter);
		model.addAttribute("menuType", menuType);
		return "/board/form";
	}

	/**
	 * 수정 화면
	 * 
	 * @param parameter
	 * @param model
	 */
	@GetMapping("/{menuType}/edit/{boardSeq}")
	@RequestConfig(loginCheck = false)
	public String edit(@PathVariable MenuType menuType, @PathVariable(required = true) int boardSeq, BoardParameter parameter, Model model) {
		Board board = boardService.get(boardSeq);
		// null 처리
		if (board == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] { "게시물" });
		}
		model.addAttribute("board", board);
		model.addAttribute("parameter", parameter);
		model.addAttribute("menuType", menuType);
		return "/board/form";
	}

	/**
	 * 상세 화면
	 * 
	 * @param boardSeq
	 * @param model
	 * @return
	 */
	@GetMapping("/{menuType}/{boardSeq}")
	public String detail(@PathVariable MenuType menuType, @PathVariable int boardSeq, Model model) {
		Board board = boardService.get(boardSeq);
		// null 처리
		if (board == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] { "게시물" });
		}
		model.addAttribute("board", board);
		model.addAttribute("menuType", menuType);
		return "/board/detail";
	}

	/**
	 * 등록 / 수정 처리.
	 * 
	 * @param parameter
	 */
	@PostMapping("/{menuType}/save")
	@RequestConfig(loginCheck = false)
	@ResponseBody
	public BaseRespose<Integer> save(@PathVariable MenuType menuType, BoardParameter parameter) {
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

}
