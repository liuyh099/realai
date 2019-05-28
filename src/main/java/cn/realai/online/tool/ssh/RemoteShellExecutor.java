package cn.realai.online.tool.ssh;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import cn.realai.online.util.StringPathToTreeUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class RemoteShellExecutor {

    private Connection conn;
    /**
     * 远程机器IP
     */
    private String ip;
    /**
     * 用户名
     */
    private String osUsername;
    /**
     * 密码
     */
    private String password;

    /**
     * 端口
     */
    private Integer port;


    private String charset = Charset.defaultCharset().toString();

    private static final int TIME_OUT = 1000 * 5 * 60;

    /**
     * 构造函数
     *
     * @param ip
     * @param usr
     * @param pasword
     */
    public RemoteShellExecutor(String ip, String usr, String pasword, Integer port) {
        this.ip = ip;
        this.osUsername = usr;
        this.password = pasword;
        this.port = port;
    }


    /**
     * 登录
     *
     * @return
     * @throws IOException
     */
    private boolean login() throws IOException {
        if (port == null) {
            conn = new Connection(ip);
        } else {
            conn = new Connection(ip, port);
        }
        conn.connect();
        return conn.authenticateWithPassword(osUsername, password);
    }

    /**
     * 执行脚本
     *
     * @param cmds
     * @return
     * @throws Exception
     */
    public String exec(String cmds) throws Exception {
        InputStream stdOut = null;
        InputStream stdErr = null;
        StringBuffer sb = new StringBuffer();
        //  StringBuffer  outErr = "";
        int ret = -1;
        try {
            if (login()) {
                // Open a new {@link Session} on this connection
                Session session = conn.openSession();
                // Execute a command on the remote machine.
                session.execCommand(cmds);

                stdOut = new StreamGobbler(session.getStdout());

                String outStr = processStream(stdOut, charset);
                sb.append(outStr);
                // outStr = processStream(stdOut, charset);
                //stdErr = new StreamGobbler(session.getStderr());
                //outErr = processStream(stdErr, charset);
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
                // System.out.println("outStr=" + outStr);
                //System.out.println("outErr=" + outErr);
                ret = session.getExitStatus();
            } else {
                throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
            }
        } finally {
            if (conn != null) {
                conn.close(); 
            }
            IOUtils.closeQuietly(stdOut);
            IOUtils.closeQuietly(stdErr);
        }
        return sb.toString();
    }

    /**
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    private String processStream(InputStream in, String charset) throws Exception {
        byte[] buf = new byte[1];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }

    public static void main(String args[]) throws Exception {
    	RemoteShellExecutor executor = new RemoteShellExecutor("47.105.102.15", "root", "realai2019asdf", 2822);
        // 执行myTest.sh 参数为java Know dummy
        String filePath = executor.exec("sh /home/shell/testDir.sh /home/file/");

        String[] list = filePath.split("\n");
        List<String> aab = new ArrayList<>(list.length);
        for (String aa : list) {
            if (aa.startsWith("/")) { 
                aa = aa.substring(1);
            }
            aa = aa.replace("//", "/");
            aab.add(aa);

        }
        System.out.println(JSON.toJSONString(StringPathToTreeUtils.listPathToTree(aab)));
    }

}
