package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.annotation.SysLog;
import io.cem.common.exception.RRException;
import io.cem.common.utils.*;
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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@RestController
@RequestMapping("/cem/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskDispatchService taskDispatchService;

    /**
     * 任务列表
     *
     * @param taskdata
     * @param page
     * @param limit
     * @return R
     * @throws Exception
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
     * 根据业务查任务
     *
     * @param servicetype
     * @return R
     */
    @RequestMapping("/infoByService/{id}")
    @RequiresPermissions("task:info")
    public R infobat(@PathVariable("id") Integer servicetype) {
        List<TaskEntity> task = taskService.infoByService(servicetype);
        System.out.println(task);
        return R.ok().put("task", task);

    }

    /**
     * 根据id查任务
     *
     * @param id
     * @return R
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("task:info")
    public R info(@PathVariable("id") Integer id) {
        TaskEntity task = taskService.queryObject(id);

        return R.ok().put("task", task);
    }

    /**
     * 保存
     *
     * @param task
     * @return R
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
     *
     * @param task
     * @return R
     */
    @RequestMapping("/update")
    @RequiresPermissions("task:update")
    public R update(@RequestBody TaskEntity task) {
        taskService.update(task);

        return R.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return R
     */
    @SysLog("删除任务")
    @RequestMapping("/delete")
    @RequiresPermissions("task:delete")
    public R delete(@RequestBody Integer[] ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("taskid", ids[0]);
        List<TaskDispatchEntity> taskDispatchEntity = taskDispatchService.queryDispatchList(map);
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
            prop.load(in);
        } catch (Exception e) {
            throw new RRException("配置文件配置有误，请重新配置");
        }
        if (taskDispatchEntity != null && taskDispatchEntity.size() != 0) {
            int flag = 0;
            int result;
            try {
                for (int i = 0; i < taskDispatchEntity.size(); i++) {
                    result = BypassHttps.sendRequestIgnoreSSL("DELETE", prop.getProperty("socketAddress") + "/web/v1/tasks/" + taskDispatchEntity.get(i).getId());
                    if (result == 401) {
                        return R.error(404, "token失效，系统已重新获取，请重试");
                    } else if (result != 200) {
                        flag = 1;
                    }
                }
            } catch (Exception e) {
                return R.error(404, "删除失败");
            }
            if (flag == 0) {
                taskService.deleteBatch(ids);
            } else {
                return R.error(404, "该任务有误，暂时无法删除");
            }
            return R.ok();
        } else {
            taskService.deleteBatch(ids);
            return R.ok();
        }
    }

}
