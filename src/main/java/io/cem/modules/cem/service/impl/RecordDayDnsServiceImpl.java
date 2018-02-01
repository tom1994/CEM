package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordDnsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayDnsDao;
import io.cem.modules.cem.entity.RecordDayDnsEntity;
import io.cem.modules.cem.service.RecordDayDnsService;



@Service("recordDayDnsService")
public class RecordDayDnsServiceImpl implements RecordDayDnsService {
	@Autowired
	private RecordDayDnsDao recordDayDnsDao;
	@Autowired
	private RecordDnsDao recordDnsDao;
	
	@Override
	public RecordDayDnsEntity queryObject(Integer id){
		return recordDayDnsDao.queryObject(id);
	}

	@Override
	public List<RecordDayDnsEntity> queryDay(Map<String,Object> map) {return recordDnsDao.queryDay(map);}

	@Override
	public List<RecordDayDnsEntity> queryList(Map<String, Object> map){
		return recordDayDnsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayDnsDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayDnsEntity recordDayDns){
		recordDayDnsDao.save(recordDayDns);
	}
	
	@Override
	public void update(RecordDayDnsEntity recordDayDns){
		recordDayDnsDao.update(recordDayDns);
	}
	
	@Override
	public void delete(Integer id){
		recordDayDnsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayDnsDao.deleteBatch(ids);
	}
	
}
