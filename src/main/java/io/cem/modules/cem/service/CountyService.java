package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.CountyEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区域县区信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:56:26
 */
public interface CountyService {
	
	CountyEntity queryObject(Integer id);
	
	List<CountyEntity> queryList(Map<String, Object> map);

	List<CountyEntity> queryByProbe(Integer id);

	List<CountyEntity> queryCountyList(Integer id);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CountyEntity county);
	
	void update(CountyEntity county);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
