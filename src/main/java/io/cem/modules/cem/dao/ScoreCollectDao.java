package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ScoreCollectEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ScoreCollectDao {
    List<ScoreCollectEntity> queryList(Map<String, Object> map);
    void save(ScoreCollectEntity sce);
    void deleteBatch(int[] ids);


}
