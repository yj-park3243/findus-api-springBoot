package com.findus.findus.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonDefaultVO {
    private int firstIndex;
    private int curPage;
    private int pageUnit;
    private int use_yn;
    private String language;
    private String search_txt;
}
