package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.LayerDao;
import io.cem.modules.cem.entity.LayerEntity;
import io.cem.modules.cem.service.LayerService;



@Service("layerService")
public class LayerServiceImpl implements LayerService {
	@Autowired
	private LayerDao layerDao;
	
	@Override
	public LayerEntity queryObject(Integer id){
		return layerDao.queryObject(id);
	}
	
	@Override
	public List<LayerEntity> queryList(Map<String, Object> map){
		return layerDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return layerDao.queryTotal(map);
	}

	@Override
	public LayerEntity queryLowLayer(Integer layerTag){
		return layerDao.queryLowLayer(layerTag);
	}

	@Override
	public int queryExist(String layerName){
		return layerDao.queryExist(layerName);
	}

	@Override
	public void save(LayerEntity layer){
		layerDao.save(layer);
	}
	
	@Override
	public void update(LayerEntity layer){
		layerDao.update(layer);
	}
	
	@Override
	public void delete(Integer id){
		layerDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		layerDao.deleteBatch(ids);
	}
	
}
