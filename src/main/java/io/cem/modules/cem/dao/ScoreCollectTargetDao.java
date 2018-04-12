package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ScoreCollectTargetEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreCollectTargetDao {
    List<ScoreCollectTargetEntity> queryList(Map<String, Object> map);
    void save(ScoreCollectTargetEntity ste);
    void deleteBatch(int[] ids);
}
