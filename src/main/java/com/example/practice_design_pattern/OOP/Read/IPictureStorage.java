package com.example.practice_design_pattern.OOP.Read;

import java.awt.*;

/**
 * @author akk
 * @className IPictureStorage
 * @description:
 * @date 2021/7/13 16:22
 */
// 抽象
public interface IPictureStorage {
    void savePicture(String picture);
    Image getPicture(String pictureId);
    void deletePicture(String pictureId);
    void modifyMetaInfo(String pictureId, String metaInfo);
}
