package com.jqgrid.dto.user;

import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(value=AccessLevel.PUBLIC)
@Setter(value=AccessLevel.PUBLIC)
public class UserDTO{
	private String mid;
	private String mpwd;
	private String mname;
	private String mtel;
	private String memail;
	private String mgender;
	private Date mregdate;
	private Date mupdatedate;
	private int mage;
}
