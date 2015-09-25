package sg.edu.nus.iss.phoenix.core.dao;

import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDaoImpl;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDaoImpl;
import sg.edu.nus.iss.phoenix.presenter.dao.PresenterDAO;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAOImpl;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.presenter.dao.PresenterDAO;
import sg.edu.nus.iss.phoenix.presenter.dao.impl.PresenterDAOImpl;
import sg.edu.nus.iss.phoenix.producer.dao.ProducerDAO;
import sg.edu.nus.iss.phoenix.producer.dao.impl.ProducerDAOImpl;

public class DAOFactoryImpl implements DAOFactory {
	private UserDao userDAO = new UserDaoImpl();
	private RoleDao roleDAO = new RoleDaoImpl();
	private ProgramDAO rpdao = new ProgramDAOImpl();
        private ScheduleDAO scdao = new ScheduleDAOImpl();
        private ProducerDAO producerdao = new ProducerDAOImpl();
        private PresenterDAO presenterdao = new PresenterDAOImpl();

	@Override
	public UserDao getUserDAO() {
		// TODO Auto-generated method stub
		return userDAO;
	}

	@Override
	public RoleDao getRoleDAO() {
		// TODO Auto-generated method stub
		return roleDAO;
	}

	@Override
	public ProgramDAO getProgramDAO() {
		// TODO Auto-generated method stub
		return rpdao;
	}
        
        @Override
	public ScheduleDAO getScheduleDAO() {
		// TODO Auto-generated method stub
		return scdao;
	}

    @Override
    public PresenterDAO getPresenterDAO() {
        return presenterdao;
    }

    @Override
    public ProducerDAO getProducerDAO() {
        return producerdao;
    }
        

   


    

}
