package com.taotao.test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.utils.FastDFSClient;

public class TestFastDFS {

	@Test
	public void TestUpload() throws Exception{
		ClientGlobal.init("/Users/savaga/Workspaces/Eclipse/JavaEE28/taotao-manager-web/src/main/resources/resource/client.conf");
		TrackerClient client = new TrackerClient();
		TrackerServer trackerServer = client.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		String[] upload_file = storageClient.upload_file("/Users/savaga/Pictures/be2de3bc-1ee8-463f-ba3e-f027676609cf.jpeg", "jpeg", null);
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
	
	@Test
	public void TestFastDFSClient() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("/Users/savaga/Workspaces/Eclipse/JavaEE28/taotao-manager-web/src/main/resources/resource/client.conf");
		String uploadFile = fastDFSClient.uploadFile("/Users/savaga/Pictures/be2de3bc-1ee8-463f-ba3e-f027676609cf.jpeg");
		System.out.println(uploadFile);
	}

}
