package edu.jabs.patientsCentral.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.jabs.patientsCentral.domain.NotExistsException;
import edu.jabs.patientsCentral.domain.AlreadyExistsException;

/**
 * Dialogue to add a patient
 */
public class AddPatientDialogue extends JDialog implements ActionListener
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------
    private final static String ADD = "Add";

    private final static String CANCEL = "Cancel";

    // -----------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------

    /**
     * It is a reference to the main window
     */
    private PatientsCentralGUI principal;

    /**
     * The type of addition that is going to be made (before or after a given patient)
     */
    private int additionForm;

    private int code;

    // -----------------------------------------------------------------
    // GUI Fields
    // -----------------------------------------------------------------


    private PatientInformationPanel informationPanel;

    /**
     * Button to add a Patient
     */
    private JButton buttonAdd;


    private JButton buttonCancel;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    public AddPatientDialogue( PatientsCentralGUI window, int tAddition, int theCode )
    {
        super( window, true );
        principal = window;
        code = theCode;
        additionForm = tAddition;
        setLayout( new GridBagLayout( ) );
        setPreferredSize( new Dimension( 298, 307 ) );

        setTitle( "Central of Patients" );
        setResizable( false );

        // Panel to enter information
        informationPanel = new PatientInformationPanel( );
        GridBagConstraints gbc = new GridBagConstraints( );
        informationPanel.changeInformationComboHospital( principal.getHospitalsCentral( ) );
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        add( informationPanel, gbc );

        // Panel with buttons add - delete
        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new GridBagLayout( ) );
        buttonAdd = new JButton( );
        buttonAdd.setActionCommand( ADD );
        buttonAdd.addActionListener( this );
        buttonAdd.setIcon( new ImageIcon( "data/add.gif" ) );
        buttonAdd.setToolTipText( "Add Patient" );
        gbc = new GridBagConstraints( );
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets( 0, 0, 0, 10 );
        buttonsPanel.add( buttonAdd, gbc );

        buttonCancel = new JButton( );
        buttonCancel.setActionCommand( CANCEL );
        buttonCancel.addActionListener( this );
        buttonCancel.setIcon( new ImageIcon( "data/cancel.gif" ) );
        buttonCancel.setToolTipText( "Cancel" );
        gbc = new GridBagConstraints( );
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        buttonsPanel.add( buttonCancel, gbc );

        gbc = new GridBagConstraints( );
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        add( buttonsPanel, gbc );

        pack( );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------


    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );

        if( command.equals( ADD ) )
        {
            try
            {
                int theCode = informationPanel.getPatientCode( );
                String theName = informationPanel.getPatientName( );
                String theHospital = informationPanel.getPatientHospital( );
                String theInformation = informationPanel.getPatientMedicalInformation( );
                int theGender = informationPanel.getPatientGender( );

                if( theCode < 0 )
                {
                    JOptionPane.showMessageDialog( this, "The code must be a positive number", "Patient Addition", JOptionPane.ERROR_MESSAGE );
                }
                else if( theName == null || theName.equals( "" ) )
                {
                    JOptionPane.showMessageDialog( this, "You must enter the patient's name", "Patient Addition", JOptionPane.ERROR_MESSAGE );
                }
                else if( theHospital == null || theHospital.equals( "" ) )
                {
                    JOptionPane.showMessageDialog( this, "You must enter the patient's hospital", "Patient Addition", JOptionPane.ERROR_MESSAGE );
                }
                else if( theInformation == null || theInformation.equals( "" ) )
                {
                    JOptionPane.showMessageDialog( this, "You must enter the patient's medical information", "Patient Addition", JOptionPane.ERROR_MESSAGE );
                }
                else
                {
                    principal.addPatient( additionForm, code, theCode, theName, theHospital, theInformation, theGender );
                    principal.updatePatientList( );
                    dispose( );
                }
            }
            catch( NumberFormatException ex )
            {
                JOptionPane.showMessageDialog( this, "The code must be a positive number", "Patient Addition", JOptionPane.ERROR_MESSAGE );
            }
            catch( AlreadyExistsException e1 )
            {
                JOptionPane.showMessageDialog( this, e1.getMessage( ), "Patient Addition", JOptionPane.ERROR_MESSAGE );
            }
            catch( NotExistsException e1 )
            {
                JOptionPane.showMessageDialog( this, "The patient with code " + code + " in relation to which the addition is going to be made is not registered", "Patient Addition", JOptionPane.ERROR_MESSAGE );
            }
        }
        else if( command.equals( CANCEL ) )
        {
            dispose( );
        }
    }
}