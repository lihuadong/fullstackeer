/**
 * 包打听全知道-微信H5版本
 * jingubang.image.biz
 * TimelineRedPkg.java
 * Ver0.0.1
 * 2017年2月23日-下午5:13:15
 *  2017全智道(北京)科技有限公司-版权所有
 * 
 */
package jingubang.image.biz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import jingubang.image.qr.QRCodeUtil;
import jingubang.image.qr.QRCodeZxingUtil;

import jingubang.image.ImageOperUtils;
import jingubang.image.ImageProcessUtils;



/**
 * 
 * TimelineRedPkg
 * 
 * 李华栋
 * 李华栋
 * 2017年2月23日 下午5:13:15
 * 
 * @version 0.0.1
 * 
 */
public class TimelineRedPkg {

	 //生成带有二维码的朋友圈红包
	public String  genTimelineRedpkg(String basePath,String baseImgUrl,String ticketUrl,String headUrl,String targerImgName) throws Exception{
		
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String date = ft.format(dNow);

		
		//从网络获取二维码
		String  qrName  = "qr_"+date+".png";
		String  qrLocalPath = basePath+"//"+qrName;
		ImageOperUtils.getImgFromUrl(ticketUrl, basePath, qrName);
		ImageProcessUtils.resizeImage(qrLocalPath, qrLocalPath, 150, 150);
		//调整二维码颜色
		BufferedImage bi = ImageIO.read(new File(qrLocalPath));
		int srcWidth = bi.getWidth();
		int srcHeight = bi.getHeight();
		String resultStr = QRCodeUtil.decode(qrLocalPath);
		QRCodeZxingUtil.encode(resultStr,srcWidth,srcHeight,qrLocalPath);
		ImageProcessUtils.resizeImage(qrLocalPath, qrLocalPath, 150, 150);
		
		//从网络获取头像
		String  headName  = "head_"+date+".png";
		String  headLocalPath = basePath+"//"+headName;
		ImageOperUtils.getImgFromUrl(headUrl, basePath, headName);
		ImageProcessUtils.resizeImage(headLocalPath, headLocalPath, 46, 46);
		
		
		//从网络获取背景
		String  bgName  = "bg_"+date+".png";
		String  bgLocalPath = basePath+"//"+bgName;
		ImageOperUtils.getImgFromUrl(baseImgUrl, basePath, bgName);
		ImageProcessUtils.resizeImage(bgLocalPath, bgLocalPath, 500, 500);

			
		//先二维码和底图合并
		String tempPICPath  = basePath+"/"+"temp_"+date+".png";
		BufferedImage buffImg = ImageOperUtils.watermark(new File(bgLocalPath), new File(qrLocalPath),185,123, 1.00f);
		ImageOperUtils.generateWaterFile(buffImg, tempPICPath);
		
		//最终生成图
		String saveFilePath = basePath+"/"+targerImgName+".png";
		BufferedImage buffImg2 = ImageOperUtils.watermark(new File(tempPICPath), new File(headLocalPath),237,175, 1.00f);
		ImageOperUtils.generateWaterFile(buffImg2, saveFilePath);
		
		return saveFilePath;
		
	}
	
	/**
	 * main(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param args 
	 *void
	 * @exception 
	 * @since  0.0.1
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimelineRedPkg  t   = new TimelineRedPkg();
		String basePath ="/Users/lihuadong/Documents/Temp/";
		String baseImgUrl ="http://data.a86.cn/wukonglai/redpkg/caishen500.png";
		String ticketUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQHi7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAycGcxeWd1OEFiNGUxajhqWU5vMUMAAgQUE3xYAwS0AAAA";
		String headUrl="http://wx.qlogo.cn/mmopen/X6Ucic5kYIBNuSElicC6fve8S9KbD2Vgt2xXfeoG0E3NqgAtVXfPPic14o05LCRzHJRcXhdGiaicrJibd0ekgkt4xC75VnVzyeyK2n/46";
		String targerImgName ="AAAABBBBCCCC.png";
		
		try {
			t.genTimelineRedpkg(basePath, baseImgUrl, ticketUrl, headUrl, targerImgName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
