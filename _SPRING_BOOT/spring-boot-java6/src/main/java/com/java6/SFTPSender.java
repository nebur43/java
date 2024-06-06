package com.java6;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;


public class SFTPSender {

	private static final Logger logger = Logger.getLogger("SFTPSender");
	
	private static final String PROTOCOL_SFTP = "sftp";
	
	private static final String PROTOCOL_FTPS = "ftps";
	
	
	private List<String> confOptions;
	
	/**
	 * @param confOptions:
	 * (0) - Host
	 * (1) - Remote dir
	 * (2) - User
	 * (3) - Password
	 * (4) - UserDirIsRoot - valores posibles: true/false
	 * (5) - Timeout -> no implementado
	 */
	public SFTPSender(List<String> myConfOptions) {
		confOptions = myConfOptions;
	}
	
	public void transferToSFTP(List<String> arrfOut) throws Exception {
		transferToSFTP(arrfOut,true);
	}
	
	public void transferToSFTP(List<String> arrfOut,boolean borrarOrigen) throws Exception{

		if (null == confOptions || confOptions.isEmpty()) {
			throw new Exception("Los datos de configuraci�n no fueron aportados. No se puede continuar.");
		}
		
		if (arrfOut.isEmpty()) {
			throw new Exception("transferToSFTP: El fichero a enviar es nulo. No se puede continuar.");
		}
		
		StandardFileSystemManager manager = new StandardFileSystemManager();
		FileSystemOptions opts = new FileSystemOptions();
	    		
		String sftpHost = confOptions.get(0);
		String sftpuser = confOptions.get(2);
		String sftpPassword = confOptions.get(3);
		String sftpDir = confOptions.get(1);
		boolean userDirIsRoot = Boolean.parseBoolean(confOptions.get(4)) ;
		
		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
		SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, userDirIsRoot);
	  	SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
	  	SftpFileSystemConfigBuilder.getInstance().setPreferredAuthentications(opts, "password,keyboard-interactive");
	  	
		manager.init();
  	    logger.debug("SFTPSender.transferToSFTP.manager.init();");
	
