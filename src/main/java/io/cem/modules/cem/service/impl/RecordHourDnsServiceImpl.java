package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordDnsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourDnsDao;
import io.cem.modules.cem.entity.RecordHourDnsEntity;
import io.cem.modules.cem.service.RecordHourDnsService;



@Service("recordHourDnsService")
public class RecordHourDnsServiceImpl implements RecordHourDnsService {
	@Autowired
	private RecordHourDnsDao recordHourDnsDao;
	@Autowired
	private RecordDnsDao recordDnsDao;
	
	@Override
	public RecordHourDnsEntity queryObject(Integer id){
		return recordHourDnsDao.queryObject(id);
	}

	@Override
	public List<RecordHourDnsEntity> queryDns(Map<String, Object> map){
		return recordDnsDao.queryDns(map);
	}

	@Override
	public List<RecordHourDnsEntity> queryList(Map<String, Object> map){
		return recordHourDnsDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourDnsEntity>> queryDnsList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourDnsDao.queryDnsList(map));
	}

	@Override
	public List<RecordHourDnsEntity> queryExitList(Map<String, Object> map){
		return recordHourDnsDao.queryExitList(map);
	}

	@Override
	public List<RecordHourDnsEntity> queryDayList(Map<String, Object> map){
		return recordHourDnsDao.queryDayList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourDnsDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourDnsEntity recordHourDns){
		recordHourDnsDao.save(recordHourDns);
	}
	
	@Override
	public void update(RecordHourDnsEntity recordHourDns){
		recordHourDnsDao.update(recordHourDns);
	}
	
	@Override
	public void delete(Integer id){
		recordHourDnsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourDnsDao.deleteBatch(ids);
	}
	
}
