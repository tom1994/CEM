package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordFailEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

public interface RecordFailDao extends BaseDao<RecordFailEntity> {
    List<RecordFailEntity> queryFail1(Map<String, Object> map);
    List<RecordFailEntity> queryFail2(Map<String, Object> map);
    List<RecordFailEntity> queryFail3(Map<String, Object> map);
    List<RecordFailEntity> queryFail4(Map<String, Object> map);
}
