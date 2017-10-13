package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.DicTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public interface DicTypeService {
	
	DicTypeEntity queryObject(Integer id);
	
	List<DicTypeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(DicTypeEntity dicType);
	
	void update(DicTypeEntity dicType);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
