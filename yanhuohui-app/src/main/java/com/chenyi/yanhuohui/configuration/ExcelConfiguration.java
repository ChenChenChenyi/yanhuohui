package com.chenyi.yanhuohui.configuration;

import com.chenyi.yanhuohui.utils.JDBCDruidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ExcelConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JDBCDruidUtils jdbcDruidUtils(){
        JDBCDruidUtils jdbcDruidUtils = new JDBCDruidUtils();
        jdbcDruidUtils.setDataSource(dataSource);
        return jdbcDruidUtils;
    }
}
