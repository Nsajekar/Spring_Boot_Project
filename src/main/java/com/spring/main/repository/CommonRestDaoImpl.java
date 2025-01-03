package com.spring.main.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.main.annotation.Log;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.DataBean;

@Log
@Repository
public class CommonRestDaoImpl implements CommonRestDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Log(logReturn = true)
	@Override
	public List<DataBean> getDataList() {
		List<DataBean> queryForList = null;
		try {
			String query = "select partition_id,request_ref_no,request_time_stamp from api_audit";
			queryForList = jdbcTemplate.query(query,new BeanPropertyRowMapper<>(DataBean.class));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return queryForList;
	}

	@Log(logReturn = true)
	@Override
	public int logRequest(CommonRequestBean<?> reqBean, String requestType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Log(logReturn = true)
	@Override
	public int logRequest(StringBuilder requestData, String requestType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Log(logReturn = true)
	@Override
	public int logResponse(Object result, String requestType) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
