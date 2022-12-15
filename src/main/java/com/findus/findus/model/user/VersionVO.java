package com.findus.findus.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class VersionVO {
    private String version_id;
    private String version_name;
    private String version_value;
    private String update_date;
}
