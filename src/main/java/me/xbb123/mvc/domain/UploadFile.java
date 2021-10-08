package me.xbb123.mvc.domain;

import lombok.Data;

@Data
public class UploadFile {

	private int uploadFIleSeq;
	private String pathname;
	private String filename;
	private String originalFilename;
	private String size;
	private String contentType;
	private String resourcePathname;
}
