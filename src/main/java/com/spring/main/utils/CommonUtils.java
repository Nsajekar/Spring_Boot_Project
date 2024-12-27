package com.spring.main.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.spring.main.annotation.Log;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;

@Log
@Component
public class CommonUtils {

	/**
	 * Generic method to copy request & response bean
	 * e.g.Partition ID, Request Reference Number
	 * Also creates response times-tamp
	 * @throws Exception 
	 */
	public ResponseEntity<?> processResponseBean(CommonRequestBean<?> fromBean, ResponseEntity<?> toBean) {
		CommonResponseBean<?> responseBeanData = new CommonResponseBean<>();
		Object toBeanData = toBean.getBody();
		if(toBeanData instanceof CommonResponseBean) {
			responseBeanData = (CommonResponseBean<?>) toBeanData;
		}
		BeanUtils.copyProperties(toBeanData,responseBeanData);
		responseBeanData.setPartitionId(fromBean.getPartitionId());
		responseBeanData.setRequestRefNo(fromBean.getRequestRefNo());
		responseBeanData.setResponseTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return ResponseEntity.ok(responseBeanData);
	}
}
