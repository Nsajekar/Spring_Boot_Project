package com.spring.main.service;

import java.util.List;

import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.DataBean;

public interface CommonRestService {

	List<DataBean> getDataList();

	int logRequest(CommonRequestBean<?> reqBean, String requestType);

	int logResponse(Object result, String requestType);

	CommonRequestBean<Object> encryptData(CommonRequestBean<Object> requestBean) throws Throwable;

	CommonRequestBean<Object> decryptData(CommonRequestBean<Object> requestBean) throws Throwable;

}
