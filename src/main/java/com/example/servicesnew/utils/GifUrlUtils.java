package com.example.servicesnew.utils;

import com.example.servicesnew.exception.ExternalDataErrorException;

import java.util.LinkedHashMap;
import java.util.Map;

public class GifUrlUtils {

    //Этот метод принимает в качестве аргумента объект типа Map и возвращает строку с URL-адресом GIF-изображения.
    public static String getUrlGif(Map<String, Object> o) throws ExternalDataErrorException {

        String urlGifId = "";
        String urlGif = "";

        if (o != null) {
            //Извлечение значения с ключом "data" из объекта o и приведение его к типу LinkedHashMap.
            // Значение сохраняется в переменной data типа Map.
            LinkedHashMap<String, Object> data = (LinkedHashMap) o.get("data");
            //Извлечение значения с ключом "id" из объекта data и приведение его к типу String.
            // Значение сохраняется в переменной urlGifId
            urlGifId = String.valueOf((data.get("id")));
        } else {
            throw new ExternalDataErrorException("Ошибка внешних данных в Giphy");
        }

        if (!urlGifId.isEmpty()) {
            //Формирование URL-адреса GIF-изображения, используя значение переменной urlGifId
            urlGif = "https://i.giphy.com/media/" + urlGifId + "/giphy.gif";
        } else
            throw new ExternalDataErrorException("Ошибка внешних данных в Giphy");
        return urlGif;
    }
}