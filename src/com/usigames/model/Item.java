package com.usigames.model;

public class Item {
	ItemType type;
	String name;
	String action;

	public Item(ItemType type, String name, String action) {
		this.type = type;
		this.name = name;
		this.action = action;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "Item{" +
				"type=" + type +
				", name='" + name + '\'' +
				", action='" + action + '\'' +
				'}';
	}
}
