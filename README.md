# Student Module Chooser

A JavaFX desktop application that allows students to create a profile, 
select and reserve course modules, and save their choices for later.

---

## Overview

Students can create a personal profile, choose their course, and select 
modules across two terms. Selected and reserved modules are tracked 
separately, and the full profile can be saved and reloaded at any time.

---

## Features

- Create a student profile (name, student number, email, course)
- Select mandatory and optional modules per term
- Reserve modules for future consideration
- Save and load profiles using Java object serialisation
- Overview screen summarising all selected and reserved modules
- Export overview to a text file

---

## Tech Stack

- Java
- JavaFX (GUI framework)
- MVC Architecture (Model-View-Controller)
- Java Object Serialisation (file persistence)
- Eclipse IDE

---

## How to Run

1. Clone the repository:
```bash
git clone https://github.com/Anasab0/student-module-chooser.git
```
2. Open the project in Eclipse (File → Import → Existing Projects into Workspace)
3. Make sure JavaFX is configured in your build path
4. Run `ApplicationLoader.java` as a Java Application

---

## Project Structure
```
src/
├── controller/    # Event handlers and application logic
├── model/         # Student profile, course, module data classes
└── view/          # JavaFX panes and UI components
```

---

## Author

Anas Abjaou
[LinkedIn](https://www.linkedin.com/in/anas-abjaou) | 
[GitHub](https://github.com/Anasab0)
