package io.cem.modules.cem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.*;
import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.service.ProbeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.TaskDispatchEntity;
import io.cem.modules.cem.service.TaskDispatchService;


/**
 * @author
 * @date 2017-11-13 11:01:11
 */
@RestController
@RequestMapping("/cem/taskdispatch")
public class TaskDispatchController {
    @Autowired
    private TaskDispatchService taskDispatchService;

    @Autowired
    private ProbeService probeService;
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
        List<TaskDispatchEntity> dispatchList = taskDispatchService.queryDispatchList(id);
        String[] targetList = new String[dispatchList.size()];
//        Integer[] targetIds = new Integer[100];
        for (int i = 0; i < dispatchList.size(); i++) {
            targetList[i] = dispatchList.get(i).getTarget();
//            System.out.print(targetList[1]);
            String targetName = taskDispatchService.queryTargetBatch(targetList[i].split(",|\""));
            dispatchList.get(i).setTargetName(targetName);
        }

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
        int total = 0;
        if (page == null) {              /*没有传入page,则取全部值*/
            map.put("offset", null);
            map.put("limit", null);
            page = 0;
            limit = 0;
        } else {
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            total = taskDispatchService.taskQueryDispatchTotal(id);
        }
        List<TaskDispatchEntity> dispatchList = taskDispatchService.taskQueryDispatchList(id);
        String[] targetList = new String[dispatchList.size()];
        for (int i = 0; i < dispatchList.size(); i++) {
            targetList[i] = dispatchList.get(i).getTarget();
            String targetName = taskDispatchService.queryTargetBatch(targetList[i].split(",|\""));
            dispatchList.get(i).setTargetName(targetName);
        }
        PageUtils pageUtil = new PageUtils(dispatchList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("taskdispatch:save")
    public R save(@RequestBody TaskDispatchEntity taskDispatch) {
        taskDispatchService.save(taskDispatch);

        return R.ok();
    }

    /**
     * 下发实时诊断任务
     */
    @RequestMapping("/saveAndReturn")
    @RequiresPermissions("taskdispatch:save")
    public R saveAndReturn(String param) {
        Map<String, Object> map = new HashMap<>();
        JSONObject probedata_jsonobject = JSONObject.parseObject(param);
        System.out.println(probedata_jsonobject);
        try {
            map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        int probeId = Integer.parseInt(map.get("probe_id").toString());
        List<ProbeEntity> probeList = probeService.queryProbeByLayer(probeId);6
        for(int i =1; i<probeList.size(); i++){

            List<TaskDispatchEntity> taskdispatch =
        }

        return R.ok();
    }
    
    @RequestMapping("/saveAll")
    @RequiresPermissions("taskdispatch:save")
    public R saveAll(@RequestBody TaskDispatchEntity taskDispatch/*, String[] probeIds*/) {
//      TODO:探针组对应方法
        System.out.println(taskDispatch.getTarget());
        if (taskDispatch.getProbeIds().isEmpty() && ! taskDispatch.getProbeGroupIds().isEmpty()) {
            String[] probeGroupIds = taskDispatch.getProbeGroupIds().split(",");
            for (int i = 0; i<taskDispatch.getProbeGroupIds().length();i++){
//               TODO: probeGroupIds[i] =
            }
            return R.ok();
        } else if (! taskDispatch.getProbeIds().isEmpty() && taskDispatch.getProbeGroupIds().isEmpty()){
            String[] probeIdsList = taskDispatch.getProbeIds().split(",");
            List<TaskDispatchEntity> taskDispatchEntityList = new ArrayList<TaskDispatchEntity>();
            taskDispatchEntityList.add(taskDispatch);
            for (int i = 1; i < probeIdsList.length; i++) {
                TaskDispatchEntity taskDispatchEntity = CloneUtils.clone(taskDispatch);
                taskDispatchEntity.setProbeId(Integer.parseInt(probeIdsList[i].split("\"")[1]));
                taskDispatchEntityList.add(taskDispatchEntity);
            }
            taskDispatchService.saveAll(taskDispatchEntityList);
            System.out.println(taskDispatch.getProbeIds());
            return R.ok();
        }else{
            return R.error(111,"探针或探针组格式错误");
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

    @RequestMapping("/cancel/{id}")
    @RequiresPermissions("taskdispatch:delete")
    public R cancel(@PathVariable("id") Integer id) {
        taskDispatchService.cancelTask(id);
        return R.ok();
    }
}
