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
		} catch(IOException e) {
	         e.printStackTrace();
	         errors.setText("Incorrect Formatting");
	    }
	
	//Execute the program until END        
        	eachLine = line.get(lineNum);
        	System.out.println(line);
    //Iterates through the lines to find variable
        	int i = 0;
        	outerloop: try {
    		for (i = 0; i < line.size(); i++) {
    			
    //What to do if Var
    			try {
    			if(line.get(i).contains("=")) {
    				int equalSign = line.get(i).indexOf('=');
    				String vName = line.get(i).substring(0, equalSign-1);
    				String vExpression = line.get(i).substring(equalSign+2);
    				
    				System.out.println(varExpression(vExpression));
    				vars.put(vName, varExpression(vExpression));
    				}
    			}catch(Exception e) {
    				errors.setText("Invalid variable format at line " + i);
    			}
    //What to do if PRINT
    			try {
    			if(line.get(i).contains("PRINT") && !line.get(i).contains("IF")) {
    					//printVar();
    					StringTokenizer tokenizer = new StringTokenizer(line.get(i));
    					tokenizer.nextToken();
    					String token = tokenizer.nextToken();

    					System.out.println("printed value " + vars.get(token));
    					System.out.println("Doing PRINT");
    					}
    			} catch(Exception e) {
    				errors.setText("Invalid PRINT format at line " + i);
    			}
    					
    //What to do if GOTO
    			try {
    			if(line.get(i).contains("GOTO") && !line.get(i).contains("IF")) {
    					StringTokenizer tokenizer = new StringTokenizer(line.get(i));
    					tokenizer.nextToken();
    					String token = tokenizer.nextToken();    					
    					i = Integer.parseInt(token)-1;
    					System.out.println("Doing GOTO");
    					}
    			} catch(Exception e) {
    				errors.setText("Invalid GOTO format at line " + i);
    			}
    					
    //What to do if IF
    			try {
    					if(line.get(i).contains("IF") && line.get(i).contains("IS") && line.get(i).contains("THEN")) {
    					int then = line.get(i).indexOf("THEN")+4;
    					StringTokenizer tokenizer1 = new StringTokenizer(line.get(i));
    			//Gets the variable and pairs it to variableToken
    					tokenizer1.nextToken();
    					String variableToken = tokenizer1.nextToken();
    					System.out.println(variableToken);
    			//Gets the value and pairs it to valueToken
    					tokenizer1.nextToken();
    					String valueToken = tokenizer1.nextToken();
    					System.out.println(valueToken);
    					
    					String afterThen = line.get(i).substring(then);
    					
    					if(vars.get(variableToken) == Double.parseDouble(valueToken)) {
    						if(afterThen.contains("GOTO")) {
    							StringTokenizer tokenizer = new StringTokenizer(afterThen);
    							tokenizer.nextToken();
    							String token = tokenizer.nextToken();
    							i = Integer.parseInt(token)-1;
    							System.out.println(token);
    							
    						}
    						if (afterThen.contains("PRINT") && line.get(line.size() - 1).equals("END")) {
    							StringTokenizer tokenizer = new StringTokenizer(afterThen);
    							tokenizer.nextToken();
    							String token = tokenizer.nextToken();
    							//if its null then error
    							if (vars.get(token) == null) {
    								errors.setText("In line " + (i + 1) + " \"" + token + "\" is undefined");
    								break outerloop;
    							} else {
    								result.append("" + vars.get(token) + '\n');
    							}
    						} 				//GOTO prints are being weird
    						}
    						if(afterThen.contains("=")) {
    							int equalSign = afterThen.indexOf('=');
    							String vName = afterThen.substring(1, equalSign - 1);
    							String vExpression = afterThen.substring(equalSign + 2);
    			//Adds the variable name and expression to hashmap
    							vars.put(vName, varExpression(vExpression));
    						}
    						}
    				} catch(Exception e) {
    					errors.setText("Invalid conditional statement format at line " + i);
    				}
    //What to do if END
    			if (line.get(line.size() - 1).equals("END")) {

					if (line.get(i).contains("PRINT") && !line.get(i).contains("IF")) {
						StringTokenizer tokenizer = new StringTokenizer(line.get(i));
						tokenizer.nextToken();
						String token = tokenizer.nextToken();

						//if its null then error
						if (vars.get(token) == null) {
							errors.setText("In line " + (i + 1) + " \"" + token + "\" is undefined");
							break outerloop;
						} else {
							result.append("" + vars.get(token) + '\n');
						}
					}
				} else {
					errors.setText("END is not last statement");
				}
    			}
        	} catch (Exception e) {
    			errors.setText("In line " + (i + 1) + " illegal statement");
        	}
    		}  

	
	//Takes in String and solves for a double
	double varExpression(String str) {
		StringTokenizer tokenizer = new StringTokenizer(str);
	//Answer starts as the first number of the expression
		double answer = Double.parseDouble(tokenizer.nextToken());
	//Loop and do the math to find the solution to the expression, setting it to variable answer
		while (tokenizer.hasMoreTokens()) {
			if (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (tokenizer.hasMoreTokens()) {
					String nextToken = tokenizer.nextToken();
					//does the math depending on the arithmetic operators
					switch (token) {
					case "*":
						answer = answer * Double.parseDouble(nextToken);
						break;
					case "/":
						answer = answer / Double.parseDouble(nextToken);
						break;
					case "+":
						answer = answer + Double.parseDouble(nextToken);
						break;
					case "-":
						answer = answer - Double.parseDouble(nextToken);
						break;
					}

				}
			}
		}
		return answer;
	}
	
} 