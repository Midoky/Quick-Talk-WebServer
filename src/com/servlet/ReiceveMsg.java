package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class ReiceveMsg extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5761070279237536473L;
	
	boolean checked;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取用户名
        String username = request.getParameter("username");
        String roomId = request.getParameter("roomId");
        String content = request.getParameter("content");
        content = new String(content.getBytes("ISO-8859-1"), "UTF-8");
        Service serv = new Service();
        boolean send = serv.sendMsg(username,roomId,content);
        if (send) {//发送信息
            System.out.println("用户"+username + "  "+"在"+ roomId+"发送："+content);
            checked =true;
            request.getSession().setAttribute("username", username);
        } else {
            System.out.println("用户"+username+"发送信息失败...");
            checked = false;
        }
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
