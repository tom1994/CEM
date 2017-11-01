package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.entity.TaskProbeRelEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author Miao
 */
public interface ProbeDao extends BaseDao<ProbeEntity> {
    List<ProbeEntity> queryProbeList(Map<String,Object> map);
}
