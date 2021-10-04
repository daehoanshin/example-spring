package me.xbb123.mvc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import me.xbb123.framework.data.domain.PageRequestParameter;
import me.xbb123.mvc.domain.Board;
import me.xbb123.mvc.parameter.BoardParameter;
import me.xbb123.mvc.parameter.BoardSearchParameter;

/**
 * 게시판 Repository
 * @author xbb123
 *
 */
@Repository
public interface BoardRepository {
	List<Board> getList(PageRequestParameter<BoardSearchParameter> pageRequestParameter);
	Board get(int boardSeq);
	void save(BoardParameter board);
	void saveList(Map<String, Object> paramMap); 
	void update(BoardParameter board);
	void delete(int boardSeq);
}
