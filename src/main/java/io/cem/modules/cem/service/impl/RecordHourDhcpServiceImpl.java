package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordDhcpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourDhcpDao;
import io.cem.modules.cem.entity.RecordHourDhcpEntity;
import io.cem.modules.cem.service.RecordHourDhcpService;



@Service("recordHourDhcpService")
public class RecordHourDhcpServiceImpl implements RecordHourDhcpService {
	@Autowired
	private RecordHourDhcpDao recordHourDhcpDao;
	@Autowired
	private RecordDhcpDao recordDhcpDao;
	
	@Override
	public RecordHourDhcpEntity queryObject(Integer id){
		return recordHourDhcpDao.queryObject(id);
	}

	@Override
	public List<RecordHourDhcpEntity> queryDhcp(Map<String, Object> map){
		return recordDhcpDao.queryDhcp(map);
	}

	@Override
	public List<RecordHourDhcpEntity> queryList(Map<String, Object> map){
		return recordHourDhcpDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryDhcpList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourDhcpDao.queryDhcpList(map));
	}

	@Override
	public List<RecordHourDhcpEntity> queryExitList(Map<String, Object> map){
		return recordHourDhcpDao.queryExitList(map);
	}

	@Override
	public List<RecordHourDhcpEntity> queryDayList(Map<String, Object> map){
		return recordHourDhcpDao.queryDayList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourDhcpDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourDhcpEntity recordHourDhcp){
		recordHourDhcpDao.save(recordHourDhcp);
	}
	
	@Override
	public void update(RecordHourDhcpEntity recordHourDhcp){
		recordHourDhcpDao.update(recordHourDhcp);
	}
	
	@Override
	public void delete(Integer id){
		recordHourDhcpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourDhcpDao.deleteBatch(ids);
	}
	
}
