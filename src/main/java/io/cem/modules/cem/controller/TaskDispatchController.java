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
     * 列表
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
     * 信息
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

        //        String[] targetList = new String[dispatchList.size()];
//        for (int i = 0; i < dispatchList.size(); i++) {
//            targetList[i] = dispatchList.get(i).getTarget();
//            String targetName = taskDispatchService.queryTargetBatch(targetList[i].split(",|\""));
//            dispatchList.get(i).setTargetName(targetName);
//        }
        PageUtils pageUtil = new PageUtils(dispatchList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }

    /**
     * ZTY用于显示探针界面的显示任务
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
     * 保存
     */
    @SysLog("下发任务")
    @RequestMapping("/save")
    @RequiresPermissions("taskdispatch:save")
    public R save(@RequestBody TaskDispatchEntity taskDispatch) {
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
        BypassHttps.sendRequestIgnoreSSL("POST", "https://114.236.91.16:23456/web/v1/tasks/" + taskDispatch.getTaskId());

        return R.ok();
    }

    /**
     * 下发实时诊断任务
     */
    @RequestMapping("/saveAndReturn")
    @RequiresPermissions("taskdispatch:save")
    public R saveAndReturn(@RequestBody String param) {
        Map<String, Object> map = new HashMap<>();
        JSONObject probedata_jsonobject = JSONObject.parseObject(param);
        System.out.println(probedata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
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
//        taskDispatch.setProbePort("port1");
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
        //TODO:增加下发失败的提醒
        if (map.containsKey("ping")) {
            int[] pingState = new int[5];
            for (int i = 1; i < 6; i++) {
                pingState[i - 1] = BypassHttps.sendRequestIgnoreSSL("POST", "https://114.236.91.16:23456/web/v1/tasks/" + i);
            }
        }
        if (map.containsKey("sla")) {
            int[] slaState = new int[6];
            for (int i = 10; i < 16; i++) {
                slaState[i - 10] = BypassHttps.sendRequestIgnoreSSL("POST", "https://114.236.91.16:23456/web/v1/tasks/" + i);
            }
        }
        if (map.containsKey("web")) {
            int webState = BypassHttps.sendRequestIgnoreSSL("POST", "https://114.236.91.16:23456/web/v1/tasks/" + 20);
        }
        if (map.containsKey("download")) {
            int[] downloadState = new int[3];
            for (int i = 30; i < 33; i++) {
                downloadState[i - 30] = BypassHttps.sendRequestIgnoreSSL("POST", "https://114.236.91.16:23456/web/v1/tasks/" + i);
            }
        }
        if (map.containsKey("video")) {
            int videoState = BypassHttps.sendRequestIgnoreSSL("POST", "https://114.236.91.16:23456/web/v1/tasks/" + 40);
        }
        if (map.containsKey("game")) {
            int gameState = BypassHttps.sendRequestIgnoreSSL("POST", "https://114.236.91.16:23456/web/v1/tasks/" + 50);
        }

        return R.ok().put("taskdispatch", dispatch);
    }

    @SysLog("下发任务")
    @RequestMapping("/saveAll")
    @RequiresPermissions("taskdispatch:save")
    public R saveAll(@RequestBody TaskDispatchEntity taskDispatch) {
        taskDispatch.setCreateTime(new Date());
        List<String> probeLimited = new ArrayList<>();
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
                    if (probe.getConcurrentTask() > taskDispatchService.queryCurrentDispatch(probe.getId())) {
                        TaskDispatchEntity taskDispatchEntity = CloneUtils.clone(taskDispatch);
                        taskDispatchEntity.setProbeId(probe.getId());
                        taskDispatchEntityList.add(taskDispatchEntity);
                    } else {
                        probeLimited.add(probe.getName());
                    }
                }
            }
            taskDispatchService.saveAll(taskDispatchEntityList);
        } else if (taskDispatch.getProbeIds() != null && taskDispatch.getProbeGroupIds() == null) {
            int[] probeIdsList = taskDispatch.getProbeIds();
            List<TaskDispatchEntity> taskDispatchEntityList = new ArrayList<>();
            Map mapEmpty = new HashMap();
            List<ProbeEntity> probeEntityList = probeService.queryList(mapEmpty);
            Map map = new HashMap();
            Map outMap = new HashMap();
            for (ProbeEntity probeEntity : probeEntityList) {
                map.put(probeEntity.getId(), probeEntity.getConcurrentTask());
                outMap.put(probeEntity.getId(), probeEntity.getName());
            }
            for (int j = 0; j < probeIdsList.length; j++) {
                int CurrentTask = taskDispatchService.queryCurrentDispatch(probeIdsList[j]);
                if (Integer.parseInt(map.get(probeIdsList[j]).toString()) > taskDispatchService.queryCurrentDispatch(probeIdsList[j])) {
                    TaskDispatchEntity taskDispatchEntity = CloneUtils.clone(taskDispatch);
                    taskDispatchEntity.setProbeId(probeIdsList[j]);
                    taskDispatchEntityList.add(taskDispatchEntity);
                    taskDispatchService.saveAll(taskDispatchEntityList);
                } else {
                    probeLimited.add(outMap.get(probeIdsList[j]).toString());
                }
            }
        } else {
            return R.error(111, "探针或探针组格式错误");
        }
        try {
            int result = BypassHttps.sendRequestIgnoreSSL("POST", "https://114.236.91.16:23456/web/v1/tasks/" + taskDispatch.getTaskId());
            if (result == 200) {
                if (probeLimited.size() == 0) {
                    return R.ok();
                } else {
                    return R.error(600, probeLimited.get(0) + "任务数量已经超出最大限制");
                }
            } else {
                taskDispatchService.cancelSave(taskDispatch.getTaskId());
                if (probeLimited.size()==0) {
                    return R.error(404, "任务下发失败，错误代码" + result);
                }else {
                    return R.error(600, probeLimited.get(0) + "任务数量已经超出最大限制");
                }
            }
        } catch (Exception e) {
            return R.error("网络故障，下发失败");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("taskdispatch:update")
    public R update(@RequestBody TaskDispatchEntity taskDispatch) {
        taskDispatchService.update(taskDispatch);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("taskdispatch:delete")
    public R delete(@RequestBody Integer[] ids) {
        taskDispatchService.deleteBatch(ids);
        return R.ok();
    }

    @SysLog("取消任务")
    @RequestMapping("/cancel/{id}")
    public R cancel(@PathVariable("id") Integer id) {
        int result = BypassHttps.sendRequestIgnoreSSL("DELETE", "https://114.236.91.16:23456/web/v1/tasks/" + id);
        if (result == 200) {
            return R.ok();
        } else {
            return R.error(404, "取消任务失败,错误代码" + result);
        }
    }
}
