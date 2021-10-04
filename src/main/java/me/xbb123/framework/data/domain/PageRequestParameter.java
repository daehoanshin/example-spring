package me.xbb123.framework.data.domain;

import lombok.Data;
import me.xbb123.mvc.domain.MySQLPageRequest;

/**
 * 페이지 요청정보와 파라메터 정보.
 * @author xbb123
 *
 * @param <T>
 */
@Data
public class PageRequestParameter<T> {
	
	private MySQLPageRequest pageRequest;
	private T parameter;
	
	public PageRequestParameter(MySQLPageRequest pageRequest, T parameter) {
		this.pageRequest = pageRequest;
		this.parameter = parameter;
	}
	
}
