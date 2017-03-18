/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ohjobs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
/**
 *
 * @author Yichuan
 */
public class DBConnect {
    Connection con;
    String host,username,password;
    Statement stmt;
    ResultSet rs;
    public DBConnect(){
        try{
            host = "jdbc:derby://localhost:1527/Jobs";
            username = "wyc";
            password = "123";
            con = DriverManager.getConnection(host,username,password);
            stmt = con.createStatement();
        }
        catch(SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    /*
     * Return the result based on the query 
     */
    public void returnData(String[] input,ArrayList result){
        try{
            con = DriverManager.getConnection(host,username,password);
            //Generate the query string from the input 
            StringBuilder queryStatement = new StringBuilder();
            queryStatement.append("SELECT * FROM WYC.JOBS ");
            boolean criExist = false;
            for(int i = 0; i < 4; i++){
                if(!input[i].isEmpty()){
                    if(criExist == true){
                        queryStatement.append("AND ");
                    }
                    else{
                        criExist = true;
                        queryStatement.append("WHERE ");
                    }
                    switch(i){
                        case 0: queryStatement.append("COMPANY_NAME = '" + input[i] + "' ");//SQL requires single quotes around text values
                                break;
                        case 1: queryStatement.append("COMPANY_ID = " + input[i] + " ");
                                break;
                        case 2: queryStatement.append("JOB_TITLE = '" + input[i] + "' ");
                                break;
                        case 3: queryStatement.append("STATUS = '" + input[i] + "' ");
                                break;
                    }
                }
            }
            String query = queryStatement.toString();
            //Create statement for query
            stmt = con.createStatement();
            //execute Query to get the result sets
            rs = stmt.executeQuery(query);
            //add every single record into the result arraylist
            while(rs.next()){
                String[] resultSet = new String[6];
                resultSet[0] = Integer.toString(rs.getInt("COMPANY_ID"));
                resultSet[1] = rs.getString("COMPANY_NAME");
                resultSet[2] = rs.getString("JOB_TITLE");
                resultSet[3] = rs.getString("JOB_LOCATION");
                resultSet[4] = rs.getString("STATUS"); 
                resultSet[5] = rs.getString("LINK");
                result.add(resultSet);//Here the add function pass by reference not by value. So if you change the value of the resultSet,
                                     //then all the values have been added to result will be changed. unless you recreate a new resultSet.
            }
            stmt.close();
            rs.close();
        }
        catch(SQLException err) {
            System.out.println(err.getMessage());
        }
    } 
    
    /*
     * Add new record to the database
     */
    public void addData(String[] input){
        try{
            con = DriverManager.getConnection(host,username,password);
            StringBuilder queryStatement = new StringBuilder();
            queryStatement.append("INSERT INTO JOBS VALUES(");
            int date = Integer.parseInt(input[0]);
            queryStatement.append(date + ",");
            for(int i = 1; i < 5; i ++){
                queryStatement.append("'" + input[i] + "',");
            }
            queryStatement.append("'" + input[5] + "')");
            
            String query = queryStatement.toString();
            Statement stmt = con.createStatement();
            //execute Query to get the result sets
            stmt.executeUpdate(query);
            stmt.close();
            rs.close();
        }catch(SQLException err) {
            System.out.println(err.getMessage());
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                con.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(con!=null)
                con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }
    
    /*
     * Edit Data   
     */
    public void edtData(String[] input){
        try{
            con = DriverManager.getConnection(host,username,password);
            StringBuilder queryStatement = new StringBuilder();
            queryStatement.append("UPDATE JOBS SET ");
            int date = Integer.parseInt(input[0]);
            queryStatement.append("COMPANY_NAME = '" + input[1] + "',");
            queryStatement.append("JOB_TITLE = '" + input[2] + "',");
            queryStatement.append("JOB_LOCATION = '" + input[3] + "',");
            queryStatement.append("STATUS = '" + input[4] + "',");
            queryStatement.append("LINK = '" + input[5] + "' ");
            queryStatement.append("WHERE COMPANY_ID = " + input[0]);
            String query = queryStatement.toString();
            System.out.println(query);
            Statement stmt = con.createStatement();
            //execute Query to get the result sets
            stmt.executeUpdate(query);
            stmt.close();
            rs.close();
        }catch(SQLException err) {
            System.out.println(err.getMessage());
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                con.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(con!=null)
                con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }
    /*
     * Delete Data 
     */
    public void delData(int row){
        try{
            String query = "DELETE FROM JOBS " + "WHERE COMPANY_ID = " + row;
            System.out.println(query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        }catch(SQLException err) {
            System.out.println(err.getMessage()); 
        }
    }
    
    public void CheckExt(String input,ArrayList result){
        try{
            con = DriverManager.getConnection(host,username,password);
            //Generate the query string from the input 
            StringBuilder queryStatement = new StringBuilder();
            queryStatement.append("SELECT * FROM WYC.JOBS WHERE ");
            queryStatement.append("COMPANY_NAME LIKE '%" + input + "%' ");//SQL requires single quotes around text values
            String query = queryStatement.toString();
            //System.out.println(query);
            //Create statement for query
            System.out.println(query);
            stmt = con.createStatement();
            //execute Query to get the result sets
            rs = stmt.executeQuery(query);
            //add every single record into the result arraylist
            while(rs.next()){
                String[] resultSet = new String[6];
                resultSet[0] = Integer.toString(rs.getInt("COMPANY_ID"));
                resultSet[1] = rs.getString("COMPANY_NAME");
                resultSet[2] = rs.getString("JOB_TITLE");
                resultSet[3] = rs.getString("JOB_LOCATION");
                resultSet[4] = rs.getString("STATUS"); 
                resultSet[5] = rs.getString("LINK");
                result.add(resultSet);//Here the add function pass by reference not by value. So if you change the value of the resultSet,
                                     //then all the values have been added to result will be changed. unless you recreate a new resultSet.
            }
            stmt.close();
            rs.close();
        }
        catch(SQLException err) {
            System.out.println(err.getMessage());
        }
    } 
}


