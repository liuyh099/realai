package cn.realai.online.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.GroupDif;
import cn.realai.online.core.entity.VariableData;

public class Test {

	public static void main(String[] args) {
	//	String str = "[{'max': 41.25, 'min': 0.0, 'mean': 0.5073635291232935, 'median': 0.0, 'percent25': 0.0, 'percent75': 0.666666667, 'count': 118, 'recommendedDelete': 1, 'dataType': 'number', 'name': '\\xe4\\xba\\xa4\\xe5\\x8f\\x89\\xe6\\xb1\\x87\\xe6\\x80\\xbb_\\xe6\\x9c\\x80\\xe8\\xbf\\x916\\xe4\\xb8\\xaa\\xe6\\x9c\\x88\\xe8\\xb4\\xb7\\xe6\\xac\\xbe\\xe7\\x94\\xb3\\xe8\\xaf\\xb7\\xe9\\x80\\x9a\\xe8\\xbf\\x87\\xe7\\x8e\\x87', 'experimentId': '276', 'variableType': 1, 'meaning': ''}, {'max': 124.0, 'min': 0.0, 'mean': 0.8677481253526618, 'median': 0.5, 'percent25': 0.0, 'percent75': 1.0, 'count': 230, 'recommendedDelete': 1, 'dataType': 'number', 'name': '\\xe4\\xba\\xa4\\xe5\\x8f\\x89\\xe6\\xb1\\x87\\xe6\\x80\\xbb_\\xe6\\x9c\\x80\\xe8\\xbf\\x9112\\xe6\\x9c\\x88\\xe8\\xb4\\xb7\\xe6\\xac\\xbe\\xe7\\x94\\xb3\\xe8\\xaf\\xb7\\xe9\\x80\\x9a\\xe8\\xbf\\x87\\xe7\\x8e\\x87', 'experimentId': '276', 'variableType': 1, 'meaning': ''}, {'max': 171.33333330000005, 'min': 0.0, 'mean': 1.0016742139642945, 'median': 0.666666667, 'percent25': 0.14285714300000002, 'percent75': 1.0, 'count': 379, 'recommendedDelete': 1, 'dataType': 'number', 'name': '\\xe4\\xba\\xa4\\xe5\\x8f\\x89\\xe6\\xb1\\x87\\xe6\\x80\\xbb_\\xe6\\x9c\\x80\\xe8\\xbf\\x9124\\xe6\\x9c\\x88\\xe8\\xb4\\xb7\\xe6\\xac\\xbe\\xe7\\x94\\xb3\\xe8\\xaf\\xb7\\xe9\\x80\\x9a\\xe8\\xbf\\x87\\xe7\\x8e\\x87', 'experimentId': '276', 'variableType': 1, 'meaning': ''}, {'max': 19.0, 'min': 0.0, 'mean': 0.7355521775307247, 'median': 0.0, 'percent25': 0.0, 'percent75': 1.0, 'count': 66, 'recommendedDelete': 1, 'dataType': 'number', 'name': '\\xe4\\xba\\xa4\\xe5\\x8f\\x89\\xe6\\xb1\\x87\\xe6\\x80\\xbb_\\xe6\\x9c\\x80\\xe8\\xbf\\x913\\xe4\\xb8\\xaa\\xe6\\x9c\\x88\\xe4\\xbf\\xa1\\xe7\\x94\\xa8\\xe5\\x8d\\xa1\\xe7\\x94\\xb3\\xe8\\xaf\\xb7\\xe9\\x80\\x9a\\xe8\\xbf\\x87\\xe7\\x8e\\x87', 'experimentId': '276', 'variableType': 1, 'meaning': ''}, {'max': 11.0, 'min': 0.0, 'mean': 0.8633586096487044, 'median': 0.5, 'percent25': 0.0, 'percent75': 1.0, 'count': 107, 'recommendedDelete': 1, 'dataType': 'number', 'name': '\\xe4\\xba\\xa4\\xe5\\x8f\\x89\\xe6\\xb1\\x87\\xe6\\x80\\xbb_\\xe6\\x9c\\x80\\xe8\\xbf\\x916\\xe4\\xb8\\xaa\\xe6\\x9c\\x88\\xe4\\xbf\\xa1\\xe7\\x94\\xa8\\xe5\\x8d\\xa1\\xe7\\x94\\xb3\\xe8\\xaf\\xb7\\xe9\\x80\\x9a\\xe8\\xbf\\x87\\xe7\\x8e\\x87', 'experimentId': '276', 'variableType': 1, 'meaning': ''}, {'max': 18.0, 'min': 0.0, 'mean': 0.901798926373009, 'median': 0.5, 'percent25': 0.0, 'percent75': 1.0, 'count': 181, 'recommendedDelete': 1, 'dataType': 'number', 'name': '\\xe4\\xba\\xa4\\xe5\\x8f\\x89\\xe6\\xb1\\x87\\xe6\\x80\\xbb_\\xe6\\x9c\\x80\\xe8\\xbf\\x9112\\xe4\\xb8\\xaa\\xe6\\x9c\\x88\\xe4\\xbf\\xa1\\xe7\\x94\\xa8\\xe5\\x8d\\xa1\\xe7\\x94\\xb3\\xe8\\xaf\\xb7\\xe9\\x80\\x9a\\xe8\\xbf\\x87\\xe7\\x8e\\x87', 'experimentId': '276', 'variableType': 1, 'meaning': ''}, {'max': 11.0, 'min': 0.0, 'mean': 0.8665015961986469, 'median': 0.666666667, 'percent25': 0.222222222, 'percent75': 1.0, 'count': 261, 'recommendedDelete': 1, 'dataType': 'number', 'name': '\\xe4\\xba\\xa4\\xe5\\x8f\\x89\\xe6\\xb1\\x87\\xe6\\x80\\xbb_\\xe6\\x9c\\x80\\xe8\\xbf\\x9124\\xe4\\xb8\\xaa\\xe6\\x9c\\x88\\xe4\\xbf\\xa1\\xe7\\x94\\xa8\\xe5\\x8d\\xa1\\xe7\\x94\\xb3\\xe8\\xaf\\xb7\\xe9\\x80\\x9a\\xe8\\xbf\\x87\\xe7\\x8e\\x87', 'experimentId': '276', 'variableType': 1, 'meaning': ''}, {'max': '-', 'min': '-', 'mean': '-', 'median': '-', 'percent25': '-', 'percent75': '-', 'count': 6, 'recommendedDelete': 1, 'dataType': 'category', 'name': '\\xe5\\x9c\\xa8\\xe6\\x9c\\xac\\xe6\\x9c\\xba\\xe6\\x9e\\x84\\xe6\\x8b\\x85\\xe4\\xbf\\x9d\\xe6\\x95\\xb0\\xe9\\x87\\x8f', 'experimentId': '276', 'variableType': 1, 'meaning': ''}, {'max': '-', 'min': '-', 'mean': '-', 'median': '-', 'percent25': '-', 'percent75': '-', 'count': 9, 'recommendedDelete': 1, 'dataType': 'category', 'name': '\\xe5\\x9c\\xa8\\xe6\\x9c\\xac\\xe6\\x9c\\xba\\xe6\\x9e\\x84\\xe8\\xb4\\xb7\\xe6\\xac\\xbe\\xe6\\x95\\xb0\\xe9\\x87\\x8f', 'experimentId': '276', 'variableType': 1, 'meaning': ''}]";
		String str = readFile();
		List<GroupDif> list = JSON.parseArray(str, GroupDif.class);
		System.out.println(list);
	}
	
    public static String readFile() {
        //String pathname = "D:\\xxx.json"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        String pathname = "D:\\groupDif.json";
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line = "";
            //网友推荐更加简洁的写法
            line = br.readLine();
                // 一次读入一行数据
                //System.out.println(line);
            
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

	
}
