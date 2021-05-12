package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import vm252utilities.VM252Utilities;


public class GUI {
    public static void main(String[] commandLineArguments) {
       EventQueue.invokeLater(
               ()->
                    {
                    ProgramFrame frame = new ProgramFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setVisible(true);
                    }
       ); 
    }    
} 
//class that builds up the Gui one button at a time, and this is where the program
//will spend the majority of its time.
//contains many of the functions that are needed to allow for the program to function
class ProgramFrame extends JFrame
{
    private static final int OUR_DEFAULT_WIDTH = 400;
    private static final int OUR_DEFAULT_HEIGHT = 400;
    //creates the MyprogramPanel JPanel
    private JPanel myProgramPanel;
    //function that returns the JPanel known as myProgram Panel
    private JPanel programPanel()
    {

        return myProgramPanel;

        }
    //function that will change the program panel to the program panel that is used in this mfunction
    private void setProgramPanel(JPanel other)
    {

        myProgramPanel = other;

        }
    //creates the JLabel so that it can be changed later
    public JLabel text;
    //The actual creation of the visual gui, including the buttons and labels within this frame are
    //found in the area below. 
    public ProgramFrame()
    {
        //sets the title of the window
        setTitle("Debugger GUI");
        //sets the size of the gui frame to the variables OUR_DEFAULT_WIDTH and OUR_DEFAULT_HEIGHT
        //as the width and heigh respectively.
        setSize(OUR_DEFAULT_WIDTH, OUR_DEFAULT_HEIGHT);
        //
        // Create buttons
        //
        //first line buttons
            JButton Aa = new JButton("Aa");
            Aa.setBounds(5,5,90,60);
            JButton Ap = new JButton("Ap");
            Ap.setBounds(100,5,90,60);
            JButton Amb = new JButton("Amb");
            Amb.setBounds(195,5,90,60);
            JButton Quit = new JButton("Quit");
            Quit.setBounds(290,5,90,60);
            
        //second line buttons
        JButton Ba = new JButton("Ba");
            Ba.setBounds(5,70,90,60);
            JButton Z = new JButton("Z");
            Z.setBounds(100,70,90,60);
            JButton Mb = new JButton("Mb");
            Mb.setBounds(195,70,90,60);
            JButton N = new JButton("N");
            N.setBounds(290,70,90,60);
            
        //Third line buttons
            JButton ob = new JButton("ob");
            ob.setBounds(5,135,90,60);
            JButton OD = new JButton("OD");
            OD.setBounds(100,135,90,60);
            JButton S = new JButton("S");
            S.setBounds(195,135,90,60);
            JButton R = new JButton("R");
            R.setBounds(290,135,90,60);
            
        //Last Line Buttons
            JButton Help = new JButton("Help");
            Help.setBounds(100,200,185,60);
            
        // Label with the name of the file
           JLabel file_label = new JLabel("File: ");
           file_label.setBounds(100,275,90,30);
           JLabel file_name = new JLabel("No File currently selected");
           text = file_name;
           file_name.setBounds(125,275,200,30);
        //Add the change file button
            JButton changeFile = new JButton("Change the current file");
            changeFile.setBounds(100,300, 185, 30);
            
        //
        // Create panel to hold all components
        //
            setProgramPanel(new JPanel());
        
            //makes it so we can set absolute positions for buttons
            programPanel().setLayout(null);
            //adds the buttons to the panel
            programPanel().add(Aa);
            programPanel().add(Ap);
            programPanel().add(Amb);
            programPanel().add(Ba);
            programPanel().add(Z);
            programPanel().add(Mb);
            programPanel().add(N);
            programPanel().add(ob);
            programPanel().add(OD);
            programPanel().add(S);
            programPanel().add(R);
            programPanel().add(Help);
            programPanel().add(Quit);
            programPanel().add(file_label);
            programPanel().add(file_name);
            programPanel().add(changeFile);
            //makes the panel visible
            add(programPanel());
            
      //
      //Creates button action/implements the quit button
      quitAction close = new quitAction();
      Quit.addActionListener(close);
      
      FileChange path = new FileChange();
      //when the program is run the user will be prompted to select a program to debug before they can do anything else. 
      if (path.file_path() == null)
        {
            JFileChooser startup = new JFileChooser();
            startup.showSaveDialog(null);
            path.ChangeAction(startup.getSelectedFile().getAbsolutePath());
            file_name.setText(startup.getSelectedFile().getName());
            path(startup.getSelectedFile().getAbsolutePath());
        }
      changeFile.addActionListener(path);
      //adds the actionListener instruction to the button s
      sAction instruction = new sAction();
      S.addActionListener(instruction);
      //adds the actionListener z to the button z
      zAction z = new zAction();
      Z.addActionListener(z);
      //adds the actionListener next to the button N
      nAction next = new nAction();
      N.addActionListener(next);
      //adds the actionListener setaccum to the button Aa
      AaAction setaccum = new AaAction();
      Aa.addActionListener(setaccum);
      //adds the actionListener setprocounter to the button Ap
      ApAction setprogcounter = new ApAction();
      Ap.addActionListener(setprogcounter);
      //adds the actionListener testing to the button Mb
      MbAction testing = new MbAction();
      Mb.addActionListener(testing);
     //adds the actionListener run to the button R 
      runAction run = new runAction();
      R.addActionListener(run);
      //adds the actionListener help to the button Help
      helpAction help = new helpAction();
      Help.addActionListener(help);
      //adds the actionListener oB to the button ob
      oBAction oB = new oBAction();
      ob.addActionListener(oB);
      //adds the actionListener od to the button od
      odAction od = new odAction();
      OD.addActionListener(od);
      //adds the actionListener ambh to the button Amb
      amb_Action ambh = new amb_Action();
      Amb.addActionListener(ambh);
     //sets the program variable to the array that come out of the vm252utilities readObjecCodeFromObject file function
      program = VM252Utilities.readObjectCodeFromObjectFile(path.file_path());
      setUpProgram(program);
      outPutFrame();
    }
    //creates the boolean of lastInstructionCausedHalt so that it can be used in the running loops
    public boolean lastInstructionCausedHalt;                         
    //creates the suppressPcIncrement variable
    public boolean suppressPcIncrement;
    //creates a byte variable program to keep the program to be referenced later
    public byte[] program;
    
