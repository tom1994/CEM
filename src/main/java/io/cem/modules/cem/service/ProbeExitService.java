package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeExitEntity;

import java.util.List;
import java.util.Map;

/**
 * 端口-出口对照表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-03-26 19:47:28
 */
public interface ProbeExitService {
	
	ProbeExitEntity queryObject(Integer id);
	
	List<ProbeExitEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProbeExitEntity probeExit);
	
	void update(ProbeExitEntity probeExit);

	void operateStatus0(Integer id);

	void operateStatus1(Integer id);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
