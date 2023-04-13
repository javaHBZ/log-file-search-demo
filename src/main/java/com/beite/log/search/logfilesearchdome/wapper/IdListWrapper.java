package com.beite.log.search.logfilesearchdome.wapper;

import java.io.Serializable;
import java.util.List;

public class IdListWrapper implements Serializable {
    private List<Long> ids;

    public IdListWrapper(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
