package com.java6;

import java.io.IOException;

public interface IFTPSender {

	void createFileFTP(String path, String fileRemoteName, byte[] data) throws IOException;

}
