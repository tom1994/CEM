package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordFailEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.Map;

public interface RecordFailDao extends BaseDao<RecordFailEntity> {
    RecordFailEntity queryFail(Map<String, Object> map);
}
