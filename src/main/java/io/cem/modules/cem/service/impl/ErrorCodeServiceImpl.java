package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.ErrorCodeDao;
import io.cem.modules.cem.entity.ErrorCodeEntity;
import io.cem.modules.cem.service.ErrorCodeService;



@Service("errorCodeService")
public class ErrorCodeServiceImpl implements ErrorCodeService {
	@Autowired
	private ErrorCodeDao errorCodeDao;
	
	@Override
	public ErrorCodeEntity queryObject(Integer code){
		return errorCodeDao.queryObject(code);
	}
	
	@Override
	public List<ErrorCodeEntity> queryList(Map<String, Object> map){
		return errorCodeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return errorCodeDao.queryTotal(map);
	}
	
	@Override
	public void save(ErrorCodeEntity errorCode){
		errorCodeDao.save(errorCode);
	}
	
	@Override
	public void update(ErrorCodeEntity errorCode){
		errorCodeDao.update(errorCode);
	}
	
	@Override
	public void delete(Integer code){
		errorCodeDao.delete(code);
	}
	
	@Override
	public void deleteBatch(Integer[] codes){
		errorCodeDao.deleteBatch(codes);
	}
	
}
