package com.impetus.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsuranceUtils {

	private static final Logger logger = LoggerFactory.getLogger(InsuranceUtils.class);

	private InsuranceUtils() {
	}
	/**
	 * @param roleId
	 * @return GetRoleName method return Role name
	 */
	public static String getRoleName(long roleId) {
		logger.info("Inside getRoleName method in InsuranceUtils");
		String roleName;
		switch ((int) roleId) {
		case 1:
			roleName = InsuranceConstant.ADMIN;
			break;
		case 2:
			roleName = InsuranceConstant.UNDERWRITER;
			break;
		case 3:
			roleName = InsuranceConstant.USER;
			break;

		default:
			roleName = "";
			break;
		}
		logger.info("Before return in getRoleName Method");
		return roleName;
	}

	/**
	 * @param date
	 * @return This method convert Date object to LocalDateTime object
	 */
	public static LocalDateTime getLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
