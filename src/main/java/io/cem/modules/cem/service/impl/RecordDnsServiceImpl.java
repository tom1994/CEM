package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordHourDnsDao;
import io.cem.modules.cem.entity.RecordHourDnsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDnsDao;
import io.cem.modules.cem.entity.RecordDnsEntity;
import io.cem.modules.cem.service.RecordDnsService;



@Service("recordDnsService")
public class RecordDnsServiceImpl implements RecordDnsService {
	@Autowired
	private RecordDnsDao recordDnsDao;

	@Autowired
	private RecordHourDnsDao recordHourDnsDao;

	@Override
	public RecordDnsEntity queryObject(Integer id){
		return recordDnsDao.queryObject(id);
	}
	
	@Override
	public List<RecordDnsEntity> queryList(Map<String, Object> map){
		return recordDnsDao.queryList(map);
	}

	@Override
	public List<RecordDnsEntity> queryDnsList(Map<String, Object> map) {
		return recordDnsDao.queryDnsList(map);
	}

	@Override
	public List<RecordHourDnsEntity> queryIntervalList(Map<String, Object> map) {
		return recordHourDnsDao.queryIntervalList(map);
	}

	@Override
	public int queryIntervalTotal(Map<String, Object> map) {
		return recordHourDnsDao.queryIntervalTotal(map);
	}


	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDnsDao.queryTotal(map);
	}

	@Override
	public List<RecordDnsEntity> queryDnsTest(Map<String, Object> map){
		return recordDnsDao.queryDnsTest(map);
	}

	@Override
	public void save(RecordDnsEntity recordDns){
		recordDnsDao.save(recordDns);
	}
	
	@Override
	public void update(RecordDnsEntity recordDns){
		recordDnsDao.update(recordDns);
	}
	
	@Override
	public void delete(Integer id){
		recordDnsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDnsDao.deleteBatch(ids);
	}
	
}
