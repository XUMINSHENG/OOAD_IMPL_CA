package sg.edu.nus.iss.phoenix.schedule.delegate;

import sg.edu.nus.iss.phoenix.radioprogram.delegate.*;
import java.util.List;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.radioprogram.service.ReviewSelectProgramService;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ReviewSelectScheduledProgramService;

public class ReviewSelectScheduledProgramDelegate {
    private ReviewSelectScheduledProgramService service;
    
	public ReviewSelectScheduledProgramDelegate() {
		service = new ReviewSelectScheduledProgramService();
	}
	
	public List<ProgramSlot> reviewSelectScheduledProgram() {
		return service.reviewSelectScheduledProgram();	
	}

}
