package me.xbb123.mvc.parameter;

import lombok.Data;
import me.xbb123.mvc.domain.BoardType;

/**
 * 게시물 검색 파라미터
 * @author xbb123
 *
 */
@Data
public class BoardSearchParameter {
	private String keyword;
	private BoardType[] boardTypes;
}
