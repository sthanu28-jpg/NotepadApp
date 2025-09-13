# Simple Notepad App

A feature-rich Java Swing-based Notepad application that allows users to create, edit, and manage text files with additional styling and customization options.

---

##  Features

-  Open and Save `.txt` files
-  Cut, Copy, Paste functionality
-  Word and Character Count
-  Change Font, Font Size, and Text Color
-  Bold, Italic, Underline, and Plain styling
-  Toggle Dark Mode
-  Exit Confirmation for Unsaved Changes
-  About dialog with author information

---

##  Technologies Used

- **Java**
- **Java Swing** (GUI)
- **JTextPane**, **JMenuBar**, **JFileChooser**, etc.

---

##  File Structure
NotepadApp/
├── NotepadAssignment.java   # Main Java source file
├── README.md                # Project documentation


##  Setup Instructions

- **Java Version**: Java 8 or later  
- This application does **not use a database**; no setup is required for database or persistence beyond saving files to the file system.

### Compile & Run

in bash :

- javac NotepadAssignment.java
- java NotepadAssignment


---
## Assumptions & Special Notes

- All file operations are with plain text; text styling (bold/italic/etc.) is for display only, and saved files are plain .txt.
- Only single-document editing is supported (no tabs or multiple open files at once).
- Undo/redo is not implemented.
- Font/color changes apply to all text; individual persistence (e.g. styled saving/loading) is not supported.

---

## Learning Highlights
This project demonstrates:

- Swing-based GUI development
- Event-driven programming
- File input/output operations
- Dynamic text formatting and styling
- Implementing Dark Mode using color switching
- Handling unsaved changes with custom logic

---

## Known Limitations

- Does not support rich-text saving (only plain text)
- No multi-tab or multi-document interface
- No undo/redo functionality

---

## Author

-  Thanu
-  ID: 028
-  GitHub: @sthanu28-jpg



