package com.ssafy.happyhouse.model;

public class NewsDto {
   private String url;
   private String title;
   private String content;
   private String writing;
   private String date;
   private String imgUrl;

   public String getImgUrl() {
      return imgUrl;
   }

   public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getWriting() {
      return writing;
   }

   public void setWriting(String writing) {
      this.writing = writing;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }
}