package com.binaryfountain.airplaneservice.config;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by smarufu15 on 6/9/17.
 */

@Configuration
public class DataSourceConfig {
  private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");

  @Bean(name = "mainDataSource")
  public DataSource createMainDataSource() {
    JdbcDataSource ds = new JdbcDataSource();
    System.out.println("GOHANIC");
    ds.setURL("jdbc:h2:"+TEMP_DIRECTORY+"/data;MODE=PostgreSQL");
    return ds;
  }
}
