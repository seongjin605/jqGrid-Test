package com.jqgrid.dto.board;

import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(value=AccessLevel.PUBLIC)
@Setter(value=AccessLevel.PUBLIC)
public class BoardDTO {
	/*스트래티지 패턴 private로 DB 필드값 캡슐화하여 정보보호 및 은닉*/
	
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private int viewcnt;
	
	
}
