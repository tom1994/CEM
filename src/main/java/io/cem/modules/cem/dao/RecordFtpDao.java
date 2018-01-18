package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordFtpEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordFtpDao extends BaseDao<RecordFtpEntity> {
    List<RecordFtpEntity> queryFtpTest(Map<String, Object> map);
}
