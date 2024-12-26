package com.spring.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.main.model.DataBean;
import com.spring.main.repository.CommonRestDao;

@Service
public class CommonRestServiceImpl implements CommonRestService{
	
	@Autowired
	CommonRestDao commonRestDao;

	@Override
	public List<DataBean> getDataList() {
		return commonRestDao.getDataList();
	}

}
