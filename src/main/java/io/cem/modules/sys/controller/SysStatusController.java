package io.cem.modules.sys.controller;

import io.cem.common.utils.R;
import io.cem.modules.sys.dao.SysStatusDao;
import io.cem.modules.sys.entity.SysStatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys/status")
public class SysStatusController extends AbstractController{
    @Autowired
    private SysStatusDao sysStatusDao;
    @RequestMapping("/show")
    public R show(Integer id){
        Map<String ,Object> params = new LinkedHashMap<String ,Object>();
        params.put("id",id);
        SysStatusEntity info = sysStatusDao.queryObject(params);
        logger.info("---------------------------"+info);
        return R.ok().put("sysinfo", info);
    }

}
