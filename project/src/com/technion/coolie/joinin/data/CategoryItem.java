package com.technion.coolie.joinin.data;

public class CategoryItem {
	public int icon;
    public String title;
    public String num;
    public String desc;
    public CategoryItem(){
        super();
    }
    
    public CategoryItem(int icon, String title, String num, String desc) {
        super();
        this.icon = icon;
        this.title = title;
        this.num = num;
        this.desc = desc;
    }
}
