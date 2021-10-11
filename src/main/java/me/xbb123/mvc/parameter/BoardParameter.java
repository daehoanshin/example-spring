package me.xbb123.mvc.parameter;

import lombok.Data;
import me.xbb123.mvc.domain.BoardType;

@Data
public class BoardParameter {
	private int boardSeq;
	private BoardType boardType; 
	private String title;
	private String contents;
	private boolean delYn;
	
	
	public BoardParameter() { }
	 
	
	public BoardParameter(BoardType boardType, String title, String contents) {
		this.boardType = boardType;
		this.title = title;
		this.contents = contents;
	}
}
