package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bussiness.SSHBusinness;
import cn.realai.online.tool.ssh.RemoteShellExecutor;
import cn.realai.online.util.StringPathToTreeUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SSHBusinnessImpl implements SSHBusinness {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${management.shell.auth.ip}")
    private String ip;

    @Value("${management.shell.auth.key}")
    private String remoteUser;

    @Value("${management.shell.auth.secret}")
    private String password;

    @Value("${management.shell.auth.shellPath}")
    private String shellPath;

    @Value("${management.shell.auth.rootDir}")
    private String rootDir;

    @Value("${management.shell.auth.offlineRootDir}")
    private String offlineRootDir;

    @Value("${management.shell.auth.port}")
    private Integer port;


    @Override
    public Object getFilePath(String type) throws Exception {
        if (StringUtils.isBlank(ip)
                || StringUtils.isBlank(remoteUser)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(shellPath)
                || StringUtils.isBlank(rootDir)
                ) {
            logger.error("未设置远程机器配置");
            return null;
        }

        Assert.isTrue("experiment".equalsIgnoreCase(type) || "offline".equalsIgnoreCase(type), "不支持的数据源文件夹类型");

        RemoteShellExecutor executor = new RemoteShellExecutor(ip, remoteUser, password, port);
        // 执行myTest.sh 参数为java Know dummy
        String command;
        if ("experiment".equalsIgnoreCase(type)) {
            command = "sh " + shellPath + " " + rootDir;
        } else {
            command = "sh " + shellPath + " " + offlineRootDir;
        }
        String filePath = executor.exec(command);
        String[] list = filePath.split("\n");
        List<String> aab = new ArrayList<>(list.length);
        for (String aa : list) {
            if (aa.startsWith("/")) {
                aa = aa.substring(1);
            }
            aa = aa.replace("//", "/");
            aab.add(aa);
        }
        return StringPathToTreeUtils.listPathToTree(aab);
    }
}
