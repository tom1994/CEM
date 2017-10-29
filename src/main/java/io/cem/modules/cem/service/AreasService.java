package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.AreasEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区域县区信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-10-27 16:11:01
 */
public interface AreasService {
	
	AreasEntity queryObject(Integer id);
	
	List<AreasEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AreasEntity areas);
	
	void update(AreasEntity areas);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
