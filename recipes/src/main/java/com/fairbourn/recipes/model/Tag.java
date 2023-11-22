package com.fairbourn.recipes.model;

public class Tag {

	private int id;
    private boolean selected;
    private String value;
    
    public Tag() {}
	    
    public Tag(int id, boolean selected, String value) {
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
