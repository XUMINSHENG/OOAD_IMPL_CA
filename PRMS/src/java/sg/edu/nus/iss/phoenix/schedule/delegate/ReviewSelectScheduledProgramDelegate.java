package sg.edu.nus.iss.phoenix.schedule.delegate;

import sg.edu.nus.iss.phoenix.radioprogram.delegate.*;
import java.util.List;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.radioprogram.service.ReviewSelectProgramService;

public class ReviewSelectScheduledProgramDelegate {
    private ReviewSelectProgramService service;
    
	public ReviewSelectScheduledProgramDelegate() {
		service = new ReviewSelectProgramService();
	}
	
	public List<RadioProgram> reviewSelectRadioProgram() {
		return service.reviewSelectRadioProgram();	
	}

}
