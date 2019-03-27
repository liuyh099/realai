package cn.realai.online.util;

import cn.realai.online.core.bo.FileClassifyBO;
import cn.realai.online.core.vo.FileTreeVo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StringPathToTreeUtils {


    public static List<FileTreeVo> listPathToTree(List<String> urlList) {
        //文件分类后的集合
        List<FileClassifyBO> fileClassifyList = new ArrayList<>();
        // 对数据进行分类
        for (String url : urlList) {
            String[] splitPath = url.split("/");
            for (int i = 0; i < splitPath.length; i++) {
                // 易得 parent,self,child 与i 的关系
                String parent = i == 0 ? null : splitPath[i - 1];
                String self = splitPath[i];
                if(i==splitPath.length-1){
                    self=url;
                }
                String child = i + 1 == splitPath.length ? null : splitPath[i + 1];
                String prefix = parent == null ? "" : getPrefix(splitPath, i);
                fileClassifyList.add(new FileClassifyBO(parent, self, child, prefix));
            }
        }


        List<FileClassifyBO> fileClassifyListnew = new ArrayList<>();
        fileClassifyList.stream().forEach(
                p -> {
                    if (!fileClassifyListnew.contains(p)) {
                        fileClassifyListnew.add(p);
                    }
                }
        );

        // 筛选出parent 为null 的FileClassify集合
        // 根据FileClassify的getSelf 分组
        Map<String, List<FileClassifyBO>> groupByMap = fileClassifyListnew.stream().filter(fileClassify -> fileClassify.getParent() == null).collect(Collectors.groupingBy(FileClassifyBO::getSelf));


        for (Map.Entry<String, List<FileClassifyBO>> entry : groupByMap.entrySet()) {
            List<FileClassifyBO> list = entry.getValue();
            List<FileClassifyBO> listnew = new ArrayList<>();
            list.stream().forEach(
                    p -> {
                        if (!listnew.contains(p)) {
                            listnew.add(p);
                        }
                    }
            );
            groupByMap.put(entry.getKey(), listnew);
        }
        // 此时  result 就是想要的结果
        List<FileTreeVo> result = getFileTreeVoList(groupByMap, fileClassifyListnew);
        return result;
    }


    public static void main(String[] args) {

        // 此时  result 就是想要的结果
//        List<FileTreeVo> result = getFileTreeVoList(groupByMap, fileClassifyListnew);


//        System.out.println(JSONObject.toJSONString(result));

    }


    /**
     * 获取文件前缀
     * 比如 image/12312/head.png
     * head.png的前缀为 image12312
     *
     * @param splitPath 文件完整路径根据"/" 切割后的数组
     * @param endIndex  终止索引
     * @return 文件前缀
     */
    private static String getPrefix(String[] splitPath, int endIndex) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < endIndex; i++) {
            result.append(splitPath[i]);
        }
        return result.toString();
    }

    /**
     * 获取文件Vo集合
     *
     * @param root             根据self分组后的 文件分类结合
     * @param fileClassifyList 文件分类集合
     */
    private static List<FileTreeVo> getFileTreeVoList(Map<String, List<FileClassifyBO>> root, List<FileClassifyBO> fileClassifyList) {

        List<FileTreeVo> result = new ArrayList<>();
        Set<Map.Entry<String, List<FileClassifyBO>>> entries = root.entrySet();
        for (Map.Entry<String, List<FileClassifyBO>> entry : entries) {
            FileTreeVo fileTreeVo = new FileTreeVo();
            String key = entry.getKey();
            fileTreeVo.setTitle(key);
            fileTreeVo.setKey(key);
            List<FileClassifyBO> list = entry.getValue();
            for (FileClassifyBO fileClassify : list) {
                if (StringUtils.isNotBlank(fileClassify.getChild())) {
                    // 首先筛选出 父节点 为key,前缀为父节点的前缀+本身 并且 子节点 不为空 的FileClassify集合
                    // 然后根据子节点 对集合进行分组
                    Map<String, List<FileClassifyBO>> child = fileClassifyList.stream()
                            .filter(e -> key.equals(e.getParent()) && e.getPrefix().equals(fileClassify.getPrefix() + fileClassify.getSelf()) && StringUtils.isNoneBlank(e.getSelf()))
                            .collect(Collectors.groupingBy(FileClassifyBO::getSelf));
                    fileTreeVo.setChildren(getFileTreeVoList(child, fileClassifyList));
                }
            }
            result.add(fileTreeVo);
        }
        return result;
    }
}
