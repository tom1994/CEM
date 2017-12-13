package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordHourFtpDao;
import io.cem.modules.cem.entity.RecordHourFtpEntity;
import io.cem.modules.cem.service.RecordHourFtpService;



@Service("recordHourFtpService")
public class RecordHourFtpServiceImpl implements RecordHourFtpService {
	@Autowired
	private RecordHourFtpDao recordHourFtpDao;
	
	@Override
	public RecordHourFtpEntity queryObject(Integer id){
		return recordHourFtpDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourFtpEntity> queryList(Map<String, Object> map){
		return recordHourFtpDao.queryList(map);
	}

	@Override
	public List<RecordHourFtpEntity> queryFtpList(Map<String, Object> map){
		return recordHourFtpDao.queryFtpList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourFtpDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourFtpEntity recordHourFtp){
		recordHourFtpDao.save(recordHourFtp);
	}
	
	@Override
	public void update(RecordHourFtpEntity recordHourFtp){
		recordHourFtpDao.update(recordHourFtp);
	}
	
	@Override
	public void delete(Integer id){
		recordHourFtpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourFtpDao.deleteBatch(ids);
	}
	
}
