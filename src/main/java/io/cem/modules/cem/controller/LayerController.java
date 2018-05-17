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

    @RequestMapping("/list")
    @RequiresPermissions("layer:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        if (!params.containsKey("limit")) {
            List<LayerEntity> layerList = layerService.queryList(params);
            return R.ok().put("layerList", layerList);
        } else {
            Query query = new Query(params);
            List<LayerEntity> layerList = layerService.queryList(query);
            int total = layerService.queryTotal(query);
            PageUtils pageUtil = new PageUtils(layerList, total, query.getLimit(), query.getPage());
            return R.ok().put("page", pageUtil);
        }
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
     * 新增层级
     */
    @RequestMapping("/save")
    @RequiresPermissions("layer:save")
    public R save(@RequestBody LayerEntity layer) {
        LayerEntity highLayer = layerService.queryObject(layer.getId());
        if (layerService.queryExist(layer.getLayerName()) > 0) {
            return R.error(300, "层级名称已存在，请重新输入");
        } else {
            LayerEntity lowLayer = layerService.queryLowLayer(highLayer.getLayerTag());
            int layerTag;
            if (lowLayer != null && lowLayer.getLayerTag() != null) {
                layerTag = (highLayer.getLayerTag() + lowLayer.getLayerTag()) / 2;
            } else {
                layerTag = highLayer.getLayerTag() / 2;
            }
            layer.setLayerTag(layerTag);
            layerService.save(layer);
            return R.ok();
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("layer:update")
    public R update(@RequestBody LayerEntity layer) {
        if (layerService.queryExist(layer.getLayerName()) > 0) {
            return R.error(300, "层级名称已存在，请重新输入");
        } else {
            layerService.update(layer);
            return R.ok();
        }
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
