package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class CreateRoom extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5718766716005972556L;
	boolean checked;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String roomId = request.getParameter("roomId");
        String whom = request.getParameter("whom");
        System.out.println("用户"+username + "  " +whom+"房间"+ roomId);

        // 建立新服务
        Service serv = new Service();
        //登录
        boolean loged = serv.create(username,roomId,whom);
        if (loged) {//登录
            System.out.println("用户"+username+"  "+whom+"房间"+roomId+"成功...");
            checked =true;
            request.getSession().setAttribute("username", username);
            // response.sendRedirect("welcome.jsp");
        } else {
            System.out.println("用户"+username+"  "+whom+"房间"+roomId+"失败...");
            checked = false;
        }
        //返回信息给客户
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if(checked){
        	out.print("pass");
        }
        else{
        	out.print("internal");
        }
        out.flush();
        out.close();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
