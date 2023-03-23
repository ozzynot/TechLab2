import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Lab2 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JButton open = new JButton("Next Program");
	JTextArea result = new JTextArea(20,40);
	JLabel errors = new JLabel();
	JScrollPane scroller = new JScrollPane();
	
	public Lab2() {
		setLayout(new java.awt.FlowLayout());
		setSize(500,430);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		add(open); open.addActionListener(this);
		scroller.getViewport().add(result);
		add(scroller);
		add(errors);
	}
	
	public void actionPerformed(ActionEvent evt) {
		result.setText("");	//clear TextArea for next program
		errors.setText("");
		processProgram();
	}
	
	public static void main(String[] args) {
		Lab2 display = new Lab2();
		display.setVisible(true);
	}
	
	String getFileName() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile().getPath();
		else
			return null;
	}
	
/************************************************************************/
/* Put your implementation of the processProgram method here.           */
/* Use the getFileName method to allow the user to select a program.    */
/* Then simulate the execution of that program.                         */
/* You may add any other methods that you think are appropriate.        */
/* However, you should not change anything in the code that I have      */
/* written.                                                             */
/************************************************************************/
	
	//Initialization
		ArrayList<String> line = new ArrayList<String>(); 
		HashMap<String,Double> vars = new HashMap<>();
        boolean end = true;
        String text;
        String eachLine;
		int lineNum = 0;
	
	void processProgram() {
	//Separate lines and put in arrayList
		try(BufferedReader reader = new BufferedReader(new FileReader((getFileName())))) {
	         while ((text = reader.readLine()) != null) {
	            line.add(text);
	         }
		} 
		catch(IOException e) {
	         e.printStackTrace();
	         errors.setText("Incorrect Formatting");
	    }
		System.out.println(line);
        
	//Execute the program until END        
        while(end) {
            eachLine = line.get(lineNum);
            lineNum++;
            //line.get(lineNum++);
            if(eachLine.equals("END")) {
            	end = false;
            	System.out.println("Program ended");
            	continue;
            }
            doMethod(eachLine);
        }
	}
	
	//Divide up each line from eachLine
			ArrayList<String> eachWord = new ArrayList<String>();
	
	void doMethod(String statement) {
	//Separates a line statement into tokens
		StringTokenizer st = new StringTokenizer(statement);
		
	//Move on to next line
		while (st.hasMoreTokens()) {
			String nextToken = st.nextToken();
			eachWord.add(nextToken);
		}
		
		String firstWord = eachWord.get(0);
		
	//Sending the method to call a specific method depending on the first word in the simple code
 		if (firstWord.equals("PRINT")) {
			printVar();
			System.out.println("Doing PRINT");
			//eachLine = 
		} else if (firstWord.equals("GOTO")) {
			GOTOn();
			System.out.println("Doing GOTO");
		} else if (firstWord.equals("IF")) {
			conditional();
			System.out.println("Doing IF");
		} else {
			varExpression();
			System.out.println("Doing Var");
		} 
	}
	
	void varExpression() {
		
	}
	
	void printVar() {
	/*	vars.put(eachWord.get(2), 3.0);
		double varVal = vars.get(eachWord.get(1));
		result.setText("" + varVal); */
			}
	
	void GOTOn() {
		int GotoVar = Integer.parseInt(eachWord.get(1));
		lineNum = GotoVar;
	}
	
	void conditional() {
		
	}
	
}