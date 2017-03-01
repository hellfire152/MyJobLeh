 package dataAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.Security;

/**
 * Handle ChatDAO
 * @author NigelChen
 * @version a.3
 * @since a.3
 */
public class ChatDAO{
	private String finalOutput = "";
	

	public ArrayList<String> readFromChatFile(String jobName) {
		
		ArrayList<String> chatArr = new ArrayList<String>();

		int noOfCol;
		
        Path path = FileSystems.getDefault().getPath("resources/chat" , jobName + ".txt");
        File file = new File (path.toUri());
        
        noOfCol = Security.generateNoOfCol(jobName);
        
		try (Scanner in=new Scanner(file))
		{
		while (in.hasNextLine()) {
			chatArr.add(Security.decryptSimpleColumnar(in.nextLine(), noOfCol));
		}
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		}
		
		return chatArr;
			
	}
	
	public void writeToChatFile(String jobName, String transformText) {
		int noOfCol;
		
        Path pathWrite = FileSystems.getDefault().getPath("resources/chat" , jobName + ".txt");
        File fileWrite = new File (pathWrite.toUri());
        
        noOfCol = Security.generateNoOfCol(jobName);
		
        String inputCipherText = Security.encryptSimpleColumnar(transformText, noOfCol);
        
		try (FileWriter writer=new FileWriter(fileWrite , true))
		{
			writer.append(inputCipherText + "\n");
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}

	/* Getter and ssetter */
	public String getFinalOutput() {
		return finalOutput;
	}

}
