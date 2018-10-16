package com.usigames;

import com.usigames.model.Directions;
import com.usigames.model.Item;
import com.usigames.model.ItemType;
import com.usigames.model.Room;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        run();
    }

    private static void run(){

	    Item goldKey = new Item(ItemType.KEY, "Gold Key", "pickup");
	    Item silverKey = new Item(ItemType.KEY, "Silver Key", "pickup");
	    Item bronzeKey = new Item(ItemType.KEY, "Bronze Key", "pickup");
	    Item knife = new Item(ItemType.KNIFE, "Chef's Knife", "pickup");

	    Item goldChest = new Item(ItemType.CHEST, "Locked Chest", "unlock");
	    Item silverChest = new Item(ItemType.CHEST, "Locked Chest", "unlock");
	    Item bronzeChest = new Item(ItemType.CHEST, "Unlocked Chest", "open");

	    ArrayList<Item> collectedItems = new ArrayList<>();

	    List<Room> rooms = buildRooms(goldKey, silverKey, bronzeKey, goldChest, silverChest, bronzeChest);

	    Scanner sc = new Scanner(System.in);
	    int userInput = 0;
	    int roomNumber = 0;

	    while (roomNumber >= 0) {
		    Room curRoom = rooms.get(roomNumber);
		    int i = 1;
		    HashMap<Integer, String> options = new HashMap<>();
		    System.out.println(curRoom.getDescription());

		    System.out.println("What would you like to do?");

		    for (Item item : curRoom.getItems()) {
			    System.out.println(i + ". " + item.getAction() + " " + item.getName());
			    options.put(i, item.getName());
			    i++;
		    }
		    userInput = sc.nextInt();
		    if (!options.containsKey(userInput)) {
			    System.out.println("Invalid Option");
			    userInput = sc.nextInt();
		    }

		    String selectedOption = options.get(userInput);
		    if (selectedOption.contains("Unlocked Door")) {
			    if (selectedOption.contains("North")) {
				    roomNumber = curRoom.getConnectedRooms().get(Directions.NORTH);
			    } else if (selectedOption.contains("South")) {
				    roomNumber = curRoom.getConnectedRooms().get(Directions.SOUTH);
			    } else if (selectedOption.contains("East")) {
				    roomNumber = curRoom.getConnectedRooms().get(Directions.EAST);
			    } else if (selectedOption.contains("West")) {
				    roomNumber = curRoom.getConnectedRooms().get(Directions.WEST);
			    }
		    }

		    if(selectedOption.contains("Key") && curRoom.getItems().contains(silverKey)){
		    	collectedItems.add(silverKey);
		    	curRoom.getItems().remove(silverKey);
		    }

		    if (selectedOption.contains("Unlocked Chest")) {
				if (curRoom.getItems().contains(bronzeChest) && !collectedItems.contains(bronzeKey)) {
					while(true){
						System.out.println("Inside the chest is a Key");
						System.out.println("What would you like to do?");
						System.out.println("1. pickup Bronze Key");
						System.out.println("2. leave ");

						userInput = sc.nextInt();
						if (userInput == 1) {
							collectedItems.add(bronzeKey);
							break;
						} else if (userInput == 2) {
							break;
						} else {
							System.out.println("Invalid Option");
						}
					}
				} else if (curRoom.getItems().contains(silverChest) && !collectedItems.contains(knife)){
					while(true) {

						System.out.println("Inside the chest is a Chef's Knife");
						System.out.println("What would you like to do?");
						System.out.println("1. pickup Chef's Knife");
						System.out.println("2. leave ");

						userInput = sc.nextInt();
						if (userInput == 1) {
							collectedItems.add(knife);
							break;
						} else if (userInput == 2) {
							break;
						} else {
							System.out.println("Invalid Option");
						}
					}
				} else if (curRoom.getItems().contains(goldChest) && !collectedItems.contains(goldKey)){
					while(true){
						System.out.println("Inside the chest is a Key");

						System.out.println("What would you like to do?");
						System.out.println("1. pickup Gold Key");
						System.out.println("2. leave ");

						userInput = sc.nextInt();
						if (userInput == 1 ) {
							collectedItems.add(goldKey);
							break;
						} else if (userInput == 2) {
							break;
						} else {
							System.out.println("Invalid Option");
						}
					}
				} else {
					System.out.println("This chest is empty");
				}
		    }

		    if (selectedOption.contains("Locked Chest")) {
				if (collectedItems.contains(bronzeKey) && curRoom.getItems().contains(silverChest)){
					silverChest.setAction("open");
					silverChest.setName("Unlocked Chest");
					System.out.println("Chest Unlocked!");
				} else if (collectedItems.contains(silverKey) && curRoom.getItems().contains(goldChest)) {
					goldChest.setAction("open");
					goldChest.setName("Unlocked Chest");
					System.out.println("Chest Unlocked!");
				} else {
					System.out.println("This chest is locked. You must find the Key.");
				}
		    }

		    if (selectedOption.contains("Locked Door")) {
			    if (collectedItems.contains(goldKey)) {
			    	System.out.println("You Have Escaped!");
				    roomNumber = -1;
			    } else {
			    	System.out.println("This door is locked. You must find the Key.");
			    }
		    }
	    }
    }

    private static List<Room> buildRooms(Item goldKey, Item silverKey, Item bronzeKey, Item goldChest, Item silverChest, Item bronzeChest){
        ArrayList<Room> rooms = new ArrayList<Room>();

    	rooms.add(new Room("You are in a dusty room filled floor to ceiling with bookshelves. There is a piano in the south-east corner, a door to the west, and another door to the north.",
		    new ArrayList<>(Arrays.asList(Directions.NORTH, Directions.WEST)),
		    new ArrayList<>(Arrays.asList(
			    new Item(ItemType.DOOR, "Unlocked Door(North)", "open"),
			    new Item(ItemType.DOOR, "Unlocked Door(West)", "open"),
			    new Item(ItemType.PIANO, "Grand Piano", "play")
		    )),
		    new HashMap<Directions, Integer>(){{
		        put(Directions.NORTH, 2);
		        put(Directions.WEST, 1);
            }}
    	));

	    rooms.add(new Room("You are in a dark closet. There is a chest hidden behind some dusty coats. The only door is the one you came in from to the east.",
		    new ArrayList<>(Arrays.asList(Directions.EAST)),
		    new ArrayList<>(Arrays.asList(
			    new Item(ItemType.DOOR, "Unlocked Door(East)", "open"),
			    bronzeChest
		    )),
		    new HashMap<Directions, Integer>(){{
			    put(Directions.EAST, 0);
		    }}
        ));

	    rooms.add(new Room("You are in the entry way. There is a door to the south, a door to the east, and another door to the north.",
		    new ArrayList<>(Arrays.asList(Directions.NORTH, Directions.SOUTH, Directions.EAST)),
		    new ArrayList<>(Arrays.asList(
	            new Item(ItemType.DOOR, "Locked Door(North)", "unlock"),
			    new Item(ItemType.DOOR, "Unlocked Door(South)", "open"),
			    new Item(ItemType.DOOR, "Unlocked Door(East)", "open")
		    )),
		    new HashMap<Directions, Integer>(){{
			    put(Directions.NORTH, -1);
			    put(Directions.SOUTH, 0);
			    put(Directions.EAST, 3);
		    }}
	    ));
	    rooms.add(new Room("You are the great hall. There is a large table in the middle of the room with scraps of rotten food. There is a chest in the corner, a door to the west, and another door to the south.",
		    new ArrayList<>(Arrays.asList(Directions.SOUTH, Directions.WEST)),
		    new ArrayList<>(Arrays.asList(
	            new Item(ItemType.DOOR, "Unlocked Door(South)", "open"),
			    new Item(ItemType.DOOR, "Unlocked Door(West)", "open"),
			    goldChest
		    )),
		    new HashMap<Directions, Integer>(){{
			    put(Directions.SOUTH, 4);
			    put(Directions.WEST, 2);
		    }}
	    ));

	    rooms.add(new Room("You are in the kitchen. There is a chest in the north-east corner, a door to the south, and another door to the north.",
		    new ArrayList<>(Arrays.asList(Directions.NORTH, Directions.SOUTH)),
		    new ArrayList<>(Arrays.asList(
	            new Item(ItemType.DOOR, "Unlocked Door(North)", "open"),
			    new Item(ItemType.DOOR, "Unlocked Door(South)", "open"),
			    silverChest
		    )),
		    new HashMap<Directions, Integer>(){{
			    put(Directions.NORTH, 3);
			    put(Directions.SOUTH, 5);
		    }}
	    ));

	    rooms.add(new Room("You are in a cold dark wine cellar. There are stacks of wine bottles and a small key on the floor. The only door is the one you came in from to the north.",
		    new ArrayList<>(Arrays.asList(Directions.NORTH)),
		    new ArrayList<>(Arrays.asList(
	            new Item(ItemType.DOOR, "Unlocked Door(North)", "open"),
				silverKey
		    )),
		    new HashMap<Directions, Integer>(){{
			    put(Directions.NORTH, 4);
		    }}
	    ));


	    return rooms;
    }
}
