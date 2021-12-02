package com.example.movietonight;

import java.util.ArrayList;

// 상위 리사이클러뷰 아이템클래스를 정의한다.
// 이때 리사이클러뷰 안에 하위리사이클러뷰 아이템으로 정의했던 SingleItem을 전역변수로 선언한다.
public class SectionItem {

    private String headerTitle;
    private ArrayList<SingleItem> singItemList;


    public SectionItem() {
    }

    public SectionItem(String headerTitle, ArrayList<SingleItem> singItemList) {
        this.headerTitle = headerTitle;
        this.singItemList = singItemList;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItem> getSingItemList() { return singItemList; }

    public void setSingItemList(ArrayList<SingleItem> singItemList) { this.singItemList = singItemList; }
}
