import java.io.*;
// Fixed: BUG!!!!!!!!!!!!!!!!!!!! could not open door after failed attempt!! (check useStuff)
public class QuestGame {
	public static BufferedReader readInput;
	public static int choice = 0;
	public static String justCont;
	public static int[] inventory = new int [10];
	public static int[] timesEntered = new int [5];
	public static String[] inventoryNames = new String [10];
	public static int fireLevel = 0;
	public static int fireScene1 = 27;
	public static int fireScene2 = 33;
	public static int fireScene3 = 36;
	public static int fireScene4 = 42;
	public static int fireScene5 = 24;
	public static int transition;
	public static int[][] doors = new int [6][2];
	public static int[][] windows = new int [5][2];
	public static int[][] escape = new int [3][2];
	public static int currentScene;
	public static int difficulty;
	public static int guy;
	public static void main(String[] args) throws IOException{
		readInput = new BufferedReader(new InputStreamReader(System.in));
		do{
			int execute = mainMenu();
			if (execute == 2){
				System.out.println("HELP");
			}else if (execute == 1){
				execute = introStory(0);
			}
		}while (true);
	}
	
	public static int mainMenu() throws IOException{
		do{
			System.out.println("\t\tNCC Catastrophe Game\n");
			System.out.println("\t1. Start Game\n\t2. Help\n\t3. Exit");
			System.out.println("Type an integer corresponding to options to continue.");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException i){
				choice = 0;
				continue;
			}
			if (choice == 1){
				do{
					System.out.println("\nChoose a difficulty:\n\t1. Easy\n\t2. Medium\n\t3. Hard\n\t4. Impossible");
					try{
						difficulty = Integer.parseInt(readInput.readLine());
					}catch (NumberFormatException i){
						difficulty = 0;
						continue;
					}
					if (difficulty == 1||difficulty == 2||difficulty == 3||difficulty == 4){
						break;
					}else{
						continue;
					}
				}while (true);
				justCont = initialize();
				break;
			}else if (choice == 3){
				System.exit(0);
			}
		}while (true);
		return choice;
	}
	public static int introStory(int place) throws IOException{
		if (place == 0){
			System.out.println("Your eyes hurt. They really hurt.");
			System.out.println("Smoke. Black smoke.\nMust be fire. [cough]");
			System.out.println("You snatch your thermostat. At 60 C you know you'll die.");
			if (difficulty == 4){
				System.out.println("You hear a scream. For some reason you're obliged to help him.");
			}
			System.out.println("There's a horrible, blaring sound. The fire alarm. What now?\nPress any key to continue.");
			justCont = readInput.readLine();
			transition = scenario1();
		}
		
		do{
			if (transition == 0){
				System.out.println("As the fire approaches, you resign yourself to your fate, and embrace the ever-consuming fire in your arms.\n");
				System.out.println("GAME OVER\nYou died!\nPress any key to continue.");
				justCont = readInput.readLine();
			}else if (transition == 1){
				transition = scenario1();
				continue;
			}else if (transition == -11){
				System.out.println("A possible escape route. But how can you jump down? No one's there to catch you!\nA rope would have been nice...");
				transition = scenario1a();
				continue;
			}else if (transition == 11){
				transition = scenario1a();
				continue;
			}else if (transition == 2){
				transition = scenario2();
				continue;
			}else if (transition == -2){
				if (difficulty == 4){
					System.out.println("The corridor. Most doors are ajar. The others are quite destroyed. Seems like everyone's left, except that guy who screamed.");
				}else{
					System.out.println("The corridor. Most doors are ajar. The others are quite destroyed. Seems like everyone's left.");
				}
				transition = scenario2();
				continue;
			}else if (transition == 3){
				transition = scenario3();
				continue;
			}else if (transition == -3){
				System.out.println("You've reached the door to the first floor. It's jammed.\nThe wall beside the emergency exit caved in. You can't exit from there.");
				transition = scenario3();
				continue;
			}else if (transition == -41){
				System.out.println("The first floor. There's a lounge and some rooms. A broken, shattered vending machine lies at the corner.\nThe ceilling between the storm doors and the interior doors has collapsed.");
				transition = scenario4a();
				continue;
			}else if (transition == 41){
				transition = scenario4a();
				continue;			
			}else if (transition == 411){
				transition = scenario4a1();
				continue;
			}else if (transition == 42){
				transition = scenario4b();
				continue;
			}else if (transition == -51){
				System.out.println("The basement seems to be much cooler than the other places, but the building's heating up.\nWhat would you find here?");
				transition = scenario5a();
				continue;
			}else if (transition == 51){
				transition = scenario5a();
				continue;
			}else if (transition == 52){
				transition = scenario5b();
				continue;
			}
			else if (transition == 1000){
				System.out.println("You manage to escape the burning building and seek shelter");
				System.out.println("YOU ESCAPED!!!\nYou won the game!\nYour Score: " + fireLevel + " moves\nPress enter to continue.");
				justCont = readInput.readLine();
			}
			break;
		}while(true);
		
		return 1;
	}
	
	public static int scenario1() throws IOException{
		timesEntered[0]++;
		currentScene = 1;
		do{
			System.out.println("Temperature: " + fireScene1 + " C");
			if (fireScene1 >= 60){
				break;
			}
			justCont = fireState(1);
			System.out.println("You have four options:\n1. Try to open the door to the corridor\n2. Try to open the window (no increase in temp. if it is broken)\n3. Explore the room\n4. Check your inventory.");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			
			if (choice == 4){
				justCont = viewInvent();
				continue;
			}
			
			if (choice == 1){
				
				if (doors[0][0] > 0){
					justCont = increaseFire();
					System.out.println("Temperature: " + fireScene1 + " C");
					System.out.println("The door's open and you step into the corridor.\nPress any key to continue.");
					justCont = readInput.readLine();
					return 2;
				}else{
					transition = useStuff(1);
					if (transition == 1){
						return -2;
					}else if (transition == 0){
						continue;
					}
				}
				
				
			}
			
			else if (choice == 2){
				
				if (windows[0][0] > 0){
					System.out.println("Temperature: " + fireScene1 + " C");
					System.out.println("The window is already broken.\nPress enter to continue");
					justCont = readInput.readLine();
					return 11;
				}else{
					transition = useStuff(-1);
					if (transition == 1){
						return -11;
					}else if (transition == 0){
						continue;
					}			
				
				}
			}
			
			else if (choice == 3){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene1 + " C");
				if (inventory[0] > 0){
					System.out.println("There is nothing that you could find that would be useful. Wasted.\nPress any key to continue.");
					justCont = readInput.readLine();
					continue;
				}else{
					justCont = inventMethod(0);
					System.out.println("You just found a " + inventoryNames[0] + ". What could it possibly do?\nPress any key to continue.");
					justCont = readInput.readLine();
					continue;
				}
			}
			
		}while (true);
		return -1;
	}

	public static int scenario1a() throws IOException{
		do{
			System.out.println("Temperature: " + fireScene1 + " C");
			if (fireScene1 >= 60){
				break;
			}
			justCont = fireState(1);
			System.out.println("You have three options:\n1. Use an item to get you down.\n2. Exit this menu (won't affect temperature).\n3. Check inventory.");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (choice == 3){
				justCont = viewInvent();
				continue;
			}
			if (choice == 2){
				System.out.println("Temperature: " + fireScene1 + " C");
				System.out.println("You draw away from your window to your room.\nPress enter to continue.");
				justCont = readInput.readLine();
				return 1;
			}
			if (choice == 1){
				transition = useStuff(9);
				if (transition == 1){
					return 1000;
				}else if (transition == 0){
					continue;
				}else if (transition == -1){
					return 0;
				}
			}
			
			continue;
		}while(true);		
		return -1;
	}
	
	public static int scenario2() throws IOException{
		currentScene = 2;
		do{
			System.out.println("Temperature: " + fireScene2 + " C");
			if (fireScene2 >= 60){
				break;
			}
			justCont = fireState(2);
			if (difficulty == 4){
				System.out.println("You have five options.\n1. Return to your room\n2. Explore the corridor\n3. Go downstairs (there is a jammed door)\n4. View inventory\n5. Locate the door where the screaming was heard.");
			}else{
				System.out.println("You have four options.\n1. Return to your room\n2. Explore the corridor\n3. Go downstairs (there is a jammed door)\n4. View inventory");
			}
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (difficulty == 4){
				if (choice == 5){
					if (doors[5][0] > 0){
						justCont = increaseFire();
						System.out.println("Temperature: " + fireScene2 + " C");
						System.out.println("The door's already opened!\nPress enter to continue.");
						justCont = readInput.readLine();
					}else{
						System.out.println("You located the door where you heard the screaming from. It's jammed!");
						transition = useStuff(6);
						if (transition == 0){
							continue;
						}
					}
					if (doors[5][0] > 0 && guy == 0){
						System.out.println("Fortunately, he can still walk.\n Press any key to continue.");
						justCont = readInput.readLine();
						guy++;
					}else if (guy > 0){
						System.out.println("The room's empty.\n");
					}
				}
			}
			if (choice == 4){
				justCont = viewInvent();
				continue;
			}
			if (choice == 1){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene2 + " C");
				System.out.println("You return to your room.\nPress enter to continue.");
				justCont = readInput.readLine();
				return 1;
			}else if (choice == 2){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene2 + " C");
				if (inventory[1] > 0){
					System.out.println("There's nothing you could find. Wasted.\nPress enter to continue.");
					justCont = readInput.readLine();
					continue;
				}else{
					inventory[1]++;
					System.out.println("You just found a " + inventoryNames[1] + ". You found it lying on the ground, and one end seems to be slightly melted.\nPress any key to continue.");
					justCont = readInput.readLine();
					continue;
				}
			}else if (choice == 3){
				if (difficulty == 4 && guy == 0){
					System.out.println("You can't descend a level without the guy you heard screaming.");
					continue;
				}
				if (doors[1][0] > 0){
					justCont = increaseFire();
					System.out.println("Temperature: " + fireScene2 + " C");
					System.out.println("The door's already opened and you descend the stairs.\nPress enter to continue.");
					justCont = readInput.readLine();
					return 3;
				}else{
					transition = useStuff(2);
					if (transition == 1){
						return -3;
					}else if (transition == 0){
						continue;
					}
				}
			}
			continue;
		}while (true);
		return -1;
	}
	

	public static int scenario3() throws IOException{
		currentScene = 3;
		do{
			System.out.println("Temperature: " + fireScene3 + " C");
			if (fireScene3 >= 60){
				break;
			}
			justCont = fireState(3);
			System.out.println("You are currently in the Western staircase.");
			System.out.println("You have five options:\n1. Go upstairs\n2. Explore the area\n3. Go to the main floor (there is a jammed door)\n4. Go down the staircase\n5. Check inventory.");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (choice == 5){
				justCont = viewInvent();
				continue;
			}
			if (choice == 1){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene3 + " C");
				System.out.println("You ascend the stairs to the secnd floor.\nPress any key to continue.");
				justCont = readInput.readLine();
				return 2;
			}else if (choice == 2){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene3 + " C");
				System.out.println("There's nothing here. Wasted\nPress any key to continue.");
				justCont = readInput.readLine();
				continue;
			}else if (choice == 3){
				if (doors[2][0] > 0){
					justCont = increaseFire();
					System.out.println("Temperature: " + fireScene3 + " C");
					System.out.println("The door's already opened and you enter the main floor.\nPress enter to continue.");
					justCont = readInput.readLine();
					return 41;
				}else{
					transition = useStuff(3);
					if (transition == 1){
						return -41;
					}else if (transition == 0){
						continue;
					}
				}
			}else if (choice == 4){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene3 + " C");
				System.out.println("You manage to vault over the railings and descend the staricase.\nBut you know there's no going back this way.\nPress any key to continue.");
				justCont = readInput.readLine();
				return -51;
			}
			continue;
		}while(true);		
		return -1;
	}
	
	public static int scenario4a() throws IOException{
		currentScene = 41;
		do{
			System.out.println("Temperature: " + fireScene4 + " C");
			if (fireScene4 >= 60){
				break;
			}
			justCont = fireState(4);
			System.out.println("You are currently in the Western half of the first floor.");
			System.out.println("You have four options:\n1. Go to Eastern half of first floor\n2. Explore the area\n3. Go to the lounge\n4. Check inventory");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (choice == 4){
				justCont = viewInvent();
				continue;
			}
			if (choice == 1){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene4 + " C");
				System.out.println("You move to the Eastern half of the first floor.\nPress enter to continue.");
				justCont = readInput.readLine();
				return 42;
			}if (choice == 2){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene4 + " C");
				System.out.println("You managed to find nothing here. Maybe you'll find better luck elsewhere.\nPress any key to continue.");
				justCont = readInput.readLine();
				continue;
			}else if (choice == 3){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene4 + " C");
				System.out.println("You move to the lounge. Maybe there's something useful there.\nPress enter to continue.");
				justCont = readInput.readLine();
				return 411;
			}
			continue;
		}while(true);
		return -1;
	}
	public static int scenario4a1() throws IOException{
		currentScene = 41;
		do{
			System.out.println("Temperature: " + fireScene4 + " C");
			if (fireScene4 >= 60){
				break;
			}
			justCont = fireState(4);
			System.out.println("There's a lot of fire here. Be careful!\nYou are currently in the lounge.");
			System.out.println("You have three options:\n1. Return to Western half of first floor\n2. Explore the lounge\n3. Check inventory");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (choice == 3){
				justCont = viewInvent();
				continue;
			}
			if (choice == 1){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene4 + " C");
				System.out.println("You return to the Western half of the first floor.\nPress any key to continue.");
				justCont = readInput.readLine();
				return 41;
			}else if (choice == 2){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene4 + " C");
				System.out.println("You notice a shiny object, and find it pretty. There seems to be some rubble over it.\nIt could prove to be very useful...");
				System.out.println("\nDo you wish to pick up the object? (y/yes or n/no)");
				justCont = readInput.readLine();
				if (justCont.equalsIgnoreCase("yes")||justCont.equalsIgnoreCase("y")){
					System.out.println("Oh no! The fiery rubble topples over you. There's no chance for escape.\nShouldn't have taken that thing...\nPress any key to continue.");
					justCont = readInput.readLine();
					return 0;
				}else{
					continue;
				}
			}
			break;
		}while(true);
		return -1;
	}
	
	public static int scenario4b() throws IOException{
		currentScene = 42;
		do{
			System.out.println("Temperature: " + fireScene4 + " C");
			if (fireScene4 >= 60){
				break;
			}
			justCont = fireState(4);
			System.out.println("You are currently in the Eastern half of the first floor.");
			System.out.println("You have four options:\n1. Go to Western half of first floor.\n2. Explore the area\n3. Try to open exit door\n4. Check inventory");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (choice == 4){
				justCont = viewInvent();
				continue;
			}
			if (choice == 1){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene4 + " C");
				System.out.println("You move to the Western half of the first floor.\nPress enter to continue.");
				justCont = readInput.readLine();
				return 41;
			}else if (choice == 2){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene4 + " C");
				System.out.println("There's nothing here.\nPress enter to continue.");
				justCont = readInput.readLine();
				continue;
			}else if (choice == 3){
				transition = useStuff(10);
				if (transition == 1){
					return 1000;
				}else if (transition == 0){
					continue;
				}else if (transition == -1){
					return 0;
				}
			}
			
			continue;
		}while(true);
		return -1;
	}
	
	public static int scenario5a() throws IOException{
		currentScene = 51;
		do{
			System.out.println("Temperature: " + fireScene5 + " C");
			if (fireScene5 >= 60){
				break;
			}
			justCont = fireState(5);
			System.out.println("You are currently in the Western half of the basement.");
			System.out.println("You have four options:\n1. Go up the stairs (looks impossible)\n2. Go to Eastern half of first floor.\n3. Explore the area\n4. Check inventory");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (choice == 4){
				justCont = viewInvent();
				continue;
			}
			if (choice == 1){
				transition = useStuff(11);
				if (transition == 1){
					System.out.println("You managed to vault over the stairs with the rope!\n");
					return 3;
				}else if (transition == 0){
					continue;
				}else if (transition == -1){
					return 0;
				}
			}else if (choice == 2){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene5 + " C");
				System.out.println("You move to the Eastern half of the basement.\nPress enter to continue.");
				justCont = readInput.readLine();
				return 52;
			}else if (choice == 3){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene5 + " C");
				if (inventory[2] > 0){
					System.out.println("There's nothing you could find. Wasted.\nPress enter to continue.");
					justCont = readInput.readLine();
					continue;
				}else{
					inventory[2]++;
					System.out.println("You just found a " + inventoryNames[2] + ". You found it lying on the ground near a busted door.\nIt strikes you as something you wanted...\nPress enter to continue.");
					justCont = readInput.readLine();
					continue;
				}
			}
			
			continue;
		}while(true);
		return -1;
	}
	
	public static int scenario5b() throws IOException{
		currentScene = 52;
		do{
			System.out.println("Temperature: " + fireScene5 + " C");
			if (fireScene5 >= 60){
				break;
			}
			justCont = fireState(5);
			System.out.println("You are currently in the Eastern half of the basement.");
			System.out.println("You have three options:\n1. Go to Western half of first floor.\n2. Ascend the stairs (there seems to be a lot of rubble)\n3. Explore the area\n4. Check inventory");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (choice == 4){
				justCont = viewInvent();
				continue;
			}
			if (choice == 1){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene5 + " C");
				System.out.println("You move to the Western half of the basement.\nPress enter to continue.");
				justCont = readInput.readLine();
				return 51;
			}else if (choice == 2){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene5 + " C");
				System.out.println("In your attempt to ascend the stairs, you slip and fall straight into the fire.\nPress enter to continue.");
				justCont = readInput.readLine();
				return 0;
			}else if (choice == 3){
				justCont = increaseFire();
				System.out.println("Temperature: " + fireScene5 + " C");
				System.out.println("You found nothing!\nPress enter to continue.");
				justCont = readInput.readLine();
				continue;
			}
			continue;
		}while(true);
		return -1;
	}
	
	// IMPLEMENTED!!!!
	public static int useStuff(int scenDoorWind) throws IOException{
		int recess = scenDoorWind;
		do{
			scenDoorWind = recess;
			if (scenDoorWind >= 9){
				if (difficulty == 4){
					if (guy < 1){
						System.out.println("You can't escape without helping that guy you heard screaming.");
						return 0;
					}
				}
				scenDoorWind = scenDoorWind - 9;
			}
			System.out.print("Pick an item from your inventory to open the ");
			if (scenDoorWind > 0){
				System.out.println("door.");
			}else if (scenDoorWind < 0||recess >= 9){
				System.out.println("window.");
			}
			System.out.println("Type the number associated with your choice. Type a negative number to exit. (Smallest integer value is -2147483648)");
			System.out.println("INVENTORY\n");
			for(int x = 0; x < 10; x++){
				if (inventory[x] > 0){
					System.out.println(x + "\t" + inventoryNames[x]);
				}
				
			}
			System.out.println("----End of inventory----");
			try{
				choice = Integer.parseInt(readInput.readLine());
			}catch (NumberFormatException e){
				continue;
			}
			if (choice > 9){
				choice = 0;
				continue;
			}else if (choice < 0){
				return 0;
			}			
			
			if (inventory[choice] > 0){
				justCont = increaseFire();
				System.out.print("Temperature: ");
				if (currentScene == 1){
					System.out.println(fireScene1 + " C");
				}else if (currentScene == 2){
					System.out.println(fireScene2 + " C");
				}else if (currentScene == 3){
					System.out.println(fireScene3 + " C");
				}else if (currentScene == 41||currentScene == 42||currentScene == 411){
					System.out.println(fireScene4 + " C");
				}else if (currentScene == 51||currentScene == 52){
					System.out.println(fireScene5 + " C");
				}
				if (recess >= 9){
					
					if (escape[scenDoorWind][1] == choice){
						escape[scenDoorWind][0]++;
						if (difficulty == 4){
							System.out.println("You manage to get out using " + inventoryNames[choice] + ", but your friend needs more time...\nPress enter to continue.");
							justCont = readInput.readLine();
							justCont = increaseFire();
							System.out.print("Temperature: ");
							if (currentScene == 1){
								System.out.println(fireScene1 + " C");
								if (fireScene1 >= 60){
									System.out.println("Your friend collapsed under the heat, and as you rush to save him, you collapse.");
									return -1;
								}
							}else if (currentScene == 2){
								System.out.println(fireScene2 + " C");
								if (fireScene2 >= 60){
									System.out.println("Your friend collapsed under the heat, and as you rush to save him, you collapse.");
									return -1;
								}
							}else if (currentScene == 3){
								System.out.println(fireScene3 + " C");
								if (fireScene3 >= 60){
									System.out.println("Your friend collapsed under the heat, and as you rush to save him, you collapse.");
									return -1;
								}
							}else if (currentScene == 41||currentScene == 42||currentScene == 411){
								System.out.println(fireScene4 + " C");
								if (fireScene4 >= 60){
									System.out.println("Your friend collapsed under the heat, and as you rush to save him, you collapse.");
									return -1;
								}
							}else if (currentScene == 51||currentScene == 52){
								System.out.println(fireScene5 + " C");
								if (fireScene5 >= 60){
									System.out.println("Your friend collapsed under the heat, and as you rush to save him, you collapse.");
									return -1;
								}
							}
							System.out.println("Your friend made it!\nPress any key to continue.");
							justCont = readInput.readLine();
							return 1;
						}
						System.out.println("You used the " + inventoryNames[choice] + " to escape!\nPress enter to continue.");
						justCont = readInput.readLine();
						return 1;
					}else{
						System.out.println("You could not escape. Aaargh!\nPress enter to continue.");
						justCont = readInput.readLine();
						continue;
					}
				}
				if (scenDoorWind < 0){
					scenDoorWind = -scenDoorWind - 1;
					if (windows[scenDoorWind][1] == choice){
						windows[scenDoorWind][0]++;
						System.out.println("You used the " + inventoryNames[choice] + " to open the window!\nPress enter to continue.");
						justCont = readInput.readLine();
						return 1;
					}else{
						System.out.println("You could not open the window with " + inventoryNames[choice] + "!\nPress enter to continue.");
						justCont = readInput.readLine();
						continue;
					}
				}
				if (recess < 9){
					scenDoorWind = scenDoorWind - 1;
				}
				
				if (doors[scenDoorWind][1] == choice){
					doors[scenDoorWind][0]++;
					System.out.println("You used the " + inventoryNames[choice] + " to open the door!\nPress enter to continue.");
					justCont = readInput.readLine();
					return 1;
				}else{
					System.out.println("You could not open the door with " + inventoryNames[choice] + "!\nPress enter to continue.");
					justCont = readInput.readLine();
					continue;
				}
			}else{
				System.out.println("You do not have such an item.\nPress any key to continue.");
				justCont = readInput.readLine();
				continue;
			}
		}while (true);
		
	}
	
	public static String inventMethod(int getNew){
		for(int x = 0; x < 10; x++){
			if (getNew == x){
				inventory[x]++;
			}			
		}
		return "true";
	}
	
	public static String viewInvent(){
		System.out.println("INVENTORY\n");
		for(int x = 0; x < 10; x++){
			if (inventory[x] > 0){
				System.out.println(inventoryNames[x]);
			}
		
		}
		System.out.println("----End of inventory----\n");
		if (difficulty == 1){
			System.out.println("Room: " + fireScene1 + " C");
			System.out.println("2nd Level Corridor: " + fireScene2 + " C");
			System.out.println("1st Level Western Landing: " + fireScene3 + " C");
			System.out.println("Main Floor: " + fireScene4 + " C");
			System.out.println("Basement: " + fireScene5 + " C\n");
		}
		return "finished";
	}
	
	public static String fireState(int place){
		if (place == 1){
			if (fireScene1 <= 30){
				System.out.println("The fire hasn't come anywhere close to your room. Awesome!");
			}else if (fireScene1 <= 37){
				System.out.println("You can see some flames at the end of the corridor from your room. You've still got some time.");
			}else if (fireScene1 <= 45){
				System.out.println("The fire's right outside. Your room is starting to light up with the fire's light.");
			}else if (fireScene1 <= 60){
				System.out.println("Your face is lit up by the fire. It's engulfing your belongings. You're next.");
			}
		}else if (place == 2){
			if (fireScene2 <= 36){
				System.out.println("The corridor's spared from the fire, but you see an orangish glow on the walls.");
			}else if (fireScene2 <= 44){
				System.out.println("The fire's creeping up the stairs. The drywall dust seems to be an excellent fuel for fire. Its still small though.");
			}else if (fireScene2 <= 52){
				System.out.println("The fire's coming. The fire thrived upstairs and utterly destroyed the ceiling, sending fiery chunks of drywall to the ground.");
			}else if (fireScene2 <= 60){
				System.out.println("It's excruciatingly hot. It's everywhere.");
			}
		}else if (place == 3){
			if (fireScene3 <= 40){
				System.out.println("The flames are growing rapidly. It's getting quite hot.");
			}else if (fireScene3 <= 48){
				System.out.println("Some electrical wiring is sparking beneath the caved wall. The fire is slowly creeping up the walls and stairs.");
			}else if (fireScene3 <= 60){
				System.out.println("The heat's getting unbearable. You need to leave this place!!");
			}
		}else if (place == 4){
			
		}
		return "yay";
	}
	public static String increaseFire(){
		fireScene1++;
		fireScene2++;
		fireScene3++;
		fireScene4++;
		fireScene5++;
		fireLevel++;
		if (guy > 0){
			fireScene1++;
			fireScene2++;
			fireScene3++;
			fireScene4++;
			fireScene5++;
			fireLevel++;
		}
		return "fire";
	}
	public static String initialize(){
		doors[0][1] = 0;
		doors[1][1] = 1;
		doors[2][1] = 1;
		doors[3][1] = 1;
		doors[4][1] = 0;
		doors[5][1] = 0;
		doors[0][0] = 0;
		doors[1][0] = 0;
		doors[2][0] = 0;
		doors[3][0] = 0;
		doors[4][0] = 0;
		doors[5][0] = 0;
		windows[0][1] = 1;
		windows[0][0] = 0;
		escape[0][1] = 2;
		escape[1][1] = 1;
		escape[2][1] = 2;
		escape[0][0] = 0;
		escape[1][0] = 0;
		escape[2][0] = 0;
		inventory[0] = 0;
		inventory[1] = 0;
		inventory[2] = 0;
		inventoryNames[0] = "wire hanger";
		inventoryNames[1] = "metal pipe";
		inventoryNames[2] = "rope";
		fireLevel = 0;
		fireScene1 = 27;
		fireScene2 = 33;
		fireScene3 = 36;
		fireScene4 = 42;
		fireScene5 = 24;
		if (difficulty == 2||difficulty == 4){
			fireScene1 = fireScene1 + 5;
			fireScene2 = fireScene2 + 5;
			fireScene3 = fireScene3 + 5;
			fireScene4 = fireScene4 + 5;
			fireScene5 = fireScene5 + 5;
		}else if (difficulty == 3){
			fireScene1 = fireScene1 + 9;
			fireScene2 = fireScene2 + 9;
			fireScene3 = fireScene3 + 9;
			fireScene4 = fireScene4 + 9;
			fireScene5 = fireScene5 + 9;
		}
		guy = 0;
		return "meh";
	}
}