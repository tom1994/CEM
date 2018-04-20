package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TargetEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface TargetService {
	
	TargetEntity queryObject(Integer id);

	List<TargetEntity> infoBatch(Integer serviceId);
	
	List<TargetEntity> queryList(Map<String, Object> map);

	List<TargetEntity> queryTgByTList(Map<String, Object> map);

	List<TargetEntity> queryTargetList(Integer spId);

	List<TargetEntity> queryTargetListByGroup(Integer id);
	
	int queryTotal(Map<String, Object> map);

	int queryExist(String targetName);

	int queryUpdate(String targetName,int id);
	
	void save(TargetEntity target);
	
	void update(TargetEntity target);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	List<TargetEntity> queryTargetNames(int[] ids);
}
