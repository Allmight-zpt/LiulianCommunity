package com.zhupeiting.bisheproject.provider;

import com.alibaba.fastjson2.JSON;
import com.zhupeiting.bisheproject.dto.AccessTokenDto;
import com.zhupeiting.bisheproject.dto.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    @Value("${github.access.token.uri}")
    private String accessTokenUri;
    @Value("${github.user.uri}")
    private String userUri;
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url(accessTokenUri + "?grant_type=authorization_code")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String accessToken = string.split(":")[1].split(",")[0];
            accessToken = accessToken.substring(1,accessToken.length()-1);
            return accessToken;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(userUri + "?access_token=" + accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
