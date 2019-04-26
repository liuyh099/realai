package cn.realai.online;

import cn.realai.online.util.SpringContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.realai.online.*.dao")
@EnableTransactionManagement // 启注解事务管理
@EnableAsync
public class OnlineApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(OnlineApplication.class, args);
        SpringContextUtils.setApplicationContext(applicationContext);
    }

}
