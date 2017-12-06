package org.kccc.gateway;

import java.util.List;

/**
 * Created by LG on 2016-02-19.
 * 데이터의 입,출력을 담당할 함수를만드는 인터페이스 클래스
 */
public interface CRUDOperations {
    void insert(ContentsVO ContentsVO);
    void insertNextQuestion(NextVO nextVO);
    void insertTarget(TargetVO targetVO);
    void insertStart();
    void updateFavorite(ContentsVO ContentsVO);
    void updateDownload(ContentsVO ContentsVO);
    void updateHistory(ContentsVO ContentsVO);
    void updateStart(int flag);
    void updateCount(int flag);
    int getCount();
    int getStart();
    void delete(ContentsVO ContentsVO);
    int count(int flag);
    ContentsVO read(int category, int index);
    List<ContentsVO> readList(int category);
    List<ContentsVO> readFavoriteList();
    List<ContentsVO> readDownloadList();
    List<ContentsVO> readHistoryList();
    List<NextVO> readNextQuestionList(int category, int index);
    List<TargetVO> readTargetList(int category, int index, int nextInd);
}
