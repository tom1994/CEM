package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public interface ProbeService {
	
	ProbeEntity queryObject(Integer id);
	
	List<ProbeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProbeEntity probe);
	
	void update(ProbeEntity probe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
