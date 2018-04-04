package io.cem.modules.cem.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.LayerEntity;
import io.cem.modules.cem.service.LayerService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * @author Fern
 * @date 2017-11-23 11:22:22
 */
@RestController
@RequestMapping("/cem/layer")
public class LayerController {
    @Autowired
    private LayerService layerService;

    /**
     * 列表
     */
    //这个方法弃用
//    @RequestMapping("/list")
//    public R list(Integer page, Integer limit) throws Exception {
//        Map<String, Object> map = new HashMap<>();
//        map.put("offset", null);
//        map.put("limit", null);
//        page = 0;
//        limit = 0;
//        int total = layerService.queryTotal(map);
//        List<LayerEntity> layer = layerService.queryList(map);
//        return R.ok().put("page", layer);
//    }

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<LayerEntity> dicTypeList = layerService.queryList(query);
        int total = layerService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(dicTypeList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 查询列表
     */
    @RequestMapping("/searchlist")
    public R searchlist(String groupdata, Integer page, Integer limit) throws Exception {
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
            total = layerService.queryTotal(map);
        }
        List<LayerEntity> layerList = layerService.queryList(map);
        PageUtils pageUtil = new PageUtils(layerList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("layer:info")
    public R info(@PathVariable("id") Integer id) {
        LayerEntity layer = layerService.queryObject(id);

        return R.ok().put("layer", layer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("layer:save")
    public R save(@RequestBody LayerEntity layer) {
        layerService.save(layer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("layer:update")
    public R update(@RequestBody LayerEntity layer) {
        layerService.update(layer);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("layer:delete")
    public R delete(@RequestBody Integer[] ids) {
        layerService.deleteBatch(ids);

        return R.ok();
    }

}
