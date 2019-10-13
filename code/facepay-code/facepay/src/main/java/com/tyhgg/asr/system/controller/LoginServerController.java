package com.tyhgg.asr.system.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tyhgg.asr.system.entity.LoginCodeEntity;
import com.tyhgg.asr.system.mapper.LoginCodeMapper;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.util.DateUtil;
import com.tyhgg.core.framework.util.Randoms;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 登录
 * @RestController = @Controller + @ResponseBody
 * @author zyt5668
 *
 */
@RestController
public class LoginServerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServerController.class);
	
	@Resource
	private LoginCodeMapper loginCodeMapper;
	
	/**
     * 获取图形验证码
     * @return
     */
    @RequestMapping(value = "/unlogin/sysfile/getImageValiteCode",method={RequestMethod.GET})
    public void getImageValiteCode(HttpServletRequest request, HttpServletResponse response) {
    	String imageUuid = StringUtil.getString(request.getParameter("imageUuid"));
    	LOGGER.info("/unlogin/sysfile/getImageValiteCode：" + imageUuid);
    	
    	if("".equals(imageUuid)){
    		LOGGER.info("imageUuid为空：");
    		return;
    	}
    	
    	// 图片高度
        int IMG_HEIGHT = 100;
        // 图片宽度
        int IMG_WIDTH = 30;
        // 验证码长度
        int CODE_LEN = 4;
    	BufferedImage bi = new BufferedImage(IMG_HEIGHT, IMG_WIDTH, BufferedImage.TYPE_INT_RGB);
        // 获取绘图工具    	
        Graphics graphics = bi.getGraphics();
        graphics.setColor(new Color(237, 236, 232)); // 使用RGB设置背景颜色
        graphics.fillRect(0, 0, 100, 30); // 填充矩形区域
       // Random random = new Random(1863895L);
        //10条干扰线
       
        // 验证码中所使用到的字符
        char[] codeChar = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHGKMNPQRSTUVWXYZ".toCharArray();
        String valiteCode = ""; // 存放生成的验证码
        Font font=new Font("Times New Roman", Font.ITALIC, 19);
        graphics.setFont(font);
        for(int i = 0; i < CODE_LEN; i++) { // 循环将每个验证码字符绘制到图片上
            int index = Randoms.nextInt(codeChar.length);
            // 随机生成验证码颜色
            graphics.setColor(new Color(Randoms.nextInt(150), Randoms.nextInt(200), Randoms.nextInt(255)));
            // 将一个字符绘制到图片上，并制定位置（设置x,y坐标）
            graphics.drawString(codeChar[index] + "", (i * 20) + 15, 20);
            valiteCode += codeChar[index];
        }        
        // 将生成的验证码code放入login_code表中
        //captcha=aesScript.cbcEncrypt(captcha.toLowerCase(), "E1B35B105C8BCA39", "48C33E7F17D743C4");        
        LoginCodeEntity loginCodeEntity = new LoginCodeEntity();
        loginCodeEntity.setUserId(imageUuid);
        
		// 登录码失效时间
		int loginCodeValidateTime = Integer.parseInt(SystemCacheHolder.
				getSystemPropertyCache().getMaps().get("logincode.validate.time"));
		Calendar calendar = Calendar.getInstance();
		Date loginTime = new Date();
		calendar.setTime(loginTime);
		calendar.add(Calendar.SECOND, loginCodeValidateTime);
		String validTime = DateUtil.dateFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");;
		loginCodeEntity.setValidTime(validTime);
        
		// imageUuid已存在的话先删除，再新增
		if(null != this.loginCodeMapper.queryLoginCodeEntity(loginCodeEntity)){
			this.loginCodeMapper.deleteLoginCode(imageUuid);
		}

        loginCodeEntity.setValidCode(valiteCode);
    	this.loginCodeMapper.save(loginCodeEntity);

        // 通过ImageIO将图片输出
        try {
        	response.setContentType("image/jpeg");
        	
			ImageIO.write(bi, "JPG", response.getOutputStream());
		} catch (IOException e) {
			LOGGER.error("获取验证码异常:", e);
		}
    }
}
