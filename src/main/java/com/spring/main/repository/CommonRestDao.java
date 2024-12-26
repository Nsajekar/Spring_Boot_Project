package com.spring.main.repository;

import java.util.List;

import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;
import com.spring.main.model.DataBean;

public interface CommonRestDao {

	List<DataBean> getDataList();

	int logRequest(CommonRequestBean<?> reqBean, String requestType);

	int logRequest(StringBuilder requestData, String requestType);

	int logResponse(CommonResponseBean<?> respBean, String requestType);

	int logResponse(Object result, String requestType);

	
}
