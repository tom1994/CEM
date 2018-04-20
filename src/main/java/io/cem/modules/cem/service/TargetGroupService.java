package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TargetGroupEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface TargetGroupService {
	
	TargetGroupEntity queryObject(Integer id);
	
	List<TargetGroupEntity> queryList(Map<String, Object> map);

	List<TargetGroupEntity> queryByTgNameList(Map<String, Object> map);

	List<TargetGroupEntity> queryTGList(Integer id);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TargetGroupEntity targetGroup);
	
	void update(TargetGroupEntity targetGroup);

	int queryExist(String tgName);

	int queryUpdate(String tgName,int id);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
