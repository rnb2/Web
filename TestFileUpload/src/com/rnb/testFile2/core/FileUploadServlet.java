package com.rnb.testFile2.core; 

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(description="This is my first annotated servlet", value="/fileUploadServlet2")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        System.out.println("servlet counstr()");
    }

    
   @Override
protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	
	   uploadFiel(request);
	
}
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("do get...");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("do post...");
		
	
		/*ServletFileUpload servletFileUpload = new ServletFileUpload();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        System.out.println("Status : "+isMultipart);
		if (isMultipart) {
			try {
				InputStream inputStream2 = null;
				DiskFileItemFactory factory = new DiskFileItemFactory();

				ServletContext servletContext = this.getServletConfig().getServletContext();
				File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
				factory.setRepository(repository);
				servletFileUpload = new ServletFileUpload(factory);
				
				List<FileItem> listFileItems = servletFileUpload.parseRequest(request);
				System.out.println("listFileItems=" + listFileItems.size());
				for (FileItem fileItem : listFileItems) {
					System.out.println("fileItem=" + fileItem.getFieldName());
					inputStream2 = fileItem.getInputStream();
				}
				System.out.println("inputStream2=" + inputStream2);
				
				FileItemIterator iter = servletFileUpload.getItemIterator(request);

				while (iter.hasNext()) {
					FileItemStream item = iter.next();

					String name = item.getFieldName();
					InputStream stream = item.openStream();

					System.out.println("name=" + name);

					String fileName = item.getName();
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}
					System.out.println("fileName=" + fileName);

					// Process the input stream
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					int len;
					byte[] buffer = new byte[8192];
					while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
						out.write(buffer, 0, len);
					}

					int maxFileSize = 10 * (1024 * 1024); // 10 megs max
					if (out.size() > maxFileSize) {
						throw new RuntimeException("File is > than "
								+ maxFileSize);
					}

					System.out.println("real path="
							+ getServletContext().getRealPath(fileName));

					String uploadDir = System.getProperty("jboss.server.base.dir") + "/uploads";
					String fullFileName = uploadDir	+ "/" + fileName;
					
					System.out.println("File will be Uploaded at: " + fullFileName);

					FileOutputStream fileOutSt = new FileOutputStream(fullFileName);
					fileOutSt.write(out.toByteArray());
					fileOutSt.close();

					System.out.print("File was saved !!!!");
					
					//worked stream from file on disk
					//InputStream inputStream = new FileInputStream(fullFileName);					
					//getServletContext().setAttribute("com.rnb2.gwt1.streamFile.xls", inputStream);
					//--
				}
				
				//try 2
				//getServletContext().setAttribute(Constants.contextAttributeStreamXlsFile, inputStream2);
			}

			catch (Exception e) {
				System.out.print("Servlet Exception...");
				e.printStackTrace();
				System.out.print("Servlet Exception.");
				throw new RuntimeException(e);
			}
		}*/
	}
	
	public void uploadFiel(HttpServletRequest request){
		/******** Following part of code is not needed ********/
        InputStream inputStream = null;
        FileOutputStream outputStream =null;
        try
         {
        	System.out.println("request.getParts()=" + request.getParts());
           for (Part part : request.getParts()) 
             {
                System.out.println(part.getName());
                inputStream = request.getPart(part.getName()).getInputStream();
                int i = inputStream.available();
                byte[] b  = new byte[i];
                inputStream.read(b);
                System.out.println("Length : " + b.length);

              // Finding the fileName //
                String fileName = "";
                String partHeader = part.getHeader("content-disposition");
                System.out.println("Part Header = " + partHeader);
                System.out.println("part.getHeader(\"content-disposition\") = " + part.getHeader("content-disposition"));

                for (String temp : part.getHeader("content-disposition").split(";")) 
                   {
                     if (temp.trim().startsWith("filename")) 
                      {
                         fileName=temp.substring(temp.indexOf('=') + 1).trim().replace("\"", "");
                      }
                   }

                // Writing contents to desired FilePath & FileName //
                String uploadDir=System.getProperty("jboss.server.base.dir")+"/uploads";
                System.out.println("File will be Uploaded at: " +uploadDir+"/"+fileName);                  
                outputStream = new FileOutputStream(uploadDir +"/"+fileName);
                outputStream.write(b);
                inputStream.close();
             }
          }
        catch(Exception e)
          {
             System.out.println("Unable to Upload File: "+e);
             e.printStackTrace();
          }
        finally
          {
              if(inputStream!=null)
                 {   
                    try{ inputStream.close(); } catch(Exception e){ e.printStackTrace(); } 
                 }
              if(outputStream!=null)
                 {   
                    try{ outputStream.close(); } catch(Exception e){ e.printStackTrace(); } 
                 }
          }
      }
	

}