	  	//arrfOut
	  	for(String fOut : arrfOut) {
	  		File file = new File(fOut);
	  		String ftpUri = PROTOCOL_SFTP + "://" + sftpuser + ":" + sftpPassword +  "@" + sftpHost + "/" + sftpDir + "/" + file.getName();
	  		
	  		logger.debug("Datos de la conexion:"+ ftpUri);
  	    
	  		FileObject localFile = manager.resolveFile(fOut);
	  		logger.debug("fOut.getAbsolutePath():" + fOut);
  	    
	  		FileObject remoteFile = manager.resolveFile(ftpUri, opts);
	  		logger.debug("Remotely copying the local file " + localFile.getName() + " to remote file:" + remoteFile.getName());
	  		
	  		remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
	  		logger.debug("[OK] Transfer completed.");
	  		if (borrarOrigen) {
//	  			FicheroUtils.borrar(fOut);
	  		}
	  		
	  	}
	}
	
	/**
	 * si no existe el directorio lo crea
	 * 
	 * @param fileRemoteName
	 * @param data
	 * @throws IOException
	 */
	public void createFileSFTP(String path, String fileRemoteName, byte[] data) throws IOException  {
		String sftpHost = confOptions.get(0).trim();
		String sftpuser = confOptions.get(1).trim();
		String sftpPassword = confOptions.get(2).trim();
		boolean userDirIsRoot = Boolean.parseBoolean(confOptions.get(3).trim()) ;
		
		OutputStream out = null;
    	StandardFileSystemManager manager = new StandardFileSystemManager();
    	manager.init();
    	FileSystemOptions opts = new FileSystemOptions();
		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
		SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, userDirIsRoot);
	  	SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
	  	SftpFileSystemConfigBuilder.getInstance().setPreferredAuthentications(opts, "password,keyboard-interactive");
    	
    	File file = new File(fileRemoteName);
    	String ftpUri = PROTOCOL_SFTP + "://" + sftpuser + ":" + sftpPassword +  "@" + sftpHost + "/" + path + "/" + file.getName();
    	logger.debug( "Se crea fichero ftp en:" + ftpUri);
    	
    	FileObject remoteFile = manager.resolveFile(ftpUri, opts);
        FileContent content = remoteFile.getContent();
        out = content.getOutputStream();
        out.write(data);
        out.flush();
        out.close();
	}
	
	public void createFileFTP(String path, String fileRemoteName, byte[] data) throws IOException  {
		String sftpHost = confOptions.get(0).trim();
		String sftpuser = confOptions.get(1).trim();
		String sftpPassword = confOptions.get(2).trim();
		boolean userDirIsRoot = Boolean.parseBoolean(confOptions.get(3).trim()) ;
		String port = confOptions.get(4).trim();
		
		OutputStream out = null;
    	StandardFileSystemManager manager = new StandardFileSystemManager();
    	manager.init();
    	FileSystemOptions opts = new FileSystemOptions();
    	FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, userDirIsRoot);
    	FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);
    	FtpFileSystemConfigBuilder.getInstance().setConnectTimeout(opts, 10000);
    	FtpFileSystemConfigBuilder.getInstance().setDataTimeout(opts, 10000);
    	FtpFileSystemConfigBuilder.getInstance().setSoTimeout(opts, 10000);
    	
    	File file = new File(fileRemoteName);
    	String ftpUri = "ftp" + "://" + sftpuser + ":" + sftpPassword +  "@" + sftpHost + ":"+port+"/" + path + "/" + file.getName();
    	logger.debug( "Se crea fichero ftp: " + ftpUri);
    	
    	FileObject remoteFile = manager.resolveFile(ftpUri, opts);
    	logger.debug( "remotefile: " + remoteFile);
        FileContent content = remoteFile.getContent();
        out = content.getOutputStream();
        out.write(data);
        out.flush();
        out.close();
	}
	
	public boolean existeDirectorioRemoto(StandardFileSystemManager manager,FileSystemOptions opts,String sftpHost,String sftpuser,String sftpPassword,String sftpDir) throws FileSystemException {
		String ftpUri = PROTOCOL_SFTP + "://" + sftpuser + ":" + sftpPassword +  "@" + sftpHost + "/" + sftpDir ;
    	logger.debug( "Se crea fichero ftp en:" + ftpUri);
    	
    	FileObject remoteFile = manager.resolveFile(ftpUri, opts);
    	return remoteFile.exists();
	}
	
	public static void main(String[] args) {
/** @param confOptions:
		 * (0) - Host
		 * (1) - User
		 * (2) - Password
		 * (3) - userDirIsRoot
		 * (4) - port
		 */
		

		try {
			
			ArrayList<String> myConfOptions = new ArrayList<String>();
			myConfOptions.add("10.148.137.70");
			myConfOptions.add("vpn02");
			myConfOptions.add(URLEncoder.encode("J@zzt3lVPN02"));
			myConfOptions.add("true");
			myConfOptions.add("2225");
		
			byte[] data = new byte[] {1,2,3};
			
			SFTPSender sftpSender = new SFTPSender(myConfOptions);
			sftpSender.createFileFTP("/ftp_jazztelvpn02/MANDATO","hola.txt", data);
//				ArrayList<String> ficheros = new ArrayList<String>()
//				ficheros.add("C:\\kk.json")
//				sftpSender.transferToSFTP(ficheros,false )
			System.out.println("********************* CONECTO!!! *******************************" );
			System.out.println("********************* CONECTO!!! *******************************" );
			System.out.println("********************* CONECTO!!! *******************************" );
			System.out.println("********************* CONECTO!!! *******************************" );
			System.out.println("********************* CONECTO!!! *******************************" );
		} catch (Exception e) {
			logger.error( "Error " , e);
		}
		
	}
	
	public static void usarClienteFtp() {
		
		try {
			System.out.println("solo ftp");
			FTPClient ftpsClient = new FTPClient();
			ftpsClient.connect("10.148.137.70",2225);
			if (ftpsClient.login("vpn02", "J@zzt3lVPN02")) {
				System.out.println("conectado: " + ftpsClient.getReplyString());
				ftpsClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
				
				
//				ftpsClient.enterLocalPassiveMode();
				ftpsClient.changeWorkingDirectory("/ftp_jazztelvpn02/MANDATO");
				System.out.println("change workdit : " + ftpsClient.getReplyString());
				
				ftpsClient.enterLocalPassiveMode();
				
				
		        InputStream fis = new ByteArrayInputStream("prueba".getBytes());
		        if (ftpsClient.storeFile("temp.txt", fis)) {
		        	System.out.println("lo almaceno");
		        	System.out.println("almacenado fichero temp.txt");
		        } else {
		        	
		        	System.out.println("no lo almaceno: " + ftpsClient.getReplyString());
		        }
		        fis.close();
		        ftpsClient.logout();
		        
			} else {
				System.out.println("no se loga");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
	}
	
}