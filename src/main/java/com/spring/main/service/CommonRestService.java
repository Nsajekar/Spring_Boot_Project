package com.spring.main.service;

import java.util.List;

import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;
import com.spring.main.model.DataBean;

public interface CommonRestService {

	List<DataBean> getDataList();

	int logRequest(CommonRequestBean<?> reqBean, String requestType);

	int logRequest(StringBuilder requestData, String requestType);

	int logResponse(CommonResponseBean<?> respBean, String requestType);

	int logResponse(Object result, String requestType);

	CommonRequestBean<String> encryptData(CommonRequestBean<DataBean> requestBean) throws Throwable;

	CommonRequestBean<DataBean> decryptData(CommonRequestBean<String> requestBean) throws Throwable;
}
