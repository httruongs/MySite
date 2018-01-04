import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;

public class BinaryCalculatorFrame extends JFrame {
    // var declaration                    
    private JButton btnClear;
    private JButton btnCompute;
    private JButton btnOne;
    private JButton btnZero;
    private ButtonGroup buttonGroup;
    private JComboBox<String> cboOperators;
    private JRadioButton rdoBinary;
    private JRadioButton rdoDecimal;
    private JTextField txtDisplay;
    
    final String [] CHOICES = {"OP", "+", "-", "*", "/", "%"};
    private String operator;
    private int operand1, operand2;
    
    // constructor
    public BinaryCalculatorFrame() {
        // window title
        super("Binary Calculator");

        //Set the Frame's Layout
        setLayout(new FlowLayout());
        
        //Create components
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // handlers
        ComputeHandler btnHandler = new ComputeHandler();
        ComboListener cboHandler = new ComboListener();
        
        // text input field & alignment
        txtDisplay = new JTextField("0", 20);
        txtDisplay.setHorizontalAlignment(JTextField.RIGHT);
        txtDisplay.setEditable(false);

        // radio buttons group
        buttonGroup = new ButtonGroup();
        rdoBinary = new JRadioButton("Binary", true);
        buttonGroup.add(rdoBinary);
        
        rdoDecimal = new JRadioButton("Decimal", false);
        buttonGroup.add(rdoDecimal);

        btnZero = new JButton("0");
        btnZero.addActionListener(btnHandler);
        
        btnOne = new JButton("1");
        btnOne.addActionListener(btnHandler);
        
        cboOperators = new JComboBox<String> (CHOICES);
        cboOperators.setEditable(false);
        cboOperators.setMaximumRowCount(CHOICES.length);
        cboOperators.addItemListener(cboHandler);        

        btnCompute = new JButton("Compute");
        btnCompute.addActionListener(btnHandler);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(btnHandler);
        
        
        // add components to layout
        add(txtDisplay);
        add(rdoBinary);
        add(rdoDecimal);
        add(btnZero);
        add(btnOne);
        add(cboOperators);
        add(btnCompute);
        add(btnClear);

    }
    
    
    //Nested class
    private class ComputeHandler implements ActionListener {
	public void actionPerformed (ActionEvent evt) {
            int val = 0;

            if(evt.getSource() == btnCompute) {
                // set operand2 value
                operand2 = Integer.parseInt(txtDisplay.getText(), 2);
                        
                try {
                    // get values of operands
                    switch (operator){
                        case "+":
                            val = operand1 + operand2;
                            break;
                        case "-":
                            val = operand1 - operand2;
                            break;
                        case "*":
                            val = operand1 * operand2;
                            break;
                        case "/":
                            val = operand1 / operand2;
                            break;
                        case "%":
                            val = operand1 % operand2;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,
                                "Input First Binary Number - Select an Operator - " +
                                "Input Second Binary Number - Click Compute",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                    
                } catch (NumberFormatException e) {
                    //txtDisplay.setText("0");
                    e.getMessage();
                }
                
                // set computed result to output string
                if (rdoBinary.isSelected()) {
                    txtDisplay.setText(Integer.toBinaryString(val));
                } else {
                    txtDisplay.setText(Integer.toString(val));
                }

                // reset operand1 for further computing
                operand1 = val;
            } else if (evt.getSource() == btnClear) {
                cboOperators.setSelectedIndex(0);
                txtDisplay.setText(Integer.toString(0));
                operand1 = operand2 = 0;
            } else if (evt.getSource() == btnZero) {
                txtDisplay.setText(txtDisplay.getText() + Integer.toString(0));
            } else if (evt.getSource() == btnOne) {
                txtDisplay.setText(txtDisplay.getText() + Integer.toString(1));
            }
	}
    }
    
    // nested class
    private class ComboListener implements ItemListener {
        public void itemStateChanged(ItemEvent evt) {
            //the event caused by changing the choice
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                // getSelectedIndex returns an int corresponding to the item selected
                // set the op value to the correlating String in the array of choice
                operator = CHOICES[cboOperators.getSelectedIndex()];
                
                // set operand1
                operand1 = Integer.parseInt(txtDisplay.getText(), 2);
                
                // reset text input field
                if (!operator.equals("OP")) {
                    txtDisplay.setText("0");
                }
            }
        }
    }
}
