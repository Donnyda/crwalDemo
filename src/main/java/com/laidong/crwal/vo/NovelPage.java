package com.laidong.crwal.vo;

/**
 * @author Administrator
 *
 */
public class NovelPage {
	private String title;//标题
	private String content;//内容
	private String nextPage;//下一页连接
	private String prePage;//上一页连接
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNextPage() {
		return nextPage;
	}
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}
	public String getPrePage() {
		return prePage;
	}
	public void setPrePage(String prePage) {
		this.prePage = prePage;
	}
	
}
