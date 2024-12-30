package com.spring.main.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.main.annotation.Log;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.DataBean;
import com.spring.main.repository.CommonRestDao;

@Log
@Service
public class CommonRestServiceImpl implements CommonRestService{
	
	@Autowired
	CommonRestDao commonRestDao;
	
	@Autowired
	JWEService jweService;

	@Override
	public List<DataBean> getDataList() {
		return commonRestDao.getDataList();
	}

	@Override
	public int logRequest(CommonRequestBean<?> reqBean, String requestType) {
		return commonRestDao.logRequest(reqBean,requestType);
	}

	@Override
	public int logResponse(Object result, String requestType) {
		return commonRestDao.logResponse(result,requestType);
	}

	@Override
	public CommonRequestBean<String> encryptData(CommonRequestBean<DataBean> requestBean)throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		String toEncryptString = mapper.writeValueAsString(requestBean.getRequestData());
		String response = jweService.jweEncryptAndSign(toEncryptString);
		return new CommonRequestBean<>(requestBean.getPartitionId(),
										requestBean.getRequestRefNo(),
										new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
										response);
	}

	@Override
	public CommonRequestBean<DataBean> decryptData(CommonRequestBean<String> requestBean) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		String decryptedRequestString = jweService.jweVerifyAndDecrypt(requestBean.getRequestData());
		return new CommonRequestBean<>(requestBean.getPartitionId(),
										requestBean.getRequestRefNo(),
										requestBean.getRequestTimeStamp(),
										mapper.readValue(decryptedRequestString, DataBean.class));
	}

}
