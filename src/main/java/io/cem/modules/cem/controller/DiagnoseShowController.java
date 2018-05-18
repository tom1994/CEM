package io.cem.modules.cem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("diagnoseshow")
public class DiagnoseShowController {

    /**
     * 实时检测
     * @param probeId
     * @param serviceType
     * @param targetId
     * @param targetName
     * @param v
     * @return ModelAndView
     */
    @RequestMapping("/setting")
    public ModelAndView show(@RequestParam Integer probeId,@RequestParam Integer serviceType,@RequestParam Integer targetId,@RequestParam String targetName, ModelAndView v) {
        Map<String,Object> m = v.getModel();
        m.put("probeId",probeId);
        m.put("serviceType",serviceType);
        m.put("targetId",targetId);
        m.put("targetName",targetName);
        v.setViewName("/modules/diagnose/diagnoseNow.html");
        return v;
    }
}
