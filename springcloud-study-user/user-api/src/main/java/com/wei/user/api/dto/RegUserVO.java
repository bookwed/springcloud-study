package com.wei.user.api.dto;

import java.util.List;

public class RegUserVO extends CommonPageResult{
    private List<RegUserDTO> records;

    public List<RegUserDTO> getRecords() {
        return records;
    }

    public void setRecords(List<RegUserDTO> records) {
        this.records = records;
    }
}
