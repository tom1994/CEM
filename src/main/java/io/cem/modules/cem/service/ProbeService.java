package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Miao
 */
public interface ProbeService {
	
	ProbeEntity queryObject(Integer id);
	
	List<ProbeEntity> queryList(Map<String, Object> map);

	List<ProbeEntity> queryProbeList(Map<String, Object> map);

	List<ProbeEntity> queryProbe(Integer id);

	List<ProbeEntity> queryProbeByLayer(Integer id);

	List<ProbeEntity> queryProbeByCity(Integer id);

	List<ProbeEntity> queryProbeListByGroup(Integer id);

	List<ProbeEntity> queryExitList(Map<String, Object> map);

	List<ProbeEntity> queryPortList(Integer id);

	ProbeEntity queryDetail(Integer id);

	int queryTotal(Map<String, Object> map);
	
	void save(ProbeEntity probe);
	
	void update(ProbeEntity probe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	void updateUpstream(Integer id);
}
