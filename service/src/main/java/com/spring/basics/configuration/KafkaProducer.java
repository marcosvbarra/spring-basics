package com.spring.basics.configuration;

import com.google.gson.Gson;
import com.spring.basics.JobRequestRepository;
import com.spring.basics.entity.JobRequest;
import com.spring.basics.entity.JobRequestStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final Gson gson = new Gson();

    @Value(value = "${kafka.topic.jobRecommendation}")
    private String topicJobRecommendation;
    @Autowired
    private JobRequestRepository jobRequestRepository;

    public void sendMessage(String message, String key) {

        if (key == null)
            key = UUID.randomUUID().toString();

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicJobRecommendation, key, message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
                JobRequest jobRequest = gson.fromJson(message, JobRequest.class);
                jobRequestRepository.updateStatus(JobRequestStatusEnum.FAILED, jobRequest.getId());
            }
        });
    }
}
