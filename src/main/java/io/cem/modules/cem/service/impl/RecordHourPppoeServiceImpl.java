package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordPppoeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourPppoeDao;
import io.cem.modules.cem.entity.RecordHourPppoeEntity;
import io.cem.modules.cem.service.RecordHourPppoeService;



@Service("recordHourPppoeService")
public class RecordHourPppoeServiceImpl implements RecordHourPppoeService {
	@Autowired
	private RecordHourPppoeDao recordHourPppoeDao;
	@Autowired
	private RecordPppoeDao recordPppoeDao;
	
	@Override
	public RecordHourPppoeEntity queryObject(Integer id){
		return recordHourPppoeDao.queryObject(id);
	}

	@Override
	public List<RecordHourPppoeEntity> queryPppoe(Map<String, Object> map){
		return recordPppoeDao.queryPppoe(map);
	}

	@Override
	public List<RecordHourPppoeEntity> queryList(Map<String, Object> map){
		return recordHourPppoeDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourPppoeEntity>> queryPppoeList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourPppoeDao.queryPppoeList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourPppoeEntity>> queryTargetHourList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourPppoeDao.queryTargetHourList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourPppoeEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourPppoeDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourPppoeEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourPppoeDao.queryDayExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourPppoeEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<> (recordHourPppoeDao.queryDayList(map));
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourPppoeDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourPppoeEntity recordHourPppoe){
		recordHourPppoeDao.save(recordHourPppoe);
	}
	
	@Override
	public void update(RecordHourPppoeEntity recordHourPppoe){
		recordHourPppoeDao.update(recordHourPppoe);
	}
	
	@Override
	public void delete(Integer id){
		recordHourPppoeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourPppoeDao.deleteBatch(ids);
	}
	
}
