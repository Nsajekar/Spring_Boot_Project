package com.spring.main.repository;

import java.util.List;

import com.spring.main.annotation.Log;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;
import com.spring.main.model.DataBean;

public interface CommonRestDao {

	@Log(logReturn = true)
	List<DataBean> getDataList();

	@Log(logReturn = true)
	int logRequest(CommonRequestBean<?> reqBean, String requestType);

	@Log(logReturn = true)
	int logRequest(StringBuilder requestData, String requestType);

	@Log(logReturn = true)
	int logResponse(CommonResponseBean<?> respBean, String requestType);

	@Log(logReturn = true)
	int logResponse(Object result, String requestType);

	
}
