package edu.jabs.patientsCentral.gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class ImagePanel extends JPanel
{

    private JLabel image;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    /**
     * Panel constructor
     */
    public ImagePanel( )
    {
        FlowLayout layout = new FlowLayout( );
        layout.setHgap( 0 );
        layout.setVgap( 0 );
        setLayout( layout );
        //
        // the image is loades
        ImageIcon icon = new ImageIcon( "data/title.jpg" );

        // The image is added to the label
        image = new JLabel( "" );
        image.setIcon( icon );
        add( image );
        //
        // The background color is set to white
        setBackground( Color.WHITE );
        setBorder( new LineBorder( Color.GRAY ) );
    }

}
