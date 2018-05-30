package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cem.common.annotation.SysLog;
import io.cem.common.exception.RRException;
import io.cem.common.utils.*;
import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.entity.TargetEntity;
import io.cem.modules.cem.entity.TaskDispatchEntity;
import io.cem.modules.cem.service.ProbeService;
import io.cem.modules.cem.service.TargetService;
import io.cem.modules.cem.service.TaskDispatchService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;


/**
 */
@RestController
@RequestMapping("/cem/taskdispatch")
public class TaskDispatchController {
    @Autowired
    private TaskDispatchService taskDispatchService;

    @Autowired
    private ProbeService probeService;

    @Autowired
    private TargetService targetService;

    /**
     * 下发任务列表
     *
     * @param params
     * @return R
     */
    @RequestMapping("/list")
    @RequiresPermissions("taskdispatch:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<TaskDispatchEntity> taskDispatchList = taskDispatchService.queryList(query);
        int total = taskDispatchService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(taskDispatchList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * tooltip显示任务
     *
     * @param id
     * @param page
     * @param limit
     * @return R
     * @throws Exception
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("taskdispatch:info")
    public R info(@PathVariable("id") Integer id, Integer page, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("taskid", id);
        int total = 0;
        if (page == null) {              /*没有传入page,则取全部值*/
            map.put("offset", null);
            map.put("limit", null);
            page = 0;
            limit = 0;
        } else {
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            total = taskDispatchService.queryDispatchTotal(id);
        }
        List<TaskDispatchEntity> dispatchList = taskDispatchService.queryDispatchList(map);
        dispatchList = taskDispatchService.transformTarget(dispatchList);
        PageUtils pageUtil = new PageUtils(dispatchList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }

    /**
     * 探针显示任务
     *
     * @param id
     * @param page
     * @param limit
     * @return R
     * @throws Exception
     */
    @RequestMapping("/infoTask/{id}")
    @RequiresPermissions("taskdispatch:infoTask")
    public R task(@PathVariable("id") Integer id, Integer page, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("probeid", id);
        int total = 0;
        if (page == null) {              /*没有传入page,则取全部值*/
            map.put("offset", null);
            map.put("limit", null);
            page = 0;
            limit = 0;
        } else {
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            total = taskDispatchService.taskQueryDispatchTotal(map);
        }

        List<TaskDispatchEntity> dispatchList = taskDispatchService.taskQueryDispatchList(map);
        dispatchList = taskDispatchService.transformTarget(dispatchList);
        PageUtils pageUtil = new PageUtils(dispatchList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }

    /**
     * 下发任务
     *
     * @param taskDispatch
     * @return R
     */
    @SysLog("下发任务")
    @RequestMapping("/save")
    @RequiresPermissions("taskdispatch:save")
    public R save(@RequestBody TaskDispatchEntity taskDispatch) {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
            prop.load(in);
        } catch (Exception e) {
            throw new RRException("配置文件配置有误，请重新配置");
        }
        if (taskDispatch.getProbeGroupId() != null) {
            List<TaskDispatchEntity> taskDispatchEntityList = new ArrayList<>();
            List<ProbeEntity> probes = probeService.queryProbeListByGroup(taskDispatch.getProbeGroupId());
            for (ProbeEntity probe : probes) {
                TaskDispatchEntity taskDispatchEntity = CloneUtils.clone(taskDispatch);
                taskDispatchEntity.setProbeId(probe.getId());
                taskDispatchEntityList.add(taskDispatchEntity);
            }
            taskDispatchService.saveAll(taskDispatchEntityList);
        } else {
            taskDispatchService.save(taskDispatch);
        }
        BypassHttps.sendRequestIgnoreSSL("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + taskDispatch.getTaskId());

        return R.ok();
    }

    /**
     * 下发实时诊断任务
     *
     * @param param
     * @return R
     */
    @RequestMapping("/saveAndReturn")
    @RequiresPermissions("taskdispatch:save")
    public R saveAndReturn(@RequestBody String param) {
        Map<String, Object> map = new HashMap<>();
        JSONObject probedata_jsonobject = JSONObject.parseObject(param);
        try {
            map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
            prop.load(in);
        } catch (Exception e) {
            throw new RRException("配置文件配置有误，请重新配置");
        }
        int probeId = Integer.parseInt(map.get("probeId").toString());
        List<ProbeEntity> probeList = probeService.queryProbeByLayer(probeId);
        JSONObject targetjson = new JSONObject();
        targetjson.put("target_port", "");
        targetjson.put("target_type", 1);
        TargetEntity targetEntity = targetService.queryObject(Integer.parseInt(map.get("target").toString()));
        targetjson.put("target_value", targetEntity.getValue());
        targetjson.put("target_id", map.get("target"));
        //定义用于存储的任务Entity
        TaskDispatchEntity taskDispatch = new TaskDispatchEntity();
        taskDispatch.setIsOndemand(1);
        taskDispatch.setStatus(0);
        taskDispatch.setTarget("[" + JSON.toJSONString(targetjson) + "]");
        int size = probeList.size();
        int ping[][] = new int[5][size];
        int sla[][] = new int[6][size];
        int web[] = new int[size];
        int download[][] = new int[3][size];
        int video[] = new int[size];
        int game[] = new int[size];
        JSONObject dispatch = new JSONObject();
        for (int i = 0; i < size; i++) {
            taskDispatch.setProbeId(probeList.get(i).getId());
            if (probeList.get(i).getType() == 10) {
                taskDispatch.setProbePort("port2");
            } else {
                taskDispatch.setProbePort("port1");
            }
            if (map.containsKey("ping")) {
                taskDispatch.setTestNumber(1);
                taskDispatch.setTestInterval(10);
                for (int a = 0; a < 5; a++) {
                    taskDispatch.setTaskId(a + 1);
                    taskDispatchService.saveAndReturn(taskDispatch);
                    ping[a][i] = taskDispatch.getId();
                }
                dispatch.put("ping", ping);
            }
            if (map.containsKey("sla")) {
                taskDispatch.setTestNumber(1);
                taskDispatch.setTestInterval(10);
                taskDispatch.setTaskId(2);
                for (int b = 0; b < 6; b++) {
                    taskDispatch.setTaskId(b + 10);
                    taskDispatchService.saveAndReturn(taskDispatch);
                    sla[b][i] = taskDispatch.getId();
                }
                dispatch.put("sla", sla);
            }
            if (map.containsKey("web")) {
                taskDispatch.setTestNumber(1);
                taskDispatch.setTestInterval(10);
                taskDispatch.setTaskId(20);
                taskDispatchService.saveAndReturn(taskDispatch);
                web[i] = taskDispatch.getId();
                dispatch.put("web", web);
            }
            if (map.containsKey("download")) {
                taskDispatch.setTestNumber(1);
                taskDispatch.setTestInterval(10);
                for (int d = 0; d < 3; d++) {
                    taskDispatch.setTaskId(d + 30);
                    taskDispatchService.saveAndReturn(taskDispatch);
                    download[d][i] = taskDispatch.getId();
                }
                dispatch.put("download", download);
            }
            if (map.containsKey("video")) {
                taskDispatch.setTestNumber(1);
                taskDispatch.setTestInterval(10);
                taskDispatch.setTaskId(40);
                taskDispatchService.saveAndReturn(taskDispatch);
                video[i] = taskDispatch.getId();
                dispatch.put("video", video);
            }
            if (map.containsKey("game")) {
                taskDispatch.setTestNumber(1);
                taskDispatch.setTestInterval(10);
                taskDispatch.setTaskId(50);
                taskDispatchService.saveAndReturn(taskDispatch);
                game[i] = taskDispatch.getId();
                dispatch.put("game", game);
            }
        }
        /*调用接口通知探针*/
        if (map.containsKey("ping")) {
            for (int i = 1; i < 6; i++) {
                BypassHttps.sendRequestIgnoreSSL("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + i);
            }
        }
        if (map.containsKey("sla")) {
            for (int i = 10; i < 16; i++) {
                BypassHttps.sendRequestIgnoreSSL("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + i);
            }
        }
        if (map.containsKey("web")) {
            BypassHttps.sendRequestIgnoreSSL("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + 20);
        }
        if (map.containsKey("download")) {
            for (int i = 30; i < 33; i++) {
                BypassHttps.sendRequestIgnoreSSL("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + i);
            }
        }
        if (map.containsKey("video")) {
            int videoState = BypassHttps.sendRequestIgnoreSSL("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + 40);
        }
        if (map.containsKey("game")) {
            int gameState = BypassHttps.sendRequestIgnoreSSL("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + 50);
        }

        return R.ok().put("taskdispatch", dispatch);
    }

    /**
     * 下发任务
     *
     * @param taskDispatch
     * @return
     */
    @SysLog("下发任务")
    @RequestMapping("/saveAll")
    @RequiresPermissions("taskdispatch:save")
    public R saveAll(@RequestBody TaskDispatchEntity taskDispatch) {
        taskDispatch.setCreateTime(new Date());
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
            prop.load(in);
        } catch (Exception e) {
            throw new RRException("配置文件配置有误，请重新配置");
        }
        if (taskDispatch.getTargetGroupIds() != null) {
            int[] targetGroupIds = taskDispatch.getTargetGroupIds();
            ArrayList target = new ArrayList();
            JSONObject targetjson = new JSONObject();
            targetjson.put("target_port", "");
            targetjson.put("target_type", 1);
            for (int i = 0; i < targetGroupIds.length; i++) {
                List<TargetEntity> targetEntities = targetService.queryTargetListByGroup(targetGroupIds[i]);
                for (int j = 0; j < targetEntities.size(); j++) {
                    JSONObject targetObject = CloneUtils.clone(targetjson);
                    targetObject.put("target_id", targetEntities.get(j).getId());
                    targetObject.put("target_value", targetEntities.get(j).getValue());
                    target.add(JSON.toJSONString(targetObject));
                }
            }
            taskDispatch.setTarget(target.toString());
        } else {
            ArrayList target = new ArrayList();
            int[] targetIds = taskDispatch.getTargetIds();
            JSONObject targetjson = new JSONObject();
            targetjson.put("target_port", "");
            targetjson.put("target_type", 1);
            for (int targetId : targetIds) {
                JSONObject targetObject = CloneUtils.clone(targetjson);
                TargetEntity targetEntity = targetService.queryObject(targetId);
                targetObject.put("target_id", targetEntity.getId());
                targetObject.put("target_value", targetEntity.getValue());
                target.add(JSON.toJSON(targetObject));
            }
            taskDispatch.setTarget(target.toString());
        }
        if (taskDispatch.getProbeIds() == null && taskDispatch.getProbeGroupIds() != null) {
            int[] probeGroupIds = taskDispatch.getProbeGroupIds();
            List<TaskDispatchEntity> taskDispatchEntityList = new ArrayList<>();
            for (int probeGroupId : probeGroupIds) {
                List<ProbeEntity> probes = probeService.queryProbeListByGroup(probeGroupId);
                for (ProbeEntity probe : probes) {
                    TaskDispatchEntity taskDispatchEntity = CloneUtils.clone(taskDispatch);
                    taskDispatchEntity.setProbeId(probe.getId());
                    taskDispatchEntityList.add(taskDispatchEntity);
                }
            }
            taskDispatchService.saveAll(taskDispatchEntityList);
        } else if (taskDispatch.getProbeIds() != null && taskDispatch.getProbeGroupIds() == null) {
            int[] probeIdsList = taskDispatch.getProbeIds();
            List<TaskDispatchEntity> taskDispatchEntityList = new ArrayList<>();
            for (int j = 0; j < probeIdsList.length; j++) {
                TaskDispatchEntity taskDispatchEntity = CloneUtils.clone(taskDispatch);
                taskDispatchEntity.setProbeId(probeIdsList[j]);
                taskDispatchEntityList.add(taskDispatchEntity);
            }
            taskDispatchService.saveAll(taskDispatchEntityList);
        } else {
            return R.error("探针或探针组格式错误");
        }
/*        try {
            int result = BypassHttps.sendRequestIgnoreSSL("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + taskDispatch.getTaskId());
            if (result == 200) {
                return R.ok();
            } else if (result == 401) {
                taskDispatchService.cancelSave(taskDispatch.getTaskId());
                return R.error(404, "token失效，系统已重新获取，请重试");
            } else {
                taskDispatchService.cancelSave(taskDispatch.getTaskId());
                return R.error(404, "任务下发失败");
            }
        } catch (Exception e) {
            return R.error("网络故障，下发失败");
        }*/
        try {
            String result = BypassHttps.taskDispatch("POST", prop.getProperty("socketAddress") + "/web/v1/tasks/" + taskDispatch.getTaskId());
            if (result == null || result.equals("")) {
                return R.error(404, "任务下发失败");
            }
            JSONObject jsonobject = JSONObject.parseObject(result);
            int code = Integer.parseInt(jsonobject.get("code").toString());
            if (code == 200) {
                return R.ok();
            } else if (code == 401) {
                taskDispatchService.cancelSave(taskDispatch.getTaskId());
                return R.error(404, "token失效，系统已重新获取，请重试");
            } else if (code == 206) {
                taskDispatchService.cancelSave(taskDispatch.getTaskId());
                String msg = jsonobject.get("msg").toString();
                String[] failIds = msg.substring(1, msg.length() - 1).split(",");
                StringBuilder stringBuilder = new StringBuilder();
                for (String id : failIds) {
                    stringBuilder.append(probeService.queryObject(Integer.parseInt(id)).getName()).append(",");
                }
                String probeName = stringBuilder.toString();
                return R.error(404, probeName.substring(0,probeName.length()-1)+"下发失败");
            }else if(code == 408){
                taskDispatchService.cancelSave(taskDispatch.getTaskId());
                return R.error("获取任务超时");
            }else{
                taskDispatchService.cancelSave(taskDispatch.getTaskId());
                return R.error("任务下发失败");
            }
        } catch (Exception e) {
            taskDispatchService.cancelSave(taskDispatch.getTaskId());
            return R.error("网络故障，下发失败");
        }
    }

    /**
     * 修改
     *
     * @param taskDispatch
     * @return R
     */
    @RequestMapping("/update")
    @RequiresPermissions("taskdispatch:update")
    public R update(@RequestBody TaskDispatchEntity taskDispatch) {
        taskDispatchService.update(taskDispatch);
        return R.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return R
     */
    @RequestMapping("/delete")
    @RequiresPermissions("taskdispatch:delete")
    public R delete(@RequestBody Integer[] ids) {
        taskDispatchService.deleteBatch(ids);
        return R.ok();
    }

    /**
     * 取消任务
     *
     * @param id
     * @return R
     */
    @SysLog("取消任务")
    @RequestMapping("/cancel/{id}")
    public R cancel(@PathVariable("id") Integer id) {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
            prop.load(in);
        } catch (Exception e) {
            throw new RRException("配置文件配置有误，请重新配置");
        }
        int result = BypassHttps.sendRequestIgnoreSSL("DELETE", prop.getProperty("socketAddress") + "/web/v1/tasks/" + id);
        if (result == 200) {
            return R.ok();
        } else if (result == 401) {
            return R.error(404, "token失效，系统已重新获取，请重试");
        } else {
            return R.error(404, "取消任务失败");
        }
    }
}
