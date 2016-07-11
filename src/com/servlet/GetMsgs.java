package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.util.JSONStringer;
import com.db.ChatItem;
import com.service.Service;

public class GetMsgs extends HttpServlet {
	/**
	 * 获取信息
	 */
	private static final long serialVersionUID = -9204370698526255775L;
	 public void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 	ArrayList<ChatItem> chatList = new ArrayList<ChatItem>();
	        String roomId = request.getParameter("roomId");
	        String username = request.getParameter("username");
	        Service serv = new Service();
	        chatList = serv.getMsg(roomId,username);  
	 		JSONStringer stringer = new JSONStringer(); 
	 		if(chatList != null){
	  try{  
	 		stringer.array();
	 		for(ChatItem c:chatList){  
	 			stringer.object().key("id").value(c.getId()).key("userName").value(c.getUser()).key("roomId").value(c.getRoomId()).key("content").value(c.getContent()).key("res").value(c.isRes()).endObject();  	 			
	 		}  
	 		stringer.endArray();
	 	 }catch(Exception e){
	 		 e.printStackTrace();
	 	 }
	        response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));  
	        response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json    
	 		}else{
	 			return;
	 		}
	 }
	    public void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	    }
}
