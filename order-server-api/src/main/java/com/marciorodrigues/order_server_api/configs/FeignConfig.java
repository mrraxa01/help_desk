package com.marciorodrigues.order_server_api.configs;

import com.marciorodrigues.order_server_api.decoders.RetrieveMessageErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

     @Bean
     public ErrorDecoder errorDecoder() {
         return new RetrieveMessageErrorDecoder();
     }

}
