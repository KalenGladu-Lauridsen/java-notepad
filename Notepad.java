/**
 * @Author  Kalen Gladu-Laurudsen
 * @Verion  1.0
 * @Date    2021-03-05
 */
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notepad {
    /**
     * Creating global variables
     */
    private static JMenuBar bar;
    private static JMenu file, format, size;
    private static JMenuItem new1, open, save, edit, exit;
    private static JRadioButtonMenuItem small, medium, large;
    private static JCheckBoxMenuItem bold, italic;
    private static JTextArea editorAreaText;
    private static JScrollPane scroll;
    private static int font;
    private static ButtonGroup bg;

    public static void main(String[] args) {
        /**
         * Creating and setting the frames
         */
        JFrame frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();

        frame.setTitle("Notepad");
        ImageIcon image = new ImageIcon("text-icon-29.jpg");
        frame.setIconImage(image.getImage());
        frame.setSize(700,500);

        /**
         * Setting variables
         */
        bar = new JMenuBar();
        file = new JMenu("File");
        format = new JMenu("Format");
        size = new JMenu("Size");
        
        small = new JRadioButtonMenuItem("Small", true);
        medium = new JRadioButtonMenuItem("Medium");
        large = new JRadioButtonMenuItem("Large");
        
        bold = new JCheckBoxMenuItem("Bold");
        italic = new JCheckBoxMenuItem("Italic");
        
        new1 = new JMenuItem("New");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        edit = new JMenuItem("Edit");
        exit = new JMenuItem("Exit");
        
        /**
         * Adding menu bar to the frame
         */
        frame.setJMenuBar(bar);

        /**
         * Setting the text area size and adding it to a scroll pane
         */
        editorAreaText = new JTextArea();
        editorAreaText.setLineWrap(true);
        editorAreaText.setWrapStyleWord(true);
        scroll = new JScrollPane(editorAreaText);

        /**
         * Adding scroll pane to frame
         */
        cp.gridx = 0;
        cp.gridy = 0;
        cp.weightx = 1;
        cp.weighty = 1;
        cp.fill = GridBagConstraints.BOTH;
        frame.add(scroll, cp);

        /**
         * Adding items to menus
         */
        file.add(new1);
        file.add(open);
        file.add(save);
        file.add(edit);
        file.add(exit);

        bg = new ButtonGroup();
        bg.add(small);
        bg.add(medium);
        bg.add(large);

        size.add(small);
        size.add(medium);
        size.add(large);

        format.add(bold);
        format.add(italic);
        format.add(size);
       
        bar.add(file);
        bar.add(format);

        /**
         * Creating short-cuts for the menu and sub-menu options
         */
        new1.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        edit.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        bold.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        italic.setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        small.setAccelerator(KeyStroke.getKeyStroke('1', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        medium.setAccelerator(KeyStroke.getKeyStroke('2', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        large.setAccelerator(KeyStroke.getKeyStroke('3', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        /**
         * Closing the window
         */
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing...");
                frame.setVisible(false);
                frame.dispose(); 
            }
        });

        /**
         * Erases all content in the textArea
         */
        new1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clearing editorAreaText...");
                editorAreaText.setText("");
            }
        });
        
       /**
        * Reads a single desired text file chosen by the user and is then printed onto the editorAreaText
        */
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int chosen;
                File selected;
                BufferedReader br;
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                chooser.setFileFilter(fileFilter);
                chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                chosen = chooser.showOpenDialog(frame);
                if (chosen == JFileChooser.APPROVE_OPTION) {
                    selected = chooser.getSelectedFile();
                    try {
                        br = new BufferedReader(new FileReader(selected));
                        editorAreaText.read(br, null);
                        br.close();
                    } catch (Exception ioe) {
                        System.out.println("Error: " + ioe);
                    }
                    System.out.println("Opening file from " + selected.getAbsolutePath());
                } else {
                    return;
                }
            }
        });

        /**
         * Saves content from the textArea in a .txt file
         */
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                File save;
                BufferedWriter bw;
                int act = chooser.showOpenDialog(frame);
                if (act == JFileChooser.APPROVE_OPTION) {
                    save = new File(chooser.getSelectedFile() + ".txt");
                    bw = null;
                    try {
                        bw = new BufferedWriter(new FileWriter(save));
                        editorAreaText.write(bw);
                    } catch (IOException ioe) {
                        System.out.println("Error: IOExeption");
                    }
                } else {
                    return;
                }
                System.out.println("Saved file in location: " + save.getAbsolutePath());
            }
        });

        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit");
            }
        });

        /**
         * Exits the program
         */
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting...");
                System.exit(0);
            }
        });

        /**
         * Sets the textArea's font to bold if bold sub-menu has been selected and removes it if it has not been selected
         */
        bold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (bold.isSelected()) {
                    font += Font.BOLD;
                    editorAreaText.setFont(editorAreaText.getFont().deriveFont(font));
                } else {
                    font -= Font.BOLD;
                    editorAreaText.setFont(editorAreaText.getFont().deriveFont(font));
                }
            }
        });

        /**
         * Sets the textArea's font to italic if italic sub-menu has been selected and removes it if it has not been selected
         */
        italic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (italic.isSelected()) {
                    font += Font.ITALIC;
                    editorAreaText.setFont(editorAreaText.getFont().deriveFont(font));
                } else {
                    font -= Font.ITALIC;
                    editorAreaText.setFont(editorAreaText.getFont().deriveFont(font));
                }
            }
        });

        /**
         * Sets the textArea's size to small(size 12)
         */
        small.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorAreaText.setFont(editorAreaText.getFont().deriveFont(12f));
            }
        });

        /**
         * Sets the textArea's size to medium(size 16)
         */
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorAreaText.setFont(editorAreaText.getFont().deriveFont(16f));
            }
        });

        /**
         * Sets the textArea's size to large(size 20)
         */
        large.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorAreaText.setFont(editorAreaText.getFont().deriveFont(20f));
            }
        });
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}