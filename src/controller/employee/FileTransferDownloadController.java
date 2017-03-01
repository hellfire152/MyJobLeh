package controller.employee;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;

import controller.Controller;
import dataAccess.FileTransfer;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.event.WorkEvent;

public class FileTransferDownloadController extends Controller implements Initializable{

    @FXML
    private JFXButton btnBack;

    @FXML
    private TextField fieldSearchBox;

    @FXML
    private Label lbJobName;

    @FXML
    private JFXListView<String> fileListView;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton btnExport;

    @FXML
    private JFXDrawer fxDrawer;
    
    private  ListView<String> selectedFiles = new ListView<String>();

	private static WorkEvent work;
    @FXML
    void handleBack(ActionEvent event) {
		try {
			m.swapScene("/employee/FileTransfer.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void handleExport(ActionEvent event) {
    	int count = 0;
    	while(true){
    		if(selectedFiles.getItems().get(count) == null){
    			break;
    		}
    		
    		System.out.println(selectedFiles.getItems().get(count));
    		count++;
    	}
        FileTransfer.downloadFile(work.getJobTitle()+"_"+work.getCompanyName(), selectedFiles);
    }

    @FXML
    void handleSearchBox(ActionEvent event) {
    
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		lbJobName.setText(work.getJobTitle());
		//Change here
        Path path = FileSystems.getDefault().getPath("resources/fileTransfer/" + work.getJobTitle()+"_"+work.getCompanyName());
        File file = new File (path.toUri());	
        File[] listOfFile = file.listFiles();
        
        
		for(int j =0;j<listOfFile.length;j++){
			fileListView.getSelectionModel().getSelectedItem();
			fileListView.getItems().add(listOfFile[j].getName());
		}        
		
        fileListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        fileListView.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
        selectedFiles.setItems(fileListView.getSelectionModel().getSelectedItems());
            
            
        });
        
        
        
	}
	
	public void setWorkEvent(WorkEvent work) {
		this.work = work;
	}

	public static WorkEvent getWork() {
		return work;
	}

	public static void setWork(WorkEvent work) {
		FileTransferDownloadController.work = work;
	}
}
