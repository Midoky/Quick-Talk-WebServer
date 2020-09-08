package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.db.ChatItem;
import com.db.DBManager;

public class Service {
	//登录
    public Boolean login(String username, String password) {
        // 登录Sql
        String logSql = "select * from tb_user where user_name ='" + username
                + "' and user_password ='" + password + "'";
        // 连接数据库
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        try {
            ResultSet rs = sql.executeQuery(logSql);
            if (rs.next()) {
                sql.closeDB();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.closeDB();
        return false;
    }
    //获取信息ֵ
    public ArrayList<ChatItem> getMsg(String roomId,String username) {
    	ArrayList<ChatItem> chatList = new ArrayList<ChatItem>();
    	ChatItem chatItem = null;
        String getInfoSql = "select * from tb_room where roomId ='" + roomId + "'";
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        ResultSet rst = sql.executeQuery(getInfoSql);
			try {
				while(rst.next()){
					boolean RL;
					if(username.equals(rst.getString("userName"))){
						RL = true;
					}else{
						RL = false;
					}
					if(rst.getString("content")!=null){
						chatItem = new ChatItem(rst.getInt("id"), rst.getString("userName"),  rst.getString("roomId"),rst.getString("content"),RL);
						chatList.add(chatItem);
					}
				}
				sql.closeDB();
				return chatList;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        sql.closeDB();    
        return null;
    }
   //注册
    public boolean register(String username, String password) {
        
        String regSql = "insert into tb_user(user_name,user_password,user_statue) values('"+ username+ "','"+ password+ "','0') ";
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        int ret = sql.executeUpdate(regSql);
        if (ret != 0) {
            sql.closeDB();
            return true;
        }
        sql.closeDB();    
        return false;
    }
	public boolean create(String username, String roomId, String whom) {
		// TODO Auto-generated method stub
		String selectSql = "select roomId from tb_room where roomId = '"+roomId+"'";
		String updateSql = "update tb_user SET user_statue = '"+roomId+"' where user_name = '"+username+"';";
		String createSql = "insert into tb_room(userName,roomId,content) values('"+username+"','"+roomId+"','加入房间'); ";
		String quitSql = "update tb_user SET user_statue = '0' where user_name = '"+username+"';";
		String quitRoomSql =  "insert into tb_room(userName,roomId,content) values('"+username+"','"+roomId+"','退出房间'); ";
		DBManager sql = DBManager.createInstance();
        sql.connectDB();
		if(whom.equals("create")){
					int ret = sql.executeUpdate(updateSql);
					int ret2 = sql.executeUpdate(createSql);
					 if (ret != 0&&ret2!=0) {
				            sql.closeDB();
				            return true;	            
					 }
					 sql.closeDB();
					 return false; 
		}else if(whom.equals("join")){
			ResultSet rst = sql.executeQuery(selectSql);
			try {
				if(rst.next()){
					int ret_ = sql.executeUpdate(createSql);
					int ret = sql.executeUpdate(updateSql);
					 if (ret != 0 && ret_ != 0) {
				            sql.closeDB();
				            return true;	            
					 }
					 sql.closeDB();
					 return false; 
				}else{
					sql.closeDB();
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(whom.equals("quit")){
				int ret_ = sql.executeUpdate(quitSql);
				int ret = sql.executeUpdate(quitRoomSql);
				if (ret != 0 && ret_ != 0) {
				            sql.closeDB();
				            return true;	            
					 }
					 sql.closeDB();
					 return false; 
				}else{
					sql.closeDB();
					return false;
		}
		return false;
	}
	//发送消息
	public boolean sendMsg(String username, String roomId, String content) {
        String regSql = "insert into tb_room(userName,roomId,content) values('"+ username+ "','"+ roomId+ "','"+content+"') ";
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        int ret = sql.executeUpdate(regSql);
        if (ret != 0) {
            sql.closeDB();
            return true;
        }
        sql.closeDB();    
        return false;
	}
}
 
