package org.unique.plugin.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * //TODO 分页model
 * 
 * @author rex
 */
@SuppressWarnings("hiding")
public class Page<Model> {

    /**
     * 对象总数量
     */
    private int totalCount;

    /**
     * 每页的数据列表
     */
    private List<Model> result = new ArrayList<Model>();


    /**
     * 根据直接提供的记录开始位置创建分页对象
     *
     * @param start
     * @param limit
     * @param <T>
     * @return
     */
    public static <T> Page<T> createFromStart(int start, int limit) {
        return new Page<T>(start / limit + 1, limit);
    }


    public Page(int pageNo, int pageSize) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
    }

    public Page(int pageNo) {
        this.setPageNo(pageNo);
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Page() {
        this.pageNo = DEFAULT_PAGE_NO;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    // 总页数，这个是根据totalcount和pageSize计算的
    public int getTotalPages() {
        if (totalCount == 0)
            return 0;

        int count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 返回下页的页号,序号从1开始.
     */
    public int getNextPage() {
        if (isHasNext())
            return pageNo + 1;
        else
            return pageNo;
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 返回上页的页号,序号从1开始.
     */
    public int getPrePage() {
        if (isHasPre())
            return pageNo - 1;
        else
            return pageNo;
    }



    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the result
     */
    public List<Model> getResult() {
        if (result == null)
            return new ArrayList<Model>();
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<Model> result) {
        this.result = result;
    }
    
    static int DEFAULT_PAGE_SIZE = 15;

    static int DEFAULT_PAGE_NO = 1;

    /**
     * 第几页
     */
    protected int pageNo = DEFAULT_PAGE_NO;

    /**
     * 每页显示对象个数
     */
    protected int pageSize = DEFAULT_PAGE_SIZE;


    // 每页的第一条记录在结果集中的位置
    public int getPageFirst() {
        return ((pageNo - 1) * pageSize);
    }

    //和上面方法一样
    public int getStart() {
        return getPageFirst();
    }

    //和getPageSize一样
    public int getLimit() {
        return pageSize;
    }


    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        if (pageNo > 0) {
            this.pageNo = pageNo;
        }
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }
}
