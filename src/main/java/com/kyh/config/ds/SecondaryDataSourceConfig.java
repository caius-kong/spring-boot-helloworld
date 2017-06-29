package com.kyh.config.ds;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by kongyunhui on 2017/6/28.
 *
 * 次数据源配置
 */
@Configuration
@MapperScan(basePackages = SecondaryDataSourceConfig.PACKAGE, sqlSessionFactoryRef="secondarySqlSessionFactory")
public class SecondaryDataSourceConfig {
    static final String PACKAGE = "com.kyh.dao.secondary";
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/secondary/*.xml";
    static final String TYPE_ALIASES_PACKAGE = "com.kyh.model.secondary";

    @Value("${spring.datasource.secondary.url}")
    private String url;
    @Value("${spring.datasource.secondary.username}")
    private String username;
    @Value("${spring.datasource.secondary.password}")
    private String password;
    @Value("${spring.datasource.secondary.driverClassName}")
    private String driverClassName;

    @Bean(name="secondaryDataSource")
    public DataSource secondaryDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(name="secondaryTransactionManager")
    public DataSourceTransactionManager secondaryTransactionManager(@Qualifier("secondaryDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SecondaryDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        return sessionFactory.getObject();
    }
}
