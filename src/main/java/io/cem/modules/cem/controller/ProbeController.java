package io.cem.modules.cem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.service.ProbeService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.excel.ExcelUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


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
	 * 列表
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
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = probeService.queryTotal(map);
		}
//		List<ProbeEntity> probeList = probeService.queryList(map);
		List<ProbeEntity> probeList = probeService.queryProbeList(map);
		PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	@RequestMapping("/download")
	@RequiresPermissions("probe:download")
	public void downloadProbe(HttpServletResponse response) throws RRException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProbeEntity> list = probeService.queryList(map);
		collectionToFile(response, list, ProbeEntity.class);
	}

	private <T> void collectionToFile(HttpServletResponse response, List<T> list, Class<T> c) throws RRException {
		InputStream is = null;
		ServletOutputStream out = null;
		try {
			XSSFWorkbook workbook = ExcelUtils.<T>exportExcel("sheet1", c, list);
			response.setContentType("application/octet-stream");
			// response.setCharacterEncoding("UTF-8");
			String fileName = c.getSimpleName().toLowerCase().replaceAll("entity", "") + "_all.xlsx";
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
	 * 按区县信息搜索探针信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("probe:info")
	public R info(@PathVariable("id") Integer id){
		List<ProbeEntity> probeList = probeService.queryProbe(id);
		System.out.println(probeList);
		return R.ok().put("probe", probeList);
	}


	/**
	 * 详细信息
	 */
	@RequestMapping("/detail/{id}")
	@RequiresPermissions("probe:detail")
	public R detail(@PathVariable("id") Integer id){
		ProbeEntity probeList = probeService.queryDetail(id);
		System.out.println(probeList);
		return R.ok().put("probe", probeList);
	}


	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("probe:save")
	public R save(@RequestBody ProbeEntity probe){
		probeService.save(probe);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("probe:update")
	public R update(@RequestBody ProbeEntity probe){
		probeService.update(probe);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("probe:delete")
	public R delete(@RequestBody Integer[] ids){
		probeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
