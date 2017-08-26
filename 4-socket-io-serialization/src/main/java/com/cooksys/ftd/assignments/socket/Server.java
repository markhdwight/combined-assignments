package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {

    	Student student = new Student();
    	
    	try 
    	{
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			student = (Student)unmarshaller.unmarshal(new File(studentFilePath));
		} 
    	catch (JAXBException e) 
    	{
			e.printStackTrace();
		}
    	
    	return student;
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     */
    public static void main(String[] args) {

    	String configLoc = "./config/config.xml";
    	String studentLoc;
    	String tempStudentLoc = "./config/tempStudent.xml";
    	File studentFile;
    	File tempStudentFile = new File(tempStudentLoc);

    	JAXBContext jaxb;
    	Marshaller marshaller;
    	Unmarshaller unmarshaller;
    	FileInputStream fileIn;
    	
    	Config config;
    	Student student;
    	LocalConfig local;
    	OutputStream output; 
    	ServerSocket server;
    	Socket socket;

    	byte[] bytes = new byte[512];		//TODO: adjust byte size if needed?
    	 
    	System.out.println("Starting server...");
    	
    	while(true)
    	{
    		System.out.println("Awaiting a student, use ctrl-c to shutdown");
    		try
    		{	
    		//Unmarshall config details from config xml file
    			jaxb = createJAXBContext();
    			config = loadConfig(configLoc,jaxb);
    			studentLoc = config.getStudentFilePath();
    			studentFile = new File(studentLoc);
    			marshaller = jaxb.createMarshaller();
    			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    			unmarshaller = jaxb.createUnmarshaller();
    		
    		//Get details from the localconfig object contained in the config.xml
    			local = config.getLocal();
    		
    		//Setup server and the input and output streams associated with the client
    			server = new ServerSocket(local.getPort());
    			socket = server.accept();

    //TODO
    		//Acknowledge connection and unmarshal the student	
    			System.out.println("Client Connection Received");
    			output = socket.getOutputStream();
    			student = (Student)unmarshaller.unmarshal(studentFile);
    			System.out.println("Printing Student Info:");
    			System.out.println(student);
    		
    		//re-marshal student and send the xml to client
    			marshaller.marshal(student, tempStudentFile);
    			fileIn = new FileInputStream(tempStudentLoc);
    			fileIn.read(bytes);
    			output.write(bytes);
    			output.flush();
    			System.out.println("Student info sent");
    		
    		//Close the connection
    			fileIn.close();
    			socket.close();
    			server.close();
    		}
    		catch(JAXBException e)
    		{
    			System.out.println(e);
    		}
    		catch(FileNotFoundException e)
    		{
    			System.out.println(e);
    		}
    		catch(IOException e)
    		{
    			System.out.println(e);
    		}
    	}
    	
    }
}
