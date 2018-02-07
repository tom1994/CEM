package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordFtpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayFtpDao;
import io.cem.modules.cem.entity.RecordDayFtpEntity;
import io.cem.modules.cem.service.RecordDayFtpService;



@Service("recordDayFtpService")
public class RecordDayFtpServiceImpl implements RecordDayFtpService {
	@Autowired
	private RecordDayFtpDao recordDayFtpDao;
	@Autowired
	private RecordFtpDao recordFtpDao;
	
	@Override
	public RecordDayFtpEntity queryObject(Integer id){
		return recordDayFtpDao.queryObject(id);
	}

	@Override
	public List<RecordDayFtpEntity> queryDay(Map<String,Object> map) {return recordFtpDao.queryDay(map);}

	@Override
	public List<RecordDayFtpEntity> queryList(Map<String, Object> map){
		return recordDayFtpDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayFtpDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayFtpEntity recordDayFtp){
		recordDayFtpDao.save(recordDayFtp);
	}
	
	@Override
	public void update(RecordDayFtpEntity recordDayFtp){
		recordDayFtpDao.update(recordDayFtp);
	}
	
	@Override
	public void delete(Integer id){
		recordDayFtpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayFtpDao.deleteBatch(ids);
	}
	
}
