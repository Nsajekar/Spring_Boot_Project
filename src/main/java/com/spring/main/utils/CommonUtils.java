package com.spring.main.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;

@Component
public class CommonUtils {

	/**
	 * Generic method to copy request & response bean
	 * e.g. Channel ID, Partition ID, Request Reference Number
	 * Also creates response timestamp
	 * @throws Exception 
	 */
	public CommonResponseBean<?> processResponseBean(CommonRequestBean<?> requestBean, CommonResponseBean<?> responseBean) {
		CommonResponseBean<?> respBean = new CommonResponseBean<>();
		BeanUtils.copyProperties(responseBean, respBean);
		respBean.setPartitionId(requestBean.getPartitionId());
		respBean.setRequestRefNo(requestBean.getRequestRefNo());
		respBean.setResponseTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return respBean;
	}
}
