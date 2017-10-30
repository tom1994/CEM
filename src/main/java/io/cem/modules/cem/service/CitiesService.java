package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.CitiesEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区域地州市信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-10-27 16:11:02
 */
public interface CitiesService {
	
	CitiesEntity queryObject(Integer id);
	
	List<CitiesEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CitiesEntity cities);
	
	void update(CitiesEntity cities);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
