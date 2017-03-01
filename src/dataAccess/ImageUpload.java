package dataAccess;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.stage.FileChooser;

/**
 * Images uploaded by user (Employee and employer) will be handled by this class.\
 * Images will be stored in the form of FileIO as a mock up.
 * 
 * (AJK) I'm using this class to retrieve some preset images as BufferedImages from file
 * @author NigelChen, AJK
 * @version b.3
 * @since a.2
 */
public class ImageUpload{
	private static BufferedImage getImage(String imgName) {
        try {
        	Path path = FileSystems.getDefault().getPath("resources/images/" +imgName +".png");
        	System.out.println(path.toString());
			return ImageIO.read(new File(path.toUri()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
    public static BufferedImage defaultProfilePic() {
        return getImage("profile_default");
    }
    public static BufferedImage errorImage() {
    	return getImage("error image");
    }
    public static BufferedImage eventConflictImage() {
    	return getImage("exclaimation mark");
    }
    public static BufferedImage confirmBoxImage() {
    	return getImage("question mark");
    }
    
    
    /**
     * User email will be used to find the image stored in the resources userProfileImages file.
     * 
     * @param userEmail - user email address
     * @return image - profile photo of the user.
     */
    public static BufferedImage getUserProfile(String userEmail){
    	BufferedImage image = null;
        try {

            Path pathOfPhoto = FileSystems.getDefault().getPath("resources/userProfileImages" , userEmail + ".png");
            File fileOfPhoto = new File (pathOfPhoto.toUri());
            
            image = ImageIO.read(fileOfPhoto);

        } catch (IOException e) {
        	e.printStackTrace();
        }
        System.out.println("Done");
    	
		return image;
    	
    }
    /**
     * Source the image in the computer and then save it into userProfileImage file in resources.
     * file name save will be according to user email address. (E.g. nigel_ncch@hotmail.com.jpg)
     * 
     * @param userEmail - User email address
     * @param absolutePathOfPhoto - path of the image in the computer (Import function need to work on)
     */
    public static void addUserProfile(String userEmail, String absolutePathOfPhoto){
    	BufferedImage image = null;
    	try {
    		
    		FileChooser fc = new FileChooser();
    		File fileOfPhoto = fc.showOpenDialog(null);
    		image = ImageIO.read(fileOfPhoto);
    		
    		Path path = FileSystems.getDefault().getPath("resources/userProfileImages" , userEmail + ".png");
    		File file = new File (path.toUri());
    		
    		ImageIO.write(image, "png", file);
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	System.out.println("Done");
    }	
}