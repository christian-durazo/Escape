package com.usigames.model;

import java.util.List;
import java.util.Map;

public class Room {
	private String description;
	private List<Directions> directions;
	private List<Item> items;
	private Map<Directions, Integer> connectedRooms;

	public Room(String description, List<Directions> directions, List<Item> items, Map<Directions, Integer> connectedRooms) {
		this.description = description;
		this.directions = directions;
		this.items = items;
		this.connectedRooms = connectedRooms;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Directions> getDirections() {
		return directions;
	}

	public void setDirections(List<Directions> directions) {
		this.directions = directions;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Map<Directions, Integer> getConnectedRooms() {
		return connectedRooms;
	}

	public void setConnectedRooms(Map<Directions, Integer> connectedRooms) {
		this.connectedRooms = connectedRooms;
	}
}
