package me.xbb123.mvc.repository;

import org.springframework.stereotype.Repository;

import me.xbb123.mvc.parameter.UploadFileParameter;

/**
 * 업로드 파일 Repository
 * @author xbb123
 *
 */
@Repository
public interface UploadFileRepository {
	
	void save(UploadFileParameter parameter);
}
