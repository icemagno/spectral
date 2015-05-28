package cmabreu.spectral.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;


public class Downloader {

	/**
	 * Faz o download de um arquivo do servidor.
	 * 
	 * @param from URL do arquivo
	 * @param to pasta para salvar o arquivo apos o download.
	 */
	public void download( String from, String to, boolean decompress ) throws Exception {
		String fileName = to;
		if ( decompress ) {
			fileName = fileName + ".gz";
		}
		
		URL link = new URL(from); 	
		InputStream in = new BufferedInputStream(link.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = in.read(buf) ) ) {
			out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();

		FileOutputStream fos = new FileOutputStream( fileName );
		fos.write(response);
		fos.close();
		
		if ( decompress ) {
			decompress(fileName, to);
			new File( fileName ).delete();
		}
	}

	public void decompress( String compressedFile, String decompressedFile ) {
		byte[] buffer = new byte[1024];
		try {
			FileInputStream fileIn = new FileInputStream(compressedFile);
			GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
			FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);
			int bytes_read;
			while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, bytes_read);
			}
			gZIPInputStream.close();
			fileOutputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
