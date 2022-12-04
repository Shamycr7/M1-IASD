import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class ESSAI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton;
	private JTextField textField_1;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton_1;
	Wolves game ;   
	private JButton btnNewButton_2;
	private int test;

	public int getTest() {
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ESSAI frame = new ESSAI(args);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ESSAI(String[] args) {
		
		
		int width = 50;
		int height = 50;
		int squaresize = 15;
		int delay = 100;
		int numWolves = 0;
		int numPreys = 0 ;
		int start=0;
		int tro;
		
		//game = new Wolves(height,width,numWolves,numPreys,10,5,1,2,start);
		game = new Wolves();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		
		
		String s1[] = { "Loup très Intelligent", "Loup Affamé", "Loup Aléatoire", "Loup Dormeur", "Loup Zoneur"};
		    
		JLabel l1 = new JLabel("Quelle stratégie souhaitez-vous ?"); 
		JComboBox combobox = new JComboBox(s1);
		contentPane.add(l1);
		contentPane.add(combobox); 
		ActionListener cbActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = (String) combobox.getSelectedItem();

                switch (s) {//check for a match
                    case "Loup très Intelligent":
                        setTest(0);
                        break;
                    case "Loup Affamé":
                    	setTest(1);
                        break;
                    case "Loup Aléatoire":
                    	setTest(2);
                        break;
                    case "Loup Dormeur":
                    	setTest(3);
                        break;
                    case "Loup Zoneur":
                    	setTest(4);
                        break;
                }
            }
        };

        combobox.addActionListener(cbActionListener);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Insérer le nombre de loups :");
		contentPane.add(lblNewLabel);
		
		textField = new JTextField(10);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnNewButton_2 = new JButton("Submit");
		//contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		lblNewLabel_1 = new JLabel("Insérer le nombre de moutons :");
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton = new JButton("C'est parti !");
		btnNewButton.setAlignmentY(122.0f);
		contentPane.setLocation(4,5);
		btnNewButton.setBounds(0, 500, 100, 100);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(textField_1.getText()) >= 1000 || Integer.parseInt(textField.getText()) >= 1000) {
					System.err.println("Num Proies < 1000 et Num Predateurs < 1000");
					textField_1.setText("");
					textField.setText("");
					game.setNumPreys(10);
					game.setNumWolves(10);
				}
				else  {
					game.setNumPreys(Integer.parseInt(textField_1.getText()));
					game.setNumWolves(Integer.parseInt(textField.getText()));
				}
				game = new Wolves(height,width,game.getNumWolves(),game.getNumPreys(),10,1,2,test);
				
				//System.out.printf("ESSAI : NumPreys = %d%n",game.getNumPreys());
				//System.out.printf("ESSAI : NumWolves = %d%n",game.getNumWolves());
				//System.out.println();
				//System.out.printf("ESSAI : Taille jeu = %d%n",height*width);
				
				WolvesApp wol = new WolvesApp("Hungry Hungry Wolves v2", height, width, squaresize,game);
				//wol.runGoL(delay); //don't uncomment	
			
			}
		});
		
	
	}

}
