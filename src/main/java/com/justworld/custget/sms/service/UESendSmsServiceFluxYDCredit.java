package com.justworld.custget.sms.service;

import com.justworld.custget.ruleengine.service.bo.SendSms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UE移动信用卡渠道
 */
@Slf4j
@Service
public class UESendSmsServiceFluxYDCredit extends UESendSmsServiceFluxBase {

    @Scheduled(cron = "20 0/10 * * * *")
    public void sendDbSms() {
        super.sendDbSms();
    }

    @KafkaListener(topics = "send_sms_notify_7", containerFactory = "kafkaListenerBatchConsumerFactory")
    public void sendQueueSms(List<SendSms> sendSmsList) {
        super.sendQueueSms(sendSmsList);

    }

    @Override
    public String getDispatcherId() {
        return "7";
    }
}
