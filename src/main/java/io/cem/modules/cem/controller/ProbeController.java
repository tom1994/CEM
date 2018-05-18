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
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


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
     *探针列表
     * @param probedata
     * @param page
     * @param limit
     * @return R
     * @throws Exception
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
        List<ProbeEntity> probeList = probeService.queryProbeList(map);
        PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }


    /**
     * 显示所有探针
     * @param probedata
     * @return R
     * @throws Exception
     */
    @RequestMapping("/showlist")
    public R showList(String probedata) throws Exception {
        List<ProbeEntity> probeList = probeService.queryShowList();
        return R.ok().put("probe", probeList);
    }

    /**
     * 查看在线探针
     * @param taskId
     * @return R
     */
    @RequestMapping("/listOnline/{id}")
    @RequiresPermissions("probe:list")
    public R listOnline(@PathVariable("id") Integer taskId) {
        List<ProbeEntity> probeList = probeService.queryOnlineList(taskId);
        return R.ok().put("probe", probeList);
    }

    /**
     * 查看核心探针
     * @param taskId
     * @return R
     */
    @RequestMapping("/listCenter/{id}")
    @RequiresPermissions("probe:list")
    public R listCenter(@PathVariable("id") Integer taskId) {
        List<ProbeEntity> probeList = probeService.queryCenterList(taskId);
        return R.ok().put("probe", probeList);
    }

    /**
     * 下载探针表格
     * @param response
     * @param probedata
     * @throws RRException
     */
    @RequestMapping("/download/{probedata}")
    @RequiresPermissions("probe:download")
    public void downloadProbe(HttpServletResponse response, @PathVariable String probedata) throws RRException {
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
     * @param id
     * @return R
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        List<ProbeEntity> probeList = probeService.queryProbe(id);
        System.out.println(probeList);
        return R.ok().put("probe", probeList);
    }

    /**
     * 按地市信息搜索探针信息
     * @param id
     * @return R
     */
    @RequestMapping("/infoByCity/{id}")
    public R infoByCity(@PathVariable("id") Integer id) {
        List<ProbeEntity> probeList = probeService.queryProbeByCity(id);
        return R.ok().put("probe", probeList);
    }

    /**
     * 按出口筛选探针
     * @param probedata
     * @param page
     * @param limit
     * @return R
     * @throws Exception
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
        List<ProbeEntity> probeList = probeService.queryExitList(map);
        PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }


    /**
     * 详细信息
     * @param id
     * @return R
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        ProbeEntity probeList = probeService.queryObject(id);
        System.out.println(probeList);
        return R.ok().put("probe", probeList);
    }


    /**
     * 保存
     * @param probe
     * @return R
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
     * @param probe
     * @return R
     */
    @SysLog("修改探针信息")
    @RequestMapping("/update")
    @RequiresPermissions("probe:update")
    public R update(@RequestBody ProbeEntity probe) {
        if (probeService.queryExist(probe.getName(), probe.getId()) > 0) {
            return R.error(300, "探针名称已存在，请重新输入");
        } else {
            probeService.update(probe);
            return R.ok();
        }
    }

    /**
     * 重启探针
     * @param ids
     * @return R
     */
    @SysLog("重启探针")
    @RequestMapping("/reboot")
    @RequiresPermissions("probe:reboot")
    public R reboot(@RequestBody Integer[] ids) {
        int result;
        try{
            Properties prop = new Properties();
                InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
                prop.load(in);
        for (int id : ids) {
            result = BypassHttps.sendRequestIgnoreSSL("GET", prop.getProperty("socketAddress") +"/probes/" + id + "/restart");
            if (result == 200 || result == 206) {
            } else {
                return R.error(404, "id为"+id+"的探针重启失败，请联系管理员");
            }
        }}catch (Exception e){
            return R.error(500, "探针重启失败，未知错误");
        }
        return R.ok("探针重启成功！");
    }

    /**
     * 删除
     * @param ids
     * @return R
     */
    @SysLog("删除探针")
    @RequestMapping("/delete")
    @RequiresPermissions("probe:delete")
    public R delete(@RequestBody Integer[] ids) {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
            prop.load(in);
        }catch (Exception e){
            throw new RRException("配置文件配置有误，请重新配置");
        }
        int result;
        for (int id : ids) {
            result = BypassHttps.sendRequestIgnoreSSL("DELETE", prop.getProperty("socketAddress") +"/probes/" + id + "/unregister/1");
            if (result == 200) {
            } else {
                return R.error(404, "删除探针失败");
            }
        }
        return R.ok();
    }


    /**
     * 端口信息
     * @param id
     * @return R
     */
    @RequestMapping("/port/{id}")
    public R port(@PathVariable("id") Integer id) {
        List<ProbeEntity> portList = probeService.queryPortList(id);
        System.out.println(portList);
        return R.ok().put("port", portList);
    }

}
