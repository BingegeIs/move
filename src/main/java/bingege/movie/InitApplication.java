package bingege.movie;

import bingege.movie.common.config.property.AppProperties;
import bingege.movie.common.model.User;
import bingege.movie.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
public class InitApplication implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger logger = LoggerFactory.getLogger(InitApplication.class);

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        boolean setup = appProperties.isSetup();
        logger.info("服务器开始初始化: " + setup);
        if (setup) {
            initAdmin();
        }
        logger.info("服务器开始初始化结束。");
    }

    private void initAdmin() {
        logger.info("开始初始化管理员账号...");
        Optional<User> user = userService.initAdmin();
        if (user.isPresent()) {
            logger.info("初始化管理员成功。Admin:{}", user.get().getUsername());
        } else {
            logger.info("初始化管理员失败,已存在?");
        }
        logger.info("初始化管理员账号结束。");
    }
}
