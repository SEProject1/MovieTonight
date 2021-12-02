package com.example.movietonight;

// 유저 계정 정보 모델 클래스
public class FollowUser {

    private String idToken; //Firebase Uid (고유 토큰정보, key 값)
    private String userEmail;  //이메일 아이디
    private String userNickname;    //닉네임


    public FollowUser(){ }

    public String getIdToken() {return idToken;}

    public void setIdToken(String idToken) {this.idToken = idToken;}

//    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

//    public String getUserNickname() {return userNickname;}

    public void setUserNickname(String userNickname) {this.userNickname = userNickname;}
    }

