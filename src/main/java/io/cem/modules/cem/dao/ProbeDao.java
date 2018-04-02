package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author Miao
 */
public interface ProbeDao extends BaseDao<ProbeEntity> {
    List<ProbeEntity> queryProbeList(Map<String,Object> map);
    List<ProbeEntity> queryExitList(Map<String,Object> map);
    List<ProbeEntity> queryProbe(Integer id);
    List<ProbeEntity> queryPortList(Integer id);
    List<ProbeEntity> queryProbeByCity(Integer id);
    ProbeEntity queryDetail(Integer id);
    ProbeEntity queryProbeByLayer(Integer id);
    List<ProbeEntity> queryProbeListByGroup(Integer id);
    List<ProbeEntity> queryOnlineList(Integer id);
    List<ProbeEntity> queryCenterList(Integer id);
    void updateUpstream(Integer id);
}
