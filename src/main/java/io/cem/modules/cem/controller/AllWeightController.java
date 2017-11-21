package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/cem/weight")
public class AllWeightController {
    @RequestMapping("/get")
    public R getWeight() {
        String[] weight = new String[6];
        try {
            weight[0] = PropertiesUtils.getValue("connectionweight");
            weight[1] = PropertiesUtils.getValue("qualityweight");
            weight[2] = PropertiesUtils.getValue("browseweight");
            weight[3] = PropertiesUtils.getValue("downloadweight");
            weight[4] = PropertiesUtils.getValue("videoweight");
            weight[5] = PropertiesUtils.getValue("gameweight");
        } catch (IOException e) {

        }
        return R.ok().put("weight",weight);
    }

    @RequestMapping("/reset")
    public R resetWeight() {
        String[] weightdefault = new String[6];
        try {
            weightdefault[0] = PropertiesUtils.getValue("connectionweightdefault");
            weightdefault[1] = PropertiesUtils.getValue("qualityweightdefault");
            weightdefault[2] = PropertiesUtils.getValue("browseweightdefault");
            weightdefault[3] = PropertiesUtils.getValue("downloadweightdefault");
            weightdefault[4] = PropertiesUtils.getValue("videoweightdefault");
            weightdefault[5] = PropertiesUtils.getValue("gameweightdefault");
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
            PropertiesUtils.setValue("browseweightSet",weight_new_jsonobject.get("browseweight").toString());
            PropertiesUtils.setValue("downloadweightSet",weight_new_jsonobject.get("downloadweight").toString());
            PropertiesUtils.setValue("videoweightSet",weight_new_jsonobject.get("videoweight").toString());
            PropertiesUtils.setValue("gameweightSet",weight_new_jsonobject.get("gameweight").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }
}
