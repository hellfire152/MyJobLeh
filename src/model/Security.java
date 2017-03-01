 package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Provide encryption to provide higher security to data.
 * @author NigelChen
 * @version a.3
 * @since a.2
 */

public class Security {
	
	/**
	 * This is for the user password
	 * 
	 * Hash the password before storing into database. 
	 * Hash the password before compaing password from userinput as well as database
	 * @param password
	 * @return hashPassword
	 */
	public static String hashSHA1(String password){
		
		byte[] passwordByte = password.getBytes();
		String hashPassword = "";
		
		for(int i=0 ; i<passwordByte.length ; i++){
			hashPassword += Integer.toString((passwordByte[i] & 0xff) + 0x100, 16).substring(1);
		}
		
		return hashPassword;
		
	}
	
	/**
	 * This is for the chat system
	 * Encrypt the chat before storing into .txt format for increased security
	 * 
	 * @param plainText - Plain text that they want to encrypt
	 * @param noOfColumn - Number of column (Key)
	 * @return cipherText - encrypted text.
	 */
	public static String encryptSimpleColumnar(String plainText, int noOfColumn){
		ArrayList<String> cipherArr = new ArrayList<String>();
		String cipherText = "";
		int startingIndex =0;
		int counter = 0;
		
		while(true){
			try{
				cipherArr.add(plainText.substring(startingIndex, startingIndex+noOfColumn));
				startingIndex += noOfColumn;
			}
			catch(StringIndexOutOfBoundsException e){
				cipherArr.add(plainText.substring(startingIndex));
				break;
			}
		}
		
		while(true){
			forLoop: for(int i=0 ; i<cipherArr.size() ; i++){
				try{
					cipherText += cipherArr.get(i).charAt(counter);
				}catch(StringIndexOutOfBoundsException e){
					break forLoop;
				}
			}
			counter++;
			if(counter == noOfColumn){
				break;
			}
		}
		
		return cipherText;

	}
	
	/**
	 * This is for the chat system
	 * Decrypt the text to make it readable to user.
	 * 
	 * @param cipherText - Cipher text that they want to decrypt
	 * @param noOfColumn - Number of column (Key)
	 * @return plainText - Readable text to user
	 */
	public static String decryptSimpleColumnar(String cipherText, int noOfColumn){
		ArrayList <String> plainArr = new ArrayList <String>();
		String plainText = "";
		int startingIndex = 0;
		int numOfCharacter = cipherText.length() / noOfColumn;
		int numOfChatacterExtend = cipherText.length() % noOfColumn;
		
		for(int i=0 ; i<noOfColumn ; i++){
			if(numOfChatacterExtend > 0){
				plainArr.add(cipherText.substring(startingIndex, startingIndex + numOfCharacter +1));
				startingIndex += numOfCharacter +1;
				numOfChatacterExtend--;
			}
			else{
				plainArr.add(cipherText.substring(startingIndex, startingIndex + numOfCharacter));
				startingIndex += numOfCharacter;
			}
		}
		
		while(true){
			try{
				for(int i=0 ; i<noOfColumn ; i++){
					plainText += plainArr.get(i).charAt(0);
					plainArr.set(i, plainArr.get(i).substring(1)); 
				}
			}catch(StringIndexOutOfBoundsException e){
				break;
			}
		}
		
		return plainText;
	}
	
	public static String encryptCaesar(String plainText){
		ArrayList<Character> charList = new ArrayList<Character>();
		String cipherText = "";
		int key = 257;
		
		char[] charArr= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
						+'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
						+'1','2','3','4','5','6','7','8','9','0'};
		
		for(char i:charArr){
			charList.add(i);
		}
		
		for(int i=0; i<plainText.length() ; i++){
			char selected = plainText.charAt(i);
			int index = charList.indexOf(selected);
			int finalIndex = index + key;
			if(finalIndex > 62){
				finalIndex = (index+key) % 62;
			}
			if(!charList.contains(selected)){
				cipherText += selected;
			}
			else{
				cipherText += charList.get(finalIndex);
			}
		}
		return cipherText;
		
	}	
	
	public static String decryptCaesar(String cipherText){
		ArrayList<Character> charList = new ArrayList<Character>();
		String plainText = "";
		int key = 257;
		
		char[] charArr= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
						+'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
						+'1','2','3','4','5','6','7','8','9','0'};
		
		for(char i:charArr){
			charList.add(i);
		}
		
		for(int i=0; i<cipherText.length() ; i++){
			char selected = cipherText.charAt(i);
			int index = charList.indexOf(selected);
			int finalIndex = index - key;
			while(finalIndex < 0){
					finalIndex = finalIndex + 62;
			}
			if(!charList.contains(selected)){
				plainText += selected;
			}
			else{
				plainText += charList.get(finalIndex);
			}
		}
		return plainText;
		
	}
	/**
	 * This is for the chat system
	 * 
	 * Find the number of col (Key) for the encryption
	 * It is base on the first character of the jobName
	 * @param jobName - jobName/CompanyName
	 * @return noOfCol - number of column for the text to be encrypt
	 */
	public static int generateNoOfCol(String jobName){
		int noOfCol;
		char firstChar;
		String charToInt;
		char secondNum;
		char firstNum;
		
		firstChar = jobName.charAt(0);
		charToInt = Integer.toString(Character.getNumericValue(firstChar));
		firstNum =	charToInt.charAt(0);
		secondNum = charToInt.charAt(1);

		noOfCol = Character.getNumericValue(firstNum) + Character.getNumericValue(secondNum);
		
		if(noOfCol <= 1){
			noOfCol = 12;
		}
		return noOfCol;
	}
	
	/**
	 * This is a generate OTP when user has been inacitve for a long period of time.
	 * @return OTP number
	 */
	public static int generateOTP(){
		Random rand = new Random();

		int numbers;
		String otp = "";
		
		
		for(int i=0 ; i<6 ; i++){
			numbers = rand.nextInt(10);
			otp += numbers;
		}
		
		return Integer.parseInt(otp);
	}
	public static void main(String[] args) {
		String text = encryptCaesar("Hello");
		System.out.println(text);
		System.out.println(decryptCaesar(text));
	}
	
}
