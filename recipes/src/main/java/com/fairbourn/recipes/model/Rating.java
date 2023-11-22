package com.fairbourn.recipes.model;

public class Rating {
	
    private int id;
    private boolean selected;
    private int value;
    
    public Rating() {}
    
    public Rating(int id, boolean selected, int value) {
    	this.id = id;
    	this.selected = selected;
    	this.value = value;
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
    
}
