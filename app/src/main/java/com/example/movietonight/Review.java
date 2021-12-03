package com.example.movietonight;

import android.widget.ImageView;

public class Review {
    String mtitle;  //영화제목
    String rtitle;  //리뷰제목
    String mgenre;  //장르
    String rcontent;    //리뷰내용
    int like ,dislike;  //좋아요, 싫어요
    String mdate;   //날짜
    String email;
    String nickname;

    public Review(String mtitle, String rtitle, String mgenre, String rcontent, int like, int dislike, String mdate, String email,String nickname) {
        this.mtitle = mtitle;
        this.rtitle = rtitle;
        this.mgenre = mgenre;
        this.rcontent=rcontent;
        this.mdate = mdate;
        this.email = email;
        this.nickname=nickname;
        this.like = like;
        this.dislike = dislike;
    }

    public Review() {

    }

    public String getMtitle(){
        return mtitle;
    }

    public String getEmail() {
        return email;
    }

    public String getRtitle() {
        return rtitle;
    }

    public String getMgenre() {
        return mgenre;
    }

    public String getRcontent() {
        return rcontent;
    }

    public String getMdate() {
        return mdate;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setLike(int like){
        this.like = like;
    }

    public void setDislike(int dislike){
        this.dislike = dislike;
    }

    public void setMtitle(String mtitle){
        this.mtitle = mtitle;
    }

    public void setRtitle(String rtitle){
        this.rtitle=rtitle;
    }

    public void setMgenre(String mgenre){
        this.mgenre=mgenre;
    }

    public void setRcontent(String rcontent){
        this.rcontent=rcontent;
    }

    public void setMdate(String mdate){
        this.mdate = mdate;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }
}
