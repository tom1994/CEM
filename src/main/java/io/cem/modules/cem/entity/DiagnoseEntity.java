package io.cem.modules.cem.entity;
public class DiagnoseEntity {
    private static final long serialVersionUID = 1L;

    //
    private Integer limit;
    //
    private Integer page;

    private Integer[] dispatchId;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer[] getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Integer[] dispatchId) {
        this.dispatchId = dispatchId;
    }
}
