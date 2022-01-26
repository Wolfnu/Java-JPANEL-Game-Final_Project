//Name:Samyam S. Khadka
//Date: January 7th, 2020
//Purpose: Rick and Morty Themed Maze Like Game

// all the methods to ensure everything works well.
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AttributeSet.ColorAttribute;
// ensures the sound works.
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//TODO: switch level 1 to level 4? and fix hint and in instruction add where to win.
public class SokobanGame extends JPanel implements ActionListener {

	Panel p_card; // to hold all of the screens
	Panel card1, card1B, card2, card2B, card2C, card3, card4, card5, card6, card7, card8A, card8B, card9, card10, card11, card12; // all the different cards to display on the main card.
	CardLayout cdLayout = new CardLayout(); // a global layout.
	// the size of the board.
	int row = 9;
	int col = 7;
	// used to see which level and place in the level the player is at.
	int level = 1;
	int placement = 0;
	// the c and y position of the player.
	int x = 2;
	int y = 1;
	int charInt = 0; // used to switch the character's costume.
	int counter = 0; // how many places the character has moved.
	int screen = ' '; // which screen the player is on.
	int rating = 10; // the rating of the game.
	int loseCount = 4; // the lives the player has left.
	int randomNum; // global variable that stores the return of randomPostition.
	int i = ' '; // global variable of value that will be returned by randomNum (stores the column of random trap);
	int j = ' '; // global variable that stores the row of the random trap.
	int temp = ' '; // global variable holds the value of the trapActivate.
	int trapHint = ' '; // holds the hints about the traps.
	String sHint = "nothing"; // global variable that stores the hint calculations.
	char characters[] = { 'n', 'b', 's' }; // allows you to switch through different costumes.
	JLabel a[] = new JLabel[row * col]; // allows you to display an image in the given coordinates after the screen has been setup.
	JLabel counting; // displays the count as a text on screen.
	JLabel lvlDisplay; // displays the level as a text on screen.
	JButton next = new JButton("Next"); // global variable for button that goes to level select screen.
	JButton hint; // global variable for the button that opens a hint screen.
	Clip clip; // allows us to control the audio that plays.
	JTextArea txt; // global variable for the text field.
	JTextArea txt1; // the global variable for the text field where the user's respond is displayed.
	JLabel lab; // the global variable for the user's review text.

	// the board that is displayed in the screen.
	int board[][] = { { 1, 1, 1, 1, 1, 1, 1 },
			{ 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 2, 2, 2, 2, 2, 2 },
			{ 3, 3, 3, 3, 2, 3, 2 },
			{ 3, 3, 3, 3, 2, 3, 2 },
			{ 2, 2, 2, 2, 2, 3, 2 },
			{ 4, 3, 3, 3, 2, 3, 4 },
			{ 3, 3, 4, 2, 2, 3, 3 },
			{ 1, 1, 1, 1, 1, 1, 1 } };

	// where our displaying board is saved.
	int save[][] = { { 1, 1, 1, 1, 1, 1, 1 },
			{ 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 2, 2, 2, 2, 2, 2 },
			{ 3, 3, 3, 3, 2, 3, 2 },
			{ 3, 3, 3, 3, 2, 3, 2 },
			{ 2, 2, 2, 2, 2, 3, 2 },
			{ 4, 3, 3, 3, 2, 3, 4 },
			{ 3, 3, 4, 2, 2, 3, 3 },
			{ 1, 1, 1, 1, 1, 1, 1 } };

	// level 1 board.
	int level1[][] = { { 1, 1, 1, 1, 1, 1, 1 },
			{ 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 2, 2, 2, 2, 2, 2 },
			{ 3, 3, 3, 3, 2, 3, 2 },
			{ 3, 3, 3, 3, 2, 3, 2 },
			{ 2, 2, 2, 2, 2, 3, 2 },
			{ 4, 3, 3, 3, 2, 3, 4 },
			{ 3, 3, 4, 2, 2, 3, 3 },
			{ 1, 1, 1, 1, 1, 1, 1 } };

	// level 2 board.
	int level2[][] = { { 3, 3, 2, 2, 2, 3, 3 },
			{ 3, 3, 2, 3, 2, 3, 3 },
			{ 2, 2, 2, 2, 2, 2, 2 },
			{ 3, 2, 3, 3, 2, 3, 2 },
			{ 3, 2, 3, 3, 2, 3, 2 },
			{ 1, 2, 3, 3, 2, 3, 2 },
			{ 1, 4, 3, 4, 2, 3, 2 },
			{ 1, 1, 1, 3, 3, 3, 2 },
			{ 1, 1, 1, 3, 4, 2, 2 } };

	// level 3 board.
	int level3[][] = { { 1, 1, 1, 1, 1, 1, 1 },
			{ 2, 2, 2, 4, 3, 4, 3 },
			{ 2, 3, 3, 3, 3, 2, 2 },
			{ 2, 3, 3, 3, 3, 3, 2 },
			{ 2, 3, 4, 2, 2, 2, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 2, 2, 2, 2, 2, 2 } };

	// level 4 board.
	int level4[][] = { { 4, 2, 2, 2, 2, 3, 3 },
			{ 3, 3, 3, 3, 2, 3, 3 },
			{ 3, 2, 2, 2, 2, 3, 3 },
			{ 3, 2, 3, 3, 3, 3, 2 },
			{ 3, 2, 3, 3, 3, 3, 2 },
			{ 3, 2, 2, 2, 2, 2, 2 },
			{ 3, 2, 3, 3, 3, 2, 3 },
			{ 3, 2, 3, 3, 3, 2, 3 },
			{ 3, 4, 3, 3, 3, 4, 3 },
			{ 3, 3, 3, 3, 3, 3, 3 } };

	// level 5 board.
	int level5[][] = { { 1, 1, 1, 1, 1, 1, 1 },
			{ 2, 2, 2, 2, 2, 2, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 2, 2, 3, 2, 2 },
			{ 2, 3, 2, 3, 3, 2, 3 },
			{ 4, 3, 2, 2, 3, 2, 2 },
			{ 3, 3, 3, 2, 3, 3, 2 },
			{ 3, 3, 3, 4, 3, 3, 4 },
			{ 1, 1, 1, 1, 1, 1, 1 } };

	// level 6 board.
	int level6[][] = { { 1, 1, 1, 1, 1, 1, 1 },
			{ 2, 2, 2, 2, 2, 2, 2 },
			{ 2, 3, 3, 3, 3, 3, 2 },
			{ 2, 3, 2, 2, 2, 3, 2 },
			{ 2, 3, 2, 3, 2, 2, 2 },
			{ 2, 3, 2, 4, 3, 3, 2 },
			{ 2, 3, 3, 3, 3, 3, 2 },
			{ 4, 3, 3, 3, 3, 3, 4 },
			{ 3, 3, 3, 3, 3, 3, 3 },
			{ 1, 1, 1, 1, 1, 1, 1 } };

	// level 7 board.
	int level7[][] = { { 1, 1, 1, 1, 1, 1, 1 },
			{ 4, 3, 3, 3, 4, 3, 3 },
			{ 2, 3, 3, 3, 2, 3, 3 },
			{ 2, 2, 2, 3, 2, 3, 3 },
			{ 2, 3, 2, 2, 2, 2, 4 },
			{ 2, 3, 3, 3, 3, 3, 3 },
			{ 2, 2, 2, 3, 2, 2, 2 },
			{ 3, 3, 2, 3, 2, 3, 2 },
			{ 3, 3, 2, 2, 2, 3, 2 },
			{ 1, 1, 1, 1, 1, 1, 1 } };

	// level 8 board.
	int level8[][] = { { 2, 2, 2, 2, 2, 2, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 2, 3, 3, 2, 3, 3, 2 },
			{ 4, 3, 3, 4, 3, 3, 4 },
			{ 1, 1, 1, 1, 1, 1, 1 } };

