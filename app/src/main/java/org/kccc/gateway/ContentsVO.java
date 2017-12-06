package org.kccc.gateway;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by LG on 2016-06-24.
 */
public class ContentsVO extends RealmObject{
    private int category;
    private int index;
    private int order;
    private String title;
    private String subTitle;
    private int favorite;
    private long favoriteDate;
    private int download;
    private long downloadDate;
    private int history;
    private long historyDate;
    private String URL;
    private String keyWord;
    private String description;
    private RealmList<QuestionVO> question1;
    private RealmList<QuestionVO> question2;
    private String time;
    private RealmList<NextVO> next;

    public ContentsVO() {
    }

    public ContentsVO(int category, int index, int order, String title, String subTitle, String url, String keyWord, String description, RealmList<QuestionVO> question1, RealmList<QuestionVO> question2, String time, RealmList<NextVO> next) {
        this.category = category;
        this.index = index;
        this.title = title;
        this.order = order;
        this.subTitle = subTitle;
        this.favorite = 0;
        this.favoriteDate = 0;
        this.download = 0;
        this.downloadDate = 0;
        this.history = 0;
        this.historyDate = 0;
        this.URL = url;
        this.keyWord = keyWord;
        this.description = description;
        this.question1 = question1;
        this.question2 = question2;
        this.time = time;
        this.next = next;
    }

    public int getCategory() { return category; }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public long getFavoriteDate() {
        return favoriteDate;
    }

    public void setFavoriteDate(long favoriteDate) {
        this.favoriteDate = favoriteDate;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public long getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(long downloadDate) {
        this.downloadDate = downloadDate;
    }

    public int getHistory() { return history; }

    public void setHistory(int history) {
        this.history = history;
    }

    public long getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(long historyDate) {
        this.historyDate = historyDate;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<QuestionVO> getQuestion1() {
        return question1;
    }

    public void setQuestion1(RealmList<QuestionVO> question1) { this.question1 = question1; }

    public RealmList<QuestionVO> getQuestion2() { return question2; }

    public void setQuestion2(RealmList<QuestionVO> question2) { this.question2 = question2; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public RealmList<NextVO> getNext() { return next; }

    public void setNext(RealmList<NextVO> next) { this.next = next; }
}
