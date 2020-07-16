# Nintendo Switch Media Sorter
Sorts screenshots and videos from the nintendo switch, renaming them appropriately and exporting them into a selected output destination.
## Prerequisites
You will need:
* A Windows Device
* Java JDK installed on your machine
* Nintendo Switch "Album" Folder
* The repository downloaded on your machine
* the "ids" txt file in the same folder as the Executable Jar File (Sort)
## Accessing Files
### From Nintendo Switch
If you want to sort titles directly from your Nintendo Switch, insert the microSD card into your machine. After running the program, you can delete the original files in the "Album" folder and replace them with the new sorted files for your convenience.
### From PC
If you want a copy of your Nintendo Switch media files, copy the contents of the microSD card of your Switch, or just the "Album" folder, and select that when running the program.
## Execution
### Executing from JAR File
Navigate to the "src" folder where the program is kept. 
![Source Folder Image Example](https://github.com/Red-CS/Nintendo-Switch-Media-Sorter/blob/master/img/src%20folder%20example.png)
Clicking on the Executable Jar File "Sort" will execute the program.
_This is important:_ For the input folder, navigate to where the __"Album"__ folder is stored on your machine and select it.
You will then be prompted with choosing an output directory. Do that however you would like.
And that's it. The media from the Album folder should be sorted into the directory you selected.
![Sorted List](https://github.com/Red-CS/Nintendo-Switch-Media-Sorter/blob/master/img/Sorted%20Game%20Title%20Directories.png)
### Executing from the Command Prompt
Navigate to the downloaded "src" folder. For example, the path may look like this:
```
C:\Users\abc12\Desktop\Nintendo
```
where "Album" is a folder in the "Nintendo" folder.
![Cmd Path Example](https://github.com/Red-CS/Nintendo-Switch-Media-Sorter/blob/master/img/Original%20cmd%20Image.png)
From there, simply type
```
java Sort.java
```
If done correctly, the Command Prompt should show the file path of each file that has been copied and renamed in the directory you specified.
![Finished Cmd Path Example](https://github.com/Red-CS/Nintendo-Switch-Media-Sorter/blob/master/img/Finished%20cmd%20Image.png)
## Nomenclature
Each file was renamed to the following format:
*yyyy-mm-dd, GAME TITLE*
![Astral Chain Example](https://github.com/Red-CS/Nintendo-Switch-Media-Sorter/blob/master/img/Sorted%20Astral%20Chain%20Example.png)
## Author
* **Red Williams** - [Red-CS](https://github.com/Red-CS)
## Contributions
* [Renan Greca](https://github.com/RenanGreca/Switch-Screenshots)
  * Provided missing game IDs responsible for identifying the game of the file, which wre used to rename files to readable format.
## Acknowledgments 
Thanks to my fellow percussionists of Eastern Loudoun Percussion who always beleived and supported me and my aspirations. Without them, this project would be impossible.
#### Initial Submission: March 23, 2020