	// level 9 board.
	int level9[][] = { { 1, 1, 1, 1, 1, 1, 1 },
			{ 4, 3, 3, 3, 3, 4, 3 },
			{ 2, 3, 4, 3, 3, 2, 2 },
			{ 2, 3, 2, 3, 3, 3, 2 },
			{ 2, 2, 2, 2, 3, 2, 2 },
			{ 3, 3, 3, 2, 3, 2, 3 },
			{ 2, 2, 2, 2, 3, 2, 3 },
			{ 3, 3, 2, 2, 2, 2, 3 },
			{ 2, 3, 3, 3, 3, 3, 2 },
			{ 2, 2, 2, 2, 2, 2, 2 } };


	// all the things that will be displayed is called.
	public SokobanGame() {

		p_card = new Panel(); // the main card is being initialized.
		p_card.setLayout(cdLayout); // the main all holding card's layout is being set up.
		// all the different screens are being processed.
		screen1();
		screen1B();
		screen2();
		screen2B();
		screen2C();
		screen3();
		screen4();
		screen5();
		screen6();
		screen7();
		screen8A();
		screen8B();
		screen9();
		screen10();
		screen11();
		screen12();
		setLayout(new BorderLayout()); // sets the layout of the screen.
		add("Center", p_card); // the card is add onto the screen.

	}

	// screen 1 is set up (splash screen with buy button).
	public void screen1() { 

		screen = 1; // counts the screen.
		Music("theme.wav"); // plays the theme song.
		card1 = new Panel(); 
		card1.setBackground(Color.black); 
		JLabel image = new JLabel(createImageIcon("splash.jpg")); 
		JButton next = new JButton("BUY"); // opens up the buy pop up.
		next.setFont(new Font("Jokerman", Font.BOLD, 32)); 
		next.setForeground(Color.cyan); 
		next.setBackground(Color.black);
		next.setActionCommand("b0"); 
		next.addActionListener(this);
		card1.add(image);
		card1.add(next);
		p_card.add("1", card1); 

	}

	// the quick story line screen is set up (image as button).
	public void screen1B() { 

		card1B = new Panel();
		card1B.setBackground(Color.black);
		JButton next = new JButton(createImageIcon("storyline.jpg")); // goes to instructions.
		next.setActionCommand("b1");
		next.addActionListener(this);
		next.setForeground(Color.cyan);
		next.setBackground(Color.black);
		next.setBorderPainted(false);
		card1B.add(next);
		p_card.add("1B", card1B);

	}

	// screen 2 is set up (instructions (image as button)).
	public void screen2() { 

		card2 = new Panel();
		card2.setBackground(Color.black);
		JButton next = new JButton(createImageIcon("instructions.jpg")); // goes to 2nd part of instructions.
		next.setActionCommand("sIntro2");
		next.addActionListener(this);
		next.setForeground(Color.cyan);
		next.setBackground(Color.black);
		next.setBorderPainted(false);
		card2.add(next);
		p_card.add("2", card2);

	}

	// second part of the instructions is set up. (image as button)
	public void screen2B() { 

		card2B = new Panel();
		card2B.setBackground(Color.black);
		JButton next = new JButton(createImageIcon("instructions1.jpg")); // goes to 3rd part of the instructions.
		next.setActionCommand("sIntro");
		next.addActionListener(this);
		next.setForeground(Color.cyan);
		next.setBackground(Color.black);
		next.setBorderPainted(false);
		card2B.add(next);
		p_card.add("2B", card2B);

	}

	// third part of the instructions is set up (image as button).
	public void screen2C() { 

		card2C = new Panel();
		card2C.setBackground(Color.black);
		JButton next = new JButton(createImageIcon("instructions3.jpg")); // moves to the game screen.
		next.setActionCommand("s3");
		next.addActionListener(this);
		next.setForeground(Color.cyan);
		next.setBackground(Color.black);
		next.setBorderPainted(false);
		card2C.add(next);
		p_card.add("2C", card2C);

	}

	// screen 3 is set up (game screen with the board and the buttons to control the player).
	public void screen3() { 

		randomNum = randomPosition(6, 3); // randomNum is calling the method that has the randomly generated column value.

		card3 = new Panel();
		JButton reset = new JButton("Reset"); // resets the level.
		JLabel title = new JLabel("Rick and Morty Adventures");
		screen3HoldC(reset, title, next);
		Panel p = new Panel(new GridLayout(row, col));
		screen3BoardCreate(p); // calls the method to create the board.
		a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		JButton up = new JButton("Up"); // player moves up.
		JButton down = new JButton("Down"); // player moves down.
		JButton right = new JButton("Right"); // player moves right.
		JButton left = new JButton("Left"); // player moves left.
		hint = new JButton("Hint"); // hint pops up.
		card3.add(reset);
		card3.add(title);
		card3.add(next);
		screen3HoldA(up, down, right, left, hint);
		card3.add(p);
		screen3HoldB(up, down, right, left, hint);
		p_card.add("3", card3);

	}

