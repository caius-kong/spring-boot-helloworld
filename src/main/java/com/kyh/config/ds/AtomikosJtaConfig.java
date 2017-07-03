package com.kyh.config.ds;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import javax.transaction.UserTransaction;

/**
 * atomikos 事务管理器配置
 *
 * 常见错误：Error updating database.  Cause: java.lang.IllegalArgumentException: null source
 * 解决方案：mysql的mysql-connector-java-5.1.6.jar有bug，升级以后这个问题即可解决。
 */
@Configuration
@EnableTransactionManagement // 启用事务管理，该注解会自动通过by-type查找满足条件的PlatformTransactionManager
public class AtomikosJtaConfig {
    /**
     * transaction manager
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager userTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(300);
        return userTransactionImp;
    }

    /**
     * jta transactionManager
     */
    @Bean
    public JtaTransactionManager transactionManager() throws Throwable {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager());
        jtaTransactionManager.setUserTransaction(userTransaction());
        return jtaTransactionManager;
    }
}