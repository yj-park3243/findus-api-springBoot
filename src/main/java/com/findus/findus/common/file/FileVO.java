package com.findus.findus.common.file;

import lombok.Data;

@Data
public class FileVO {
    private Integer fileno;
    private String parentPK;
    private String filename;
    private String realname;
    private String filepath;
    private long filesize;
    private String filesizeString;
}
