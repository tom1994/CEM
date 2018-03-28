package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordFtpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourFtpDao;
import io.cem.modules.cem.entity.RecordHourFtpEntity;
import io.cem.modules.cem.service.RecordHourFtpService;



@Service("recordHourFtpService")
public class RecordHourFtpServiceImpl implements RecordHourFtpService {
	@Autowired
	private RecordHourFtpDao recordHourFtpDao;
	@Autowired
	private RecordFtpDao recordFtpDao;
	
	@Override
	public RecordHourFtpEntity queryObject(Integer id){
		return recordHourFtpDao.queryObject(id);
	}

	@Override
	public List<RecordHourFtpEntity> queryFtp(Map<String, Object> map){
		return recordFtpDao.queryFtp(map);
	}

	@Override
	public List<RecordHourFtpEntity> queryList(Map<String, Object> map){
		return recordHourFtpDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourFtpEntity>> queryFtpList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourFtpDao.queryFtpList(map));
	}

	@Override
	public List<RecordHourFtpEntity> queryDayList(Map<String, Object> map){
		return recordHourFtpDao.queryDayList(map);
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
