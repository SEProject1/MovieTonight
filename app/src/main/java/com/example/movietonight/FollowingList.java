package com.example.movietonight;

public class FollowingList {
    private String nickName;//사용자가 팔로우 하는 유저 닉네임
    private String idToken;//idToken으로 유저 찾아 이미지를 가져온다

    public FollowingList(String nickName, String idToken) {
        this.nickName = nickName;
        this.idToken = idToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
