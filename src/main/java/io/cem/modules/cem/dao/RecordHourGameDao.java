package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourGameEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-13 17:02:51
 */
public interface RecordHourGameDao extends BaseDao<RecordHourGameEntity> {
    List<RecordHourGameEntity> queryGameList(Map<String, Object> map);
}
