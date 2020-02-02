/**
 * 包打听全知道-微信H5版本
 * jingubang.image
 * ImageOperUtils.java
 * Ver0.0.1
 * 2017年2月23日-下午6:03:06
 *  2017全智道(北京)科技有限公司-版权所有
 * 
 */
package jingubang.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;


/**
 * 
 * ImageOperUtils
 * 
 * 李华栋
 * 李华栋
 * 2017年2月23日 下午6:03:06
 * 
 * @version 0.0.1
 * 
 */
public class ImageOperUtils {

	/**
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
	
	/**
	 * 从URL获取图片
	 * 
	 * @param imgUrl
	 * @param desc
	 * @param imgName
	 * @throws Exception
	 */
	public static void getImgFromUrl(String imgUrl, String desc, String imgName) throws Exception {
		
		URL url = new URL(imgUrl);
		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式为"GET"
		conn.setRequestMethod("GET");
		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();
		// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
		byte[] data = readInputStream(inStream);
		// new一个文件对象用来保存图片，默认保存当前工程根目录
		File imageFile = new File(desc + "//" + imgName);
		// 创建输出流
		FileOutputStream outStream = new FileOutputStream(imageFile);
		// 写入数据
		outStream.write(data);
		// 关闭输出流
		outStream.close();
		
	}
	
	
	