    public short accumulator = 0;
    public short programCounter = 0;
    public int opcode;
    public short operand;
    public static final int numberOfMemoryBytes = 8192;
    public JTextArea textArea = new JTextArea();
    
    //creates the quit action class and allows for the program to be closed
    //via the quit button
      private class quitAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }        
    }
      //creates the zAction class that when called will set the program counter
      //to zero 
      private class zAction implements ActionListener
      {
          
          @Override
          public void actionPerformed(ActionEvent Event)
            {
                programCounter = 0;
            }
      }
      //creates the fileChange class that will change the path of the program
      //which will be called later 
      private class FileChange implements ActionListener
    {
        private String file_path;
            
        private String file_path()
            {
                return file_path;
            }
        private void setfile_path(String other)
            {
                file_path = other;
            }
        public String ChangeAction(String other)
            {
                setfile_path(other);
                return file_path();
            }
        @Override
        //something hinky is happening here, something about the path to file isn't acutally being changed
        public void actionPerformed(ActionEvent event)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.showSaveDialog(null);
                path(chooser.getSelectedFile().getAbsolutePath());
                program = VM252Utilities.readObjectCodeFromObjectFile(path2file);
                setUpProgram(program);
                text.setText(chooser.getSelectedFile().getName());
                accumulator = 0;
                programCounter = 0;
                textArea.setText("");
            }
      }
      //takes the input from the user using a JOptionPane and returns the input as a byte.
      public byte window(String question)
        {
            byte input;
            input = Byte.valueOf(JOptionPane.showInputDialog(question));
            return input;
        }
      //takes the input of the user using a JOptionPane and returns the vakye as an int.
      public int intInput(String question)
        {
            int input;
            input = Integer.parseInt(JOptionPane.showInputDialog(question));
            return input;
        }
      public void path(String path)
        {
            path2file = path;
        }
      public String path2file;
      //creates the Saction class that when called will change the program that is being looked at. 
    private class sAction implements ActionListener
      {
        private byte[] program;
        
        public byte[] getProgram()
        {
            return program;
        }
        public void setProgram()
        {
            program = VM252Utilities.readObjectCodeFromObjectFile(path2file);
            setUpProgram(program);
            
        }
            //this overide will print out the appropraite line of the program that is being looked at
            //using the opcodes that are comming out of the program
            @Override
        public void actionPerformed(ActionEvent event)
            {
                f.setVisible(true);
                setProgram();
                textArea.append("\nACC = "+accumulator);
                textArea.append("\nPC = "+programCounter);
                textArea.append("\nNext Instruction - ");
                byte [] encodedInstruction
                                = fetchBytePair(memory, programCounter);

                            int [] decodedInstruction
                                = VM252Utilities.decodedInstruction(encodedInstruction);
                              opcode = decodedInstruction[ 0 ];
                            
                             operand
                               = decodedInstruction.length == 2
                                    ? ((short) (decodedInstruction[ 1 ]))
                                    : 0;
                            switch (opcode)
                            {
                                case VM252Utilities.INPUT_OPCODE-> {
                                    textArea.append("INPUT\n");
                                }
                                case VM252Utilities.LOAD_OPCODE-> {
                                    textArea.append("LOAD ");
                                    textArea.append(String.valueOf(operand)+"\n");
                                }
                                case VM252Utilities.SET_OPCODE-> {
                                    textArea.append("SET ");
                                    textArea.append(String.valueOf(operand)+"\n");
                                }
                                case VM252Utilities.STORE_OPCODE -> {
                                    //add the word next to it that it is beign stored to
                                    textArea.append("STORE ");
                                    textArea.append(String.valueOf(operand)+"\n");
                                }
                                case VM252Utilities.ADD_OPCODE-> {
                                    textArea.append("ADD");
                                }
                                case VM252Utilities.SUBTRACT_OPCODE-> {
                                    textArea.append("SUB");
                                }
                                case VM252Utilities.JUMP_OPCODE-> {
                                    textArea.append("JUMP ");
                                    textArea.append(String.valueOf(operand)+"\n");
                                }
                                case VM252Utilities.JUMP_ON_ZERO_OPCODE-> {
                                    textArea.append("JUMP ON ZERO ");
                                    textArea.append(String.valueOf(operand)+"\n");
                                }
                                case VM252Utilities.JUMP_ON_POSITIVE_OPCODE-> {
                                    textArea.append("JUMP ON POS");
                                    textArea.append(String.valueOf(operand)+"\n");
                                }
                                case VM252Utilities.OUTPUT_OPCODE-> {
                                    textArea.append("OUTPUT\n");
                                }
                                case VM252Utilities.NO_OP_OPCODE-> {
                                    textArea.append("NOOP\n");
                                }
                                case VM252Utilities.STOP_OPCODE-> {
                                    textArea.append("STOP\n");
                                }
                            }
            }
        }
    private static short nextMemoryAddress(short address)
        {

            return ((short) ((address + 1) % numberOfMemoryBytes));

            }
    private static byte [] fetchBytePair(byte [] memory, short address)
        {

            byte [] bytePair = { memory[ address ], memory[ nextMemoryAddress(address) ] };

            return bytePair;

            }
     private static short bytesToInteger(
            byte mostSignificantByte,
            byte leastSignificantByte
            )
        {

            return
                ((short)
                    ((mostSignificantByte << 8 & 0xff00 | leastSignificantByte & 0xff)));

            }
     public byte[] memory;
     //creatses a copy of the program called memory
        public void setUpProgram(byte[] program)
        {
            memory = null;
            
            
            if (program != null)
            {
              memory = new byte[numberOfMemoryBytes];
            for (int loadAddress = 0; loadAddress < program.length; ++ loadAddress)
                            
                        memory[ loadAddress ] = program[ loadAddress ];

            for (int loadAddress = program.length;
                    loadAddress < numberOfMemoryBytes;
                    ++ loadAddress)
                    
                memory[ loadAddress ] = 0;
            
        }
        }
       //returns 2 depending on if the encoding for the opcode is requireing 2 spots or not.  
        private static int instructionSize(int opcode)
        {

            switch (opcode) {

                case VM252Utilities.LOAD_OPCODE,
                    VM252Utilities.SET_OPCODE,
                    VM252Utilities.STORE_OPCODE,
                    VM252Utilities.ADD_OPCODE,
                    VM252Utilities.SUBTRACT_OPCODE,
                    VM252Utilities.JUMP_OPCODE,
                    VM252Utilities.JUMP_ON_ZERO_OPCODE,
                    VM252Utilities.JUMP_ON_POSITIVE_OPCODE -> {

                    return 2;

                    }

                default -> {

                    return 1;

                    }

                }

        }
        //creates a class naction that when called will and will perform the next thing on them    
            private class nAction implements ActionListener
      {
            @Override
        public void actionPerformed(ActionEvent event)
            {
                  byte [] encodedInstruction
                                = fetchBytePair(memory, programCounter);

                            int [] decodedInstruction
                                = VM252Utilities.decodedInstruction(encodedInstruction);
                            int opcode = decodedInstruction[ 0 ];

                            short operand
                                = decodedInstruction.length == 2
                                    ? ((short) (decodedInstruction[ 1 ]))
                                    : 0;
                                switch (opcode) {
                                case VM252Utilities.LOAD_OPCODE -> {
                                    accumulator = fetchIntegerValue(memory, operand);
                                    }
                                case VM252Utilities.SET_OPCODE -> {
                                    accumulator = operand;
                                    }
                                case VM252Utilities.STORE_OPCODE -> {
                                    storeIntegerValue(memory, operand, accumulator);
                                    }
                                case VM252Utilities.ADD_OPCODE -> {
                                    accumulator += fetchIntegerValue(memory, operand);
                                    }
                                case VM252Utilities.SUBTRACT_OPCODE -> {
                                    accumulator -= fetchIntegerValue(memory, operand);
                                    }
                                case VM252Utilities.JUMP_OPCODE -> {
                                    programCounter = operand;
                                    suppressPcIncrement = true;
                                    }
                                case VM252Utilities.JUMP_ON_ZERO_OPCODE -> {
                                    if (accumulator == 0) {
                                        programCounter = operand;
                                        suppressPcIncrement = true;
                                        }
                                    }
                                case VM252Utilities.JUMP_ON_POSITIVE_OPCODE -> {

                                    if (accumulator > 0) {
                                        programCounter = operand;
                                        suppressPcIncrement = true;
                                        }
                                    }
                               case VM252Utilities.INPUT_OPCODE -> {
                                    int input_value =  intInput("Input an integer Value");
                                        lastInstructionCausedHalt = false;
                                        if (lastInstructionCausedHalt) {

                                            textArea.append(
                                                "EOF reading input;  machine halts"
                                                );
                                            System.out.flush();

                                            suppressPcIncrement = true;

                                            }
                                    //
                                    // Otherwise let accumulator = the next integer read
                                    //     from the standard input stream
                                    //
                                        else{

                                            accumulator = ((short) input_value);
                                            textArea.append("INPUT: "+accumulator+"\n");
                                        }
                               }

                                case VM252Utilities.OUTPUT_OPCODE -> {

                                    textArea.append("OUTPUT: " + accumulator+"\n");
                                    System.out.flush();

                                    }

                                case VM252Utilities.NO_OP_OPCODE -> {

                                    ; // do nothing

                                    }

                                case VM252Utilities.STOP_OPCODE -> {

                                    lastInstructionCausedHalt = true;
                                    suppressPcIncrement = true;
                }

            }
                                programCounter
                                    = ((short)
                                        ((programCounter + instructionSize(opcode))
                                            % numberOfMemoryBytes)
                                            );
            }
        //converts a short data valy to a byte value and returnst it
        private byte[] integerToBytes(short dataValue) {
            byte [] dataBytes
                = {((byte) (dataValue >> 8 & 0xff)),
                    ((byte) (dataValue & 0xff))
                    };

            return dataBytes;
        }
        //retrives and returns the integer value from the memory at the target address
        private short fetchIntegerValue(byte[] memory, short address) {
            byte [] integerBytes = fetchBytePair(memory, address);

            return bytesToInteger(integerBytes[ 0 ], integerBytes[ 1 ]);
        }
        //stores integervalue in the memory at the target address
        private void storeIntegerValue(byte[] memory, short address, short dataValue ) {
            
        
            byte [] dataBytes = integerToBytes(dataValue);

            memory[ address ] = dataBytes[ 0 ];
            memory[ nextMemoryAddress(address) ] = dataBytes[ 1 ];
      }
            }
            
      
        //creates the aa class that when it is called, will prompt the user for an input
            // to chnge the accumulator to
      private class AaAction implements ActionListener
      {
            @Override
        public void actionPerformed(ActionEvent event)
            {
                accumulator = ((short) intInput("What do you want to set the accumulator to"));
                f.setVisible(true);
                textArea.append("accumulator is now: "+accumulator+"\n");
            }
      }
      //creates the ap class that when it is called, will prompt the user for an input
            // to change the program coutner to
      private class ApAction implements ActionListener
      {
      @Override
        public void actionPerformed(ActionEvent event)
            {
                programCounter = ((short) intInput("What do you want to set the accumulator to"));
                System.out.println(programCounter);
            }
      }
      //creates the MbAction class that when it is called will display all of the memory bytes
      //in hex in sets of two to the display window
      private class MbAction implements ActionListener
      {
          @Override
          public void actionPerformed(ActionEvent event)
          {
              f.setVisible(true);
              int counter = 0;
              for(int j =0;j< memory.length;++j)
              {
                  if (counter%20==0)
                  {
                      if (counter == 0)
                        {
                            textArea.append("\n [ADDR       "+counter+"]");
                        }
                      else if (counter <100)
                      {
                      textArea.append("\n [ADDR     "+counter+"]");
                      }
                      else
                      {
                        textArea.append("\n [ADDR   "+counter+"]");
                      }
                  }
                  
                  var thing = (Integer.toHexString(memory[j]));
                  if(thing.length()>1)
                      {textArea.append(" "+thing.substring(thing.length()-2));
                  }
                  else
                  {
                      textArea.append(" 0"+thing);
                  }
               counter +=1;     
              }
              textArea.append("\n");
              
          }
      }
      private static short fetchIntegerValue(byte [] memory, short address)
        {

            byte [] integerBytes = fetchBytePair(memory, address);

            return bytesToInteger(integerBytes[ 0 ], integerBytes[ 1 ]);

            }
     private static void storeIntegerValue(
            byte [] memory,
            short address,
            short dataValue
            )
        {

            byte [] dataBytes = integerToBytes(dataValue);

            memory[ address ] = dataBytes[ 0 ];
            memory[ nextMemoryAddress(address) ] = dataBytes[ 1 ];

            }
     private static byte [] integerToBytes(short data)
        {

            byte [] dataBytes
                = {((byte) (data >> 8 & 0xff)),
                    ((byte) (data & 0xff))
                    };

            return dataBytes;

            }
      private class runAction implements ActionListener
      {
          
           @Override
           public void actionPerformed(ActionEvent event)
           {
               f.setVisible(true);
               if (program != null) {

                //
                // Let accumulator, programCounter, and memory be the simulated hardware
                //     components for the simulated machine
                //

                    byte [] memory = new byte[ numberOfMemoryBytes ];

                boolean suppressPcIncrement;
                boolean lastInstructionCausedHalt;

                //
                // Let memory[ 0 ... numberOfMemoryBytes-1 ] =
                //     the bytes of the program whose execution is to be simulated, followed,
                //     to the end of memory, 0-initialized bytes
                //

                    for (int loadAddress = 0; loadAddress < program.length; ++ loadAddress)
                            //
                            // Loop invariant:
                            //     memory[ 0 ... loadAddres-1 ] has been assignment
                            //         program[ 0 ... loadAddress-1 ]
                            //
                        memory[ loadAddress ] = program[ loadAddress ];

                    for (int loadAddress = program.length;
                            loadAddress < numberOfMemoryBytes;
                            ++ loadAddress)
                            //
                            // Loop invariant:
                            //     Each byte in
                            //         memory[ program.length ... numberOfMemoryBytes-1 ] has
                            //         been assigned 0
                            //
                        memory[ loadAddress ] = 0;

                //
                // Simulate the execution of the program whose binary encoding is found in
                //     memory[ 0 ... program.length-1 ]
                //

                    do {
                            //
                            // Loop invariant:
                            //     The simulation of the execution of all instructions whose
                            //         address was previously contained in programCounter
                            //         has been completed

                        //
                        // Let opcode = the operation code portion of the instruction stored
                        //     at address programCounter
                        // Let operand = the operand portion (if any) of the instruction
                        //     stored at address programCounter
                        //

                            byte [] encodedInstruction
                                = fetchBytePair(memory, programCounter);

                            int [] decodedInstruction
                                = VM252Utilities.decodedInstruction(encodedInstruction);
                            int opcode = decodedInstruction[ 0 ];

                            short operand
                                = decodedInstruction.length == 2
                                    ? ((short) (decodedInstruction[ 1 ]))
                                    : 0;

                        suppressPcIncrement = false;
                        lastInstructionCausedHalt = false;

                        //
                        // Simulate execution of a VM252 instruction represented by opcode
                        //     (and for instructions that have an operand, operand), altering
                        //     accumulator, programCounter, and memory, as required
                        // Let supressPcIncrement = true iff any kind of jump instruction was
                        //     executed, a STOP instruction was executed, or a failed INPUT
                        //     instruction was executed
                        //

                            switch (opcode) {

                                case VM252Utilities.LOAD_OPCODE -> {

                                    accumulator = fetchIntegerValue(memory, operand);

                                    }

                                case VM252Utilities.SET_OPCODE -> {

                                    accumulator = operand;

                                    }

                                case VM252Utilities.STORE_OPCODE -> {

                                    storeIntegerValue(memory, operand, accumulator);

                                    }

                                case VM252Utilities.ADD_OPCODE -> {

                                    accumulator += fetchIntegerValue(memory, operand);

                                    }

                                case VM252Utilities.SUBTRACT_OPCODE -> {

                                    accumulator -= fetchIntegerValue(memory, operand);

                                    }

                                case VM252Utilities.JUMP_OPCODE -> {

                                    programCounter = operand;
                                    suppressPcIncrement = true;

                                    }

                                case VM252Utilities.JUMP_ON_ZERO_OPCODE -> {

                                    if (accumulator == 0) {
                                        programCounter = operand;
                                        suppressPcIncrement = true;
                                        }

                                    }

                                case VM252Utilities.JUMP_ON_POSITIVE_OPCODE -> {

                                    if (accumulator > 0) {
                                        programCounter = operand;
                                        suppressPcIncrement = true;
                                        }

                                    }

                               case VM252Utilities.INPUT_OPCODE -> {

                                  
                                    //add the input method that we have
                                       int input_value =  intInput("Input an integer Value");

                                        lastInstructionCausedHalt = false;

                                        if (lastInstructionCausedHalt) {

                                            textArea.append(
                                                "EOF reading input;  machine halts"
                                                );
                                            System.out.flush();

                                            suppressPcIncrement = true;

                                            }

                                    //
                                    // Otherwise let accumulator = the next integer read
                                    //     from the standard input stream
                                    //

                                        else{

                                            accumulator = ((short) input_value);
                                            textArea.append("INPUT: "+accumulator+"\n");

                                    }
                               }

                                case VM252Utilities.OUTPUT_OPCODE -> {

                                    textArea.append("OUTPUT: " + accumulator+"\n");
                                    System.out.flush();

                                    }

                                case VM252Utilities.NO_OP_OPCODE -> {

                                    ; // do nothing
                                    }

                                case VM252Utilities.STOP_OPCODE -> {

                                    lastInstructionCausedHalt = true;
                                    suppressPcIncrement = true;

                                    }

                                }

                        //
                        // Increment the program counter to contain the address of the next
                        //     instruction to execute, unless the program counter was already
                        //     adjusted or the program is not continuing
                        //

                            if (! lastInstructionCausedHalt && ! suppressPcIncrement)

                                programCounter
                                    = ((short)
                                        ((programCounter + instructionSize(opcode))
                                            % numberOfMemoryBytes)
                                            );

                        } while (! lastInstructionCausedHalt);

                }
           }
      
      }
      //is the outputframe of the program
      public void outPutFrame()
      {
          textArea.setEditable(false);
          JScrollPane scrollPane = new JScrollPane(textArea);
          f.setSize(700,400);
          f.setLocation(400, 0);
          if ( ! f.isVisible())
          {
            f.setVisible(true);
            f.add(scrollPane);
          }   
      }
      //creates the helpaction class that when it is called, it will display all of the
      //pertinent information on what the various buttons do. 
      private class helpAction implements ActionListener
      {
          @Override
           public void actionPerformed(ActionEvent event)
          {
              f.setVisible(true);
              textArea.append("Aa: Displays a popup window that asks for user input and sets that input to the accumulator.\n" +
"\n" +
"Ap: Displays a popup window that asks for user input and alters the contents of the program counter to memory.\n" +
"\n" +
"Amb: Displays a popup window that asks for two input values, one for the position and the other for the hex byte value that will be stored in that position. \n" +
"\n" +
"Ba: Places a breakpoint at the point of memory.\n" +
"\n" +
"Z: Resets the program counter to zero. \n" +
"\n" +
"Mb: Opens up a popup window and displays machine memory as bytes in hex value. \n" +
"\n" +
"N: Executes the next line of the program and opens corresponding input/output windows as needed. \n" +
"\n" +
"Ob: Opens a display window showing the memory of the machine as byte hex values. \n" +
"\n" +
"Od: Opens a display window that shows the memory of the machine as two-byte hex values. \n" +
"\n" +
"S: Opens a display window that shows the pertinent information about the machine state. \n" +
"\n" +
"R: Runs the program until the end or error, performing the appropriate input/output with a different window as needed.\n" +
"\n" +
"Help: Opens a display window that describes the actions of each button and the functions they represent.\n" +
"\n" +
"Quit: Closes the window and terminates the program as it is clicked. \n" +
"\n" +
"Change the current file: Opens a file select window in which the user is able to choose a new file. \n" 
                      );
          }
      }
      //displays the memory of the program in sets of hexbye code in sets of 2
      private class oBAction implements ActionListener
      {
          @Override
           public void actionPerformed(ActionEvent event)
          {
              f.setVisible(true);
              int counter = 0;
              for(int j =0;j< program.length;++j)
              {
                  if (counter%20==0)
                  {
                      if (counter == 0)
                        {
                            textArea.append("\n [ADDR       "+counter+"]");
                        }
                      else if (counter <100)
                      {
                      textArea.append("\n [ADDR     "+counter+"]");
                      }
                      else
                      {
                        textArea.append("\n [ADDR   "+counter+"]");
                      }
                  }
                  
                  var thing = (Integer.toHexString(memory[j]));
                  if(thing.length()>1)
                      {textArea.append(" "+thing.substring(thing.length()-2));
                  }
                  else
                  {
                      textArea.append(" 0"+thing);
                  }
               counter +=1;     
              }
              textArea.append("\n");
              
          }
          
      }
      //displays the memory of the program in sets of hexbye code in sets of 4
      private class odAction implements ActionListener
      {
          @Override
          public void actionPerformed(ActionEvent event)
          {
              f.setVisible(true);
              int counter = 0;
              for(int j =0;j< program.length;++j)
              {
                  if (counter%20==0)
                  {
                      if (counter == 0)
                        {
                            textArea.append("\n [ADDR       "+counter+"]");
                        }
                      else if (counter <100)
                      {
                      textArea.append("\n [ADDR     "+counter+"]");
                      }
                      else
                      {
                        textArea.append("\n [ADDR   "+counter+"]");
                      }
                  }
                  
                  var thing = (Integer.toHexString(memory[j]));
                  if(thing.length()>1)
                  {
                      if ((counter+1)%2 ==0)
                      {textArea.append(thing.substring(thing.length()-2));        
                      }
                      else
                      {
                       textArea.append(" "+thing.substring(thing.length()-2));
                      }
                  }
                  else
                  {
                      if ((counter+1)%2 ==0)
                      {textArea.append("0"+thing);        
                      }
                      else
                      {
                       textArea.append(" 0"+thing);
                      }
                  }
               counter +=1;     
              }
              textArea.append("\n");
              
          
      }
      }
      //takes in a users input to change the spot in the machine memory to whatever the next input is. 
      private class amb_Action implements ActionListener
      {    
           @Override
           public void actionPerformed(ActionEvent event)
           {
               short spot = ((short) intInput("What spot would you like to change?"));
               try{
               memory[spot] = (window("What value would you like to fill that spot?"));
               }
               catch(Exception e){
               memory[spot] = (window("Please enter a new value, your previous input wasn't accepted."));
               }
               }
      }
      public JFrame f = new JFrame();
}





