package dataAccess;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
/**
 * User upload any files into local file so that it will be shared among employee and employer for them to view and download.
 * file will be stored inside resources/fileTransfer
 * 
 * @version a.3
 * @since a.2
 * @author NigelChen
 *
 */
public class FileTransfer extends Controller implements Initializable{
	
	/**
	 * Collect the file that user input and store them accordingly
	 * Every job will have their own individual subfolders which will be created automatically
	 * 
	 * @param selectedFile - File that the user input
	 * @param jobName - JobName to categorize the files
	 * @return - boolean value, determine to print and show stored or not stored.
	 */
	
		public static boolean uploadFile(File selectedFile, String jobName){
        
        Path folderPath = FileSystems.getDefault().getPath("resources/fileTransfer", jobName);
        File folderFile = new File (folderPath.toUri());    
		System.out.println(folderPath);
        System.out.println(folderFile.mkdir());
        
        Path path = FileSystems.getDefault().getPath("resources/fileTransfer/"+jobName, selectedFile.getName());
        System.out.println("file: " + selectedFile.getName());
        System.out.println(path);
        
        try {
			Files.copy(selectedFile.toPath(), path); 
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(selectedFile.getName() + " already exist");
			return false;
		}
        System.out.println("Successful");
        return true;
	}
	/**
	 * Download the file that the employer or other employee/employer uploaded to the fileTransfer area. 
	 * All files will automatically download to the user download file.
	 * @param jobName - To source for the subFolders, which the folder will be named.
	 */
	public static void downloadFile(String jobName, ListView<String> selectedFiles){
		
		int count = 0;
		while(true){
			if(selectedFiles.getItems().get(count) == null){
				break;
			}
			
			Path pathFolder = FileSystems.getDefault().getPath("resources/fileTransfer/"+jobName, selectedFiles.getItems().get(count));
			File fileFolder = new File (pathFolder.toUri());
			
			String findUserSystem = System.getProperty("user.home");
			File downloadFileTo = new File (findUserSystem + "/Downloads/", selectedFiles.getItems().get(count));
			
			Path path = FileSystems.getDefault().getPath("resources/fileTransfer/"+jobName, selectedFiles.getItems().get(count));
			
			count++;
			try {
				Files.copy(fileFolder.toPath(), downloadFileTo.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Successful");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