	/**
	 * 
	 * @param filename 文件的名字
	 * @return 文件的后缀名
	 */
	public static String getFileExtension(String filename){
		return filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
	}
	/**
	 * @param filename
	 * @return 图片文件("bmp", "gif", "jpg", "jpeg", "png")返回：true
	 */
	public static boolean isImageFile(String filename){
		String imageExtendsion[] = {"bmp", "gif", "jpg", "jpeg", "png"};
		String fileExtendsion = getFileExtension(filename);
		for(int i = 0; i < imageExtendsion.length; ++i){
			if(imageExtendsion[i].equals(fileExtendsion)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: getFileImage
	 * @Description: 读取源文件 转换为java.awt.Image类型的数据
	 * @param file 源文件
	 * @return Image
	 */
	private static Image getFileImage(File file){
		Image image = null;

		/*		if(getFileExtension(file.getName()).equals("bmp")){
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			image = com.yume.imgcut.BMPLoader.read(in);
		}else{
			
		}*/
		
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * 
	 * @Title: getMyBufferedImage
	 * @Description: 构建一个预定义图像类型的BufferedImage
	 * @param file 需要被叠加的图片文件
	 * @param scale 缩放等级 等于1 不进行缩放
	 * @return BufferedImage
	 */
	private static BufferedImage getMyBufferedImage(File file, float scale){

		Image image = getFileImage(file);// 得到Image对象
		BufferedImage buffImg = null;
		try {
			buffImg = javax.imageio.ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//宽跟高
		int width = (int) (image.getWidth(null));
		int height = (int) (image.getHeight(null));
		// 构建一个预定义图像类型的BufferedImage
		buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		// 创建Graphics2D对象，用在BufferedImage对象上绘图
		Graphics2D g2d = buffImg.createGraphics();
		// 设置图形上下文的当前颜色为透明色
		Color transparent = new Color(0, 0, 0, 0);
		g2d.setColor(transparent);
		// 填充指定的矩形区域
		g2d.fillRect(0, 0, width, height);
		
		g2d.drawImage(image, 0, 0, width, height, null);
		if(scale != 1.0f){
			//缩放图片
			BufferedImage filteredBufImage =new BufferedImage((int) (width*scale), (int) (height*scale),BufferedImage.TYPE_INT_RGB); //过滤后的图像
			AffineTransform transform = new AffineTransform(); //仿射变换对象
			transform.setToScale(scale, scale); //设置仿射变换的比例因子	
			AffineTransformOp imageOp = new AffineTransformOp(transform, null);//创建仿射变换操作对象			
			imageOp.filter(buffImg, filteredBufImage);//过滤图像，目标图像在filteredBufImage
			buffImg = filteredBufImage;
		}
		return buffImg;
	}
	
	/**
	 * 算法选择
	 * @return RenderingHints的一个对象
	 */
	private static RenderingHints getMyRenderingHints(){
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,// 抗锯齿提示键。
				RenderingHints.VALUE_ANTIALIAS_ON);// 抗锯齿提示值——使用抗锯齿模式完成呈现。
		rh.put(RenderingHints.KEY_TEXT_ANTIALIASING ,// 文本抗锯齿提示键。
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB);//要求针对 LCD 显示器优化文本显示
		rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,// Alpha 插值提示值
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );// Alpha 插值提示值——选择偏重于精确度和视觉质量的 alpha 混合算法
		rh.put(RenderingHints.KEY_RENDERING,// 呈现提示键。
				RenderingHints.VALUE_RENDER_QUALITY);// 呈现提示值——选择偏重输出质量的呈现算法
		rh.put(RenderingHints.KEY_STROKE_CONTROL ,//笔划规范化控制提示键。
				RenderingHints.VALUE_STROKE_NORMALIZE);//几何形状应当规范化，以提高均匀性或直线间隔和整体美观。
		rh.put(RenderingHints.KEY_COLOR_RENDERING  ,//颜色呈现提示键。
				RenderingHints.VALUE_COLOR_RENDER_QUALITY );// 用最高的精确度和视觉质量执行颜色转换计算。
		return rh;
	}
	
	
	/**
	 * 
	 * @Title: printWatemark
	 * @Description: 添加水印
	 * @param g2d 由源文件生成的Graphics
	 * @param img 需要叠加的水印Image
	 * @param x 以右下角为原点 水印放置的X坐标
	 * @param y 以右下角为原点 水印放置的Y坐标
	 * @param width 水印图片的宽度
	 * @param height 水印图片的高度
	 * @param alpha 水印的透明度 选择值从0.0~1.0: 完全透明~完全不透明
	 * @return void
	 */
	private static void printWatemark(Graphics2D g2d,Image img,int x,int y,int width, int height,float alpha){
		//在图形和图像中实现混合和透明效果
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		g2d.drawImage(img, x, y, width, height, null);
	}
	
	/**
	 * 
	 * @Title: watermark
	 * @Description: 生成水印并返回java.awt.image.BufferedImage
	 * @param file 源文件(图片)
	 * @param waterFile 水印文件(图片)
	 * @param x 距离右下角的X偏移量
	 * @param y 距离右下角的Y偏移量
	 * @param alpha 透明度, 选择值从0.0~1.0: 完全透明~完全不透明
	 * @return BufferedImage
	 */
	public static BufferedImage watermark(File file, File waterFile,int x,int y, float alpha) {
		BufferedImage buffImg = getMyBufferedImage(file, 1.0f);
		BufferedImage waterImg = null;
		try {
			waterImg = javax.imageio.ImageIO.read(waterFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 创建Graphics2D对象，用在BufferedImage对象上绘图
		Graphics2D g2d = buffImg.createGraphics();
		g2d.setRenderingHints(getMyRenderingHints());
		
		int sourceImgWidth  = buffImg.getWidth();
		int sourceImgHeight = buffImg.getHeight();

		int waterImgWidth = waterImg.getWidth();
		int waterImgHeight = waterImg .getHeight();


		printWatemark(g2d,waterImg,sourceImgWidth-waterImgWidth-x,sourceImgHeight-waterImgHeight-y,waterImgWidth,waterImgHeight,alpha);
		g2d.dispose();// 释放图形上下文使用的系统资源
		return buffImg;
	}
	
	/**
	 * 输出水印图片
	 * @param buffImg 图像加水印之后的BufferedImage对象
	 * @param savePath 图像加水印之后的保存路径
	 */
	public static void generateWaterFile(BufferedImage buffImg, String savePath) {
		int temp = savePath.lastIndexOf(".") + 1;
		try {
			ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
