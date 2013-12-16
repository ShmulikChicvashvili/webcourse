package com.technion.coolie.joinin.data;

public class CategoryItem {
	public int icon;
    public String title;
	public int icon2;
    public String num;
    public CategoryItem(){
        super();
    }
    
    public CategoryItem(int icon, String title,int icon2,String num) {
        super();
        this.icon = icon;
        this.title = title;
        this.icon2 = icon2;
        this.num = num;
    }
}
