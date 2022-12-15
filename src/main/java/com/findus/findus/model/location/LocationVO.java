package com.findus.findus.model.location;

import com.findus.findus.model.common.CommonDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocationVO extends CommonDefaultVO {

    private BigInteger location_id;
    private String latitude;
    private String longitude;
    private String auth_token;
}
