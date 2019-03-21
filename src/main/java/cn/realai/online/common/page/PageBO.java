package cn.realai.online.common.page;

import cn.realai.online.core.query.PageQuery;

import java.util.List;

/**
 * 分页结果模板类
 *
 * @param <T>
 * @author lhzissky
 */
public class PageBO<T> {

    /**
     * 页面内容
     */
    private List<T> pageContent;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 数据总数
     */
    private long count;

    /**
     * 分页总数
     */
    private int totalPage;

    public PageBO(List<T> pageContent, int pageSize, int pageNum, long count, int totalPage) {
        this.pageContent = pageContent;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.count = count;
        this.totalPage = totalPage;
    }


    /**
     * 每页大小
     */
    public static final int PAGE_SIZE = 10;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getPageContent() {
        return pageContent;
    }

    public void setPageContent(List<T> pageContent) {
        this.pageContent = pageContent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public PageBO() {
        super();
    }

    public PageBO(PageQuery pageQuery) {
        this.pageSize = pageQuery.getPageSize();
        this.pageNum = pageQuery.getPageNum();
    }

    public PageBO(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void calculateTotalPage(int count, int pageSize) {
        if (count < 0 || count <= 0) {
            return;
        }
        int totalPage = count / pageSize;
        if (count % pageSize != 0) {
            totalPage = totalPage + 1;
        }
        this.totalPage = totalPage;
    }

    public PageBO(List<T> pageContent, int pageSize, int pageNum, int count, int totalPage) {
        super();
        this.pageContent = pageContent;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.count = count;
        this.totalPage = totalPage;
    }

}
