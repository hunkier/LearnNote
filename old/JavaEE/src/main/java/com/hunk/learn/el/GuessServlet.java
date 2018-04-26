package com.hunk.learn.el;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * GuessServlet
 * Created by hunk on 2015/8/4.
 */
@WebServlet(name = "GuessServlet", urlPatterns = "/GuessServlet")
public class GuessServlet extends HttpServlet {
    // 产生一个幸运数字
    int answer;

    /**
     * 新游戏方法。产生一个新的幸运数字
     */
    private void newGame(){
        Random random = new Random();
        answer = random.nextInt(30);
        System.out.println("幸运数字："+ answer);
    }

    /**
     * 第一次访问是
     */
    public GuessServlet(){
        newGame();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 1.接收输入的数字
        String lucyNoStr = request.getParameter("lucyNo");
        System.out.println("答案："+lucyNoStr);
        Integer lucyNo = null;
        // 2.判断幸运数字和用户的数字
        // 2.1 把用户输入的数字转换成整数
        if (null!=lucyNoStr && !"".equals(lucyNoStr)){
            lucyNo = Integer.parseInt(lucyNoStr);
        }

        // 标记记录当前竞猜的此时
        Integer times = 1;//初始值

        // 接收客户当前竞猜次数
        String timeStr = request.getParameter("times");
        if (null!=timeStr && !"".equals(timeStr)){
            times = Integer.parseInt(timeStr) + 1 ;
        }

        if (times < 5) {
            String msg = "";
            // 比较
            if (lucyNo > answer){
                // 大了
                msg = "可惜，大了点！";
            }else  if (lucyNo < answer){
                msg = "可惜，小了点！";
            }else  if (lucyNo == answer){
                msg = "恭喜你，中得1000000元现金大奖！";
            }
            // 把当前竞猜的次数放入域对象
            request.setAttribute("times", times);
            // 把信息放入域对象
            request.setAttribute("msg", msg);
        }else  {
            // 产生新的幸运数字
            newGame();
            // 游戏结束
            response.getWriter().write("<html><body>游戏结束。<a href='" + request.getContextPath() + "/el/05.guess.jsp'>再来一盘</a></body></html>");
            return;
        }
        // 转发
        request.getRequestDispatcher("/el/05.guess.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
