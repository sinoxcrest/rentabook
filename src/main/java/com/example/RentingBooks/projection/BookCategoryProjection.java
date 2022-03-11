package com.example.RentingBooks.projection;

public interface BookCategoryProjection {
Integer getId();
String getCategoryName();
String getBookName();
Integer getNoOfPages();
String getISBN();
String getRating();
String getStockCount();
String getPublishedDate();
}
