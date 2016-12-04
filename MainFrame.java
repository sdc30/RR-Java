// Cartwright, Stephen D
// 10/6/16 952pm
// Script running program with Swing - GUI

import java.awt.*;
import java.awt.event.*;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.TextArea;
import java.awt.Label;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Choice;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;



public class MainFrame extends Frame{
	
	int width = 800, height = 450;
	int panelCount = 6;
	int coor = 7;
	
	String languages[] = {"Java", "C++", "C", "Python", "Ruby", "Node.js", "Scala", "C#"};
	String runCommands[] = {"java", "./", "./", "python", "ruby", "node", "scala", "./"};
	String panelNames[] = {"File Selection", "Langauge Selection", "Script Input", "Build Options | Arguments", "Run Options", "Output Log"};
	String output = new String("Logging Output...\n");
	
	int gridBagConstraintsCoordinates[][] = {
	{0, 0},
	{1, 0},
	{0, 2},
	{0, 3},
	{1, 3},
	{0, 4},
	{0, 5},
	{0, 1},
	{0, 2},
	{0, 3},
	{1, 2},
	{1, 3}
	};
	
	public MainFrame(int xPos, int yPos) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		try {
		
		String rr = new String("Runner Runner");
		
			
		GraphicsDevice gd =	ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Frame f = new Frame(rr, gc);
		Rectangle bounds = gc.getBounds();
	
				
		Panel p = new Panel();
			p.setBackground(Color.GRAY);
			p.setSize(width, height);
	
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
			GridBagConstraints gbc_sub1 = new GridBagConstraints();
			
			
			p.setLayout(gridbag);
			
		Panel panelArray[] = new Panel[panelCount];
			
			TextArea textArea = new TextArea("Code Field", 5, 50, 0);
			TextArea outputLog = new TextArea("Output Log...");
			TextField opargField = new TextField("Options/Args", 25);
			TextField programName = new TextField("Program Name", 10);
			TextField fileField = new TextField("Filename", 50);
			Choice language = new Choice();
			Button runButton = new Button("Run");
			Button haltButton = new Button("Halt");
			CheckboxGroup cbgroup = new CheckboxGroup();
			Checkbox compileBox = new Checkbox("Compile Only", false, cbgroup);
			Checkbox runBox = new Checkbox("Run Only", false, cbgroup);
			Checkbox compileRunBox = new Checkbox("Compile and Run", true, cbgroup);
			FileDialog fileDialog = new FileDialog(f, "Select File...");
			Button openFileDialogButton = new Button("New/Open File");

			
			
			for(int i = 0; i < panelCount; i++) {
				panelArray[i] = new Panel();
				panelArray[i].setBackground(Color.WHITE);
				Label l = new Label(panelNames[i]);
				
				if(i != 1 && i != 4) {
					gbc.anchor = GridBagConstraints.LINE_START;
					panelArray[i].setLayout(new BorderLayout());
					panelArray[i].add(l, BorderLayout.NORTH);
				}
				else {
					gbc_sub1.anchor = GridBagConstraints.FIRST_LINE_START;
					panelArray[i].setLayout(new GridBagLayout());
					panelArray[i].add(l, gbc_sub1);
					
					gbc_sub1.anchor = GridBagConstraints.LINE_START;
					gbc.anchor = GridBagConstraints.LINE_END;

				}
				

				gbc.gridx = gridBagConstraintsCoordinates[i][0];
				gbc.gridy = gridBagConstraintsCoordinates[i][1];
				
				
					p.add(panelArray[i], gbc);
				
			}
			


				outputLog.setEditable(false);

				fileField.setEditable(true);

			for(String s: languages)
				language.add(s);

			runButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					Handlers h;
					FileOperations fo;
					

					
					try {

						if(fileDialog.getFile() != null && !fileDialog.getFile().equals("Filename")){
							
							fo = new FileOperations();
							String file = fileField.getText();
							String madeFile = fo.makeFile(file, textArea.getText());
							
							output += "\nRunning... " + fileDialog.getFile();
							
							output += file + "\n";
							output += madeFile + "\n";
							
							if(madeFile.equals("Error")) {
								output += "File Exists Already\n";
							}
							
						}
						

						
						String arg = runCommands[language.getSelectedIndex()];
						String s;
						
						if(arg.equals("./"))
						   s = "";
						else
						   s = " ";
						
						String compileString = opargField.getText();
						//String runString = arg + s + programName.getText();
						
						
						System.out.println("Compile String: " + compileString);
						//System.out.println("Run String: " + runString);
						
						List<String> args = new ArrayList<String>();
						Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");;
						Matcher regexMatcher = regex.matcher(compileString);
						
						while (regexMatcher.find()) {
							if (regexMatcher.group(1) != null) {
								args.add(regexMatcher.group(1));
							}
							else if (regexMatcher.group(2) != null) {
								args.add(regexMatcher.group(2));
							}
							else {
								args.add(regexMatcher.group());
							}

						}


						
//						for(String qs: args) {
//							System.out.println(qs);
//						}

	/*					if(compileBox.getState()) {
							h = new Handlers(compileString);
							output += h.output;
						} else if(runBox.getState()) {
							h = new Handlers(runString);
							output += h.output;
						} else if(compileRunBox.getState()) {
							
							h = new Handlers(compileString);
							output += h.output;
							
							h = new Handlers(runString);
							output += h.output;
		
							
						} else {}
	*/

						h = new Handlers(args);
						
						
						if(h.output != null)
							output += h.output + "\n";
					} catch (Exception e) {
						
					}
					
					outputLog.setText(output);
				}
			});

			haltButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					output += "Halted.\n";
					outputLog.setText(output);
				}
			});
			

			compileBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ie) {
					output += "\nCompile Option Checked.\n";
					outputLog.setText(output);
				}
			});
			runBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ie) {
					output += "\nRun Option Checked.\n";
					outputLog.setText(output);
				}
			});
			compileRunBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ie) {
					output += "\nCompile and Run Option Checked.\n";
					outputLog.setText(output);
				}
			});
			

			openFileDialogButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					fileDialog.setVisible(true);
					
					String file = fileDialog.getDirectory() + fileDialog.getFile();
					
					fileField.setText(file);
					FileOperations fo = new FileOperations();
					
					String[] res = new String[2];
					
					res = fo.readFile(file);
					
					if(res[0] != null)
						textArea.setText(res[0]);
					if(res[1] != null)
						output += res[1];
				}

			});

			
			
			//////////////////
			


			panelArray[0].add(openFileDialogButton, BorderLayout.WEST);
			panelArray[0].add(fileField, BorderLayout.CENTER);
			
			gbc_sub1.gridx = 0;
			gbc_sub1.gridy = 1;
			panelArray[1].add(language, gbc_sub1);
			
			panelArray[2].add(opargField, BorderLayout.NORTH);
			panelArray[2].add(programName, BorderLayout.CENTER);
			
			panelArray[3].add(textArea, BorderLayout.WEST);
			
			gbc_sub1.gridx = gridBagConstraintsCoordinates[coor][0];
			gbc_sub1.gridy = gridBagConstraintsCoordinates[coor++][1];
			
			
			panelArray[4].add(compileBox, gbc_sub1);
			
			gbc_sub1.gridx = gridBagConstraintsCoordinates[coor][0];
			gbc_sub1.gridy = gridBagConstraintsCoordinates[coor++][1];
			
			panelArray[4].add(runBox, gbc_sub1);
			
			gbc_sub1.gridx = gridBagConstraintsCoordinates[coor][0];
			gbc_sub1.gridy = gridBagConstraintsCoordinates[coor++][1];
			
			panelArray[4].add(compileRunBox, gbc_sub1);
			
			gbc_sub1.gridx = gridBagConstraintsCoordinates[coor][0];
			gbc_sub1.gridy = gridBagConstraintsCoordinates[coor++][1];
			gbc_sub1.anchor = GridBagConstraints.LINE_END;
			
			panelArray[4].add(runButton, gbc_sub1);
			
			gbc_sub1.gridx = gridBagConstraintsCoordinates[coor][0];
			gbc_sub1.gridy = gridBagConstraintsCoordinates[coor++][1];
			
			panelArray[4].add(haltButton, gbc_sub1);
			
			panelArray[5].add(outputLog, BorderLayout.WEST);
			

			
			//////////////////
			
			f.setBackground(Color.BLACK);
			f.add(p);
			f.setLocation(xPos + bounds.x, yPos + bounds.y);
			f.setSize(width, height);
			f.setVisible(true);
			f.setResizable(true);
			
			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					System.exit(0);
				}
			});
			
			
		} catch (HeadlessException e) {
			
			
		}
	}
	
	
	
}




/*
 

 
 */
