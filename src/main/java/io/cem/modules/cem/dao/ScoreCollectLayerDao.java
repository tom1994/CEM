package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ScoreCollectLayerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface ScoreCollectLayerDao {
    List<ScoreCollectLayerEntity> queryList(Map<String,Object> p);
    void save(ScoreCollectLayerEntity s);
    void del(Map<String,Object> p);
    void delAll();
}
