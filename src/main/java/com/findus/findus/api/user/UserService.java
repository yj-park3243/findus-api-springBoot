package com.findus.findus.api.user;

import com.findus.findus.common.exception.FileFormatException;
import com.findus.findus.common.file.CustomFileUtil;
import com.findus.findus.common.interceptor.LoggerInterceptor;
import com.findus.findus.common.jwt.JwtTokenProvider;
import com.findus.findus.common.file.FileVO;
import com.findus.findus.model.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomFileUtil customFileUtil;

    public HashMap login(UserVO userVo, HttpServletRequest request) {
        /*try{
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(userVo.getAuth_token());
            if(userRecord.isDisabled()|| userRecord.isEmailVerified())
                throw new UserNotFoundException("Do not have user.");
        }catch (Exception e){
            throw new UserNotFoundException("Do not have user.");
        }*/

        HashMap map = new HashMap();
        final int result = userMapper.isUserExist(userVo)==0 ? userMapper.insertUserInfo(userVo) : userMapper.updateUserInfo(userVo);
        map.put("result" , jwtTokenProvider.createToken(userMapper.getUserInfo(userVo)));
        map.put("version" , userMapper.getVersion() );

        userVo.setIp(new LoggerInterceptor(jwtTokenProvider).getClientIP(request));
        userMapper.insertLog(userVo);
        return map;
    }

    public HashMap getVersion() {
        HashMap map = new HashMap();
        map.put("version" , userMapper.getVersion() );
        return map;
    }

    public void userUpdate(UserVO vo) {
        userMapper.updateUserInfo(vo);
    }
    public HashMap uploadProfile(UserVO vo ){
        if (vo.getFile() != null) {
            String org_name = vo.getFile().getOriginalFilename();
            String pjt = "";
            pjt = org_name.substring(org_name.lastIndexOf('.') + 1, org_name.length()); // 확장자

            if (pjt != null && !"".equals(pjt)) {
                if (pjt.equals("jpg") || pjt.equals("gif") || pjt.equals("png") || pjt.equals("jpeg") || pjt.equals("bmp")) {

                    FileVO fileVO = customFileUtil.saveSingleFile("profile", vo.getFile());
                    vo.setProfile_url(fileVO.getFilename());

                    userMapper.updateUserInfo(vo);

                } else {
                    throw new FileFormatException("업로드 불가능 파일");
                }

            }
        }
        HashMap map = new HashMap();
        map.put("profileUrl" , vo.getProfile_url());
        return map;
    }

    public HashMap isExistNickName(String nickname){
        HashMap map = new HashMap();
        map.put("value" ,  userMapper.isExistNickName(nickname) == 0);
        return map;
    }

    public HashMap insertBanUser(String vanUserToken , HttpServletRequest request){
        String authToken = jwtTokenProvider.getAuthToken(jwtTokenProvider.resolveToken(request));
        HashMap map = new HashMap();
        map.put("auth_token" , authToken);
        map.put("ban_token" , vanUserToken);
        if(userMapper.getBanUserList(map).isEmpty()){
            userMapper.insertBanUser(map);
        }
        return null;
    }

    public HashMap getBanUserList(HttpServletRequest request){
        String authToken = jwtTokenProvider.getAuthToken(jwtTokenProvider.resolveToken(request));
        HashMap map = new HashMap();
        map.put("auth_token" , authToken);
        HashMap payload = new HashMap();
        payload.put("result" , userMapper.getBanUserList(map));
        return payload;
    }


    public HashMap deleteBanUser(String vanUserToken , HttpServletRequest request){
        String authToken = jwtTokenProvider.getAuthToken(jwtTokenProvider.resolveToken(request));
        HashMap map = new HashMap();
        map.put("auth_token" , authToken);
        map.put("ban_id" , vanUserToken);
        userMapper.deleteBanUser(map);
        return null;
    }

}
