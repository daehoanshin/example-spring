package me.xbb123.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.xbb123.configuration.exception.BaseException;
import me.xbb123.configuration.http.BaseResponseCode;
import me.xbb123.configuration.http.BaseRespose;
import me.xbb123.mvc.domain.Board;
import me.xbb123.mvc.parameter.BoardParameter;
import me.xbb123.mvc.service.BoardService;

/**
 * 게시판 컨트롤러
 * 
 * @author xbb123
 *
 */
@RestController
@RequestMapping("/board")
@Api(tags = "게시판 API")
public class BoardController {

	@Autowired
	private BoardService boardService;

	/**
	 * 목록 리턴.
	 * 
	 * @return
	 */
	@GetMapping
	@ApiOperation(value = "목록조회", notes = "게시물 목록 정보를 조회할 수 있습니다.")
	public BaseRespose<List<Board>> getList() {
		return new BaseRespose<List<Board>>(boardService.getList());
	}

	/**
	 * 상세 정보 리턴.
	 * 
	 * @param boardSeq
	 * @return
	 */
	@GetMapping("/{boardSeq}")
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
	 * 등록 / 수정 처리.
	 * 
	 * @param parameter
	 */
	@PutMapping
	@ApiOperation(value = "등록 / 수정 처리", notes = "신구 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
	@ApiImplicitParams({ @ApiImplicitParam(name = "boardSeq", value = "게시물 번호", example = "1"),
			@ApiImplicitParam(name = "title", value = "제목", example = "spring"),
			@ApiImplicitParam(name = "contents", value = "강좌", example = "spring 강좌") })
	public BaseRespose<Integer> save(BoardParameter parameter) {
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
}
