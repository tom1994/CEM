package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDayDhcpDao;
import io.cem.modules.cem.entity.RecordDayDhcpEntity;
import io.cem.modules.cem.service.RecordDayDhcpService;



@Service("recordDayDhcpService")
public class RecordDayDhcpServiceImpl implements RecordDayDhcpService {
	@Autowired
	private RecordDayDhcpDao recordDayDhcpDao;
	
	@Override
	public RecordDayDhcpEntity queryObject(Integer id){
		return recordDayDhcpDao.queryObject(id);
	}
	
	@Override
	public List<RecordDayDhcpEntity> queryList(Map<String, Object> map){
		return recordDayDhcpDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDayDhcpDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDayDhcpEntity recordDayDhcp){
		recordDayDhcpDao.save(recordDayDhcp);
	}
	
	@Override
	public void update(RecordDayDhcpEntity recordDayDhcp){
		recordDayDhcpDao.update(recordDayDhcp);
	}
	
	@Override
	public void delete(Integer id){
		recordDayDhcpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDayDhcpDao.deleteBatch(ids);
	}
	
}
