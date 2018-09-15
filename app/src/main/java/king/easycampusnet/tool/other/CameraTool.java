package king.easycampusnet.tool.other;

import android.graphics.*;
import android.hardware.*;
import android.hardware.Camera.*;
import android.util.*;
import android.view.*;
import java.util.*;
import king.easycampusnet.manager.*;
import king.easycampusnet.tool.*;

import android.hardware.Camera;
import king.easycampusnet.*;
import android.content.*;
import android.os.*;
import java.io.*;

public class CameraTool
{
	private final static String TAG="CameraTool";
	private static String imagePath;
	private static Camera camera;
	private static byte[] data;
	public static void takePicture(){
		if(!EMailTool.check())return;
		camera = getFacingFrontCamera(); 
		if(camera!=null){
			SurfaceView preview = CameraWindow.getDummyCameraView(); 
			if(preview!=null){
				try { 
					camera.setPreviewDisplay(preview.getHolder());
					//Camera.Parameters parameters=camera.getParameters();
					//parameters.setPreviewSize(1,1);
					//parameters.setPictureSize(600,800);
					//camera.setParameters(parameters);
					//MessageCenter.send("开始预览...");
					camera.startPreview();
					//MessageCenter.send("自动对焦中...");
					camera.autoFocus(new Camera.AutoFocusCallback(){

							@Override
							public void onAutoFocus(boolean p1, Camera p2)
							{
								//MessageCenter.send("拍照中...");
								camera.takePicture(new Camera.ShutterCallback(){
										@Override
										public void onShutter()
										{
											// TODO: Implement this method
											try
											{
												Thread.sleep(1000);
											}
											catch (InterruptedException e)
											{e.printStackTrace();}
										}
									}
									, null, new Camera.PictureCallback(){

										@Override
										public void onPictureTaken(byte[] p1, Camera p2)
										{
											releaseCamera(camera); 
											data=p1;
											// TODO: Implement this method
											new Thread(new Runnable(){
													@Override
													public void run()
													{
														// TODO: Implement this method
														try{
															//MessageCenter.send("拍照成功!");
															Logger.logInfo(TAG,"拍照成功!");
															Bitmap bmp=rotaingImageView(-90,BitmapFactory.decodeByteArray(data, 0,data.length));
															String fileName=Environment.getExternalStorageDirectory().toString()
																+File.separator+".ecn"+File.separator+"target.jpg";
															/*+File.separator
															 +"AppTest"
															 +File.separator
															 +"PicTest_"+System.currentTimeMillis()+".jpg";*/
															File file=new File(fileName);
															if(!file.getParentFile().exists()){
																file.getParentFile().mkdir();//创建文件夹
															}
															BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
															bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);//向缓冲区压缩图片
															bos.flush();
															bos.close();
															//MessageCenter.send("图片保存成功!"+fileName);
															imagePath=fileName;
															EMailTool.send();
														} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
															MessageCenter.send(e.toString());
														}
													}
												}).start();
										}
									});
							}
						});
				} catch (Exception e) { 
					MessageCenter.send(e.toString());
					e.printStackTrace(); 
					releaseCamera(camera); 
			   }
				
			}
		}
	}

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) { 
		Bitmap returnBm = null; 
		// 根据旋转角度，生成旋转矩阵 
		Matrix matrix = new Matrix(); 
		matrix.postRotate(angle); 
		try { 
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片 
			returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); 
		} catch (OutOfMemoryError e) { 
		} 
		if (returnBm == null) { 
			returnBm = bitmap; 
		} 
		if (bitmap != returnBm) { 
			bitmap.recycle(); 
		} 
		return returnBm; 
	} 
	
	public static String getImagePath(){
		return imagePath;
	}
	private static void releaseCamera(Camera camera) { 
		if (camera != null) { 
			Logger.logInfo(TAG, "releaseCamera..."); 
			camera.stopPreview(); 
			camera.release(); 
			camera = null;
		} 
	} 
	
	private static Camera getFacingFrontCamera() { 
		CameraInfo cameraInfo = new CameraInfo(); 
		int numberOfCameras = Camera.getNumberOfCameras(); 
		for (int i = 0; i < numberOfCameras; i++) { 
			Camera.getCameraInfo(i, cameraInfo); 
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) { 
				try { 
					return Camera.open(i); 
				} catch (Exception e) { 
					e.printStackTrace(); 
					MessageCenter.send(e.toString());
				} 
			} 
		} 
		return null; 
	} 

}
