package org.unique.plugin.dao;

import java.util.List;

/**
 * page model
 * @author:rex
 * @date:2014年8月21日
 * @version:1.0
 */
public class Page<T> {

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
	private List<T> results;

	/**
	 * @param totleCount 	总记录数
	 * @param page			当前第几页
	 * @param pageSize	 	每页显示条数
	 */
	public Page(Long totleCount, Integer page, Integer pageSize) {

		this.page = page;

		this.pageSize = pageSize;

		//总条数
		this.setTotalCount(totleCount);

		//总页数
		this.setTotalPage((int) (totleCount / pageSize));

		//起始位置
		this.setStartIndex(Math.max(0, (this.page - 1) * pageSize));

		//首页
		this.setHome_page(1);

		//尾页
		this.setLast_page(this.totalPage);

		//上一页
		this.setPrev_page(Math.max(this.page - 1, home_page));

		//下一页
		this.setNext_page(Math.min(this.page + 1, last_page));

	}

	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartIndex() {
		return this.startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getTotalPage() {
		return this.totalPage;
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
		return this.prev_page;
	}

	public void setPrev_page(Integer prev_page) {
		this.prev_page = prev_page;
	}

	public Integer getNext_page() {
		return this.next_page;
	}

	public void setNext_page(Integer next_page) {
		this.next_page = next_page;
	}

	public Integer getHome_page() {
		return this.home_page;
	}

	public void setHome_page(Integer home_page) {
		this.home_page = home_page;
	}

	public Integer getLast_page() {
		return this.last_page;
	}

	public void setLast_page(Integer last_page) {
		this.last_page = last_page;
	}

	public List<T> getResults() {
		return this.results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Integer getNavNum() {
		return this.navNum;
	}

	public void setNavNum(Integer navNum) {
		this.navNum = navNum;
	}

}
