package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.*;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 报表策略
 */
@RestController
@RequestMapping("reportpolicy")
public class ReportPolicyController {
	@Autowired
	private ReportPolicyService reportPolicyService;
	@Autowired
	private RecordPingService recordPingService;
	@Autowired
	private RecordTracertService recordTracertService;
	@Autowired
	private RecordSlaService recordSlaService;
	@Autowired
	private RecordPppoeService recordPppoeService;
	@Autowired
	private RecordDhcpService recordDhcpService;
	@Autowired
	private RecordDnsService recordDnsService;
	@Autowired
	private RecordRadiusService recordRadiusService;
	@Autowired
	private RecordWebPageService recordWebPageService;
	@Autowired
	private RecordWebDownloadService recordWebDownloadService;
	@Autowired
	private RecordFtpService recordFtpService;
	@Autowired
	private RecordWebVideoService recordWebVideoService;
	@Autowired
	private RecordGameService recordGameService;

	/**
	 * 数据报表列表
	 * @param reportdata
	 * @param page
	 * @param limit
	 * @return R
	 * @throws Exception
	 */
	@RequestMapping("/reportlist")
	@RequiresPermissions("reportpolicy:reportlist")
	public R reportlist(String reportdata, Integer page, Integer limit) throws Exception{
		Map<String, Object> map = new HashMap<>();
		JSONObject reportdata_jsonobject = JSONObject.parseObject(reportdata);
		try {
			map.putAll(JSONUtils.jsonToMap(reportdata_jsonobject));
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
			total = reportPolicyService.queryTotal(map);
		}
		List<ReportPolicyEntity> probeList = reportPolicyService.queryList(map);
		PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 下载数据报表
	 * @param response
	 * @param probedata
	 * @throws RRException
	 */
	@RequestMapping("/download/{probedata}")
	@RequiresPermissions("reportpolicy:download")
	public void downloadProbe(HttpServletResponse response, @PathVariable String probedata) throws RRException {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		ReportPolicyEntity detail = reportPolicyService.queryObject(Integer.parseInt(map.get("id").toString()));
		int service = detail.getServiceType();
		int queryType = detail.getQueryType();

		map.put("probe_id",detail.getProbeId());
		map.put("service_type",detail.getServiceType());
		map.put("start_time",detail.getStartTime());
		map.put("end_time",detail.getEndTime());

		if (queryType== 1) {
			if(service == 1||service==2||service==3){
				List<RecordPingEntity> list = recordPingService.queryPingList(map);
				System.out.println(list);
				CollectionToFile.collectionToFile(response, list, RecordPingEntity.class);
			}else if(service==4||service==5){
				List<RecordTracertEntity> list = recordTracertService.queryTracertList(map);
				CollectionToFile.collectionToFile(response, list, RecordTracertEntity.class);
			}else if(service==10||service==11){
				List<RecordSlaEntity> list = recordSlaService.querySlaList(map);
				CollectionToFile.collectionToFile(response, list, RecordSlaEntity.class);
			}else if(service==12){
				List<RecordPppoeEntity> list = recordPppoeService.queryPppoeList(map);
				CollectionToFile.collectionToFile(response, list, RecordPppoeEntity.class);
			}else if(service==13){
				List<RecordDhcpEntity> list = recordDhcpService.queryDhcpList(map);
				CollectionToFile.collectionToFile(response, list, RecordDhcpEntity.class);
			}else if(service==14){
				List<RecordDnsEntity> list = recordDnsService.queryDnsList(map);
				CollectionToFile.collectionToFile(response, list, RecordDnsEntity.class);
			} else if(service==15){
				List<RecordRadiusEntity> list = recordRadiusService.queryRadiusList(map);
				CollectionToFile.collectionToFile(response, list, RecordRadiusEntity.class);
			} else if(service==20){
				List<RecordWebPageEntity> list = recordWebPageService.queryWebPageList(map);
				CollectionToFile.collectionToFile(response, list, RecordWebPageEntity.class);
			}else if(service==30){
				List<RecordWebDownloadEntity> list = recordWebDownloadService.queryWebDownloadList(map);
				CollectionToFile.collectionToFile(response, list, RecordWebDownloadEntity.class);
			}else if(service==31||service==32){
				List<RecordFtpEntity> list = recordFtpService.queryFtpList(map);
				CollectionToFile.collectionToFile(response, list, RecordFtpEntity.class);
			}else if(service==40){
				List<RecordWebVideoEntity> list = recordWebVideoService.queryWebVideoList(map);
				CollectionToFile.collectionToFile(response, list, RecordWebVideoEntity.class);
			}else if(service==50){
				List<RecordGameEntity> list = recordGameService.queryGameList(map);
				CollectionToFile.collectionToFile(response, list, RecordGameEntity.class);
			}
			else{}


		} else {
			map.put("interval",detail.getInterval());
			if(service == 1||service==2||service==3){
				List<RecordHourPingEntity> list = recordPingService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourPingEntity.class);
			}else if(service==4||service==5){
				List<RecordHourTracertEntity> list = recordTracertService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourTracertEntity.class);
			}else if(service==10||service==11){
				List<RecordHourSlaEntity> list = recordSlaService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourSlaEntity.class);
			}else if(service==12){
				List<RecordHourPppoeEntity> list = recordPppoeService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourPppoeEntity.class);
			}else if(service==13){
				List<RecordHourDhcpEntity> list = recordDhcpService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourDhcpEntity.class);
			}else if(service==14){
				List<RecordHourDnsEntity> list = recordDnsService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourDnsEntity.class);
			}else if(service==15){
				List<RecordHourRadiusEntity> list = recordRadiusService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourRadiusEntity.class);
			}else if(service==20){
				List<RecordHourWebPageEntity> list = recordWebPageService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourWebPageEntity.class);
			}else if(service==30){
				List<RecordHourWebDownloadEntity> list = recordWebDownloadService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourWebDownloadEntity.class);
			}else if(service==31||service==32){
				List<RecordHourFtpEntity> list = recordFtpService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourFtpEntity.class);
			}else if(service==40){
				List<RecordHourWebVideoEntity> list = recordWebVideoService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourWebVideoEntity.class);
			}else if(service==50){
				List<RecordHourGameEntity> list = recordGameService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourGameEntity.class);
			}
			else{}

		}
	}

	/**
	 * 根据id筛选
	 * @param id
	 * @return R
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("reportpolicy:info")
	public R info(@PathVariable("id") Integer id){
		ReportPolicyEntity reportPolicy = reportPolicyService.queryObject(id);
		
		return R.ok().put("reportPolicy", reportPolicy);
	}

	/**
	 * 保存
	 * @param reportPolicy
	 * @return R
	 */
	@RequestMapping("/save")
	@RequiresPermissions("reportpolicy:save")
	public R save(@RequestBody ReportPolicyEntity reportPolicy){
		if (reportPolicyService.queryExist(reportPolicy.getReportName()) > 0) {
			return R.error(300, "定时报表名称已存在，请重新输入");
		} else {
			reportPolicyService.save(reportPolicy);
			return R.ok();
		}
	}

	/**
	 * 修改
	 * @param reportPolicy
	 * @return R
	 */
	@RequestMapping("/update")
	@RequiresPermissions("reportpolicy:update")
	public R update(@RequestBody ReportPolicyEntity reportPolicy){
		if (reportPolicyService.queryExist(reportPolicy.getReportName()) > 0) {
			return R.error(300, "定时报表名称已存在，请重新输入");
		} else {
			reportPolicyService.update(reportPolicy);
			return R.ok();
		}
	}

	/**
	 * 删除
	 * @param ids
	 * @return R
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("reportpolicy:delete")
	public R delete(@RequestBody Integer[] ids){
		reportPolicyService.deleteBatch(ids);
		return R.ok();
	}
	
}
