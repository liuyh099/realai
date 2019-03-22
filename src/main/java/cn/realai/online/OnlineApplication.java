package cn.realai.online;

import cn.realai.online.util.SpringContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.realai.online.*.dao")
@EnableTransactionManagement // 启注解事务管理
public class OnlineApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(OnlineApplication.class, args);
        SpringContextUtils.setApplicationContext(applicationContext);
    }

}
