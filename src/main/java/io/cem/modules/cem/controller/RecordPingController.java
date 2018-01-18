package io.cem.modules.cem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.excel.ExcelUtils;
import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.service.TaskDispatchService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.RecordPingEntity;
import io.cem.modules.cem.service.RecordPingService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import static java.lang.Thread.sleep;


/**
 */
@RestController
@RequestMapping("recordping")
public class RecordPingController {
    @Autowired
    private RecordPingService recordPingService;

    @Autowired
    private TaskDispatchService taskDispatchService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("recordping:list")
    public R list(String resultdata, Integer page, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<>();
        JSONObject resultdata_jsonobject = JSONObject.parseObject(resultdata);
        try {
            map.putAll(JSONUtils.jsonToMap(resultdata_jsonobject));
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
            total = recordPingService.queryTotal(map);
        }
        if (Integer.parseInt(map.get("queryType").toString()) == 1) {
            List<RecordPingEntity> resultList = recordPingService.queryPingList(map);
            System.out.println(resultList);
            PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
            return R.ok().put("page", pageUtil);
        } else {
            List<RecordHourPingEntity> resultList = recordPingService.queryIntervalList(map);
            System.out.println(resultList);
            PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
            return R.ok().put("page", pageUtil);
        }

    }

    @RequestMapping("/diagnose")
    public R diagnose(String resultdata, Integer page, Integer limit, Integer dispatchId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        JSONObject resultdata_jsonobject = JSONObject.parseObject(resultdata);
        try {
            map.putAll(JSONUtils.jsonToMap(resultdata_jsonobject));
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
            total = recordPingService.queryTotal(map);
        }
        map.put("dispatch_id", dispatchId);
        while (true) {
//            int testStatus = Integer.parseInt(map.get("dispatchId").toString());
//            if (taskDispatchService.queryTestStatus(testStatus) > 0) {
            if (taskDispatchService.queryTestStatus(dispatchId) > 0) {
                break;
            } else {
                sleep(5000);
            }
        }
        List<RecordPingEntity> resultList = recordPingService.queryPingTest(map);
        System.out.println(resultList);
        PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping("/download")
    @RequiresPermissions("recordping:download")
    public void downloadRecord(HttpServletResponse response) throws RRException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<RecordPingEntity> list = recordPingService.queryPingList(map);
        collectionToFile(response, list, RecordPingEntity.class);
    }

    private <T> void collectionToFile(HttpServletResponse response, List<T> list, Class<T> c) throws RRException {
        InputStream is = null;
        ServletOutputStream out = null;
        try {
            XSSFWorkbook workbook = ExcelUtils.<T>exportExcel("sheet1", c, list);
            response.setContentType("application/octet-stream");
            // response.setCharacterEncoding("UTF-8");
            String fileName = c.getSimpleName().toLowerCase().replaceAll("entity", "") + "_filtered.xlsx";
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            // File outFile = new File("F://out.xlsx");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RRException("下载文件出错");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("recordping:info")
    public R info(@PathVariable("id") Integer id) {
        RecordPingEntity recordPing = recordPingService.queryObject(id);

        return R.ok().put("recordPing", recordPing);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("recordping:save")
    public R save(@RequestBody RecordPingEntity recordPing) {
        recordPingService.save(recordPing);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("recordping:update")
    public R update(@RequestBody RecordPingEntity recordPing) {
        recordPingService.update(recordPing);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("recordping:delete")
    public R delete(@RequestBody Integer[] ids) {
        recordPingService.deleteBatch(ids);

        return R.ok();
    }

}
