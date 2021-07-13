package com.example.practice_design_pattern.OOP.Read;

import java.awt.*;

/**
 * @author akk
 * @className PictureStorage
 * @description:
 * @date 2021/7/13 16:23
 */
// 抽象
public class PictureStorage implements IPictureStorage{
    @Override
    public void savePicture(String picture) {

    }

    @Override
    public Image getPicture(String pictureId) {
        return null;
    }

    @Override
    public void deletePicture(String pictureId) {

    }

    @Override
    public void modifyMetaInfo(String pictureId, String metaInfo) {

    }
}
