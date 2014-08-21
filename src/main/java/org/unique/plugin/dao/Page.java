package org.unique.plugin.dao;

import java.util.List;

/**
 * 分页model
 * @author:rex
 * @date:2014年8月21日
 * @version:1.0
 */
@SuppressWarnings("hiding")
public class Page<Model> {

	/*
	 * 当前页
	 */
	private Integer page;
	/*
	 * 查询起点
	 */
	private Integer startIndex;
	/*
	 * 每页条数
	 */
	private Integer pageSize;
	/*
	 * 总页数
	 */
	private Integer totalPage;
	/*
	 * 总记录数
	 */
	private Long totalCount;
	/*
	 * 上一页
	 */
	private Integer prev_page;
	/*
	 * 下一页
	 */
	private Integer next_page;
	/*
	 * 首页
	 */
	private Integer home_page;
	/*
	 * 尾页
	 */
	private Integer last_page;
	/*
	 * 固定导航数
	 */
	private Integer navNum;
	/*
	 * 数据集
	 */
	private List<Model> results;

	/**
	 * @param totleCount 	总记录数
	 * @param page			当前第几页
	 * @param pageSize	 	每页显示条数
	 */
	public Page(Long totleCount, Integer page, Integer pageSize) {
		
		this.page = page;
		
		this.pageSize = pageSize;
		
		//总条数
		this.totalCount = totleCount;
		
		//总页数
		this.totalPage = (int) (totleCount / pageSize);

		//起始位置
		this.startIndex = Math.max(0, (this.page - 1) * pageSize);
		
		//首页
		this.home_page = 1;
		
		//尾页
		this.last_page = this.totalPage;
		
		//上一页
		this.prev_page = Math.max(this.page - 1, home_page);

		//下一页
		this.next_page = Math.min(this.page + 1, last_page);

	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPrev_page() {
		return prev_page;
	}

	public void setPrev_page(Integer prev_page) {
		this.prev_page = prev_page;
	}

	public Integer getNext_page() {
		return next_page;
	}

	public void setNext_page(Integer next_page) {
		this.next_page = next_page;
	}

	public Integer getHome_page() {
		return home_page;
	}

	public void setHome_page(Integer home_page) {
		this.home_page = home_page;
	}

	public Integer getLast_page() {
		return last_page;
	}

	public void setLast_page(Integer last_page) {
		this.last_page = last_page;
	}

	public List<Model> getResults() {
		return results;
	}

	public void setResults(List<Model> results) {
		this.results = results;
	}

	public Integer getNavNum() {
		return navNum;
	}

	public void setNavNum(Integer navNum) {
		this.navNum = navNum;
	}

}
