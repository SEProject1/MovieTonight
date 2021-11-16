package com.example.movietonight;

// 유저 계정 정보 모델 클래스
public class UserAccount {

    private String idToken; //Firebase Uid (고유 토큰정보, key 값)
    private String userId;  //이메일 아이디
    private String userNickname;    //닉네임
    private String passwd;  //비밀번호

    public UserAccount(){ }

    public String getIdToken() {return idToken;}

    public void setIdToken(String idToken) {this.idToken = idToken;}

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    public String getUserNickname() {return userNickname;}

    public void setUserNickname(String userNickname) {this.userNickname = userNickname;}

    public String getPasswd() {return passwd;}

    public void setPasswd(String passwd) {this.passwd = passwd;}
 }
