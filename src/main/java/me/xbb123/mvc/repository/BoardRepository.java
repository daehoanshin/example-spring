package me.xbb123.mvc.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import me.xbb123.mvc.domain.Board;
import me.xbb123.mvc.parameter.BoardParameter;

/**
 * 게시판 Repository
 * @author xbb123
 *
 */
@Repository
public interface BoardRepository {
	List<Board> getList();
	Board get(int boardSeq);
	int save(BoardParameter board);
	void update(BoardParameter board);
	void delete(int boardSeq);
}
