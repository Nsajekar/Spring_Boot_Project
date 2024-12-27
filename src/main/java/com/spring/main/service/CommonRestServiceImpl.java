package com.spring.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.main.annotation.Log;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;
import com.spring.main.model.DataBean;
import com.spring.main.repository.CommonRestDao;

@Log
@Service
public class CommonRestServiceImpl implements CommonRestService{
	
	@Autowired
	CommonRestDao commonRestDao;

	@Override
	public List<DataBean> getDataList() {
		return commonRestDao.getDataList();
	}

	@Override
	public int logRequest(CommonRequestBean<?> reqBean, String requestType) {
		return commonRestDao.logRequest(reqBean,requestType);
	}

	@Override
	public int logRequest(StringBuilder requestData, String requestType) {
		return commonRestDao.logRequest(requestData,requestType);
	}

	@Override
	public int logResponse(CommonResponseBean<?> respBean, String requestType) {
		return commonRestDao.logResponse(respBean,requestType);
	}

	@Override
	public int logResponse(Object result, String requestType) {
		return commonRestDao.logResponse(result,requestType);
	}

}
