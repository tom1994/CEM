package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.ProbeDao;
import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.service.ProbeService;



@Service("probeService")
public class ProbeServiceImpl implements ProbeService {
	@Autowired
	private ProbeDao probeDao;
	
	@Override
	public ProbeEntity queryObject(Integer id){
		return probeDao.queryObject(id);
	}
	
	@Override
	public List<ProbeEntity> queryList(Map<String, Object> map){
		return probeDao.queryList(map);
	}

	@Override
	public List<ProbeEntity> queryProbeList(Map<String, Object> map){
		return probeDao.queryProbeList(map);
	}

	@Override
	public List<ProbeEntity> queryProbe(Integer id){
		return probeDao.queryProbe(id);
	}


	@Override
	public int queryTotal(Map<String, Object> map){
		return probeDao.queryTotal(map);
	}
	
	@Override
	public void save(ProbeEntity probe){
		probeDao.save(probe);
	}
	
	@Override
	public void update(ProbeEntity probe){
		probeDao.update(probe);
	}
	
	@Override
	public void delete(Integer id){
		probeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		probeDao.deleteBatch(ids);
	}
	
}
