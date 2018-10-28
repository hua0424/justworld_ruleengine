package com.justworld.custget.intf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justworld.custget.ruleengine.dao.AiSmsJobDAO;
import com.justworld.custget.ruleengine.service.bo.AiSmsJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

import static com.justworld.custget.intf.common.ResultBuilder.buildResult;

/**
 * AI短信控制器
 */
@Slf4j
@Controller
@RequestMapping(value = "/click")
public class SmsClickController {

    @Autowired
    private AiSmsJobDAO aiSmsJobDAO;

    @GetMapping(value = "/aismsjob/{id}")
    public String clickAiSmsJob(AiSmsJob aiSmsJob){
        aiSmsJob = aiSmsJobDAO.lockByPrimaryKey(aiSmsJob.getId());
        if(aiSmsJob.getShortUrlStatus().equals("1")){
            log.debug("短链接未生成，不记录点击数");
            return null;
        }
        aiSmsJob.setClickCount(aiSmsJob.getClickCount()+1);
        aiSmsJob.setClickTime(new Timestamp(System.currentTimeMillis()));
        aiSmsJobDAO.updateByPrimaryKey(aiSmsJob);

        //重定向
        return "redirect:"+aiSmsJob.getSmsTemplateUrl();
    }
}