package com.java6;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.log4j.Logger;

public class FTPSender implements IFTPSender {

	private static final Logger logger = Logger.getLogger(FTPSender.class);
	
	private static final String UTF_8 = "UTF-8";
	
	
	private static final String PROTOCOL_FTP = "ftp";
	
	private String host;
	private int port;
	private String user;
	private String password;
	private boolean userDirIsRoot;
	private boolean pasiveMode;
	
	/**
	 * @param confOptions:
	 * (0) - Host
	 * (1) - Remote dir
	 * (2) - User
	 * (3) - Password
	 * (4) - UserDirIsRoot - valores posibles: true/false
	 * (5) - Timeout -> no implementado
	 */
	public FTPSender(String host, int port, String user, String password, boolean userDirIsRoot, boolean pasiveMode) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.userDirIsRoot = userDirIsRoot;
		this.pasiveMode = pasiveMode;
	}
	

	@Override
	public void createFileFTP(String path, String fileRemoteName, byte[] data) throws IOException  {
		
		OutputStream out = null;
    	StandardFileSystemManager manager = new StandardFileSystemManager();
    	manager.init();
    	FileSystemOptions opts = new FileSystemOptions();
    	FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, userDirIsRoot);
    	FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, pasiveMode);
    	FtpFileSystemConfigBuilder.getInstance().setConnectTimeout(opts, 10000);
    	FtpFileSystemConfigBuilder.getInstance().setDataTimeout(opts, 10000);
    	FtpFileSystemConfigBuilder.getInstance().setSoTimeout(opts, 10000);
    	
    	String ftpUri = PROTOCOL_FTP + "://" + URLEncoder.encode(user,UTF_8) + ":" + URLEncoder.encode(password,UTF_8) +  "@" + host + ":"+port+"/" 
    			+ URLEncoder.encode(path,UTF_8) + "/" + URLEncoder.encode(fileRemoteName,UTF_8);
    	logger.debug( "Se crea fichero ftp: " + ftpUri);
    	
    	FileObject remoteFile = manager.resolveFile(ftpUri, opts);
    	logger.debug( "remotefile: " + remoteFile);
        FileContent content = remoteFile.getContent();
        out = content.getOutputStream();
        out.write(data);
        out.flush();
        out.close();
	}
	
	
	public static void main(String[] args) {
/** @param confOptions:
		 * (0) - Host
		 * (1) - User
		 * (2) - Password
		 * (3) - userDirIsRoot
		 * (4) - port
		 */
		

			byte[] data = new byte[] {1,2,3};
			
			FTPSender sftpSender = new FTPSender("10.148.137.70" , 2225, "vpn02", "J@zzt3lVPN02", true, true);
			try {
				sftpSender.createFileFTP("/ftp_jazztelvpn02/CONTRATO","Nuevo_hola.txt", data);
				logger.debug("********************* CONECTO!!! *******************************" );
			} catch (Exception e) {
				logger.error( "Error " , e);
			}
		
		
		
	}
}