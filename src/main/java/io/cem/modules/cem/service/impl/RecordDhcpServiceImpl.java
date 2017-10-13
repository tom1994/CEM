package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDhcpDao;
import io.cem.modules.cem.entity.RecordDhcpEntity;
import io.cem.modules.cem.service.RecordDhcpService;



@Service("recordDhcpService")
public class RecordDhcpServiceImpl implements RecordDhcpService {
	@Autowired
	private RecordDhcpDao recordDhcpDao;
	
	@Override
	public RecordDhcpEntity queryObject(Integer id){
		return recordDhcpDao.queryObject(id);
	}
	
	@Override
	public List<RecordDhcpEntity> queryList(Map<String, Object> map){
		return recordDhcpDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordDhcpDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordDhcpEntity recordDhcp){
		recordDhcpDao.save(recordDhcp);
	}
	
	@Override
	public void update(RecordDhcpEntity recordDhcp){
		recordDhcpDao.update(recordDhcp);
	}
	
	@Override
	public void delete(Integer id){
		recordDhcpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordDhcpDao.deleteBatch(ids);
	}
	
}
