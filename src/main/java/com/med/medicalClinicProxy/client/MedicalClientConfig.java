package com.med.medicalClinicProxy.client;

import com.med.medicalClinicProxy.exception.MedicalClinicErrorDecoder;
import feign.Client;
import feign.Retryer;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MedicalClientConfig {

    @Bean
    public Client feignClient() {
        return new feign.okhttp.OkHttpClient(new OkHttpClient());
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

    @Bean
    public MedicalClinicErrorDecoder medicalClinicErrorDecoder() {
        return new MedicalClinicErrorDecoder();
    }
}
