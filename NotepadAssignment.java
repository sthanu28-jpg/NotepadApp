import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotepadAssignment extends JFrame {

    private JTextPane textPane;
    private boolean isUnderlined = false;
    private boolean darkMode = false;
    private boolean isModified = false; // Tracks unsaved changes

    public NotepadAssignment() {
        // Window settings
        setTitle("Simple Notepad");
        setSize(500, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // We handle exit manually

        // Text pane (for rich text)
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        // Track modifications
        textPane.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { isModified = true; }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { isModified = true; }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { isModified = true; }
        });

        // Confirm on window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleExit();
            }
        });

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        openItem.addActionListener(e -> {
            if (confirmSaveIfNeeded()) openFile();
        });
        saveItem.addActionListener(e -> saveFile());
        exitItem.addActionListener(e -> handleExit());

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem wordCountItem = new JMenuItem("Word Count"); // NEW

        cutItem.addActionListener(e -> textPane.cut());
        copyItem.addActionListener(e -> textPane.copy());
        pasteItem.addActionListener(e -> textPane.paste());
        wordCountItem.addActionListener(e -> showWordCount());

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(wordCountItem);

        // Format Menu
        JMenu formatMenu = new JMenu("Format");
        JMenuItem fontItem = new JMenuItem("Font");
        fontItem.addActionListener(e -> changeFont());

        JMenuItem fontSizeItem = new JMenuItem("Font Size");
        fontSizeItem.addActionListener(e -> changeFontSize());

        JMenuItem textColorItem = new JMenuItem("Text Color");
        textColorItem.addActionListener(e -> changeTextColor());

        JMenu fontStyleMenu = new JMenu("Font Style");
        JMenuItem boldItem = new JMenuItem("Bold");
        boldItem.addActionListener(e -> setFontStyle(Font.BOLD));
        JMenuItem italicItem = new JMenuItem("Italic");
        italicItem.addActionListener(e -> setFontStyle(Font.ITALIC));
        JMenuItem plainItem = new JMenuItem("Plain");
        plainItem.addActionListener(e -> setFontStyle(Font.PLAIN));
        JMenuItem underlineItem = new JMenuItem("Underline");
        underlineItem.addActionListener(e -> toggleUnderline());

        fontStyleMenu.add(boldItem);
        fontStyleMenu.add(italicItem);
        fontStyleMenu.add(plainItem);
        fontStyleMenu.add(underlineItem);

        JMenuItem darkModeItem = new JMenuItem("Toggle Dark Mode");
        darkModeItem.addActionListener(e -> toggleDarkMode());

        formatMenu.add(fontItem);
        formatMenu.add(fontSizeItem);
        formatMenu.add(textColorItem);
        formatMenu.add(fontStyleMenu);
        formatMenu.add(darkModeItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                " Simple Notepad\nCreated by: Thanu\nID: 028",
                "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    // Confirm save dialog
    private boolean confirmSaveIfNeeded() {
        if (!isModified) return true;
        int choice = JOptionPane.showConfirmDialog(this,
                "You have unsaved changes. Do you want to save?",
                "Unsaved Changes",
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.CANCEL_OPTION) return false;
        if (choice == JOptionPane.YES_OPTION) saveFile();
        return true;
    }

    // Exit handler
    private void handleExit() {
        if (confirmSaveIfNeeded()) System.exit(0);
    }

    // Open File
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                textPane.read(reader, null);
                isModified = false;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file!");
            }
        }
    }

    // Save File
    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                textPane.write(writer);
                isModified = false;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file!");
            }
        }
    }

    // Formatting
    private void changeFont() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String selectedFont = (String) JOptionPane.showInputDialog(
                this, "Select Font:", "Font",
                JOptionPane.PLAIN_MESSAGE, null, fonts, textPane.getFont().getFamily()
        );
        if (selectedFont != null) {
            Font currentFont = textPane.getFont();
            textPane.setFont(new Font(selectedFont, currentFont.getStyle(), currentFont.getSize()));
        }
    }

    private void changeFontSize() {
        String input = JOptionPane.showInputDialog(this, "Enter Font Size:", textPane.getFont().getSize());
        if (input != null) {
            try {
                int newSize = Integer.parseInt(input);
                Font currentFont = textPane.getFont();
                textPane.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), newSize));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid size entered!");
            }
        }
    }

    private void changeTextColor() {
        Color newColor = JColorChooser.showDialog(this, "Choose Text Color", textPane.getForeground());
        if (newColor != null) textPane.setForeground(newColor);
    }

    private void setFontStyle(int style) {
        Font currentFont = textPane.getFont();
        textPane.setFont(new Font(currentFont.getFamily(), style, currentFont.getSize()));
    }

    private void toggleUnderline() {
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start == end) {
            JOptionPane.showMessageDialog(this, "Please select text to underline.");
            return;
        }
        StyledDocument doc = textPane.getStyledDocument();
        Element element = doc.getCharacterElement(start);
        AttributeSet as = element.getAttributes();
        boolean isCurrentlyUnderlined = StyleConstants.isUnderline(as);
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setUnderline(sas, !isCurrentlyUnderlined);
        doc.setCharacterAttributes(start, end - start, sas, false);
    }

    // Word Count (NEW)
    private void showWordCount() {
        String text = textPane.getText();
        int charCount = text.length();
        int wordCount = text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
        JOptionPane.showMessageDialog(this,
                "Words: " + wordCount + "\nCharacters: " + charCount,
                "Word Count", JOptionPane.INFORMATION_MESSAGE);
    }

    // Dark Mode (NEW)
    private void toggleDarkMode() {
        if (!darkMode) {
            textPane.setBackground(Color.BLACK);
            textPane.setForeground(Color.WHITE);
        } else {
            textPane.setBackground(Color.WHITE);
            textPane.setForeground(Color.BLACK);
        }
        darkMode = !darkMode;
    }

    // Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NotepadAssignment::new);
    }
}
