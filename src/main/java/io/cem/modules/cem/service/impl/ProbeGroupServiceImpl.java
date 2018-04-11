package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.ProbeGroupDao;
import io.cem.modules.cem.entity.ProbeGroupEntity;
import io.cem.modules.cem.service.ProbeGroupService;



@Service("probeGroupService")
public class ProbeGroupServiceImpl implements ProbeGroupService {
	@Autowired
	private ProbeGroupDao probeGroupDao;
	
	@Override
	public ProbeGroupEntity queryObject(Integer id){
		return probeGroupDao.queryObject(id);
	}
	
	@Override
	public List<ProbeGroupEntity> queryList(Map<String, Object> map){
		return probeGroupDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return probeGroupDao.queryTotal(map);
	}
	
	@Override
	public void save(ProbeGroupEntity probeGroup){
		probeGroupDao.save(probeGroup);
	}
	
	@Override
	public void update(ProbeGroupEntity probeGroup){
		probeGroupDao.update(probeGroup);
	}
	
	@Override
	public void delete(Integer id){
		probeGroupDao.delete(id);
	}

	@Override
	public int queryExist(String pgName){
		return probeGroupDao.queryExist(pgName);
	}


	@Override
	public void deleteBatch(Integer[] ids){
		probeGroupDao.deleteBatch(ids);
	}
	
}
