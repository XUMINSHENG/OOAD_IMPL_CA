/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.SPSearchObject;

/**
 *
 * @author charan-a0134630r
 */
@Action("copysched")
public class CopyWeeklyScheduleCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        
        String srcDateVal= req.getParameter("src_date_val");
        String destDateVal= req.getParameter("dest_date_val");
        String copy_user_resp=null;
        String copy_sched_is_sel= null;
        
        if(req.getParameter("copy_user_resp") != null)
            copy_user_resp= req.getParameter("copy_user_resp").toString();
        if(req.getParameter("copy_sched_is_sel") != null)
            copy_sched_is_sel= req.getParameter("copy_sched_is_sel").toString();
        
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        df.setLenient(false);
        Date srcDate=null;
        Date destDate=null;
        Date srcStartDate=null;
        Date destStartDate=null;
        ArrayList<String> lbl_status=new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int year;
        int week;
        SPSearchObject srcSpso=new SPSearchObject();
        SPSearchObject destSpso = new SPSearchObject();
        boolean srcFlag=false;
        boolean destFlag=false;
        if (srcDateVal != null && !srcDateVal.isEmpty())
        {
         try {
            srcDate = df.parse(srcDateVal);
            req.setAttribute("src_date_val", srcDateVal);
            cal.setTime(srcDate);
            year=cal.get(Calendar.YEAR);
            req.setAttribute("src_date_year", year);
            week =cal.get(Calendar.WEEK_OF_YEAR);
            req.setAttribute("src_date_weeknum", week);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            srcStartDate=cal.getTime();
            req.setAttribute("src_date_weekstart", df.format(srcStartDate));
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            req.setAttribute("src_date_weekend", df.format(cal.getTime()));
            srcSpso.setYear(String.valueOf(year));
            srcSpso.setWeek(String.valueOf(week));
            }
          catch (ParseException ex) 
          {
            Logger.getLogger(CopyWeeklyScheduleCmd.class.getName()).log(Level.SEVERE, null, ex);
            lbl_status.add("Invalid Source Week Date!!!");
            srcFlag=true;
          }
        }
        if (destDateVal != null && !destDateVal.isEmpty())
        {
         try{
             
            destDate = df.parse(destDateVal);
            req.setAttribute("dest_date_val", destDateVal);
            cal.setTime(destDate);
            year=cal.get(Calendar.YEAR);
            req.setAttribute("dest_date_year", year);
            week =cal.get(Calendar.WEEK_OF_YEAR);
            req.setAttribute("dest_date_weeknum", week);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            destStartDate=cal.getTime();
            req.setAttribute("dest_date_weekstart", df.format(destStartDate));
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            req.setAttribute("dest_date_weekend", df.format(cal.getTime()));
            destSpso.setYear(String.valueOf(year));
            destSpso.setWeek(String.valueOf(week));
           
            }
         catch (ParseException ex) 
            {
                Logger.getLogger(CopyWeeklyScheduleCmd.class.getName()).log(Level.SEVERE, null, ex);
                lbl_status.add("Invalid Destination Week Date!!!");
                destFlag=true;
            }
        }
        ArrayList<ProgramSlot> data = null;
        Calendar destWeek = Calendar.getInstance();
        Calendar thisWeek = Calendar.getInstance();
        if(destDate != null)
            destWeek.setTime(destDate);
        int destWeekNum=destWeek.get(Calendar.WEEK_OF_YEAR);
        int thisWeekNum=thisWeek.get(Calendar.WEEK_OF_YEAR);
        if (req.getParameter("copy_sched") != null || Boolean.valueOf(copy_sched_is_sel))        
        {
            if (!srcFlag && (srcSpso.getYear() == null || srcSpso.getYear().isEmpty() || srcSpso.getWeek() == null || srcSpso.getWeek().isEmpty()))
                lbl_status.add("Source Week Date cannot be empty");
            if (!destFlag && (destSpso.getYear() == null || destSpso.getYear().isEmpty() || destSpso.getWeek() == null || destSpso.getWeek().isEmpty()))
                lbl_status.add("Destination Week Date cannot be empty");

            if(destWeekNum < thisWeekNum)
                lbl_status.add("Invalid Destination Week Date!!! Not allowed to change past schedules");
        }
        if (lbl_status.size() > 0)
        {
            req.setAttribute("src_date_val", srcDateVal);
            req.setAttribute("dest_date_val", destDateVal);
            req.setAttribute("splist", data);
            req.setAttribute("lbl_status", lbl_status);
            return "/pages/copysched.jsp";
        }            
        ScheduleDelegate delegate = new ScheduleDelegate();
        if (req.getParameter("display_src_sched") != null)        
        {
            if (srcSpso.getYear() != null && !srcSpso.getYear().isEmpty() && srcSpso.getWeek() != null && !srcSpso.getWeek().isEmpty())
                data = delegate.findSPByCriteria(srcSpso);
            req.setAttribute("actionSrc","src");
        }
        else if (req.getParameter("display_dest_sched") != null)        
        {
            if (destSpso.getYear() != null && !destSpso.getYear().isEmpty() && destSpso.getWeek() != null && !destSpso.getWeek().isEmpty())
                data = delegate.findSPByCriteria(destSpso);
            req.setAttribute("actionSrc","dest");
        }
        else if (req.getParameter("copy_sched") != null)        
        {
            try {
                ArrayList<ProgramSlot> srcSlots = new ArrayList<ProgramSlot>();
                ArrayList<ProgramSlot> destSlots = new ArrayList<ProgramSlot>();
                srcSlots = delegate.findSPByCriteria(srcSpso);
                destSlots = delegate.findSPByCriteria(destSpso);
                if(destSlots.size() > 0)
                {
                    if(destWeekNum == thisWeekNum)
                        lbl_status.add("Mid week!!! Not allowed to change past schedules");
                    else
                        req.setAttribute("is_prompt",true);
                    req.setAttribute("copy_sched_is_sel","true");    
                    req.setAttribute("src_date_val", srcDateVal);
                    req.setAttribute("dest_date_val", destDateVal);
                    req.setAttribute("splist", data);
                    req.setAttribute("lbl_status", lbl_status);
                    return "/pages/copysched.jsp";
                }                
                else
                {
                    req.setAttribute("is_prompt",false);                
                    req.setAttribute("actionSrc","copyYes");
                    try {
                        long diff =destStartDate.getTime()-srcStartDate.getTime();
                        data = delegate.processCopy(srcSpso,destSpso,TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                    } catch (Exception ex) {
                        Logger.getLogger(CopyWeeklyScheduleCmd.class.getName()).log(Level.SEVERE, null, ex);
                        lbl_status.add("Error Copying!!!");
                        req.setAttribute("actionSrc","copyErr");
                                
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(CopyWeeklyScheduleCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (req.getParameter("copy_sched") == null && Boolean.valueOf(copy_sched_is_sel) == true)        
        {
            if(copy_user_resp.equals("yes"))
            {
                req.setAttribute("actionSrc","copyYes");
                try {
                    long diff =destStartDate.getTime()-srcStartDate.getTime();
                    data = delegate.processCopy(srcSpso,destSpso,TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                } catch (Exception ex) {
                    Logger.getLogger(CopyWeeklyScheduleCmd.class.getName()).log(Level.SEVERE, null, ex);
                    lbl_status.add("Error Copying!!!");
                    req.setAttribute("actionSrc","copyErr");
                }
            }
            else
            {
                req.setAttribute("actionSrc","copyNo");                    
                data=delegate.findSPByCriteria(destSpso);
            }
            req.setAttribute("copy_sched_is_sel","false");                
        }
        else if (req.getParameter("reset") != null)        
        {
            req.setAttribute("actionSrc",null);
            srcDateVal=null;
            destDateVal=null;
            srcStartDate=null;
            destStartDate=null;
            req.setAttribute("src_date_year", null);
            req.setAttribute("src_date_weeknum", null);
            req.setAttribute("src_date_weekstart", null);
            req.setAttribute("src_date_weekend", null);
            req.setAttribute("dest_date_year", null);
            req.setAttribute("dest_date_weeknum", null);
            req.setAttribute("dest_date_weekstart", null);
            req.setAttribute("dest_date_weekend", null);
        }
        req.setAttribute("src_date_val", srcDateVal);
        req.setAttribute("dest_date_val", destDateVal);
        req.setAttribute("splist", data);
        req.setAttribute("lbl_status", lbl_status);
        return "/pages/copysched.jsp";
    }
}
