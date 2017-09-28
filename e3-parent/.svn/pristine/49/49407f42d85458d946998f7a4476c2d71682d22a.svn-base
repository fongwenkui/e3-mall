package cn.e3mall.upload;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class UploadTest {

	@Test
	public void testName() throws Exception {
		// 1、加载配置文件，配置文件中的内容就是tracker服务的地址。
				String path = UploadTest.class.getClassLoader().getResource("conf/client.conf").getPath();
				ClientGlobal.init(path);
				// 2、创建一个TrackerClient对象。直接new一个。
				TrackerClient trackerClient = new TrackerClient();
				// 3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
				TrackerServer trackerServer = trackerClient.getConnection();
				// 4、创建一个StorageServer的引用，值为null
				StorageServer storageServer = null;
				// 5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
				StorageClient storageClient = new StorageClient(trackerServer, storageServer);
				// 6、使用StorageClient对象上传图片。
				//扩展名不带“.”
				String[] strings = storageClient.upload_file("E:/java/黑马就业班/WEB31_网上商城/代码/HeimaShop/WebContent/products/1/556d64fcNf28f90d0.jpg", "jpg", null);
				// 7、返回数组。包含组名和图片的路径。
				for (String string : strings) {
					System.out.println(string);
				}
	}
	
	/**
	 * 使用工具类
	 * @throws Exception
	 */
	@Test
	public void utilTest() throws Exception{
		String path = UploadTest.class.getClassLoader().getResource("conf/client.conf").getPath();
		FastDFSClient fastDFSClient=new FastDFSClient(path);
		String uploadFile = fastDFSClient.uploadFile("E:/java/黑马就业班/WEB31_网上商城/代码/HeimaShop/WebContent/products/1/c_0001.jpg");
		System.out.println(uploadFile);
	}
	
}
