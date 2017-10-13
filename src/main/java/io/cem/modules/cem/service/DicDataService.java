package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.DicDataEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public interface DicDataService {
	
	DicDataEntity queryObject(Integer id);
	
	List<DicDataEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(DicDataEntity dicData);
	
	void update(DicDataEntity dicData);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
