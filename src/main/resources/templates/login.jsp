<%@page content-type="text/html";charset="utf-8" %>
<%@page language="java" impor="java.sql.*"%>
<%@page import="java.sql.*, javax.sql.DataSource, javax.naming.*" %>
<%
    // 获取用户的表单
    String sUsername, sPasswd;
    sUsername = request.getParameter("username");
    sUsername = sUsername.replaceAll("'", "''");
    sPasswd = request.getParameter("passwd");
    sPasswd = sPasswd.replaceAll("'", "''");


    // 检查用户信息是否正确
    String query = "select * from users where username = '" + sUsername + "' and passwd = '" + sPasswd + "'";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try{
        // 连接数据库
        
         // 使用JNDI获取数据源（需配置Tomcat的context.xml）
        Context ctx = new InitialContext();
        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/s_system");
        conn = ds.getConnection();
        
        // 使用预编译语句
        String sql = "SELECT username, realname, password_hash FROM users WHERE username = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
         // 查询数据
       
        rs = stmt.executeQuery(query);

        if(rs.next()){
            session.setAttribute("username", rs.getString("username"));
            session.setAttribute("realnaem", rs.getString("realname"));
            // 转到之后的页面
            response.sendRedirect("index");

        }else{
            // 密码错误返回
            response.sendRedirect("login");
        }
    }catch(SQLException ex){
        ex.printStackTrace();
        response.sendRedirect("login");
        return;
    }

    finnally{
        // 关闭数据库连接
        stmt.close();
        conn.close();
    }



%>