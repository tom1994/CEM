package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.AlarmTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public interface AlarmTemplateService {
	
	AlarmTemplateEntity queryObject(Integer id);
	
	List<AlarmTemplateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AlarmTemplateEntity alarmTemplate);
	
	void update(AlarmTemplateEntity alarmTemplate);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
