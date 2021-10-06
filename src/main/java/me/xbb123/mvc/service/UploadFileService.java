package me.xbb123.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.xbb123.mvc.parameter.UploadFileParameter;
import me.xbb123.mvc.repository.UploadFileRepository;


/**
 * 업로드 파일 서비스
 * @author xbb123
 *
 */
@Service
public class UploadFileService {
	
	@Autowired
	private UploadFileRepository repository;
	
	
	/**
	 * 등록 처리.
	 * @param parameter
	 */
	public void save(UploadFileParameter parameter) {
		repository.save(parameter);
	}
	
}
