package cn.realai.online.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	/**
     * 读入TXT文件
     */
    public static List<String> readFile(String path) {
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        List<String> content = new ArrayList<String>();
    	try (
        	FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
            	content.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    	return content;
    }
	
}
