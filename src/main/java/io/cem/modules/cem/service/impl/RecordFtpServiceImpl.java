package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordHourFtpDao;
import io.cem.modules.cem.entity.RecordHourFtpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordFtpDao;
import io.cem.modules.cem.entity.RecordFtpEntity;
import io.cem.modules.cem.service.RecordFtpService;



@Service("recordFtpService")
public class RecordFtpServiceImpl implements RecordFtpService {
	@Autowired
	private RecordFtpDao recordFtpDao;

	@Autowired
	private RecordHourFtpDao recordHourFtpDao;
	
	@Override
	public RecordFtpEntity queryObject(Integer id){
		return recordFtpDao.queryObject(id);
	}
	
	@Override
	public List<RecordFtpEntity> queryList(Map<String, Object> map){
		return recordFtpDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordFtpDao.queryTotal(map);
	}

	@Override
	public List<RecordFtpEntity> queryFtpTest(Map<String, Object> map){
		return recordFtpDao.queryFtpTest(map);
	}

	@Override
	public List<RecordFtpEntity> queryFtpList(Map<String, Object> map) {
		return recordFtpDao.queryFtpList(map);
	}

	@Override
	public List<RecordHourFtpEntity> queryIntervalList(Map<String, Object> map) {
		return recordHourFtpDao.queryIntervalList(map);
	}

	@Override
	public int queryIntervalTotal(Map<String, Object> map) {
		return recordHourFtpDao.queryIntervalTotal(map);
	}


	@Override
	public void save(RecordFtpEntity recordFtp){
		recordFtpDao.save(recordFtp);
	}
	
	@Override
	public void update(RecordFtpEntity recordFtp){
		recordFtpDao.update(recordFtp);
	}
	
	@Override
	public void delete(Integer id){
		recordFtpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordFtpDao.deleteBatch(ids);
	}
	
}
