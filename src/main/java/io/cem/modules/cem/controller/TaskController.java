package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.collections.MappingChange;
import io.cem.common.annotation.SysLog;
import io.cem.common.exception.RRException;
import io.cem.common.utils.BypassHttps;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.TaskDispatchEntity;
import io.cem.modules.cem.entity.TaskEntity;
import io.cem.modules.cem.service.TaskDispatchService;
import io.cem.modules.cem.service.TaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cem/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskDispatchService taskDispatchService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("task:list")
    public R list(String taskdata, Integer page, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        JSONObject taskdata_jsonobject = JSONObject.parseObject(taskdata);
        try {
            map.putAll(JSONUtils.jsonToMap(taskdata_jsonobject));
        } catch (RuntimeException e) {
            throw new RRException("内部参数错误，请重试！");
        }
        List<TaskEntity> taskList = taskService.queryTaskList(map);
        int total = taskService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(taskList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }


    /**
     * 根据servicetype查询task
     */
    @RequestMapping("/infoByService/{id}")
    @RequiresPermissions("task:info")
    public R infobat(@PathVariable("id") Integer servicetype) {
        List<TaskEntity> task = taskService.infoByService(servicetype);
        System.out.println(task);
        return R.ok().put("task", task);

    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("task:info")
    public R info(@PathVariable("id") Integer id) {
        TaskEntity task = taskService.queryObject(id);

        return R.ok().put("task", task);
    }

    /**
     * 保存
     */
    @SysLog("新建任务")
    @RequestMapping("/save")
    @RequiresPermissions("task:save")
    public R save(@RequestBody TaskEntity task) {
        if (taskService.queryExist(task.getTaskName()) > 0) {
            return R.error(300, "任务名称已存在，请重新输入");
        } else {
            taskService.save(task);
            return R.ok();
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("task:update")
    public R update(@RequestBody TaskEntity task) {
        taskService.update(task);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除任务")
    @RequestMapping("/delete")
    @RequiresPermissions("task:delete")
    public R delete(@RequestBody Integer[] ids) {
        int result = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("taskid", ids[0]);
        List<TaskDispatchEntity> taskDispatchEntity = taskDispatchService.queryDispatchList(map);
        if (taskDispatchEntity != null && taskDispatchEntity.size() != 0) {
            for (int i = 0; i < taskDispatchEntity.size(); i++) {
                result = BypassHttps.sendRequestIgnoreSSL("DELETE", "https://114.236.91.16:23456/web/v1/tasks/" + taskDispatchEntity.get(i).getId());
            }
            if (result != 0 & result != 500) {
                taskService.deleteBatch(ids);
                return R.ok();
            } else {
                return R.error(404, "该任务有误，暂时无法删除");
            }
        } else {
            taskService.deleteBatch(ids);
            return R.ok();
        }
    }

}
