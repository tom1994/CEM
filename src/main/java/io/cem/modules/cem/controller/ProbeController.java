package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.annotation.SysLog;
import io.cem.common.exception.RRException;
import io.cem.common.utils.*;
import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.service.ProbeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Miao
 * @date 2017-10-12 17:12:46
 */
@RestController
@RequestMapping("/cem/probe")
public class ProbeController {
    @Autowired
    private ProbeService probeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("probe:list")
    public R list(String probedata, Integer page, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<>();
        JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
        try {
            map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        int total = 0;
        if (page == null) {              /*没有传入page,则取全部值*/
            map.put("offset", null);
            map.put("limit", null);
            page = 0;
            limit = 0;
        } else {
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            total = probeService.queryTotal(map);
        }
//		List<ProbeEntity> probeList = probeService.queryList(map);
        List<ProbeEntity> probeList = probeService.queryProbeList(map);
        PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping("/listOnline/{id}")
    @RequiresPermissions("probe:list")
    public R listOnline(@PathVariable("id") Integer taskId) {
        List<ProbeEntity> probeList = probeService.queryOnlineList(taskId);
        return R.ok().put("probe", probeList);
    }

    @RequestMapping("/listCenter/{id}")
    @RequiresPermissions("probe:list")
    public R listCenter(@PathVariable("id") Integer taskId) {
        List<ProbeEntity> probeList = probeService.queryCenterList(taskId);
        return R.ok().put("probe", probeList);
    }

    //    @RequestMapping("/download")
//    @RequiresPermissions("probe:download")
//    public void downloadProbe(HttpServletResponse response) throws RRException {
//        Map<String, Object> map = new HashMap<String, Object>();
//        List<ProbeEntity> list = probeService.queryList(map);
//        CollectionToFile.collectionToFile(response, list, ProbeEntity.class);
//    }
    @RequestMapping("/download/{probedata}")
    @RequiresPermissions("probe:download")
    public void downloadProbe(HttpServletResponse response,@PathVariable String probedata) throws RRException {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonobject = JSONObject.parseObject(probedata);
        try {
            map.putAll(JSONUtils.jsonToMap(jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        List<ProbeEntity> probeList = probeService.queryProbeList(map);
        CollectionToFile.collectionToFile(response, probeList, ProbeEntity.class);
    }


    /**
     * 按区县信息搜索探针信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        List<ProbeEntity> probeList = probeService.queryProbe(id);
        System.out.println(probeList);
        return R.ok().put("probe", probeList);
    }

    /**
     * 按地市信息搜索探针信息
     */
    @RequestMapping("/infoByCity/{id}")
    public R infoByCity(@PathVariable("id") Integer id) {
        List<ProbeEntity> probeList = probeService.queryProbeByCity(id);
        return R.ok().put("probe", probeList);
    }

    /**
     * 按出口筛选探针
     */
    @RequestMapping("/exitlist")
    @RequiresPermissions("probe:exitlist")
    public R exitList(String probedata, Integer page, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<>();
        JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
        try {
            map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        int total = 0;
        if (page == null) {              /*没有传入page,则取全部值*/
            map.put("offset", null);
            map.put("limit", null);
            page = 0;
            limit = 0;
        } else {
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            total = probeService.queryTotal(map);
        }
//		List<ProbeEntity> probeList = probeService.queryList(map);
        List<ProbeEntity> probeList = probeService.queryExitList(map);
        PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }


    /**
     * 详细信息
     */
    @RequestMapping("/detail/{id}")
    @RequiresPermissions("probe:detail")
    public R detail(@PathVariable("id") Integer id) {
//		ProbeEntity probeList = probeService.queryDetail(id);
        ProbeEntity probeList = probeService.queryObject(id);
        System.out.println(probeList);
        return R.ok().put("probe", probeList);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("probe:save")
    public R save(@RequestBody ProbeEntity probe) {
        if (probeService.queryExist(probe.getLayerName()) > 0) {
            return R.error(300, "探针名称已存在，请重新输入");
        } else {
            probeService.save(probe);
            return R.ok();
        }
    }

    /**
     * 修改
     */
    @SysLog("修改探针信息")
    @RequestMapping("/update")
    @RequiresPermissions("probe:update")
    public R update(@RequestBody ProbeEntity probe) {
        if (probeService.queryExist(probe.getName()) > 0) {
            return R.error(300, "探针名称已存在，请重新输入");
        } else {
            probeService.update(probe);
            return R.ok();
        }
    }

    /**
     * 删除
     */
    @SysLog("删除探针")
    @RequestMapping("/delete")
    @RequiresPermissions("probe:delete")
    public R delete(@RequestBody Integer[] ids) {
//		probeService.deleteBatch(ids);
//		for(int id : ids){
//			probeService.updateUpstream(id);
//		}

        int result;
        for (int id : ids) {
            result = BypassHttps.sendRequestIgnoreSSL("DELETE", "https://114.236.91.16:23456/web/v1/probes/" + id + "/unregister/1");
            if (result == 200 || result == 206) {
            } else {
                return R.error(404, "删除探针失败");
            }
        }
        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/port/{id}")
    public R port(@PathVariable("id") Integer id) {
        List<ProbeEntity> portList = probeService.queryPortList(id);
        System.out.println(portList);
        return R.ok().put("port", portList);
    }

}
