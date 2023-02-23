package edu.jabs.patientsCentral.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.jabs.patientsCentral.domain.PatientsCentral;
import edu.jabs.patientsCentral.domain.NotExistsException;
import edu.jabs.patientsCentral.domain.Patient;
import edu.jabs.patientsCentral.domain.AlreadyExistsException;


public class PatientsCentralGUI extends JFrame
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    public static final int BEFORE = 0;


    public static final int AFTER = 1;

    public static final int FIRST = 2;


    public static final int END = 3;

    // -----------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------

    private PatientsCentral central;

    // -----------------------------------------------------------------
    // GUI Fields
    // -----------------------------------------------------------------

    private PatientListPanel listPanel;

    private ImagePanel imagePanel;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------


    public PatientsCentralGUI( )
    {
        // The main class is initialized
        central = new PatientsCentral( );

        // The layout is set
        setLayout( new GridBagLayout( ) );
        setSize( 370, 347 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "Patients Central" );
        setResizable( false );

        // Panels
        imagePanel = new ImagePanel( );
        GridBagConstraints gbc = new GridBagConstraints( );
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        add( imagePanel, gbc );

        listPanel = new PatientListPanel( this );
        gbc = new GridBagConstraints( );
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add( listPanel, gbc );

        gbc = new GridBagConstraints( );
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;

        setLocationRelativeTo( null );

        pack( );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    public void showInsertPatientDialogue( int additionForm, int code )
    {
        AddPatientDialogue dialogue = new AddPatientDialogue( this, additionForm, code );
        dialogue.setLocationRelativeTo( this );
        dialogue.setVisible( true );
    }


    public void showAddPatientOptionsDialogue( )
    {
        if( central.getNumberPatients( ) > 0 )
        {
            InsertOptionsDialogue dialogue = new InsertOptionsDialogue( this );
            dialogue.setLocationRelativeTo( this );
            dialogue.setVisible( true );
        }
        else
        {
            showInsertPatientDialogue( FIRST, -1 );
        }
    }


    public void addPatient( int additionForm, int codPatient, int theCode, String theName, String theHospital, String theMedicalInformation, int theGender ) throws NotExistsException, AlreadyExistsException
    {
        Patient patient = new Patient( theCode, theName, theHospital, theMedicalInformation, theGender );

        // It verifies that the patient does not exist
        if( central.findPatient( theCode ) != null )
        {
            throw new AlreadyExistsException( theCode );
        }
        else
        {
            switch( additionForm )
            {
                case BEFORE:
                    central.addPatientBefore( codPatient, patient );
                    break;
                case AFTER:
                    central.addPatientAfter( codPatient, patient );
                    break;
                case FIRST:
                    central.addPatientAtTheBeginning( patient );
                    break;
                case END:
                    central.addPatientAtTheEnd( patient );
                    break;
            }
        }
    }

    public ArrayList getHospitalsCentral( )
    {
        return central.getHospitalsList( );
    }

    public void updatePatientList( )
    {
        listPanel.updateList( central.getPatients( ) );
    }

    public void searchPatient( )
    {
        String code = JOptionPane.showInputDialog( this, "Code:", "Patient Search", JOptionPane.QUESTION_MESSAGE );
        try
        {
            if( code != null )
            {
                int cod = Integer.parseInt( code );

                Patient patient = central.findPatient( cod );

                if( patient != null )
                {
                    showPatientInformation( patient );
                }
                else
                {
                    JOptionPane.showMessageDialog( this, "The patient with code " + cod + " is not registered", "Patient Search", JOptionPane.INFORMATION_MESSAGE );
                }
            }
        }
        catch( NumberFormatException e )
        {
            JOptionPane.showMessageDialog( this, "The code of the patient must be a numerical value", "Patient Search", JOptionPane.ERROR_MESSAGE );
        }
    }

    public void deletePatient( )
    {
        String code = JOptionPane.showInputDialog( this, "Code:", "Delete Patient", JOptionPane.QUESTION_MESSAGE );
        try
        {
            if( code != null )
            {
                int cod = Integer.parseInt( code );
                central.deletePatient( cod );
                updatePatientList( );
                JOptionPane.showMessageDialog( this, "The patient was deleted", "Delete Patient", JOptionPane.INFORMATION_MESSAGE );
            }
        }
        catch( NumberFormatException e )
        {
            JOptionPane.showMessageDialog( this, "The code must be a numerical value", "Delete Patient", JOptionPane.ERROR_MESSAGE );
        }
        catch( NotExistsException e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Delete Patient", JOptionPane.ERROR_MESSAGE );
        }
    }

    public void showPatientInformation( Patient patient )
    {
        ShowPatientInformationDialogue dialogo = new ShowPatientInformationDialogue( this, patient );
        dialogo.setLocationRelativeTo( this );
        dialogo.setVisible( true );
    }

    // -----------------------------------------------------------------
    // Extension Methods
    // -----------------------------------------------------------------


    public void reqFuncOption1( )
    {
        String result = central.method1( );
        JOptionPane.showMessageDialog( this, result, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }

    public void reqFuncOption2( )
    {
        String result = central.method2( );
        JOptionPane.showMessageDialog( this, result, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Extension Method 3
     */
    public void reqFuncOption3( )
    {
        String result = central.method3( );
        JOptionPane.showMessageDialog( this, result, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Extension Method 4
     */
    public void reqFuncOption4( )
    {
        String result = central.method4( );
        JOptionPane.showMessageDialog( this, result, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Extension Method 5
     */
    public void reqFuncOption5( )
    {
        String result = central.method5( );
        JOptionPane.showMessageDialog( this, result, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }

    // -----------------------------------------------------------------
    // Main Method
    // -----------------------------------------------------------------

    /**
     * This method executes the application, building a new gui
     * @param args The arguments for the application. None is required.
     */
    public static void main( String[] args )
    {
        PatientsCentralGUI gui = new PatientsCentralGUI( );
        gui.setVisible( true );
    }
}
