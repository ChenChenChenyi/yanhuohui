package com.chenyi.yanhuohui.configuration;

import com.chenyi.yanhuohui.util.uuid.GlobalOrderNumberSequenceGenerator;
import com.chenyi.yanhuohui.util.uuid.NumberSequenceGenerator;
import com.chenyi.yanhuohui.util.uuid.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class NumberSequenceGeneratorConfiguration {

    @Bean("numberSequenceGenerator")
    public NumberSequenceGenerator numberSequenceGenerator(DataSource dataSource){
        GlobalOrderNumberSequenceGenerator numberSequenceGenerator = new GlobalOrderNumberSequenceGenerator();
        numberSequenceGenerator.setSeqName("SEQUENCE_NAME");
        numberSequenceGenerator.setDataSource(dataSource);
        numberSequenceGenerator.setUseTransaction(true);
        numberSequenceGenerator.afterPropertiesSet();
        return numberSequenceGenerator;
    }
}
