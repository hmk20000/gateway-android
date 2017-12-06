package org.kccc.gateway;

/**
 * Created by LG on 2016-07-27.
 * 다음 영상의 인덱스를 저장할 클래스
 */
public class TargetVO {
    private int category;
    private int index;
    private int nextInd;
    private int targetCategory;
    private int targetIndex;
    private String targetName;

    public TargetVO() {
    }

    public TargetVO(int category, int index, int nextInd, int targetCategory, int targetIndex) {
        this.category = category;
        this.index = index;
        this.nextInd = nextInd;
        this.targetCategory = targetCategory;
        this.targetIndex = targetIndex;
        this.targetName = "";
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNextInd() {
        return nextInd;
    }

    public void setNextInd(int nextInd) {
        this.nextInd = nextInd;
    }

    public int getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(int targetCategory) {
        this.targetCategory = targetCategory;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
