package me.xbb123.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.xbb123.framework.data.domain.PageRequestParameter;
import me.xbb123.mvc.domain.Board;
import me.xbb123.mvc.parameter.BoardParameter;
import me.xbb123.mvc.parameter.BoardSearchParameter;
import me.xbb123.mvc.repository.BoardRepository;


/**
 * 게시판 서비스
 * @author xbb123
 *
 */
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository repository;
	
	/**
	 * 목록 리턴.
	 * @return
	 */
	public List<Board> getList(PageRequestParameter<BoardSearchParameter> pageRequestParameter) {
		return repository.getList(pageRequestParameter);
	}
	
	/**
	 * 상세 정보 리턴.
	 * @param boardSeq
	 * @return
	 */
	public Board get(int boardSeq) {
		return repository.get(boardSeq);
	}
	
	/**
	 * 등록 처리.
	 * @param parameter
	 */
	public int save(BoardParameter parameter) {
		// 조회하여 리턴된 정보
		Board board = repository.get(parameter.getBoardSeq());
		if(board == null) {
			repository.save(parameter);
		} else {
			repository.update(parameter);
		}		
		return parameter.getBoardSeq();
	}
	
	/** 
	 * 삭제 처리.
	 * @param boardSeq
	 */
	public boolean delete(int boardSeq) {
		repository.delete(boardSeq);
		return true;
	}
	
	/**
	 * 단순 반목문을 이용한 등록 처리.
	 * @param list
	 */
	public void saveList1(List<BoardParameter> list) {
		for (BoardParameter parameter : list) {
			repository.save(parameter);
		}
	}
	
	/**
	 * 100개씩 배열에 담아서 일괄 등록 처리.
	 * @param boardList
	 */
	public void saveList2(List<BoardParameter> boardList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardList", boardList);
		repository.saveList(paramMap);
	}
}
