package com.jt.common.vo;

import java.io.Serializable;
import java.util.List;


public class PageObject<T> implements Serializable{

	/**实体类分装分页信息
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**当前页码*/
		private Integer pageCurrent=1;
		/**页面大小(每页最多显示多少条记录)*/
		private Integer pageSize=3;
		/**总记录数*/
		private Integer rowCount;
		/**总页数*/
		private Integer pageCount;
		/**当前页实体数据*/
		private List<T> records;
		
		public Integer getPageCurrent() {
			return pageCurrent;
		}
		public void setPageCurrent(Integer pageCurrent) {
			this.pageCurrent = pageCurrent;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		public Integer getRowCount() {
			
			return rowCount;
		}
		public void setRowCount(Integer rowCount) {
			this.rowCount = rowCount;
		}
		public Integer getPageCount() {
			//
			pageCount=rowCount/pageSize;
			if(rowCount%pageSize!=0)
				pageCount++;
			return pageCount;
		}
		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}
		public List<T> getRecords() {
			return records;
		}
		public void setRecords(List<T> records) {
			this.records = records;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
}