	// creates the game board.
	public void screen3BoardCreate(Panel p) { 

		int move = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				a[move] = new JLabel(createImageIcon(board[i][j] + ".png"));
				// change to be your size
				a[move].setPreferredSize(new Dimension(64, 64));
				p.add(a[move]);
				move++;
			}
		}
	}

	// broken off portion of the game screen; changes the color of the movement and hint button.
	public void screen3HoldA(JButton up, JButton down, JButton right, JButton left, JButton hint) { 

		card3.setBackground(Color.black);
		up.setActionCommand("up");
		up.addActionListener(this);
		up.setBackground(Color.black);
		up.setForeground(Color.white);
		down.setActionCommand("down");
		down.addActionListener(this);
		down.setBackground(Color.black);
		down.setForeground(Color.white);
		right.setActionCommand("right");
		right.addActionListener(this);
		right.setBackground(Color.black);
		right.setForeground(Color.white);
		left.setActionCommand("left");
		left.addActionListener(this);
		left.setBackground(Color.black);
		left.setForeground(Color.white);
		hint.setActionCommand("hint");
		hint.addActionListener(this);
		hint.setBackground(Color.black);
		hint.setForeground(Color.white);

	}

	// more broken off portion of the game screen; adds the movement and hint button and changes font of the texts and adds onto the screen.
	public void screen3HoldB(JButton up, JButton down, JButton right, JButton left, JButton hint) { 

		Panel dir = new Panel(new GridLayout(3, 3));
		Panel text = new Panel(new GridLayout(1, 1));
		JLabel filler = new JLabel("");
		JLabel filler2 = new JLabel("");
		JLabel filler3 = new JLabel("");
		JLabel filler4 = new JLabel("");
		dir.add(filler);
		dir.add(up);
		dir.add(filler2);
		dir.add(left);
		dir.add(hint);
		dir.add(right);
		dir.add(filler3);
		dir.add(down);
		dir.add(filler4);
		card3.add(dir);
		counting = new JLabel("The move counter is " + counter + ".");
		lvlDisplay = new JLabel("          LEVEL: " + level);
		counting.setForeground(Color.white);
		lvlDisplay.setForeground(Color.white);
		text.add(counting);
		text.add(lvlDisplay);
		card3.add(text);

	}

	// even more broken off portion of the game screen; change color of reset and the title.
	public void screen3HoldC(JButton reset, JLabel title, JButton next) {

		reset.setActionCommand("reset");
		reset.addActionListener(this);
		reset.setForeground(Color.cyan);
		reset.setBackground(Color.black);
		title.setForeground(Color.cyan);
		title.setFont(new Font("Jokerman", Font.BOLD, 40));
		next.setActionCommand("s4");
		next.addActionListener(this);
		next.setForeground(Color.cyan);
		next.setBackground(Color.black);
		next.setEnabled(false); // Extra feature: disables the next button.

	}

	// screen 4 is set up (level choosing).
	public void screen4() { 

		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(400, 700));
		panel2.setOpaque(false);
		JPanel panel3 = new JPanel();
		panel3.setPreferredSize(new Dimension(725, 150));
		panel3.setOpaque(false);
		card4 = new Panel();
		card4.setBackground(Color.black);
		JLabel img = new JLabel(createImageIcon("lvlChoose.jpg"));
		img.setLayout(new FlowLayout());
		card4.add(img);
		img.add(panel3);
		img.add(panel2);
		JButton next = new JButton("Level Selector?"); // goes to level selector screen.
		next.setActionCommand("s7");
		next.addActionListener(this);
		JButton end = new JButton("Quit?"); // goes to quit screen.
		end.setActionCommand("s6");
		end.addActionListener(this);
		JLabel l1 = new JLabel(" ");
		next.setFont(new Font("Jokerman", Font.BOLD, 30));
		next.setForeground(Color.cyan);
		next.setBackground(Color.black);
		l1.setFont(new Font("Jokerman", Font.BOLD, 80));
		end.setFont(new Font("Jokerman", Font.BOLD, 30));
		end.setForeground(Color.cyan);
		end.setBackground(Color.black);
		panel2.add(next);
		panel2.add(l1);
		panel2.add(end);
		p_card.add("4", card4);

	}

	// screen 5 is set up (lose screen).
	public void screen5() { 

		card5 = new Panel();
		card5.setBackground(Color.cyan);
		JLabel title = new JLabel("You Lose.");
		JButton next = new JButton("Back to Introduction?"); // goes to the introduction screen.
		next.setActionCommand("s1"); 
		next.addActionListener(this);
		JButton end = new JButton("Quit?"); // goes to the quit screen.
		end.setActionCommand("s6");
		end.addActionListener(this);
		card5.add(title);
		card5.add(next);
		card5.add(end);
		p_card.add("5", card5);

	}

	// screen 6 is set up (terms screen).
	public void screen6() { 

		card6 = new Panel();
		card6.setBackground(Color.black);
		JButton next = new JButton("Next");
		next.setForeground(Color.cyan);
		next.setBackground(Color.black);
		next.setFont(new Font("Jokerman", Font.BOLD, 16));
		next.setActionCommand("s2");
		next.addActionListener(this);
		JLabel img = new JLabel(createImageIcon("term.jpg"));
		card6.add(next);
		card6.add(img);
		p_card.add("6", card6);

	}

	// screen 7 is set up (level selector).
	public void screen7() { 

		JPanel panel2 = new JPanel(); 
		panel2.setPreferredSize(new Dimension(725, 300));
		panel2.setOpaque(false);
		JPanel panel3 = new JPanel();
		panel3.setPreferredSize(new Dimension(725, 150));
		panel3.setOpaque(false);
		card7 = new Panel();
		card7.setBackground(Color.black);
		JLabel img = new JLabel(createImageIcon("lvlChoose.jpg"));
		img.setLayout(new FlowLayout());
		card7.add(img);
		img.add(panel3);
		img.add(panel2);
		JButton lv1 = new JButton("Level 1?"); // goes to level 1.
		lv1.setActionCommand("lvl1");
		lv1.addActionListener(this);
		JButton lv2 = new JButton("Level 2?"); // goes to level 2.
		lv2.setActionCommand("lvl2");
		lv2.addActionListener(this);
		JButton lv3 = new JButton("Level 3?"); // goes to level 3.
		lv3.setActionCommand("lvl3");
		lv3.addActionListener(this);
		JButton lv4 = new JButton("Level 4?"); // goes to level 4.
		lv4.setActionCommand("lvl4");
		lv4.addActionListener(this);
		JButton lv5 = new JButton("Level 5?"); // goes to level 5.
		lv5.setActionCommand("lvl5");
		lv5.addActionListener(this);
		lv1.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv1.setForeground(Color.cyan);
		lv1.setBackground(Color.black);
		lv2.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv2.setForeground(Color.cyan);
		lv2.setBackground(Color.black);
		lv3.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv3.setForeground(Color.cyan);
		lv3.setBackground(Color.black);
		lv4.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv4.setForeground(Color.cyan);
		lv4.setBackground(Color.black);
		lv5.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv5.setForeground(Color.cyan);
		lv5.setBackground(Color.black);
		JButton lv6 = new JButton("Level 6?"); // goes to level 6.
		lv6.setActionCommand("lvl6");
		lv6.addActionListener(this);
		lv6.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv6.setForeground(Color.cyan);
		lv6.setBackground(Color.black);
		JButton lv7 = new JButton("Level 7?"); // goes to level 7.
		lv7.setActionCommand("lvl7");
		lv7.addActionListener(this);
		lv7.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv7.setForeground(Color.cyan);
		lv7.setBackground(Color.black);
		JButton lv8 = new JButton("Level 8?"); // goes to level 8.
		lv8.setActionCommand("lvl8");
		lv8.addActionListener(this);
		lv8.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv8.setForeground(Color.cyan);
		lv8.setBackground(Color.black);
		JButton lv9 = new JButton("Level 9?"); // goes to level 9.
		lv9.setActionCommand("lvl9");
		lv9.addActionListener(this);
		lv9.setFont(new Font("Jokerman", Font.BOLD, 30));
		lv9.setForeground(Color.cyan);
		lv9.setBackground(Color.black);
		panel2.add(lv1); // ExtraFeature: button on top of an image.
		panel2.add(lv2);
		panel2.add(lv3);
		panel2.add(lv4);
		panel2.add(lv5);
		panel2.add(lv6);
		panel2.add(lv7);
		panel2.add(lv8);
		panel2.add(lv9);
		p_card.add("7", card7);

	}

	// review screen is set up.
	public void screen8A() { 

		card8A = new Panel();
		card8A.setBackground(Color.black);
		JLabel image = new JLabel(createImageIcon("review1.jpg"));
		card8A.add(image);
		JRadioButton b1 = new JRadioButton();
		b1.setText("Yes");
		b1.setBackground(Color.black);
		b1.setForeground(Color.cyan);
		b1.setFont(new Font("Jokerman", Font.BOLD, 20));
		b1.setActionCommand("review1");
		b1.addActionListener(this);
		b1.setSelected(false);
		card8A.add(b1);
		JRadioButton b2 = new JRadioButton(); // Extra feature: radio buttons is set up.
		b2.setText("No");
		b2.setBackground(Color.black);
		b2.setForeground(Color.cyan);
		b2.setFont(new Font("Jokerman", Font.BOLD, 20));
		b2.setActionCommand("quit");
		b2.addActionListener(this);
		b2.setSelected(false);
		card8A.add(b2);
		p_card.add("8A", card8A);

	}

	// the second review screen is set up.
	public void screen8B() { 

		card8B = new Panel();
		card8B.setBackground(Color.black);
		JLabel image = new JLabel(createImageIcon("review2.jpg"));
		card8B.add(image);
		JLabel txt = new JLabel("What things do you not like about the game?");
		txt.setForeground(Color.cyan);
		card8B.add(txt);
		JCheckBox b1 = new JCheckBox("Gameplay"); // Extra feature: check box is set up.
		b1.setForeground(Color.cyan);
		b1.setBackground(Color.black);
		b1.setActionCommand("negReview");
		b1.addActionListener(this);
		card8B.add(b1);
		JCheckBox b2 = new JCheckBox("Audio");
		b2.setForeground(Color.cyan);
		b2.setBackground(Color.black);
		b2.setActionCommand("negReview");
		b2.addActionListener(this);
		card8B.add(b2);
		JCheckBox b3 = new JCheckBox("Hints");
		b3.setForeground(Color.cyan);
		b3.setBackground(Color.black);
		b3.setActionCommand("negReview");
		b3.addActionListener(this);
		card8B.add(b3);
		JButton done = new JButton("Done");
		done.setFont(new Font("Jokerman", Font.BOLD, 14));
		done.setBackground(Color.black);
		done.setForeground(Color.cyan);
		done.setActionCommand("review2");
		done.addActionListener(this);
		card8B.add(done);
		p_card.add("8B", card8B);

	}

	// the third review screen is set up.
	public void screen9() { 

		card9 = new Panel();
		card9.setBackground(Color.black);
		JLabel image = new JLabel(createImageIcon("review3.jpg"));
		txt = new JTextArea(3, 60); // Extra feature: the text field is set up.
		JScrollPane scroll = new JScrollPane(txt); // Extra feature: the scroll bar is set up.
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
		JButton b = new JButton("Done");
		b.setFont(new Font("Jokerman", Font.BOLD, 20));
		b.setBackground(Color.black);
		b.setForeground(Color.cyan);
		b.setActionCommand("reviewDone");
		b.addActionListener(this);
		card9.add(image);
		card9.add(scroll);
		card9.add(b);
		p_card.add("9", card9);

	}

	// screen 10 is set up (user's review is displayed)
	public void screen10() { 

		card10 = new Panel();
		card10.setBackground(Color.black);
		lab = new JLabel("Your Rating was: " + rating + " /10");
		lab.setForeground(Color.cyan);
		lab.setFont(new Font("Jokerman", Font.BOLD, 40));
		txt1 = new JTextArea(30, 60);
		txt1.setForeground(Color.black);
		txt1.append(txt.getText());
		txt1.setEnabled(false);
		JScrollPane scroll = new JScrollPane(txt1);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txt1.setLineWrap(true);
		txt1.setWrapStyleWord(true);
		JButton end = new JButton("Quit?");
		end.setActionCommand("quit");
		end.addActionListener(this);
		end.setFont(new Font("Jokerman", Font.BOLD, 30));
		end.setForeground(Color.cyan);
		end.setBackground(Color.black);
		card10.add(lab);
		card10.add(txt1);
		card10.add(end);
		p_card.add("10", card10);

	}

	// screen 11 is set up (lose screen).
	public void screen11() { 

		card11 = new Panel();
		card11.setBackground(Color.black);
		JLabel img = new JLabel(createImageIcon("lose.jpg"));
		JButton end = new JButton("Quit?");
		end.setActionCommand("s6");
		end.addActionListener(this);
		end.setFont(new Font("Jokerman", Font.BOLD, 30));
		end.setForeground(Color.cyan);
		end.setBackground(Color.black);
		JButton replay = new JButton("Replay!");
		replay.setActionCommand("lvl1");
		replay.addActionListener(this);
		replay.setFont(new Font("Jokerman", Font.BOLD, 30));
		replay.setForeground(Color.cyan);
		replay.setBackground(Color.black);
		card11.add(img);
		card11.add(replay);
		card11.add(end);
		p_card.add("11", card11);

	}

	// screen 12 is set up (win screen).
	public void screen12() { 

		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(725, 300));
		panel2.setOpaque(false);
		JPanel panel3 = new JPanel();
		panel3.setPreferredSize(new Dimension(725, 150));
		panel3.setOpaque(false);
		card12 = new Panel();
		card12.setBackground(Color.black);
		JLabel img = new JLabel(createImageIcon("win.jpg"));
		img.setLayout(new FlowLayout());
		card12.add(img);
		img.add(panel3);
		img.add(panel2);
		JMenuBar menuBar = new JMenuBar (); // Extra Feature: menu bar is set up.
		menuBar.setBackground(new Color(20, 20, 20)); // Extra Feature: own custom color (almost black grey) to make it pop out a bit.
		JMenu menu = new JMenu ("Navigate");
		menu.setFont(new Font("Jokerman", Font.BOLD, 40));
		menu.setForeground(Color.cyan);
		menu.setBackground(Color.black);
		menuBar.add (menu);
		JMenuItem menuItem = new JMenuItem ("Quit?");
		menuItem.setForeground(Color.cyan);
		menuItem.setBackground(Color.black);
		menuItem.setFont(new Font("Jokerman", Font.BOLD, 30));
		menuItem.addActionListener (this);
		menuItem.setActionCommand ("s6");
		menu.add (menuItem);
		menuItem = new JMenuItem ("Level Selector?");
		menuItem.setForeground(Color.cyan);
		menuItem.setBackground(Color.black);
		menuItem.setFont(new Font("Jokerman", Font.BOLD, 30));
		menuItem.addActionListener (this);
		menuItem.setActionCommand ("s7");
		menu.add (menuItem);
		panel2.add(menuBar);
		p_card.add("12", card12);

	}

	// allows the images to be loaded into the screen.
	protected static ImageIcon createImageIcon(String path) { 

		java.net.URL imgURL = SokobanGame.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}

	}

	// method that gives out a random value that is used as the y-value.
	public int randomPosition(int max, int min) { 

		char operator[] = { '+', '-', ' ' }; // stores the operation symbols.
		int fNum = 0, sNum = 0;
		int i = 11;
		int x = (int) (Math.random() * 2); // gets random value.

		while (true) { // makes up an equation randomly for the random y-value.
			if (operator[x] == '+') {
				fNum = (int) (Math.random() * 10);
				sNum = (int) (Math.random() * 10);
				i = (fNum + sNum);
			} else if (operator[x] == '-') {
				fNum = (int) (Math.random() * 10);
				sNum = (int) (Math.random() * 10);
				i = (fNum - sNum);
			} else {
				fNum = (int) (Math.random() * 10);
				sNum = (int) (Math.random() * 10);
				i = (fNum + sNum);
				i = (fNum * sNum);
			}
			if (i <= max && i >= min) // as long as the equation adds up it breaks the loop.
				break;
		}

		sHint = fNum + " " + operator[x] + " " + sNum; // sets the global variable to store the calculations.
		return i; // returns the random position.

	}

	// refreshes the screen and re-puts all the items in the board.
	public void redraw() { 

		int move = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				a[move].setIcon(createImageIcon(board[i][j] + ".png"));
				move++;
			}
		}

	}

	public void moveUp() { // allows the player to move up.

		counter++; // increases the move count.
		trapActivate(); // the trap check is ran to see if a trap should be placed.
		counting.setText("The move counter is " + counter + "."); // changes the text of the count.
		if (x - 1 < 0) // if the player touches the edge message pops up.
			JOptionPane.showMessageDialog (null, "You are walking off the edge", "Error!", JOptionPane.ERROR_MESSAGE);
		else if (board[x - 1][y] == 4) { // checks if it's touching image number 4 (win piece)
			win(); // runs the code that should run after one wins.
		} else if (board[x - 1][y] != 2)  // if the player is not touching the walk path (number 2) then message pops up.
			JOptionPane.showMessageDialog (null, "The path is blocked!", "Error!", JOptionPane.ERROR_MESSAGE);
		else { // moves the player up.
			
			a[x * col + y].setIcon(createImageIcon("3.png"));
			board[x][y] = 3;
			x--;
			board[x][y] = 1;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "up.png")); // redraws the player.
			removeCheck(); // runs the command to remove a random block.
		}

	}

	public void moveDown() { // moves the player down.

		counter++; // increases the count.
		trapActivate(); // activates the traps.
		counting.setText("The move counter is " + counter + ".");
		if (x + 1 >= row) // if touching edge gives error.
			JOptionPane.showMessageDialog (null, "You are walking off the edge", "Error!", JOptionPane.ERROR_MESSAGE);
		else if (board[x + 1][y] == 4) { // if touching the win block.
			win(); // runs the win method.
		} else if (board[x + 1][y] != 2) // if touching other than path gives error.
			JOptionPane.showMessageDialog (null, "The path is blocked!", "Error!", JOptionPane.ERROR_MESSAGE);
		else { // moves the player down.
			a[x * col + y].setIcon(createImageIcon("3.png"));
			board[x][y] = 3;
			x++;
			board[x][y] = 1;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			removeCheck(); // runs the command to remove a random block.
		}

	}

	public void moveLeft() { // moves the player to the left.

		counter++; // increases the move count.
		trapActivate(); // activates a trap.
		counting.setText("The move counter is " + counter + ".");
		if (y - 1 < 0) // if touching edge, gives error.
			JOptionPane.showMessageDialog (null, "You are walking off the edge!", "Error!", JOptionPane.ERROR_MESSAGE);
		else if (board[x][y - 1] == 4) { // if touching portal then the player succeeds.
			win();
		} else if (board[x][y - 1] != 2) // if not touching path gives error.
			JOptionPane.showMessageDialog (null, "The path is blocked!", "Error!", JOptionPane.ERROR_MESSAGE);
		else { // makes the player move to the left.
			a[x * col + y].setIcon(createImageIcon("3.png"));
			board[x][y] = 3;
			y--;
			board[x][y] = 1;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "left.png"));
			removeCheck(); // runs the command to remove a random block.

		}

	}

	public void moveRight() { // moves the player to the right.

		counter++; // increases the move count.
		trapActivate(); // activates a trap.
		counting.setText("The move counter is " + counter + ".");
		if (y + 1 >= col) // if touching edge, gives error.
			JOptionPane.showMessageDialog (null, "You are walking off the edge!", "Error!", JOptionPane.ERROR_MESSAGE);
		else if (board[x][y + 1] == 4) { // if touching portal then the player succeeds.
			win();
		} else if (board[x][y + 1] != 2) // if not touching path gives error.
			JOptionPane.showMessageDialog (null, "The path is blocked!", "Error!", JOptionPane.ERROR_MESSAGE);
		else { // makes the player move to the left.
			a[x * col + y].setIcon(createImageIcon("3.png"));
			board[x][y] = 3;
			y++;
			board[x][y] = 1;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "right.png"));
			removeCheck(); // runs the command to remove a random block.
		}

	}

	public void removeCheck() { // checks what the level and the placement is to remove a random block within certain condition.

		if (level == 1 && placement == 0)
			randomRemove(5); // removes a random block from the row range of 0 to 5.
		else if (level == 2 && placement == 1) {
			randomRemove(5);
		} else if (level == 3 && placement == 1) {
			if (counter == 3) {
				board[5][0] = 3; //sets the board placement to be 3 (sand).
				board[4][3] = 3;
				redraw(); // recreates the screen so the change is displayed.
				a[x * col + y].setIcon(createImageIcon(characters[charInt] + "up.png")); // puts the character onto the screen.
			}
			randomRemove(4);
		} else if (level == 5 && placement == 1) {
			randomRemove(3);
		}

	}

	public int trapActivate() { // activates the trap method by checking what the level and the placement and returns the value where the trap was activated.

		int i = 0; // the return variable
		if (level == 1 && placement == 0)
			i = trap(7, 7, 4); // activates a the trap method (players count, column, row). 
		else if (level == 1 && placement == 1)
			i = trap(4, 6, 2);
		else if (level == 2 && placement == 1) {
			if (counter == 2)
				i = trap(2, 0, 3);
			else if (counter == 3)
				i = trap(3, 2, 4);
			trapReset();
			i = trap(9, 4, 4);
		} else if (level == 2 && placement == 2)
			i = trap(2, 6, 4);
		else // if none of the conditions is met then the second part is run.
			i = trapActivate2(i);  // i is given as condition and i will be the value of the method.
		return i; // returns the i.

	}

	public int trapActivate2(int x) { // the second portion of the trapActivate method, returns x (value taken as argument).

		if (level == 3 && placement == 1) {
			x = trap(3, 6, 0);
			x = trap(2, 6, 3);
		} else if (level == 4 && placement == 1) {
			x = trap(4, 7, 5);
			if (counter == 8)
				board[2][1] = 3;
			redraw();
		} else if (level == 4 && placement == 2) {
			x = trap(2, 8, 3);
			x = trap(2, 1, 1);
			x = trap(2, 1, 2);
			a[2 * col + 4].setIcon(createImageIcon("5.png"));
		} else if (level == 5 && placement == 1) {
			if (i <= 3)
				x = trap(4, 3, 3);
			x = trap(1, 3, 0);
		} else // if condition not met the third part is ran.
			x = trapActivate3(x); // x is the value returned by method and value given to method.
		return x; // returns x.

	}

	public int trapActivate3(int q) { // the third portion of the trapActivate method, returns q (value taken as argument).

		if (level == 6 && placement == 1) {
			if (counter == 5)
				a[4 * col + 4].setIcon(createImageIcon("5.png"));
			else if (counter == 10)
				a[4 * col + 2].setIcon(createImageIcon("5.png"));
			q = trap(5, 5, 6);
			q= trap (5, 5, 0);
		} else if (level == 6 && placement == 2)
			q = trap(3, 5, 6);
		else if (level == 6 && placement == 3) {
			if (counter == 2)
				a[7 * col + 4].setIcon(createImageIcon("5.png"));
		} else if (level == 7 && placement == 1) {
			q = trap(15, 2, 0);
			q = trap(21, 2, 4);
		} else if (level == 8 && placement == 1) {
			if (counter == 5) {
				board[6][0] = 3;
				a[6 * col + 0].setIcon(createImageIcon("3.png"));
			}
			q = trap(2, 4, 3);
		} else if (level == 8 && placement == 3) {
			if (counter == 1)
				a[8 * col + 1].setIcon(createImageIcon("5.png"));
		} else // if condition not met the fourth part is ran.
			q = trapActivate4(q); // q is the value returned by method and value given to method.
		return q; // returns q.

	}

	public int trapActivate4(int s) { // the fourth portion of the trapActivate method, returns s (value taken as argument).

		if(level == 9 && placement == 1) {
			if(counter == 1) {
				board[6][2] = 5;
				board[7][5] = 5;
				a[6 * col + 2].setIcon(createImageIcon("5.png"));
				a[7 * col + 5].setIcon(createImageIcon("5.png"));
			}else if (counter == 3) {
				board[3][2] = 3;
				a[3 * col + 2].setIcon(createImageIcon("3.png"));
			} else if (counter == 4)
				a[4 * col + 0].setIcon(createImageIcon("5.png"));
		}
		return s; // returns s.

	}

	public void trapReset() { // resets the trap (makes it back to walk path) if the condition is met.

		if (level == 2 && placement == 1) {
			if (counter == 4) {
				board[0][3] = 2;
				a[0 * col + 3].setIcon(createImageIcon("2.png"));
			}
			if (counter == 6) {
				board[2][4] = 2;
				a[2 * col + 4].setIcon(createImageIcon("2.png"));
			}
		}

	}

	public void randomRemove(int maxRandomValue) { // sets the value of the 

		if (i == ' ')
			i = randomNum; // i takes the positional value of the randomNum.

		loop: while (true) {// runs until the position is not the edge or the portal and makes the block in the position a sand.
			j = (int) (Math.random() * maxRandomValue);
			if ((j != 0 && j != 6) && (board[i][j] != 1 && board[i][j] != 4)) {
				board[i][j] = 3;
				a[i * col + j].setIcon(createImageIcon("3.png"));
				break loop;
			}
		}

	}

	public int trap(int count, int trapy, int trapx) { // add the trap in the position given; when specified count is met.

		int temp = 0;
		if (i < 4 && (counter % count == 0)) { // sets the trap when the random removes row is higher than 4.
			board[trapy][trapx] = 5;
			a[trapy * col + trapx].setIcon(createImageIcon("5.png"));
			temp = 0; // makes temp 0.
		} else if (i >= 4 && (counter % count == 0)) { // sets the position when the random removes column is less than 4.
			board[trapy][trapx] = 5;
			a[trapy * col + trapx].setIcon(createImageIcon("5.png"));
			temp = 1; // makes temp 1.
		}
		return temp; // returns the value of the temp.

	}

	public void lose() { // the command is ran when the player loses (loseCount == 0).

		cdLayout.show(p_card, "11"); // the lose screen is displayed.
		loseCount = 4; // resets the lose count.

	}

	public void reset() { // resets the levels when the reset button is pressed.

		loseCount--; // the loseCount (of 4) goes down by 1.
		if (loseCount > 0) { // the code runs as long as loseCount is not 0.
			switch (level) { // Extra feature: switch.
			case 1: // reset level 1.
				copyOver(board, save);
				redraw();
				x = 2;
				y = 1;
				placement = 0;
				counter = 0;
				counting.setText("The move counter is " + counter + ".");
				a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
				break;
			case 2: // reset level 2.
				copyOver(board, save);
				redraw();
				y = 0;
				x = 2;
				placement = 1;
				counter = 0;
				counting.setText("The move counter is " + counter + ".");
				a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
				break;
			default: // if its none of the levels.
				reset2(); // go to the second portion.
			}
		} else // if more than 4 then make the player lose.
			lose();

	}

	public void reset2() { // second portion of the reset.

		switch (level) {
		case 3: // reset level 3.
			copyOver(board, save);
			redraw();
			y = 4;
			x = 8;
			placement = 1;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			charInt = 0;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			break;
		case 4: // reset level 4.
			copyOver(board, save);
			redraw();
			y = 6;
			x = 3;
			placement = 1;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			break;
		case 5: // reset level 5.
			copyOver(board, save);
			redraw();
			x = 1;
			y = 0;
			placement = 1;
			charInt = 1;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			break;
		default: // if none of the condition is met then run the 3rd portion of the reset.
			reset3();
		}

	}

	public void reset3() { // third portion of the reset.

		switch (level) {
		case 6: // reset level 6.
			copyOver(board, save);
			redraw();
			x = 1;
			y = 3;
			placement = 1;
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			break;
		case 7: // reset level 7.
			copyOver(board, save);
			redraw();
			x = 8;
			y = 6;
			placement = 1;
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			break;
		default: // if none of the levels met run the 4th aspect.
			reset4();
		}

	}

	public void reset4() // fourth portion of the reset.
	{
		switch(level) {
		case 8 : // reset level 8.
			copyOver(board, save);
			redraw();
			x = 0;
			y = 3;
			placement = 1;
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			break;
		case 9 : // reset level 9.
			copyOver(board, save);
			redraw();
			x = 7;
			y = 3;
			placement = 1;
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			JOptionPane.showMessageDialog(null, createImageIcon("message.jpg"), "URGENT MESSAGE!",
					JOptionPane.ERROR_MESSAGE); // pop up with an image.
			break;
		}
	}
	public void copyOver(int m[][], int n[][]) { // copies the m values to be the n value.

		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				m[i][j] = n[i][j];
	}

	public void levelUp() { // after 3 portals in a levels; increases the level.

		counter = 0; // count is set to 0.
		level++; // level is increased.
		lvlDisplay.setText("          LEVEL: " + level); // changes the level text.
		placement = 1; // placement is set to 1.
		if (level == 1) { // if level is 1.
			copyOver(board, level1);
			copyOver(save, level1);
		} else if (level == 2) { //if level is 2.
			copyOver(board, level2);
			copyOver(save, level2);
		} else if (level == 3) { // if level is 3.
			copyOver(board, level3);
			copyOver(save, level3);
		} else if (level == 4) { // if level is 4.
			copyOver(board, level4);
			copyOver(save, level4);
		} else if (level == 5) { // if level is 5.
			copyOver(board, level5);
			copyOver(save, level5);
		} else if (level == 6) { // if level is 6.
			copyOver(board, level6);
			copyOver(save, level6);
		} else if (level == 7) { // if level is 6.
			copyOver(board, level7);
			copyOver(save, level7);
		} else // if no condition is met run the second portion.
			levelUp2();
		redraw();

	}

	public void levelUp2() { // second portion of levelUp.

		if (level == 8) { // if level is 8.
			copyOver(board, level8);
			copyOver(save, level8);
		} else if (level == 9) { // if level is 9.
			copyOver(board, level9);
			copyOver(save, level9);
		}

	}

	public void sneakyClearUp(int x, int y, int pos) { // cleans up the edges (sometimes the random path builder goes hay-wire).

		if (pos == 0) { // makes all column (x) position as 1(space pic).
			for (int i = 0; i < col; i++)
				board[x][i] = 1;
		} else { // makes all row (y) position as 1(space pic).
			for (int i = 0; i < row; i++)
				board[i][y] = 1;
		}

	}

	public void pathEmpty() { // makes the portals into sand.

		for (int i = 1; i < (row - 1); i++)
			for (int j = 1; j < (col - 1); j++)
				if (board[i][j] == 5)
					board[i][j] = 3;

	}

	public void levelBuilderx(int start, int end, int b) { // builds a path horizontally in the given column (b) from the value of start to end.

		pathEmpty(); // calls on the pathEmpty
		for (int i = start; i < end; i++) // makes the pathway.
			board[b][i] = 2;
		if (temp == ' ')
			temp = trapActivate(); // sets the value of temp to trap Activate.

	}

	public void levelBuildery(int start, int end, int b) { // builds a semi random path horizontally in the given row (b) from the value of start to end.

		int temp;
		for (int i = start; i < end; i++) // makes the pathway.
			board[i][b] = 2;

		while (true) { // randomly makes the path a bit weird and not only linear.
			pathEmpty();
			temp = (int) ((int) (start + 3) + (Math.random() * (row - (end - 1)))); // gets random temp values.
			if (temp > -1 && temp < 8) { // as long as temp is in the range of the board makes the path weird in the screen and breaks the loop.
				board[temp][b - 1] = 2;
				for (int i = 0; i <= 3; i++) { // makes the value a sand and the value behind it a path.
					temp--; // decreases temp value.
					if (board[temp][b] != 4) {
						board[temp][b] = 3;
						board[temp][b - 1] = 2;
					}
				}
				break; // breaks the loop.
			}
		}

	}

	public void win() { // the win method (runs after the portal is touched) depending on the level and the placement.

		if (level == 1 && placement == 0) { // when the first portal is touched in level 1.
			charInt=0; // the character skin is the first skin.
			placement++; // increase placement. 
			counter = 0; // make counter to 0.
			counting.setText("The move counter is " + counter + "."); // displays the changed message.
			levelBuilderx(1, 7, 6); // make a horizontally pathway.
			redraw(); // reset the screen to show the change.
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png")); // refresh the character.
		} else if (level == 1 && placement == 1) { // second portal in level 1.
			counter = 0;
			counting.setText("The move counter is " + counter + "."); // displays the changed message.
			placement++; // increases the placement.
			levelBuilderx(0, 4, 7); // horizontal pathway.
			redraw(); // reset the screen.
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png")); // redraw the character.
		} else if (level == 1 && placement == 2) { // third portal in level 1.
			levelUp(); // increase the level to level 2.
			charInt=0; // the character skin is the first skin.
			counter = 0; 
			counting.setText("The move counter is " + counter + "."); // display the changed message.
			y = 0; // change characters y value.
			x = 2; // change characters x value.
			randomPosition(5, 3); // gets the random column position; takes the max column value and the min.
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png")); // redraws the player.
		} else // if none of the condition is met display the second portion.
			win2();

	}

	public void win2() { // second portion of win

		if (level == 2 & placement == 1) { // level 2 first portal is touched.
			counter = 0;
			counting.setText("The move counter is " + counter + "."); // display the changed message.
			placement++; // increase placement.
			levelBuilderx(3, 7, 8); // creates horizontal pathway.
			levelBuildery(3, 8, 6); // creates vertical pathway.
			levelBuildery(3, 5, 1);
			levelBuilderx(1, 5, 3);
			board[5][1] = 2; // set a path (sometimes random becomes weird).
			board[7][0] = 1; // set the edge to be space.
			redraw(); // refresh the screen and display change.
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png")); // put character onto the screen.
		}else if (level == 2 && placement == 2) { // second portal of level 2, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuilderx(1, 3, 6);
			redraw();
			randomPosition(5, 3);
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 2 && placement == 3) {// final portal of level 2, increases the level, display new counter message and gets a random column value.
			levelUp();
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			y = 4;
			x = 8;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			randomPosition(7, 3);
		} else // if none of the condition match run the third part of win screen.
			win3();

	}

	public void win3() { // third portion of win.

		if (level == 3 && placement == 1) { // first portal of level 3, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuildery(1, 5, 6);
			levelBuilderx(4, 6, 4);
			board[4][3] = 2;
			board[4][4] = 2;
			sneakyClearUp(0, ' ', 0); // cleans up the screen to look nice. 
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			a[4 * col + 2].setIcon(createImageIcon("6.png")); // sets (4, 2) to be 6 (the random character).
		}else if (level == 3 && placement == 2) { // second portal of level 3, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			placement++;
			charInt = 1; // increases the charInt to be the second screen.
			levelBuildery(1, 5, 3);
			board[1][3] = 4;
			board[1][2] = 2;
			board[6][1] = 2;
			board[2][2] = 2;
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 3 && placement == 3) { // final portal of level 2, increases the level, display new counter message and gets a random column value.
			levelUp();
			charInt = 1;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			y = 6;
			x = 3;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			randomPosition(4, 0);
		} else // if no condition match then run the fourth broken off part.
			win4();

	}

	public void win4() { // the fourth broken part of the win.

		if (level == 4 && placement == 1) { // first portal of level 4, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuilderx(1, 5, 8);
			levelBuildery(1, 6, 2);
			board[2][1] = 2;
			board[2][2] = 2;
			board[6][2] = 2;
			board[7][2] = 2;
			board[0][2] = 2;
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 4 && placement == 2) { // second portal of level 4, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			simplifiedLevelBuildery(0, 4, 0);
			levelBuilderx(0, 3, 4);
			simplifiedLevelBuildery(4, 8, 3);
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 4 && placement == 3) { // final portal of level 4, increases the level, display new counter message and gets a random column value.
			levelUp();
			charInt = 1;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			x = 1;
			y = 0;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			randomPosition(5, 4);
			
		}  else // if no conditions match then go to the fifth portion.
			win5();

	}

	public void win5() {

		if (level == 5 && placement == 1) { // first portal of level 5, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuilderx(4, 7, 8);
			redraw();
			a[8 * col + 3].setIcon(createImageIcon("6.png"));
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 5 && placement == 2) { // second portal of level 5, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			simplifiedLevelBuildery(6, 8, 3);
			levelBuilderx(1, 3, 6);
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 5 && placement == 3) { // final portal of level 5, increases the level and display new counter message.
			levelUp();
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			x = 1;
			y = 3;
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 6 && placement == 1) { // first portal of level 6, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuilderx(0, 2, 5);
			levelBuilderx(3, 7, 5);
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else // if no conditions match display the 6th aspect.
			win6();

	}

	public void win6() { // sixth aspect of the win screen.

		if (level == 6 && placement == 2) { // second portal of level 6, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuilderx(0, 6, 7);
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 6 && placement == 3) { // final portal of level 6, increases the level and display new counter message.
			levelUp();
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			x = 8;
			y = 6;
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 7 && placement == 1) { // first portal of level 7, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			simplifiedLevelBuildery(1, 4, 6);
			levelBuilderx(5, 6, 1);
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else // if yet no levels and placements match then goes to the 7th win.
			win7();

	}

	public void win7() { // seventh portion of the win.

		if (level == 7 && placement == 2) { // second portal of level 7, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuilderx(1, 5, 1);
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 7 && placement == 3) { // final portal of level 7, increases the level and display new counter message.
			levelUp();
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			x = 0;
			y = 3;
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 8 && placement == 1) { // first portal of level 8, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			charInt = 2; // switches to the last dress.
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuilderx(4, 7, 8);
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else // if no other condition is met then runs this.
			win8();

	}

	public void win8() { // last part of the win.

		charInt = 2; // increase the character's outfit to the last one.
		if (level == 8 && placement == 2) { // second portal of level 8, increases placement, display new counter message and makes a pathway and refreshes the screen to display the changes.
			placement++;
			charInt = 2;
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			levelBuilderx(1, 7, 8);
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
		} else if (level == 8 && placement == 3) { // final portal of level 8, increases the level and display new counter message.
			levelUp();
			hint.setEnabled(false); // sets the hint to be false.
			counter = 0;
			counting.setText("The move counter is " + counter + ".");
			charInt = 2;
			x = 7;
			y = 3;
			redraw();
			a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));
			JOptionPane.showMessageDialog(null, createImageIcon("message.jpg"), "URGENT MESSAGE!",
					JOptionPane.ERROR_MESSAGE); // Extra Feature: pop up with an image.
		} else { // if no win conditions match then it is level 9 first portal so it changes to screen 12 and sets the next button to be enabled.
			cdLayout.show(p_card, "12"); 
			next.setEnabled(true);  
		}

	}

	public void simplifiedLevelBuildery(int start, int end, int b) { // a simple row builder used when randomized one was not needed.
		for (int i = start; i <= end; i++) // in the row (b) build from the start to the end value.
			board[i][b] = 2;
	}

	public void hintToScreen() { // displays the hints up on to the screen as a pop up.

		if(j == 32) // not initialized variables become 32; this is a quick bug fixer.
			j = 0;
		// level one hints:
		if (level == 1 && placement == 0)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Go not to: " + sHint + "; " + j + "\n"
					+ "Going straight to the deep of hell is never ideal!\n",
					"Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 1 && placement == 1)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "The path to victory is never straight!\n"
					+ "One must the pick path that is painful\n"
					+ "and harder to reach!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 1 && placement == 2)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "When the opportunity is present one!\n"
					+ "should simply march to claim ones long\n"
					+ "sought after objective!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		// level 2 hints:
		else if (level == 2 && placement == 1)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Go not to: " + sHint + "; " + j + "\n"
					+ "A trap in LOOPS may seem to make one stuck, unless\n"
					+ "one has the faith to go THROUGH them. Just as victory\n"
					+ "is only given to the one that goes the furthest!\n"
					+ "A trap is only an illusion and illusion is not real!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else // if none of it match run the second portion.
			hintToScreen1();

	}

	public void hintToScreen1() { // second portion of hints.

		// level 2 hints:
		if (level == 2 && placement == 2)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "The wise goes through the path\n"
					+ "which is not BLOCKED for you to\n"
					+ "pass your way through!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 2 && placement == 3)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "A great person once proclaimed the wisest\n"
					+ "of the wise simply WALK to the objective and win!", "Instructions",
					JOptionPane.QUESTION_MESSAGE);
		// level 3 hints:
		else if (level == 3 && placement == 1)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Go not to: " + sHint + "; " + j + "\n"
					+ "One ought not go to the path that is NOT right!\n"
					+ "Size really does matter hence small is not good!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 3 && placement == 2)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Maybe going to some random character would help?\n"
					+ "Or it could be a trap set by the prison guards!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 3 && placement == 3)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "I know you don't want to be a sweat,\n"
					+ "so just walk to the nearest portal!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else // if condition don't match run hint part 3.
			hintToScreen2();

	}

	public void hintToScreen2() { // third portion of the hints.

		// level 4 hints:
		if (level == 4 && placement == 1)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "The path to the HEAVENS always envolves death!\n"
					+ "The quick path is said to lead people to destruction!\n"
					+ "The only way left therefore must be the way!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 4 && placement == 2)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Let'e be honest you think the path,\n"
					+ "will be so easy? If you think so guess again,\n"
					+ "go the hard way! One of the guard is only,\n"
					+ "part of your imagination. Imagination is not real,\n"
					+ "hence just walk through it!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 4 && placement == 3)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Do you seriously still need hints?\n"
					+ "Just figure it out, the path is the\n"
					+ "only viable path!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		// level 5 hints:
		else if (level == 5 && placement == 1)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Go not to: " + sHint + "; " + j + "\n"
					+ "One ought not go to the path that is in front!\n"
					+ "One ought really take into account '3rd time is the charm!'!", "Instructions",
					JOptionPane.QUESTION_MESSAGE);
		else // if none of the conditions match goes to the fourth portion.
			hintToScreen3();
	}

	public void hintToScreen3() { // fourth portion of the hints.

		// level 5 hints.
		if (level == 5 && placement == 2)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Maybe going to some random character would help?\n"
					+ "Or it could be a trap set by the prison guards!\n"
					+ "Even worse an illusion!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 5 && placement == 2)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Simple advise, just walk the path!\n"
					+ "Works 100% of the time.", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 5 && placement == 3)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Just walk to the portal!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		// level 6 hints:
		else if (level == 6 && placement == 1)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "The path that heads down only leads to despair!\n"
					+ "TRUST the LOOP and never doubt the way!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if (level == 6 && placement == 2)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "The path that is right is not always the right path!\n"
					+ "Hence one must take the path that may seem not right!", "Instructions",
					JOptionPane.QUESTION_MESSAGE);
		else if (level == 6 && placement == 3)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "The path is clearly visible to the one that walks!\n"
					+ "The only path is the right path!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else // if no other condition is met then runs the fourth part.
			hintToScreen4();

	}

	public void hintToScreen4() { // last part of the hintToScreen.

		// level 7 hints:
		if (level == 7 && placement == 1)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Memorize a certian code for it shall come in handy for quite some levels to come.\n"
					+ "Sucess comes to the one that sacrifices blood, sweat and tears!\n"
					+ "The path to the first smells of a bit FISHY!\n"
					+ "The path to the third smells of sweat!\n"
					+ "The path to the second smells of flowers!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if ((level == 7 && placement == 2) || (level == 7 && placement == 3))
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "Walk simply to the portal, there is nothing else.", "Instructions", JOptionPane.QUESTION_MESSAGE);
		// level 8 hints:
		else if (level == 8 && placement == 1)
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "The first path leads to heavens.\n"
					+ "The second to the gateways of hell.\n"
					+ "The third leads directly to tears!\n"
					+ "The wise has taken the advise stated before\n"
					+ "and knows the path to sucess!", "Instructions", JOptionPane.QUESTION_MESSAGE);
		else if ((level == 8 && placement == 2) || (level == 8 && placement == 3))
			JOptionPane.showMessageDialog(null, "* * * H I N T S *    * * \n \n"
					+ "For that wise the levels are easy.\n"
					+ "The pathway is straight and the wise\n"
					+ "shall head there with no doubt in mind!", "Instructions", JOptionPane.QUESTION_MESSAGE);

	}

	public void Music(String filepath) { // ExtraFeatures: sound. Plays the music whenever this is called (takes the name of the file as the argument).

		String mainPath = System.getProperty("user.dir"); // gets where this file is located in the computer.

		try { // runs if no error.
			File musicPath = new File(mainPath + "\\src\\\\" + filepath); // ExtraFeature: Files, sets the path as a file value.
			if (musicPath.exists()) { // checks if it exists and returns true if it does
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath); // Initializes the audio to be played.
				clip = AudioSystem.getClip(); // the clip accesses the audio.
				clip.open(audioInput); // opens up the file.
				clip.setMicrosecondPosition(0); // starts at 0 seconds (0 second of the audio [the start]).
				clip.start(); // starts to play the audio.
				clip.loop(Clip.LOOP_CONTINUOUSLY); // loops the audio over and over.
			} else { // runs if it can't find the file.
				System.out.println("Can't find file!");
			}
		} catch (Exception ex) { // runs if for some reason there is an error.
			ex.printStackTrace(); // prints out the actual error message
		}

	}

	public void MusicChange(String file) { // switches the sound track by stopping the song and running a new song.
		clip.stop();
		Music(file);
	}

	public void purchasePopUp() { // Extra feature: Pop-up with dual buttons. The pop up message that comes up when the user presses buy in the first screen.

		Object[] options = { "OK", "CANCEL" }; // the messages in the button.
		int selectedVale = JOptionPane.showOptionDialog(null, "Click OK to purchase. NO REFUNDS!", "Purchase",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, options, options[0]); // gets the value depending on which the user pressed.
		if (selectedVale == 0) { // if user pressed the buy, change the screen and switch the audio.
			cdLayout.show(p_card, "1B");
			MusicChange("speechMusic.wav");
		} else // if user did not press buy then send a pop-up error message.
			JOptionPane.showMessageDialog(null, "Your purchase has failed!", "Purchase Failed",
					JOptionPane.ERROR_MESSAGE); // Extra feature: Pop-up message.

	}

	public void level1Reset() { // resets the first level.

		cdLayout.show(p_card, "3");
		charInt = 0;
		level = 1;
		copyOver(board, level1);
		copyOver(save, board);
		redraw();
		x = 2;
		y = 1;
		placement = 0;
		counter = 0;
		counting.setText("The move counter is " + counter + ".");
		a[x * col + y].setIcon(createImageIcon(characters[charInt] + "down.png"));

	}

	public void levelsPick(int l, int p) { // when the user chooses a button it lets the user go to that level depending on the placement and the level.
		cdLayout.show(p_card, "3");
		level = l;
		placement = p;
		win();
	}

	public void actionPerformed(ActionEvent e) { // runs when the certain button is pressed.

		if (e.getActionCommand().equals("b1")) // goes to screen 6.
			cdLayout.show(p_card, "6");
		else if (e.getActionCommand().equals("b0")) // opens up the purchase pop up.
			purchasePopUp();
		else if (e.getActionCommand().equals("s1")) // goes to the screen 1.
			cdLayout.show(p_card, "1");
		else if (e.getActionCommand().equals("s2")) // goes to screen 2.
			cdLayout.show(p_card, "2");
		else if (e.getActionCommand().equals("sIntro2")) // goes to screen 2B.
			cdLayout.show(p_card, "2B");
		else if (e.getActionCommand().equals("sIntro")) // goes to screen 2C.
			cdLayout.show(p_card, "2C");
		else if (e.getActionCommand().equals("s3")) { // changes the music and goes to the game screen.
			MusicChange("gameJuice.wav");
			cdLayout.show(p_card, "3");
		} else if (e.getActionCommand().equals("s4")) // goes to screen 4.
			cdLayout.show(p_card, "4");
		else if (e.getActionCommand().equals("s5")) // goes to screen 5.
			cdLayout.show(p_card, "5");
		else if (e.getActionCommand().equals("s6")) // goes to screen 8A.
			cdLayout.show(p_card, "8A");
		else if (e.getActionCommand().equals("s7")) // goes to screen 7.
			cdLayout.show(p_card, "7");
		else if (e.getActionCommand().equals("up")) // moves up if condition met.
			moveUp();
		else if (e.getActionCommand().equals("down")) // moves down method if condition met.
			moveDown();
		else if (e.getActionCommand().equals("left")) // moves left if condition met.
			moveLeft();
		else if (e.getActionCommand().equals("right")) // moves right if condition met.
			moveRight();
		else if (e.getActionCommand().equals("hint")) // opens up the hints.
			hintToScreen();
		else if (e.getActionCommand().equals("reset")) // resets the level.
			reset();
		else if (e.getActionCommand().equals("lvl1")) // resets the first level.
			level1Reset();
		else if (e.getActionCommand().equals("lvl2")) // resets level 2.
			levelsPick(1, 2);
		else if (e.getActionCommand().equals("lvl3")) // resets level 3.
			levelsPick(2, 3);
		else if (e.getActionCommand().equals("lvl4")) // resets level 4.
			levelsPick(3, 3);
		else if (e.getActionCommand().equals("lvl5")) // resets level 5.
			levelsPick(4, 3);
		else if (e.getActionCommand().equals("lvl6")) // resets level 6.
			levelsPick(5, 3);
		else if (e.getActionCommand().equals("lvl7")) // resets level 7.
			levelsPick(6, 3);
		else if (e.getActionCommand().equals("lvl8")) // resets level 8.
			levelsPick(7, 3);
		else if (e.getActionCommand().equals("lvl9")) // resets level 9.
			levelsPick(8, 3);
		else if (e.getActionCommand().equals("quit")) // exits the game.
			System.exit(0);
		else if (e.getActionCommand().equals("negReview")) // if the user check the check boxes removes a rating.
			rating -= 2;
		else if (e.getActionCommand().equals("review1"))  // goes to screen 8B.
			cdLayout.show(p_card, "8B");
		else if (e.getActionCommand().equals("review2")) // goes to screen 9.
			cdLayout.show(p_card, "9");
		else if (e.getActionCommand().equals("reviewDone")) // in screen 10 shows what the rating user gave and the suggestion they gave.
			lastReview();

	}

	public void lastReview() { // it shows what the user gave as rating and feedback text in screen 10.

		cdLayout.show(p_card, "10"); // goes to screen 10.
		txt1.append(txt.getText());
		if(rating >= 0) // as long as rating is more than 0.
			lab.setText("Your rating was: " + rating + " / 10.");
		else // quick fix for ticking and not ticking check boxes cause it to go to the negative value.
			lab.setText("Your rating was: " + 2 + " / 10.");

	}

	public static void main(String[] args) { // runs the screen.

		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create and set up the window.
		JFrame frame = new JFrame("Rick and Morty Adventures"); // the name of the application.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create and set up the content pane.
		JComponent newContentPane = new SokobanGame(); // runs the current file.
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		frame.setSize(725, 700); // sets the screen size.
		frame.setVisible(true);

	}

}// class ends
