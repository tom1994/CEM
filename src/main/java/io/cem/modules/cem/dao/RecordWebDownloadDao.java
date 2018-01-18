package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordWebDownloadEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordWebDownloadDao extends BaseDao<RecordWebDownloadEntity> {
    List<RecordWebDownloadEntity> queryWebDownloadTest(Map<String, Object> map);
}
