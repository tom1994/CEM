package io.cem.modules.cem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.TaskDispatchEntity;
import io.cem.modules.cem.service.TaskDispatchService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * @author ${author}
 * @email ${email}
 * @date 2017-11-13 11:01:11
 */
@RestController
@RequestMapping("/cem/taskdispatch")
public class TaskDispatchController {
    @Autowired
    private TaskDispatchService taskDispatchService;

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

}
