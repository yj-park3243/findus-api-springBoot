package com.findus.findus.api.user;

import com.findus.findus.model.user.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {
    HashMap getUserInfo(UserVO userVo);
    int isUserExist(UserVO userVo);
    int insertUserInfo(UserVO userVo);
    int updateUserInfo(UserVO userVo);

    List<HashMap> getVersion();
    int isExistNickName(String nickName);

    int insertLog(UserVO userVo);
    void insertBanUser(HashMap vo);
    void deleteBanUser(HashMap vo);
    List<HashMap> getBanUserList(HashMap vo);

}
