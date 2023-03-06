package com.chenyi.yanhuohui.configuration;

import com.chenyi.yanhuohui.util.uuid.NumberSequenceGenerator;
import com.chenyi.yanhuohui.util.uuid.PrefixTimeFormatSequenceGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(NumberSequenceGeneratorConfiguration.class)
public class SeqGeneratorAutoConfiguration {

    @Bean("prefixTimeFormatSequenceGenerator")
    public PrefixTimeFormatSequenceGenerator prefixTimeFormatSequenceGenerator(NumberSequenceGenerator numberSequenceGenerator){
        PrefixTimeFormatSequenceGenerator prefixTimeFormatSequenceGenerator = new PrefixTimeFormatSequenceGenerator();
        prefixTimeFormatSequenceGenerator.setGenerator(numberSequenceGenerator);
        prefixTimeFormatSequenceGenerator.setPrefix("CY");
        prefixTimeFormatSequenceGenerator.setValidLength(5);
        prefixTimeFormatSequenceGenerator.setTimeFormat("yyyyMMddHHmmss");
        return prefixTimeFormatSequenceGenerator;
    }

}
