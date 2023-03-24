/* Java Review Project and Fse
 * A Maze game
 * 
 * By: Ada Taing 
 * Last Modified: 2020 11 06
 */
import java.util.Scanner;
import java.util.Random;

public class FSE {
	//whole program lags behind by 2 lines when getting player input
	public static void main(String[] args) {
		//scanners
		Scanner getNums = new Scanner(System.in);
		Scanner getText = new Scanner(System.in);
		
		//random generator
		Random randGen = new Random();
		
		//player stats
		String name;
		String[] inventoryName = {"Coins (Value = $100)", "Gold (Value = $300)", "Relics (Value = $800)", "Magic Items (Value = $1500)",
								  "Ancient Items (Value = $5000)", "Forbidden Items (Value = $25000)", "Legendary Items (Value = $100000)", 
								  "Divine Items (Value = $500000)"};
		int[] inventoryNum = {0, 0, 0, 0, 0, 0, 0, 0};
		
		//other variables
		boolean game = true;
		boolean level = true;
		int levelNum = 1;
		
		char[][] map = new char[54][54];
		int[][] playerPos = new int[54][54];
		int input;
		
		
		//start up
		System.out.println("                       The Wandering Explorer				         ");
		System.out.println("/////////////////////////////////////////////////////////////////////");
		System.out.println("                        press Enter to start				         ");
		var choice = getText.nextLine();
		
		//username input
		System.out.println("");
		System.out.println("Enter your username");
		name = getText.nextLine();
		
		System.out.println("Username:   " + name);
		System.out.println("");
		
		//start of game
		System.out.println("/////////////////////////////////////////////////////////////////////");
		
		System.out.println("Game Rules");
		System.out.println("- Your objective is to travel the maze and search for treasure");
		System.out.println("- Use the number keys (1), (2), (3), (4), or (5)");
		System.out.println("- (1) is to move forward or attack");
		System.out.println("- (2) is to move left or interact");
		System.out.println("- (3) is to move right or to block");
		System.out.println("- (4) is to move backwards");
		System.out.println("- (5) is to open the map and inventory");
		
		System.out.println("/////////////////////////////////////////////////////////////////////");
		System.out.println("");
		
		
		try {
			Thread.sleep(1000); //delays 1 second of time
			
			//start of the game
			while(game == true) {
				
				//fills mapping indicator with 0
				for(int i = 0; i < playerPos.length; i++) {
					for(int j = 0; j < playerPos.length; j++) {
						playerPos[i][j] = 0;
					}
				}
				//places treasure
				for(int i = 0; i < 35; i++) {
					int treasLocaX = randGen.nextInt(54);
					int treasLocaY = randGen.nextInt(54);
					playerPos[treasLocaY][treasLocaX] = 3;
				}
				
				//creates map graphic and boundaries
				for(int i = 0; i < map.length; i++) {
					for(int j = 0; j < map.length; j++) {
						if(i == 0 || i == 51) {
							map[i][j] = '|';
							playerPos[i][j] = 5;
						}
						else if(i != 0 || i != map.length) {
							map[i][j] = '.';
							if(j == 0 || j == 1 || j == 50 || j == 51) {
								map[i][j] = '|';
								playerPos[i][j] = 5;
							}
						}
						
					}
				}
				
				//8 means north split
				//6 means east split
				//2 means south split
				//4 means west split
				//1 means normal path
				//5 means path block
				//9 means exit
				//in north facing, -y is moving up, +y is moving down, -x is going left, +x is going right
				
				
				int x = 26;
				int y = 50;
				int direction = 8;
				boolean split = false; //if a split occurs
				int count = 0; //turn count
				
				boolean moved = false; //determines if player moved
				int obstacle = 0; //number of obstacles currently
				int splits = 0; //number of splits currently
				int maxObst = 10; //the max amount of random generated obstacles
				
				//map icons and indicators
				playerPos[y][x] = 1;
				playerPos[y][x+1] = 5;
				playerPos[y][x-1] = 5;
				map[y][x+1] = '|';
				map[y][x-1] = '|';
				
				//sets level to true
				level = true;
				System.out.println("New level: Level " + levelNum);
				Thread.sleep(1000); //delays 1 second of time
				System.out.println("/////////////////////////////////////////////////////////////////////");
				System.out.println("                           <    Start   >                            ");
				System.out.println("/////////////////////////////////////////////////////////////////////");
				System.out.println("");
				Thread.sleep(1000); //delays 1 second of time
				
				while(level != false) {
					boolean move = false;

					do {
						
						//displays the action menu and a few dialogues
						
						if(playerPos[y][x] == 8 || playerPos[y][x] == 6 || playerPos[y][x] == 2 || playerPos[y][x] == 4) {
							System.out.println("You hit a split, you can't go forward");
							System.out.println("Go left or right");
							System.out.println("");
							//displays controls
							System.out.println("Make Your move");
							System.out.println("");
							System.out.println("Actions:");
							System.out.println("(2) Move left");
							System.out.println("(3) Move right");
							System.out.println("(4) Move backwards");
							System.out.println("(5) Open the map and inventory");
							System.out.println("");
							split = true;
						}
						else if(playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) {
							split = false;
							//displays controls
							System.out.println("Make Your move");
							System.out.println("");
							System.out.println("Actions:");
							System.out.println("(1) Move forward");
							System.out.println("(4) Move backwards");
							System.out.println("(5) Open the map and inventory");
							System.out.println("");
						}
						
						//displays on the map your current position
						map[y][x] = ' ';
						
						//tells player to make move
						input = getNums.nextInt();
						
						
						//splits
						if(split == true) {
							//if player tries to move forward in a split
							//you can't move forward
							if(split == true && input == 1) {
								moved = false;
								//indicates that you have chosen your action
								move = true;
							}
							
							//left
							else if(split == true && input == 2) {
								//if facing north
								if(direction == 8) {
									x = x - 1;
									if(playerPos[y][x] == 5) {//there is an obstacle
										x = x + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
									}
									else if(playerPos[y][x] != 5) {//theres no obstacle
										direction = 4;
										moveDirection(input, direction);
										
										split = false;
										moved = true;
									}
								}
								
								//if facing east
								else if(direction == 6) {
									y = y - 1;
									if(playerPos[y][x] == 5) {//there is an obstacle
										y = y + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
									}
									else if(playerPos[y][x] != 5) {//theres no obstacle
										direction = 8;
										moveDirection(input, direction);
										
										split = false;
										moved = true;
									}
								}
								
								//if facing south
								else if(direction == 2) {
									x = x + 1;
									if(playerPos[y][x] == 5) {//there is an obstacle
										x = x - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
									}
									else if(playerPos[y][x] != 5) {//theres no obstacle
										direction = 6;
										moveDirection(input, direction);
										
										split = false;
										moved = true;
									}
								}
								
								//if facing west
								else if(direction == 4) {
									y = y + 1;
									if(playerPos[y][x] == 5) {//there is an obstacle
										y = y - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
									}
									else if(playerPos[y][x] != 5) {//theres no obstacle
										direction = 2;
										moveDirection(input, direction);
										
										split = false;
										moved = true;
									}
								}
								
								//indicates that you have chosen your action
								move = true;
							}
							
							//right
							else if(split == true && input == 3) {
								//if facing north
								if(direction == 8) {
									x = x + 1;
									if(playerPos[y][x] == 5) {//there is an obstacle
										x = x - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
									}
									else if(playerPos[y][x] != 5) {//theres no obstacle
										direction = 6;
										moveDirection(input, direction);
										
										split = false;
										moved = true;
									}
								}
								
								//if facing east
								else if(direction == 6) {
									y = y + 1;
									if(playerPos[y][x] == 5) {//there is an obstacle
										y = y - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									else if(playerPos[y][x] != 5) {//theres no obstacle
										direction = 2;
										moveDirection(input, direction);
										
										split = false;
										moved = true;
									}
								}
								
								//if facing south
								else if(direction == 2) {
									x = x - 1;
									if(playerPos[y][x] == 5) {//there is an obstacle
										x = x + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
									}
									else if(playerPos[y][x] != 5) {//theres no obstacle
										direction = 4;
										moveDirection(input, direction);
										
										split = false;
										moved = true;
									}
								}
								
								//if facing west
								else if(direction == 4) {
									y = y - 1;
									if(playerPos[y][x] == 5) {//there is an obstacle
										y = y + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
									}
									else if(playerPos[y][x] != 5) {//theres no obstacle
										direction = 8;
										moveDirection(input, direction);
										
										split = false;
										moved = true;
									}
								}
								
								//indicates that you have chosen your action
								move = true;
							}
							
							//move backwards
							else if(input == 4 && split == true) {
								
								//facing north
								if(direction == 8){
									y = y + 1; //is moving backwards
									
									if(playerPos[y][x] == 5) { //if player hits map boundaries
										y = y - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
									//if not in a split or obstacle
									else if(playerPos[y][x] != 5 || playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) { //if player doesn't hit boundary or split
										moved = true;
										split = false;
										moveDirection(input, direction);
									}
									map[y-1][x] = ' ';
								}	
								
								//facing east
								if(direction == 6){
									x = x - 1; //is moving backwards
									
									if(playerPos[y][x] == 5) { //if player hits map boundaries
										x = x + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
									//if not in a split or obstacle
									else if(playerPos[y][x] != 5 || playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) { //if player doesn't hit boundary or split
										moved = true;
										split = false;
										moveDirection(input, direction);
									}
									map[y][x+1] = ' ';
								}
								
								//facing south
								if(direction == 2){
									y = y - 1; //is moving backwards
									
									if(playerPos[y][x] == 5) { //if player hits map boundaries
										y = y + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
									//if not in a split or obstacle
									else if(playerPos[y][x] != 5 || playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) { //if player doesn't hit boundary or split
										moved = true;
										split = false;
										moveDirection(input, direction);
									}
									map[y+1][x] = ' ';
								}
								
								//facing west
								if(direction == 4){
									x = x + 1; //is moving backwards
									
									if(playerPos[y][x] == 5) { //if player hits map boundaries
										x = x - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
									//if not in a split or obstacle
									else if(playerPos[y][x] != 5 || playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) { //if player doesn't hit boundary or split
										moved = true;
										split = false;
										moveDirection(input, direction);
									}
									map[y][x-1] = ' ';
								}
								
								if(playerPos[y][x] == 8) { // if player goes back to north facing split
									direction = 8;
									moved = true;
									split = true;
									moveDirection(input, direction);
								}
								else if(playerPos[y][x] == 6) { // if player goes back to east facing split
									direction = 6;
									moved = true;
									split = true;
									moveDirection(input, direction);
								}
								else if(playerPos[y][x] == 2) { // if player goes back to south facing split
									direction = 2;
									moved = true;
									split = true;
									moveDirection(input, direction);
								}
								else if(playerPos[y][x] == 4) { // if player goes back to west facing split
									direction = 4;
									moved = true;
									split = true;
									moveDirection(input, direction);
								}
								
								move = true;
							}
							
							//map and inventory
							else if(input == 5 && split == true) {
								map[y][x] = 'H';
								mapDisplay(map, playerPos, count, direction, split, splits, obstacle, maxObst);
								//inventory
								inventoryDis(inventoryName, inventoryNum);
								score(inventoryName, inventoryNum, game);
								
								//if turn count reaches 50, ask player if they want to leave to next level
								if (count >= 50) {
									System.out.println("It is currently turn " + count + ", would you like to leave to this dungeon?");
									System.out.println("(1) yes");
									System.out.println("(4) no");
									input = getNums.nextInt();
									System.out.println("");
									
									//if yes
									if(input == 1) {
										System.out.println("Leaving area....");
										System.out.println("");
										level = false;
										break;
									}
									//if no
									else if(input == 4) {
										level = true;
									}
								}
							}
							
						}
						
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						
						//non split movement
						else if(split == false) {
							System.out.println("");
							//move forward
							if(input == 1 && split == false) {
								
								//chance to get a split or an obstacle or nothing
								int chance = randGen.nextInt(14);
								
								//facing north
								if(direction == 8){
									y = y - 1; //moves forward north
									
									//If the area is undiscovered
									if(playerPos[y][x] == 0) {
										
										//if a split is found
										if(chance == 3 || chance == 5) {
											playerPos[y][x] = 8;
											//walls
											//makes sure split doesn't destroy boundaries
											if(playerPos[y][x-1] != 5) {
												playerPos[y][x-1] = 1;
												playerPos[y+1][x-1] = 5;
												map[y+1][x-1] = '|';
												map[y][x-1] = ' ';
											}
											if(playerPos[y][x+1] != 5) {
												playerPos[y][x+1] = 1;
												playerPos[y+1][x+1] = 5;
												map[y+1][x+1] = '|';
												map[y][x+1] = ' ';
											}
											if(playerPos[y-1][x-1] != 5 ) {
												playerPos[y-1][x-1] = 5;
												map[y-1][x-1] = '_';
											}
											if(playerPos[y-1][x] != 5) {
												playerPos[y-1][x] = 5;
												map[y-1][x] = '_';
											}
											if(playerPos[y-1][x+1] != 5) {
												playerPos[y-1][x+1] = 5;
												map[y-1][x+1] = '_';
											}
											
											split = true;
											splits = splits + 1;
										}
										//if a road block is found
										else if(chance == 8 || chance == 11) {
											if(obstacle == maxObst) {//if max amount of obstacles is reached, treat as normal path
												playerPos[y][x] = 1;
												//walls 
												playerPos[y][x-1] = 5;
												playerPos[y][x+1] = 5;
												map[y][x-1] = '|';
												map[y][x+1] = '|';
												
												moved = true;
											}
											else if(obstacle != maxObst) {//if max amount of obstacles is not reached
												if(splits >= 2) { //if a split has happened
													y = y + 1;
													obstacle = obstacle + 1;
													playerPos[y-1][x] = 5;
													map[y-1][x] = '_';
													System.out.println("You've hit a dead end");
													System.out.println("");
													moved = false;
													move = false;
												}
												else if(splits < 2) { //if split has not happened yet
													playerPos[y][x] = 1;
													//walls 
													playerPos[y][x-1] = 5;
													playerPos[y][x+1] = 5;
													map[y][x-1] = '|';
													map[y][x+1] = '|';
													
													moved = true;
												}
												
											}
											
										}
										//if treasure has been found
										else if(chance == 12 || chance == 13) {
											//treasure obtaining
											int treasure = randGen.nextInt(20);
											if(treasure == 20) {
												inventoryNum[7] = inventoryNum[7] + 1; //divine items
												System.out.println("You found " + inventoryName[7]);
												System.out.println("");
											}
											else if(treasure == 10) {
												inventoryNum[6] = inventoryNum[6] + 1; //legendary items
												System.out.println("You found " + inventoryName[6]);
												System.out.println("");
											}
											else if(treasure == 11) {
												inventoryNum[5] = inventoryNum[5] + 1; //forbidden items
												System.out.println("You found " + inventoryName[5]);
												System.out.println("");
											}
											else if(treasure == 9) {
												inventoryNum[4] = inventoryNum[4] + 1; //ancient items
												System.out.println("You found " + inventoryName[4]);
												System.out.println("");
											}
											else if(treasure == 5) {
												inventoryNum[3] = inventoryNum[3] + 1; //magic items
												System.out.println("You found " + inventoryName[3]);
												System.out.println("");
											}
											else if(treasure != 20 || treasure != 10 || treasure != 11 || treasure != 9 || treasure != 5) {
												int common = randGen.nextInt(2);
												inventoryNum[common] = inventoryNum[common] + 1; //coins, gold, relics
												System.out.println("You found " + inventoryName[common]);
												System.out.println("");
											}
											//and new area discovered
											playerPos[y][x] = 1;
											//walls 
											playerPos[y][x-1] = 5;
											playerPos[y][x+1] = 5;
											map[y][x-1] = '|';
											map[y][x+1] = '|';
											
											moved = true;
										}
										
										//if new path area is found
										else if(chance != 3 || chance != 5 || chance != 8 || chance != 11 || chance != 12 || chance != 13) {
											playerPos[y][x] = 1;
											//walls 
											playerPos[y][x-1] = 5;
											playerPos[y][x+1] = 5;
											map[y][x-1] = '|';
											map[y][x+1] = '|';
											
											moved = true;
										}
	
										//
										map[y+1][x] = ' ';
										moved = true;
										moveDirection(input, direction);
									}
									
									//if area was already discovered
									else if(playerPos[y][x] == 1) {
										map[y+1][x] = ' ';
										moved = true;
										moveDirection(input, direction);
										
									}
									
									//if treasure has been found
									else if(playerPos[y][x] == 3) {
										//getting treasure
										int treasure = randGen.nextInt(20);
										if(treasure == 20) {
											inventoryNum[7] = inventoryNum[7] + 1; //divine items
											System.out.println("You found " + inventoryName[7]);
											System.out.println("");
										}
										else if(treasure == 10) {
											inventoryNum[6] = inventoryNum[6] + 1; //legendary items
											System.out.println("You found " + inventoryName[6]);
											System.out.println("");
										}
										else if(treasure == 11) {
											inventoryNum[5] = inventoryNum[5] + 1; //forbidden items
											System.out.println("You found " + inventoryName[5]);
											System.out.println("");
										}
										else if(treasure == 9) {
											inventoryNum[4] = inventoryNum[4] + 1; //ancient items
											System.out.println("You found " + inventoryName[4]);
											System.out.println("");
										}
										else if(treasure == 5) {
											inventoryNum[3] = inventoryNum[3] + 1; //magic items
											System.out.println("You found " + inventoryName[3]);
											System.out.println("");
										}
										else if(treasure != 20 || treasure != 10 || treasure != 11 || treasure != 9 || treasure != 5) {
											int common = randGen.nextInt(2);
											inventoryNum[common] = inventoryNum[common] + 1; //coins, gold, relics
											System.out.println("You found " + inventoryName[common]);
											System.out.println("");
										}
										//setting place to 1 so treasure can only be obtained once
										playerPos[x][y] = 1;
										//walls 
										playerPos[y][x-1] = 5;
										playerPos[y][x+1] = 5;
										map[y][x-1] = '|';
										map[y][x+1] = '|';
										
										moved = true;
										
									}
	
									//if you hit a dead end
									else if(playerPos[y][x] == 5) { 
										y = y + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
								}
								
								//facing east
								else if(direction == 6){
									x = x + 1; //moves forward east
									
									//If the area is undiscovered
									if(playerPos[y][x] == 0) {
										
										//if a split is found
										if(chance == 3 || chance == 5) {
											playerPos[y][x] = 6;
											//walls
											//makes sure split doesn't destroy boundaries
											if(playerPos[y-1][x] != 5) {
												playerPos[y-1][x] = 1;
												playerPos[y-1][x-1] = 5;
												map[y-1][x] = ' ';
												map[y-1][x-1] = '|';
											}
											if(playerPos[y+1][x] != 5) {
												playerPos[y+1][x] = 1;
												playerPos[y+1][x-1] = 5;
												map[y+1][x] = ' ';
												map[y+1][x-1] = '|';
											}
											if(playerPos[y-1][x+1] != 5) {
												playerPos[y-1][x+1] = 5;
												map[y-1][x+1] = '|';
											}
											if(playerPos[y][x+1] != 5) {
												playerPos[y][x+1] = 5;
												map[y][x+1] = '|';
											}
											if(playerPos[y+1][x+1] != 5) {
												playerPos[y+1][x+1] = 5;
												map[y+1][x+1] = '|';
											}
											split = true;
											splits = splits + 1;
										}
										//if a road block is found
										else if(chance == 8 || chance == 11) {
											if(obstacle == maxObst) {//if max amount of obstacles is reached, treat as normal path
												playerPos[y][x] = 1;
												//walls 
												playerPos[y-1][x] = 5;
												playerPos[y+1][x] = 5;
												map[y-1][x] = '_';
												map[y+1][x] = '—';
												
												moved = true;
											}
											else if(obstacle != maxObst) {//if max amount of obstacles is not reached
												if(splits >= 2) { //if a split has happened
													x = x - 1;
													obstacle = obstacle + 1;
													playerPos[y][x+1] = 5;
													map[y][x+1] = '|';
													System.out.println("You've hit a dead end");
													System.out.println("");
													moved = false;
													move = false;
												}
												else if(splits < 2) { //if split has not happened yet
													playerPos[y][x] = 1;
													//walls 
													playerPos[y-1][x] = 5;
													playerPos[y+1][x] = 5;
													map[y-1][x] = '_';
													map[y+1][x] = '—';
													moved = true;
												}
												
											}
											
										}
										//if treasure has been found
										else if(chance == 12 || chance == 13) {
											//treasure obtaining
											int treasure = randGen.nextInt(20);
											if(treasure == 20) {
												inventoryNum[7] = inventoryNum[7] + 1; //divine items
												System.out.println("You found " + inventoryName[7]);
												System.out.println("");
											}
											else if(treasure == 10) {
												inventoryNum[6] = inventoryNum[6] + 1; //legendary items
												System.out.println("You found " + inventoryName[6]);
												System.out.println("");
											}
											else if(treasure == 11) {
												inventoryNum[5] = inventoryNum[5] + 1; //forbidden items
												System.out.println("You found " + inventoryName[5]);
												System.out.println("");
											}
											else if(treasure == 9) {
												inventoryNum[4] = inventoryNum[4] + 1; //ancient items
												System.out.println("You found " + inventoryName[4]);
												System.out.println("");
											}
											else if(treasure == 5) {
												inventoryNum[3] = inventoryNum[3] + 1; //magic items
												System.out.println("You found " + inventoryName[3]);
												System.out.println("");
											}
											else if(treasure != 20 || treasure != 10 || treasure != 11 || treasure != 9 || treasure != 5) {
												int common = randGen.nextInt(2);
												inventoryNum[common] = inventoryNum[common] + 1; //coins, gold, relics
												System.out.println("You found " + inventoryName[common]);
												System.out.println("");
											}
											//and new area discovered
											playerPos[y][x] = 1;
											//walls 
											playerPos[y-1][x] = 5;
											playerPos[y+1][x] = 5;
											map[y-1][x] = '_';
											map[y+1][x] = '—';
											
											moved = true;
										}
										//if path area is found
										else if(chance != 3 || chance != 5 || chance != 8 || chance != 11 || chance != 12 || chance != 13) {
											playerPos[y][x] = 1;
											//walls 
											playerPos[y-1][x] = 5;
											playerPos[y+1][x] = 5;
											map[y-1][x] = '_';
											map[y+1][x] = '—';
											
											moved = true;
										}
										
										//
										map[y][x-1] = ' ';
										moved = true;
										moveDirection(input, direction);
									}
									
									//if area was already discovered
									else if(playerPos[y][x] == 1) {
										map[y][x-1] = ' ';
										moved = true;
										moveDirection(input, direction);
										
									}
									
									//if treasure has been found
									else if(playerPos[y][x] == 3) {
										//getting treasure
										int treasure = randGen.nextInt(20);
										if(treasure == 20) {
											inventoryNum[7] = inventoryNum[7] + 1; //divine items
											System.out.println("You found " + inventoryName[7]);
											System.out.println("");
										}
										else if(treasure == 10) {
											inventoryNum[6] = inventoryNum[6] + 1; //legendary items
											System.out.println("You found " + inventoryName[6]);
											System.out.println("");
										}
										else if(treasure == 11) {
											inventoryNum[5] = inventoryNum[5] + 1; //forbidden items
											System.out.println("You found " + inventoryName[5]);
											System.out.println("");
										}
										else if(treasure == 9) {
											inventoryNum[4] = inventoryNum[4] + 1; //ancient items
											System.out.println("You found " + inventoryName[4]);
											System.out.println("");
										}
										else if(treasure == 5) {
											inventoryNum[3] = inventoryNum[3] + 1; //magic items
											System.out.println("You found " + inventoryName[3]);
											System.out.println("");
										}
										else if(treasure != 20 || treasure != 10 || treasure != 11 || treasure != 9 || treasure != 5) {
											int common = randGen.nextInt(2);
											inventoryNum[common] = inventoryNum[common] + 1; //coins, gold, relics
											System.out.println("You found " + inventoryName[common]);
											System.out.println("");
										}
										//setting place to 1 so treasure can only be obtained once
										playerPos[y][x] = 1;
										//walls 
										playerPos[y-1][x] = 5;
										playerPos[y+1][x] = 5;
										map[y-1][x] = '_';
										map[y+1][x] = '—';
										
										moved = true;
										
									}
	
									//if you hit a dead end
									else if(playerPos[y][x] == 5) { 
										x = x - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
								}
								
								//facing south
								else if(direction == 2){
									y = y + 1; //moves forward south
									
									//If the area is undiscovered
									if(playerPos[y][x] == 0) {
										
										//if a split is found
										if(chance == 3 || chance == 5) {
											playerPos[y][x] = 2;
											//walls
											//makes sure split doesn't destroy boundaries
											if(playerPos[y][x-1] != 5) {
												playerPos[y][x-1] = 1;
												playerPos[y-1][x-1] = 5;
												map[y-1][x-1] = '|';
												map[y][x-1] = ' ';
											}
											if(playerPos[y][x+1] != 5) {
												playerPos[y][x+1] = 1;
												playerPos[y-1][x+1] = 5;	
												map[y-1][x+1] = '|';
												map[y][x+1] = ' ';
											}
											if(playerPos[y+1][x-1] != 5 ) {
												playerPos[y+1][x-1] = 5;
												map[y+1][x-1] = '—';
											}
											if(playerPos[y+1][x] != 5) {
												playerPos[y+1][x] = 5;
												map[y+1][x] = '—';
											}
											if(playerPos[y+1][x+1] != 5) {
												playerPos[y+1][x+1] = 5;
												map[y+1][x+1] = '—';
											}
											
											split = true;
											splits = splits + 1;
										}
										//if a road block is found
										else if(chance == 8 || chance == 11) {
											if(obstacle == maxObst) {//if max amount of obstacles is reached, treat as normal path
												playerPos[y][x] = 1;
												//walls 
												playerPos[y][x-1] = 5;
												playerPos[y][x+1] = 5;
												map[y][x-1] = '|';
												map[y][x+1] = '|';
												
												moved = true;
											}
											else if(obstacle != maxObst) {//if max amount of obstacles is not reached
												if(splits >= 2) { //if a split has happened
													y = y - 1;
													obstacle = obstacle + 1;
													playerPos[y+1][x] = 5;
													map[y+1][x] = '—';
													System.out.println("You've hit a dead end");
													System.out.println("");
													moved = false;
													move = false;
												}
												else if(splits < 2) { //if split has not happened yet
													playerPos[y][x] = 1;
													//walls 
													playerPos[y][x-1] = 5;
													playerPos[y][x+1] = 5;
													map[y][x-1] = '|';
													map[y][x+1] = '|';
													
													moved = true;
												}
												
											}
											
										}
										//if treasure has been found
										else if(chance == 12 || chance == 13) {
											//treasure obtaining
											int treasure = randGen.nextInt(20);
											if(treasure == 20) {
												inventoryNum[7] = inventoryNum[7] + 1; //divine items
												System.out.println("You found " + inventoryName[7]);
												System.out.println("");
											}
											else if(treasure == 10) {
												inventoryNum[6] = inventoryNum[6] + 1; //legendary items
												System.out.println("You found " + inventoryName[6]);
												System.out.println("");
											}
											else if(treasure == 11) {
												inventoryNum[5] = inventoryNum[5] + 1; //forbidden items
												System.out.println("You found " + inventoryName[5]);
												System.out.println("");
											}
											else if(treasure == 9) {
												inventoryNum[4] = inventoryNum[4] + 1; //ancient items
												System.out.println("You found " + inventoryName[4]);
												System.out.println("");
											}
											else if(treasure == 5) {
												inventoryNum[3] = inventoryNum[3] + 1; //magic items
												System.out.println("You found " + inventoryName[3]);
												System.out.println("");
											}
											else if(treasure != 20 || treasure != 10 || treasure != 11 || treasure != 9 || treasure != 5) {
												int common = randGen.nextInt(2);
												inventoryNum[common] = inventoryNum[common] + 1; //coins, gold, relics
												System.out.println("You found " + inventoryName[common]);
												System.out.println("");
											}
											//and new area discovered
											playerPos[y][x] = 1;
											//walls 
											playerPos[y][x-1] = 5;
											playerPos[y][x+1] = 5;
											map[y][x-1] = '|';
											map[y][x+1] = '|';
											
											moved = true;
										}
										//if path area is found
										else if(chance != 3 || chance != 5 || chance != 8 || chance != 11 || chance != 12 || chance != 13) {
											playerPos[y][x] = 1;
											//walls 
											playerPos[y][x-1] = 5;
											playerPos[y][x+1] = 5;
											map[y][x-1] = '|';
											map[y][x+1] = '|';
											
											moved = true;
										}
										
										//
										map[y-1][x] = ' ';
										moved = true;
										moveDirection(input, direction);
									}
									
									//if area was already discovered
									else if(playerPos[y][x] == 1) {
										map[y-1][x] = ' ';
										moved = true;
										moveDirection(input, direction);
										
									}
									
									//if treasure has been found
									else if(playerPos[y][x] == 3) {
										//getting treasure
										int treasure = randGen.nextInt(20);
										if(treasure == 20) {
											inventoryNum[7] = inventoryNum[7] + 1; //divine items
											System.out.println("You found " + inventoryName[7]);
											System.out.println("");
										}
										else if(treasure == 10) {
											inventoryNum[6] = inventoryNum[6] + 1; //legendary items
											System.out.println("You found " + inventoryName[6]);
											System.out.println("");
										}
										else if(treasure == 11) {
											inventoryNum[5] = inventoryNum[5] + 1; //forbidden items
											System.out.println("You found " + inventoryName[5]);
											System.out.println("");
										}
										else if(treasure == 9) {
											inventoryNum[4] = inventoryNum[4] + 1; //ancient items
											System.out.println("You found " + inventoryName[4]);
											System.out.println("");
										}
										else if(treasure == 5) {
											inventoryNum[3] = inventoryNum[3] + 1; //magic items
											System.out.println("You found " + inventoryName[3]);
											System.out.println("");
										}
										else if(treasure != 20 || treasure != 10 || treasure != 11 || treasure != 9 || treasure != 5) {
											int common = randGen.nextInt(2);
											inventoryNum[common] = inventoryNum[common] + 1; //coins, gold, relics
											System.out.println("You found " + inventoryName[common]);
											System.out.println("");
										}
										//setting place to 1 so treasure can only be obtained once
										playerPos[x][y] = 1;
										//walls 
										playerPos[y][x-1] = 5;
										playerPos[y][x+1] = 5;
										map[y][x-1] = '|';
										map[y][x+1] = '|';
										
										moved = true;
										
									}
	
									//if you hit a dead end
									else if(playerPos[y][x] == 5) { 
										y = y - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
								}
								
								//facing west
								else if(direction == 4 && split == false){
									x = x - 1; //moves forward west
									
									//If the area is undiscovered
									if(playerPos[y][x] == 0) {
										
										//if a split is found
										if(chance == 3 || chance == 5) {
											playerPos[y][x] = 4;
											//walls
											//makes sure split doesn't destroy boundaries
											if(playerPos[y-1][x] != 5) {
												playerPos[y-1][x] = 1;
												playerPos[y-1][x+1] = 5;
												map[y-1][x] = ' ';
												map[y-1][x+1] = '|';
												
											}
											if(playerPos[y+1][x] != 5) {
												playerPos[y-1][x] = 1;
												playerPos[y+1][x+1] = 5;
												map[y+1][x] = ' ';
												map[y+1][x+1] = '|';
												
											}
											if(playerPos[y+1][x-1] != 5 ) {
												playerPos[y+1][x-1] = 5;
												map[y+1][x-1] = '|';
											}
											if(playerPos[y][x-1] != 5) {
												playerPos[y][x-1] = 5;
												map[y][x-1] = '|';
											}
											if(playerPos[y-1][x-1] != 5) {
												playerPos[y-1][x-1] = 5;
												map[y-1][x-1] = '|';
											}
											
											splits = splits + 1;
											split = true;
										}
										//if a road block is found
										else if(chance == 8 || chance == 11) {
											if(obstacle == maxObst) {//if max amount of obstacles is reached, treat as normal path
												playerPos[y][x] = 1;
												//walls 
												playerPos[y-1][x] = 5;
												playerPos[y+1][x] = 5;
												map[y-1][x] = '_';
												map[y+1][x] = '—';
												
												moved = true;
											}
											else if(obstacle != maxObst) {//if max amount of obstacles is not reached
												if(splits >= 2) { //if a split has happened
													x = x + 1;
													obstacle = obstacle + 1;
													playerPos[y][x-1] = 5;
													map[y][x-1] = '|';
													System.out.println("You've hit a dead end");
													System.out.println("");
													moved = false;
													move = false;
												}
												else if(splits < 2) { //if split has not happened yet
													playerPos[y][x] = 1;
													//walls 
													playerPos[y-1][x] = 5;
													playerPos[y+1][x] = 5;
													map[y-1][x] = '_';
													map[y+1][x] = '—';
													moved = true;
												}
												
											}
											
										}
										//if treasure has been found
										else if(chance == 12 || chance == 13) {
											//treasure obtaining
											int treasure = randGen.nextInt(20);
											if(treasure == 20) {
												inventoryNum[7] = inventoryNum[7] + 1; //divine items
												System.out.println("You found " + inventoryName[7]);
												System.out.println("");
											}
											else if(treasure == 10) {
												inventoryNum[6] = inventoryNum[6] + 1; //legendary items
												System.out.println("You found " + inventoryName[6]);
												System.out.println("");
											}
											else if(treasure == 11) {
												inventoryNum[5] = inventoryNum[5] + 1; //forbidden items
												System.out.println("You found " + inventoryName[5]);
												System.out.println("");
											}
											else if(treasure == 9) {
												inventoryNum[4] = inventoryNum[4] + 1; //ancient items
												System.out.println("You found " + inventoryName[4]);
												System.out.println("");
											}
											else if(treasure == 5) {
												inventoryNum[3] = inventoryNum[3] + 1; //magic items
												System.out.println("You found " + inventoryName[3]);
												System.out.println("");
											}
											else if(treasure != 20 || treasure != 10 || treasure != 11 || treasure != 9 || treasure != 5) {
												int common = randGen.nextInt(2);
												inventoryNum[common] = inventoryNum[common] + 1; //coins, gold, relics
												System.out.println("You found " + inventoryName[common]);
												System.out.println("");
											}
											//and new area discovered
											playerPos[y][x] = 1;
											//walls 
											playerPos[y-1][x] = 5;
											playerPos[y+1][x] = 5;
											map[y-1][x] = '_';
											map[y+1][x] = '—';
											
											moved = true;
										}
										//if path area is found
										else if(chance != 3 || chance != 5 || chance != 8 || chance != 11 || chance != 12 || chance != 13) {
											playerPos[y][x] = 1;
											//walls 
											playerPos[y-1][x] = 5;
											playerPos[y+1][x] = 5;
											map[y-1][x] = '_';
											map[y+1][x] = '—';
											
											moved = true;
										}
										
										//
										map[y][x+1] = ' ';
										moved = true;
										moveDirection(input, direction);
									}
									
									//if area was already discovered
									else if(playerPos[y][x] == 1) {
										map[y][x+1] = ' ';
										moved = true;
										moveDirection(input, direction);
										
									}
									
									//if treasure has been found
									else if(playerPos[y][x] == 3) {
										//getting treasure
										int treasure = randGen.nextInt(20);
										if(treasure == 20) {
											inventoryNum[7] = inventoryNum[7] + 1; //divine items
											System.out.println("You found " + inventoryName[7]);
											System.out.println("");
										}
										else if(treasure == 10) {
											inventoryNum[6] = inventoryNum[6] + 1; //legendary items
											System.out.println("You found " + inventoryName[6]);
											System.out.println("");
										}
										else if(treasure == 11) {
											inventoryNum[5] = inventoryNum[5] + 1; //forbidden items
											System.out.println("You found " + inventoryName[5]);
											System.out.println("");
										}
										else if(treasure == 9) {
											inventoryNum[4] = inventoryNum[4] + 1; //ancient items
											System.out.println("You found " + inventoryName[4]);
											System.out.println("");
										}
										else if(treasure == 5) {
											inventoryNum[3] = inventoryNum[3] + 1; //magic items
											System.out.println("You found " + inventoryName[3]);
											System.out.println("");
										}
										else if(treasure != 20 || treasure != 10 || treasure != 11 || treasure != 9 || treasure != 5) {
											int common = randGen.nextInt(2);
											inventoryNum[common] = inventoryNum[common] + 1; //coins, gold, relics
											System.out.println("You found " + inventoryName[common]);
											System.out.println("");
										}
										//setting place to 1 so treasure can only be obtained once
										playerPos[y][x] = 1;
										//walls 
										playerPos[y-1][x] = 5;
										playerPos[y+1][x] = 5;
										map[y-1][x] = '_';
										map[y+1][x] = '—';
										
										moved = true;
										
									}
									
									//if you hit a dead end
									else if(playerPos[y][x] == 5) { 
										x = x + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
								}
								
								//indicates that you have chosen your action
								move = true;
							}
							
							//if player tries to move left or right
							else if(input == 2 && split == false || input == 3 && split == false) {
								System.out.println("You can't go left or right");
								System.out.println("");
								moved = false;
								move = false;
							}
							
							
							//move backwards
							else if(input == 4 && split == false) {
								
								//facing north
								if(direction == 8){
									y = y + 1; //is moving backwards
									
									if(playerPos[y][x] == 5) { //if player hits map boundaries
										y = y - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
									//if not in a split or obstacle
									else if(playerPos[y][x] != 5 || playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) { //if player doesn't hit boundary or split
										moved = true;
										split = false;
										moveDirection(input, direction);
									}
									map[y-1][x] = ' ';
								}	
								
								//facing east
								if(direction == 6){
									x = x - 1; //is moving backwards
									
									if(playerPos[y][x] == 5) { //if player hits map boundaries
										x = x + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
									//if not in a split or obstacle
									else if(playerPos[y][x] != 5 || playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) { //if player doesn't hit boundary or split
										moved = true;
										split = false;
										moveDirection(input, direction);
									}
									map[y][x+1] = ' ';
								}
								
								//facing south
								if(direction == 2){
									y = y - 1; //is moving backwards
									
									if(playerPos[y][x] == 5) { //if player hits map boundaries
										y = y + 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
									//if not in a split or obstacle
									else if(playerPos[y][x] != 5 || playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) { //if player doesn't hit boundary or split
										moved = true;
										split = false;
										moveDirection(input, direction);
									}
									map[y+1][x] = ' ';
								}
								
								//facing west
								if(direction == 4){
									x = x + 1; //is moving backwards
									
									if(playerPos[y][x] == 5) { //if player hits map boundaries
										x = x - 1;
										System.out.println("You've hit a dead end");
										System.out.println("");
										moved = false;
										move = false;
									}
									
									//if not in a split or obstacle
									else if(playerPos[y][x] != 5 || playerPos[y][x] != 8 || playerPos[y][x] != 6 || playerPos[y][x] != 2 || playerPos[y][x] != 4) { //if player doesn't hit boundary or split
										moved = true;
										split = false;
										moveDirection(input, direction);
									}
									map[y][x-1] = ' ';
								}
								
								if(playerPos[y][x] == 8) { // if player goes back to north facing split
									direction = 8;
									moved = true;
									split = true;
									
								}
								else if(playerPos[y][x] == 6) { // if player goes back to east facing split
									direction = 6;
									moved = true;
									split = true;
	
								}
								else if(playerPos[y][x] == 2) { // if player goes back to south facing split
									direction = 2;
									moved = true;
									split = true;
	
								}
								else if(playerPos[y][x] == 4) { // if player goes back to west facing split
									direction = 4;
									moved = true;
									split = true;
	
								}
								
								move = true;
							}
							
							//display map and inventory
							else if(input == 5 && split == false) {
								map[y][x] = 'H';
								mapDisplay(map, playerPos, count, direction, split, splits, obstacle, maxObst);
								//inventory
								inventoryDis(inventoryName, inventoryNum);
								score(inventoryName, inventoryNum, game);
								
								//if turn count reaches 50, ask player if they want to leave to next level
								if (count >= 50) {
									System.out.println("It is currently turn " + count + ", would you like to leave to this dungeon?");
									System.out.println("(1) yes");
									System.out.println("(4) no");
									input = getNums.nextInt();
									System.out.println("");
									
									//if yes
									if(input == 1) {
										System.out.println("Leaving area....");
										System.out.println("");
										level = false;
										break;
									}
									//if no
									else if(input == 4) {
										level = true;
									}
								}
								
							}
						}
						System.out.println("---------------------------------------------------------------------");
						
					} 
					while(move == false); //end of action loop
					
					//makes sure if the player leaves the dungeon they actually leave
					if(level == false) {
						break;
					}
					else if(level != false) {
						level = true;
					}
					
					
					
					Thread.sleep(200); //delays .2 second of time
					
					//tells you if you actually moved
					if(moved == true) {
						System.out.println("You Moved Successfully");
					}
					else if(moved == false) {
						System.out.println("You Moved Unsuccessfully");
					}
					System.out.println("---------------------------------------------------------------------");
					
					moved = false;
					
					System.out.println("");
					count = count + 1;//adds to turn count
					
					//if all of the dungeon has been explored
					if (obstacle == (splits * 2) && splits >= 1) {
						System.out.println("Looks like this place is empty");
						System.out.println("");
						Thread.sleep(1000); //delays 1 second of time
						System.out.println("Leaving area....");
						System.out.println("");
						level = false; //switch to new area
					}
					
				} //end of level loop
				
				Thread.sleep(1000); //delays 1 second of time
				System.out.println("Current Status:");
				inventoryDis(inventoryName, inventoryNum);
				score(inventoryName, inventoryNum, game);
				System.out.println("");
				System.out.println("Would you like to continue the game?");
				System.out.println("(1) yes");
				System.out.println("(any other number) no");
				input = getNums.nextInt();
				System.out.println("");
				
				//if yes
				if(input == 1) {
					System.out.println("Preparing next level...");
					System.out.println("");
					levelNum = levelNum + 1;
					level = true;
					game = true;
				}
				//if no
				else if(input != 1) {
					game = false;
					break;
				}
				
				
			} //end of game loop
			
			//when you lose
			System.out.println("Game over");
			System.out.println("");
			System.out.println("Level Reached: Level " + levelNum);
			score(inventoryName, inventoryNum, game);
			
			getNums.close();
			getText.close();
		}
		
		catch(Exception ex){
			System.err.println("Error: " + ex.getMessage()); //needs to know what to do in the event of an error
		}
		
		
		
	}
	
	//inventory display
	public static void inventoryDis(String inventoryName[], int inventoryNum[]) {
		System.out.println("Inventory:");
		for(int i = 0; i < inventoryNum.length; i++) {
			System.out.println((i + 1) + ". " + inventoryName[i] + " x " + inventoryNum[i]);	
		}
		System.out.println("");
	}
	
	//map displaying
	//if player chooses to display map
	public static void mapDisplay(char map[][], int playerPos[][], int count, int direction, boolean split, int splits, int obstacles, int maxObst) {
		mapDisplay2(map);
		System.out.println("Turn: " + count); //displays turn count
		facingDirection(direction); //displays facing direction
		System.out.println("");
		//for debugging purposes
		System.out.println("---------------------------------------------------------------------");
		mapDisplay3(playerPos);
		System.out.println("Split path= " + split);
		System.out.println("Splits= " + splits);
		System.out.println("Obstacles= " + obstacles + "/" + maxObst);
		System.out.println("---------------------------------------------------------------------");
		System.out.println("");
	}
	
	//map
	public static void mapDisplay2(char map[][]) {
		
		for(int i = 0; i < 52; i++) {
			for(int j = 0; j < 52; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
		
	}
	//map debugging
	public static void mapDisplay3(int map[][]) {
		
		for(int i = 0; i < 52; i++) {
			for(int j = 0; j < 52; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
		
	}
	
	//directions
	//movement directions
	public static void moveDirection(int direction, int facingDir) {
		if(direction == 1) {
			System.out.println("You moved forward");
		}
		else if(direction == 4) {
			System.out.println("You moved backwards");
		}
		else if(direction == 2) {
			System.out.println("You moved left");
		}
		else if(direction == 3) {
			System.out.println("You moved right");
		}
		facingDirection(facingDir);
		
	}
	//facing directions
	public static void facingDirection(int facingDir) {
		if(facingDir == 8) {
			System.out.println("Facing North");
		}
		else if(facingDir == 6) {
			System.out.println("Facing East");
		}
		else if(facingDir == 2) {
			System.out.println("Facing South");
		}
		else if(facingDir == 4) {
			System.out.println("Facing West");
		}
		
	}
	
	//Score display
	public static void score(String inventoryName[], int inventoryNum[], boolean game) {
		if(game == true) {
			int total = 0;
			total = (inventoryNum[0] * 100) + (inventoryNum[1] * 300) + (inventoryNum[2] * 800) + (inventoryNum[3] * 1500) + (inventoryNum[4] * 5000)
					+ (inventoryNum[5] * 25000) + (inventoryNum[6] * 100000) + (inventoryNum[7] * 500000);
			
			System.out.println("Total Value: $" + total);
			System.out.println("");
		}
		else if(game == false) {
			int finalTotal = (inventoryNum[0] * 100) + (inventoryNum[1] * 300) + (inventoryNum[2] * 800) + (inventoryNum[3] * 1500) + (inventoryNum[4] * 5000)
					+ (inventoryNum[5] * 25000) + (inventoryNum[6] * 100000) + (inventoryNum[7] * 500000);
			
			inventoryDis(inventoryName, inventoryNum);
			
			System.out.println("Final Total Value: $" + finalTotal);
			System.out.println("");
		}
	}

}

