package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.CityEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区域地州市信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:56:26
 */
public interface CityService {
	
	CityEntity queryObject(Integer id);
	
	List<CityEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CityEntity city);
	
	void update(CityEntity city);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
