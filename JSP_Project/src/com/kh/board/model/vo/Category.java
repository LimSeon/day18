package com.kh.board.model.vo;

public class Category {
	private int CategoryNo; //CATEGORY_NO
	private String categoryName; //CATEGORY_NAME
	
	public Category() {
	}

	public Category(int categoryNo, String categoryName) {
		CategoryNo = categoryNo;
		this.categoryName = categoryName;
	}

	public int getCategoryNo() {
		return CategoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		CategoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "Category [CategoryNo=" + CategoryNo + ", categoryName=" + categoryName + "]";
	}

	

}
