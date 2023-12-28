//***************************************
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class TicTacToe implements ActionListener{

	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JButton[] buttons = new JButton[9];
    JButton start_play = new JButton();
    JButton[] replay = new JButton[2];
    JTextField textFieldD = new JTextField("Initial Text");
	boolean player1_turn;
    int count;                                                  //Count of no of turns that have been played in the specific game

    public void repeat()
    {
        count = 0;
        player1_turn = true;
        textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free",Font.BOLD,75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Tic-Tac-Toe");
		textfield.setOpaque(true);
		
		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0,0,800,100);

        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.PAGE_START);

        start_play.setBounds(0, 100, 800, 600);
        start_play.addActionListener(this);
        start_play.setFont(new Font(start_play.getFont().getName(), start_play.getFont().getStyle(), 44));
        start_play.setText("Press to start the game");
        start_play.setFocusable(false);
        start_play.setBorder(BorderFactory.createEtchedBorder());
        start_play.addActionListener(this);

        frame.add(start_play);
		
		button_panel.setLayout(new GridLayout(3,3));
		button_panel.setBackground(new Color(150,150,150));
        button_panel.setVisible(true);
		
		for(int i=0;i<9;i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
		}
    }

	TicTacToe(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);

        repeat();
    }

	@Override
	public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == start_play) {
            frame.getContentPane().removeAll();
            start_play.setVisible(false);
            frame.add(title_panel, BorderLayout.PAGE_START);
            button_panel.setEnabled(true);
            button_panel.setVisible(true);
            frame.add(button_panel);  
            firstTurn();       
        } else if(e.getSource() == replay[0]) {         //replay[0] ----> Yes button to play again
            button_panel.removeAll();
            textfield.removeAll();
            button_panel.setVisible(false);
            frame.invalidate();
            repeat();
        } else if(e.getSource() == replay[1]) {         //replay[0] ----> No button to stop play
           textfield.setText("Ok see you again");
           button_panel.setVisible(false);
           new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException er) {
                    er.printStackTrace();
                }
                SwingUtilities.invokeLater(() -> frame.dispose());
            }).start();
        } else {
            for(int i=0;i<9;i++) {                      //Checking where each token is placed
                if(e.getSource()==buttons[i]) {
                    count++;
                    if(player1_turn) {
                        if(buttons[i].getText()=="") {
                            buttons[i].setForeground(new Color(255,0,0));
                            buttons[i].setText("X");
                            player1_turn=false;
                            textfield.setText("O turn");
                            check();
                        }
                    }
                    else {
                        if(buttons[i].getText()=="") {
                            buttons[i].setForeground(new Color(0,0,255));
                            buttons[i].setText("O");
                            player1_turn=true;
                            textfield.setText("X turn");
                            check();
                        }
                    }
                }
            }
        }
	}
	
	public void firstTurn() {
        textfield.setText("X plays");
        player1_turn = true;
	}
	
	public void check() {
		//check win conditions
		if((buttons[0].getText() == buttons[1].getText() &&
            buttons[0].getText() == buttons[2].getText() &&
            buttons[0].getText() != "")) {
			    winner(0, 1, 2, buttons[1].getText());
		}
        if((buttons[3].getText() == buttons[4].getText() &&
            buttons[3].getText() == buttons[5].getText()&&
            buttons[3].getText() != "")) {
			    winner(3, 4, 5, buttons[3].getText());
		}
        if((buttons[6].getText() == buttons[7].getText() &&
            buttons[6].getText() == buttons[8].getText()&&
            buttons[6].getText() != "")) {
			    winner(6, 7, 8, buttons[6].getText());
		}
        if((buttons[0].getText() == buttons[3].getText() &&
            buttons[0].getText() == buttons[6].getText()&&
            buttons[0].getText() != "")) {
			    winner(0, 3, 6, buttons[6].getText());
		}
        if((buttons[1].getText() == buttons[4].getText() &&
            buttons[1].getText() == buttons[7].getText()&&
            buttons[1].getText() != "")) {
			    winner(1, 4, 7, buttons[1].getText());
		}
        if((buttons[2].getText() == buttons[5].getText() &&
            buttons[2].getText() == buttons[8].getText()&&
            buttons[2].getText() != "")) {
			    winner(2, 5, 8, buttons[2].getText());
		}
        if((buttons[0].getText() == buttons[4].getText() &&
            buttons[0].getText() == buttons[8].getText()&&
            buttons[0].getText() != "")) {
			    winner(0, 4, 8, buttons[0].getText());
		}
        if((buttons[2].getText() == buttons[4].getText() &&
            buttons[2].getText() == buttons[6].getText()&&
            buttons[2].getText() != "")) {
			    winner(2, 4, 6, buttons[2].getText());
		}
        checkDone();

	}
	
    public void winner(int a, int b, int c, String p)
    {
        buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);

        textfield.setText(p + " wins");
        System.out.println("Game won");
    }

    public void endGame()
    {
        textfield.setText("Do you wanna play again?");
        System.out.println("Reached endGame");

        button_panel.removeAll();
        frame.add(title_panel, BorderLayout.PAGE_START);

        button_panel.setLayout(new GridLayout(2,1));
		button_panel.setBackground(new Color(150,150,150));

        for(int i=0;i<2;i++) {
			replay[i] = new JButton();
			button_panel.add(replay[i]);
			replay[i].setFont(new Font("MV Boli",Font.BOLD,120));
			replay[i].setFocusable(false);
			replay[i].addActionListener(this);
		}

        replay[0].setForeground(new Color(255,0,0));
		replay[0].setText("Yes");

        replay[1].setForeground(new Color(255,0,0));
		replay[1].setText("No");
    }

    public void checkDone()
    {
        if(!(textfield.getText().equals("X wins")) && !(textfield.getText().equals("O wins")) 
                    && count==9) {
            for(int i=0;i<9;i++) {
                buttons[i].setEnabled(false);
            }
            textfield.setText("Game drawn");
            new Thread(() -> {
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SwingUtilities.invokeLater(() -> endGame());
            }).start();
        } 

        if(textfield.getText().equals("X wins") || textfield.getText().equals("O wins")) {
            for(int i=0;i<9;i++) {
                buttons[i].setEnabled(false);
            }
            new Thread(() -> {
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SwingUtilities.invokeLater(() -> textfield.setText("Game Done!!"));
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SwingUtilities.invokeLater(() -> endGame());
            }).start();
        }
    } 
}

//***************************************

public class Game {
	public static void main(String[] args) {

		TicTacToe tictactoe = new TicTacToe();

	}
}