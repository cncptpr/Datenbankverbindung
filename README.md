![image](https://github.com/cncptpr/TiTab-SQL/assets/65164803/7213bf88-ec25-4256-ab49-11803a762af7)

# TiTab-SQL
A simple program for learning how to write SQL Statements.

This programm provides a simple GUI to connect to a database server, write SQL statements, view their results and changes and some other tools. 

## Design
The program with it's Installation, Configuration and Saving of the History is designed in a way that it can be set up by the administrator on one computer, and then simply be copied on more computers (in a school/work scenario) while the History stays local to the computer/user.
This is the reason why it doesn't follow the standards of where to save what on an Operating System.

The configuration file is in the same folder as the Jar file, allowing for easy copying and the path where the history is saved can be configured and for example be set to the user directory synced with the server (if such a setup is present). 

Note: The "history" are the SQL Statements that where executed by the user. It is being displayed inside the the Program for the user as a way to look at older commands. Addidtionally it can be saved to files in order to allow the admin or a teacher to better judge how good a user is or how active they participate. 

## Installation
Simply Download the Jar file, put it into a folder and run it with the JRE (Java Runtime Enviromnt).
Used JDK/JRE Version is: 21

There are no official packages, apps or installers for this Program.

## Configuration
After the first run a 'config.json' file is generated in the same location as containing the configuration options with empty values:
- ip: The IP Address the Programm should connect to.
- database: The name of the database on the server the Programm should connnect to.
- history_dir: The Path the history should be saved in.

Both the IP Address and the Database Name are being used to prefill the Login UI and can be changed there.
The Database Name is required by the server to initially connect to a Database. The Database can be switched via a dropdown menu once connected.

If the history path is not provided or inaccessible to the program the history feature will still work, it just won't be saved.
The history path is realative to the directory you started to Programm in (not always the directory the Jar file is located in).
A absolute path is advised for a more reliable location.
