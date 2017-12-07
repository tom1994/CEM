package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/cem/allweight")
public class AllWeightController {
    @RequestMapping("/get")
    public R getWeight() {
        String[] weight = new String[6];

        try {
            weight[0] = PropertiesUtils.getValue("connectionweight");
            weight[1] = PropertiesUtils.getValue("qualityweight");
            weight[2] = PropertiesUtils.getValue("downloadweight");
            weight[3] = PropertiesUtils.getValue("browseweight");
            weight[4] = PropertiesUtils.getValue("videoweight");
            weight[5] = PropertiesUtils.getValue("gameweight");
            weight[6] = PropertiesUtils.getValue("ping_icmp");
            weight[7] = PropertiesUtils.getValue("ping_tcp");
            weight[8] = PropertiesUtils.getValue("ping_udp");
            weight[9] = PropertiesUtils.getValue("tr_tcp");
            weight[10] = PropertiesUtils.getValue("tr_icmp");
            weight[11] = PropertiesUtils.getValue("sla_tcp");
            weight[12] = PropertiesUtils.getValue("sla_udp");
            weight[13] = PropertiesUtils.getValue("dns");
            weight[14] = PropertiesUtils.getValue("dhcp");
            weight[15] = PropertiesUtils.getValue("adsl");
            weight[16] = PropertiesUtils.getValue("radius");
            weight[17] = PropertiesUtils.getValue("ftp_upload");
            weight[18] = PropertiesUtils.getValue("ftp_download");
            weight[19] = PropertiesUtils.getValue("web_download");
            weight[20] = PropertiesUtils.getValue("webpage");
            weight[21] = PropertiesUtils.getValue("video");
            weight[22] = PropertiesUtils.getValue("game");
        } catch (IOException e) { }
        return R.ok().put("allweight",weight);
    }

    @RequestMapping("/reset")
    public R resetWeight() {
        String[] weightdefault = new String[6];
        try {
            weightdefault[0] = PropertiesUtils.getValue("connection_default");
            weightdefault[1] = PropertiesUtils.getValue("quality_default");
            weightdefault[2] = PropertiesUtils.getValue("browse_default");
            weightdefault[3] = PropertiesUtils.getValue("download_default");
            weightdefault[4] = PropertiesUtils.getValue("video_default");
            weightdefault[5] = PropertiesUtils.getValue("game_default");
        } catch (IOException e) {

        }
        return R.ok().put("weightdefault",weightdefault);
    }

    @RequestMapping("/set")
    public R setWeight(String weight_new) {
        System.out.println(weight_new);
        JSONObject weight_new_jsonobject = JSON.parseObject(weight_new);
        System.out.println(weight_new_jsonobject.get("connectionweight"));
        try {
            PropertiesUtils.setValue("connectionweight",weight_new_jsonobject.get("connectionweight").toString());
            PropertiesUtils.setValue("qualityweight",weight_new_jsonobject.get("qualityweight").toString());
            PropertiesUtils.setValue("browseweight",weight_new_jsonobject.get("browseweight").toString());
            PropertiesUtils.setValue("downloadweight",weight_new_jsonobject.get("downloadweight").toString());
            PropertiesUtils.setValue("videoweight",weight_new_jsonobject.get("videoweight").toString());
            PropertiesUtils.setValue("gameweight",weight_new_jsonobject.get("gameweight").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }
}
