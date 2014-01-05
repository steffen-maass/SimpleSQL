Project Overview
=======
This project has been developed as part of the class "CS 6422: Database System Implementation" at GeorgiaTech in Fall 2013 with professor Liu.

Setup Project
=======

Notice: We used exclusively IntelliJ IDEA to develop and build this application, this platform supports all the needed
features to build the project using Gradle, the same may not apply to other development environments!

1. Install Gradle (http://www.gradle.org)
	1. extract to arbitrary directory
	2. add `bin` to your `PATH`(for Windows...)
2. Clone repository to directory of choice
4. Choose `Open...` in IntelliJ, navigate to the `build.gradle`-file and open it, import it as a project
5. In IntelliJ: Open `Gradle` in sidebar, run the task `build` to build the project (this will also download all
    required libraries and link them to the project!) and also generate the Java-sources from the ANTLR-grammar
6. Project-Setup is finished!

Run Project
=======

Run main in SimpleSQLMain-class, the command-line will open and you can start typing...

Todo
=======
* Implement a Storage Manager and a Query execution
* Implement missing parts of Data Manipulation Language (`INSERT/UPDATE/DELETE`)
* Implement interface for Data Definition Language
* Implement a proper type system for database catalog
* Implement actual cost-based optimization, this is currently just a brute-force-approach