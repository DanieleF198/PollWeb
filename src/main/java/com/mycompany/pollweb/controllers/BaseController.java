/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mycompany.pollweb.result.TemplateManagerException;
import com.mycompany.pollweb.data.DataException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import com.mycompany.pollweb.dao.PollWebDataLayer;
import com.mycompany.pollweb.result.FailureResult;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;

       

/**
 *
 * @author joker
 */
@WebServlet(name = "BaseController", urlPatterns = {"/BaseController"})
public abstract class BaseController extends HttpServlet {
    @Resource(name = "jdbc/pollwebdb")
    private DataSource ds;
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws TemplateManagerException
     * @throws DataException
     * @throws SQLException
     */
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TemplateManagerException, DataException, SQLException;
    
    private void processBaseRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TemplateManagerException, DataException, SQLException{
        //WARNING: never declare DB-related objects including references to Connection and Statement (as our data layer)
        //as class variables of a servlet. Since servlet instances are reused, concurrent requests may conflict on such
        //variables leading to unexpected results. To always have different connections and statements on a per-request
        //(i.e., per-thread) basis, declare them in the doGet, doPost etc. (or in methods called by them) and 
        //(possibly) pass such variables through the request.
        try (PollWebDataLayer datalayer = new PollWebDataLayer(ds)) {
            datalayer.init();
            request.setAttribute("datalayer", datalayer);
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace(); //for debugging only
            (new FailureResult(getServletContext())).activate(
                    (ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
        }
    }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processBaseRequest(request, response);
        } catch (TemplateManagerException | DataException | SQLException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processBaseRequest(request, response);
        } catch (TemplateManagerException | DataException | SQLException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
