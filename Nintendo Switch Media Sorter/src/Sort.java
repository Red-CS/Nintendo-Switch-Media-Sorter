import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

public class Sort {

	private static int filesCreated = 0;
	private static int titleStatus = 1;
	private static String filepath;
	private static ArrayList<String> games = new ArrayList<String>();
	private static File outputLoc;
	private static File destinationRename;
	private static boolean unknownFolder = false;
	private static boolean statusDebug = false;
	private static boolean printFilepath = false;

	public static void main(String[] args) throws InterruptedException, IOException {

		// Declare media filename
		String filename, gameTitle;

		// Opening File Chooser to select Album File
		File filepath = getFolder();

		if (printFilepath) {
			System.out.println(filepath);
			System.out.println(filepath.toString());
			System.exit(1);
		}

		// Open "Album Folder"
		File[] albumList = filepath.listFiles();

		outputLoc = getFolder();

		boolean status = new File(outputLoc + "\\Nintendo (Sorted)").mkdirs(); // Will fail if file is already created

		// Update filepath variable
		String filePathStr = outputLoc.toString() + "\\Nintendo (Sorted)";
		System.out.println("New directory created at: " + filePathStr);

		// File Creation Debug (boolean class variable)
		if (statusDebug) {
			System.out.println(status);
			System.exit(1);
		}

		// Open each "year" Folder
		for (File years : albumList) {

			// Copy Extra Folder
			if (years.getName().equals("Extra")) {
				copyExtraFolder(years, (new File(filePathStr + "\\Extra")));
				continue;
			}

			File[] year = years.listFiles();

			// Open each "month" Folder
			for (File y : year) {
				File[] month = y.listFiles();

				// Open each "day" Folder
				for (File m : month) {
					File[] day = m.listFiles();

					// Create instance counts
					int fileInstance = 0;
					int unknownCount = 0;

					// Manipulate Files
					for (File d : day) {

						try {

							// Sets file name and attributes
							String[] fileData = new String[5];
							filename = d.toString();
							filename = setFileString(filename);
							fileData = setData(fileData, filename);
							// fileData --> {year, month, day, ID, filename extension}

							// Creating File Directories as Needed
							gameTitle = getGameTitle(filename);
							if (gameTitle != null) {
								if (!games.contains(gameTitle)) {
									games.add(gameTitle);
									Path thisGamePath = Paths.get(filePathStr, gameTitle);
									Files.createDirectories(thisGamePath);
								} else {
									games.add(gameTitle);
								}

								// Rename and Add File
								destinationRename = new File(filePathStr + "\\" + gameTitle + "\\" + fileData[1] + "-"
										+ fileData[2] + "-" + fileData[0] + ", " + gameTitle + fileData[4]);
								try {
									copyFile(d, destinationRename);
								} catch (Exception e) {
									fileInstance++;
									File destinationRename2 = new File(filePathStr + "\\" + gameTitle + "\\"
											+ fileData[1] + "-" + fileData[2] + "-" + fileData[0] + " (" + fileInstance
											+ "), " + gameTitle + fileData[4]);

									copyFile(d, destinationRename2);
								}

							} else {

								/*
								 * Check if Unknown Directory is created, if not, create it then add file to
								 * that directory
								 */
								String nameUnknown = "Unknown Titles";
								if (!unknownFolder) {
									unknownFolder = true;

									Path unknownGamePath = Paths.get(filePathStr, nameUnknown);
									Files.createDirectories(unknownGamePath);
								}
								destinationRename = new File(filePathStr + "\\" + nameUnknown + "\\" + fileData[1] + "-"
										+ fileData[2] + "-" + fileData[0] + ", " + gameTitle + fileData[4]);
								try {
									copyFile(d, destinationRename);
								} catch (Exception e) {
									unknownCount++;
									File destinationRename2 = new File(filePathStr + "\\" + gameTitle + "\\"
											+ fileData[1] + "-" + fileData[2] + "-" + fileData[0] + " (" + unknownCount
											+ "), " + nameUnknown + fileData[4]);

									copyFile(d, destinationRename2);
								}

							}
						} catch (Exception e) {
							continue;
						}
						System.out.println(destinationRename.toString());
						filesCreated++;
					}
					// Reset fileInstance variable for next "day" folder
					fileInstance = 0;
				}
			}
		}
		System.out.println();
		System.out.println(filesCreated + " files sorted");
		double kb = (outputLoc.length() / 1000);
		System.out.println(kb + " kilobytes of data created");
	}

	private static void copyFile(File src, File dest) throws IOException {
		Files.copy(src.toPath(), dest.toPath());
	}

	private static String getGameTitle(String gameID) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("ids.txt"));
		String compare, original = "";
		while (sc.hasNextLine()) {
			original = sc.nextLine();
			compare = original.substring(0, 32);
			if (gameID.indexOf(compare) >= 0) {
				break;
			} else if (!sc.hasNext()) {
				return null;
			}
		}
		return original.substring(original.indexOf(":") + 1);
	}

	public static String[] setData(String[] strArr, String filename) {
		strArr[0] = filename.substring(0, 4); // Sets Year
		strArr[1] = filename.substring(4, 6); // Sets Month
		strArr[2] = filename.substring(6, 8); // Sets Day
		strArr[3] = filename.substring(17, filename.length() - 4);
		strArr[4] = filename.substring(filename.length() - 4);
		return strArr;
	}

	private static String setFileString(String file) {
		while (file.indexOf('\\') >= 0 || file.indexOf('/') >= 0) {
			file = file.substring(1);
		}
		return file;
	}

	private static File getFolder() throws InterruptedException {

		// Sets FileExplorer to a similar style as the OS
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		File folder = null;

		// Using JFileChooser to open file chooser window
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		if (titleStatus == 1) {
			jfc.setDialogTitle("Choose Input Path");
			titleStatus = 0;
		} else {
			jfc.setDialogTitle("Choose Output Path");
		}

		// Program only accepts directories
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			folder = jfc.getSelectedFile();
		} else {
			// System will exit if file is not found
			System.out.println("No folder chosen, system will exit");
			Thread.sleep(500);
			System.exit(0);
		}
		return folder;
	}

	private static void copyExtraFolder(File original, File destination) {
		try {
			copyFile(original, destination);
		} catch (IOException e) {
			System.out.println("Error in copying \"Extra\" folder");
			e.printStackTrace();
		}
		renameExtra();
	}

	private static void renameExtra() {

	}

}
