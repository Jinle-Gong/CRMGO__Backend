package common.entity;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

public class PageData<T> implements Serializable {
  private long total;//总记录数
  private int pages;//总页数
  private int pageNum;//当前页
  private int pageSize;//每页记录数
  private List<T> list;//数据集

  public PageData(List<T> list) {
    if (list instanceof Page) {
      Page page = (Page) list;
      this.pages = page.getPages();//总页数
      this.list = page;//数据
      this.total = page.getTotal();//总记录数
      this.pageSize = page.getPageSize();
      this.pageNum = page.getPageNum();
    }
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }
}
