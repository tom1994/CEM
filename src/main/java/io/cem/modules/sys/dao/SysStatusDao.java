package io.cem.modules.sys.dao;

import io.cem.modules.sys.entity.SysStatusEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public interface SysStatusDao {
    SysStatusEntity queryObject(Map<String, Object> params);
}
