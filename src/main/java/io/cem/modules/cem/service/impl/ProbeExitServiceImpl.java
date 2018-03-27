package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.ProbeExitDao;
import io.cem.modules.cem.entity.ProbeExitEntity;
import io.cem.modules.cem.service.ProbeExitService;



@Service("probeExitService")
public class ProbeExitServiceImpl implements ProbeExitService {
	@Autowired
	private ProbeExitDao probeExitDao;
	
	@Override
	public ProbeExitEntity queryObject(Integer id){
		return probeExitDao.queryObject(id);
	}
	
	@Override
	public List<ProbeExitEntity> queryList(Map<String, Object> map){
		return probeExitDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return probeExitDao.queryTotal(map);
	}
	
	@Override
	public void save(ProbeExitEntity probeExit){
		probeExitDao.save(probeExit);
	}
	
	@Override
	public void update(ProbeExitEntity probeExit){
		probeExitDao.update(probeExit);
	}
	
	@Override
	public void delete(Integer id){
		probeExitDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		probeExitDao.deleteBatch(ids);
	}
	
}
